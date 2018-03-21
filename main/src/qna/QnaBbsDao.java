package qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import db.DBClose;
import db.DBConnection;
import qna.PagingBean;
import qna.PagingUtil;




public class QnaBbsDao implements QnaBbsDaoImpl {

	/*private static QnaBbsDao qNaDao = new QnaBbsDao();
	
	private QnaBbsDao() {}
	
	public static QnaBbsDao getInstance() {
		return qNaDao;
	}*/
	
	
	@Override
	public boolean checkcomment(int seq) {
		String sql = " SELECT ANSWERCOUNT FROM QNA "
				+ " WHERE SEQ=? ";
		Connection conn=null;
		PreparedStatement psmt=null;
		ResultSet rs=null;
		int commentcount=0;
		try {
			conn = DBConnection.getConnection();	
			psmt=conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			rs = psmt.executeQuery();
			System.out.println("2/6 checkcomment Success");
			
			rs.next();
			commentcount = rs.getInt(1);	// row의 총 갯수
			
		} catch (SQLException e) {
			System.out.println(" dislikecount fail");	
			e.printStackTrace();
		}finally{
			
			DBClose.close(psmt, conn, rs);	
		}		
		return commentcount>0?true:false;
	}
	
	@Override
	public String[] getTagName(String tagname) {
		StringTokenizer st= new StringTokenizer(tagname, "-");
		
		int len=st.countTokens();	//split.length
		System.out.println(len); 
		String[] tagnames=new String[len];
		for (int i = 0; i < len; i++) {
			tagnames[i]=st.nextToken();
		}
		
		return tagnames;
	}
	
	@Override
	public boolean answer(int seq, QnaDto dto) {
		int count = 0;
		// update : 기존의 댓글들을 한칸(STEP)씩 진행시키는 작업
		String sql1 = " UPDATE QNA "
				+ " SET STEP=STEP+1 "
				+ " WHERE REF=(SELECT REF FROM QNA WHERE SEQ=?) "
				+ "	  AND STEP > (SELECT STEP FROM QNA WHERE SEQ=?) ";
		
		String sql = " INSERT INTO QNA"
				+ " (SEQ, ID, REF, STEP, DEPTH, "
				+ " TITLE, CONTENT, TAG, WDATE, PARENT,"
				+ " DEL, READCOUNT, FAVOR, LVPOINT, ANSWERCOUNT) "
				+ " VALUES"
				+ " (SEQ_QNA.NEXTVAL, ?, (SELECT NVL(MAX(REF),0)+1 FROM QNA), 0, 0, "
				+ " ?, ?, ?, SYSDATE, 0, "
				+ " 0, 0, 0, 0, 0) ";
		
		String sql2 = " INSERT INTO QNA "
				+ " (SEQ, ID, REF, STEP, DEPTH, "
				+ " TITLE, CONTENT, TAG, WDATE, PARENT, DEL, "
				+ " READCOUNT, FAVOR, LVPOINT, ANSWERCOUNT) "
				+ " VALUES (SEQ_QNA.NEXTVAL, ?, "
				+ "		(SELECT REF FROM QNA WHERE SEQ=? ), "
				+ "		(SELECT STEP FROM QNA WHERE SEQ=? )+1, "
				+ " 	(SELECT DEPTH FROM QNA WHERE SEQ=?)+1, "
				+ "		 ?, ?, ?, SYSDATE, ?, 0, "
				+ "		0, 0, 0, (SELECT NVA(MAX(ANSWERCOUNT),0)+1 FROM QNA WHERE SEQ=?)";
		
		Connection conn = null;		
		PreparedStatement psmt = null;
		
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			System.out.println("1/6 answer Success");
			
			psmt = conn.prepareStatement(sql1);
			psmt.setInt(1, seq);
			psmt.setInt(2, seq);
			System.out.println("2/6 answer Success");
			
			count = psmt.executeUpdate();
			System.out.println("3/6 answer Success");
			
			psmt.clearParameters();
			
			psmt = conn.prepareStatement(sql2);
			psmt.setString(1, dto.getId());
			psmt.setInt(2, seq);
			psmt.setInt(3, seq);
			psmt.setInt(4, seq);
			psmt.setString(5, dto.getTitle());// 부모글의 타이틀 가져옴
			psmt.setString(6, dto.getContent());
			psmt.setString(7, dto.getTagname());
			psmt.setInt(8, seq);	// parent를 부모의 seq로
			psmt.setInt(9, seq);
			System.out.println("4/6 answer Success");
			System.out.println("dto = " + dto.toString());
			count = psmt.executeUpdate();
			conn.commit();
			System.out.println("5/6 answer Success");
			
		} catch (SQLException e) {
			System.out.println("answer fail");			
			try {
				conn.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}			
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			DBClose.close(psmt, conn, null);
			System.out.println("6/6 answer Success");	
		}
		
		
		return count>0;
	}
	
	
	public List<QnaDto> getqnaBbssortPagingList(PagingBean paging, String whatsort){
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<QnaDto> qnalist = new ArrayList<QnaDto>();
		String sWord = "";
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getqnaBbssortPagingList Success");
			
			// 글의 총수
			String totalSql = " SELECT COUNT(SEQ) FROM QNA WHERE DEL=0 ";
			psmt = conn.prepareStatement(totalSql);
			rs = psmt.executeQuery();
			System.out.println("2/6 getqnaBbssortPagingList Success");
			
			int totalCount = 0;
			rs.next();
			totalCount = rs.getInt(1);	// row의 총 갯수
			
			paging.setTotalCount(totalCount);
			paging = PagingUtil.setPagingInfo(paging);
			
			psmt.close();
			rs.close();
			if(whatsort.equals("wdate")) {	// 날짜
				sWord = " SEQ ";
			}else if(whatsort.equals("likecount")) {	// ID
				sWord = " LIKECOUNT ";
			}else if(whatsort.equals("contentcount")) {	// ID
				sWord = " COMMENTCOUNT ";
			}
			else if(whatsort.equals("scrapcount")) {	// ID
				sWord = " SCRAPCOUNT ";
			}
			else if(whatsort.equals("readcount")) {	// ID
				sWord = " READCOUNT ";
			}
			
			// row를 취득
			String sql = " SELECT * FROM "
						+ " (SELECT * FROM (SELECT * FROM QNA ORDER BY "+ sWord +" ASC)  "
						+ "  WHERE ROWNUM <= " + paging.getStartNum() + " AND DEL=0  ORDER BY "+ sWord +" DESC) "
						+ " WHERE ROWNUM <= " + paging.getCountPerPage() + " AND DEL=0 ";
			
			System.out.println("paging.getStartNum() = " + paging.getStartNum());
			
			psmt = conn.prepareStatement(sql);
			System.out.println("3/6 getqnaBbssortPagingList Success");
			
			rs = psmt.executeQuery();
			System.out.println("4/6 getqnaBbssortPagingList Success");
			
			while(rs.next()) {
				QnaDto dto = new QnaDto(rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getInt(7),
						rs.getInt(8),
						rs.getInt(9),
						rs.getString(10),
						rs.getString(11),
						rs.getInt(12),
						rs.getInt(13)
						);
						// seq, id, title, content, wdate, del, readcount, likecount, scrapcount)
				qnalist.add(dto);				
			}
			System.out.println("5/6 getqnaBbssortPagingList Success");			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("getqnaBbssortPagingList Fail");
		} finally {
			DBClose.close(psmt, conn, rs);
			System.out.println("6/6 getqnaBbssortPagingList Success");	
		}
		
		return qnalist;
	}
	
	
	/*@Override
	public List<QnaDto> getQnaPagingList(PagingBean paging, String searchWord, int search) {
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<QnaDto> list = new ArrayList<QnaDto>();
		
		System.out.println("search = " + search);
		
		String sWord = "";		
		if(search == 0) {	// 제목
			sWord = " WHERE TITLE LIKE '%" + searchWord.trim() + "%' ";
		}else if(search == 1) {	// 작성자
			sWord = " WHERE ID='" + searchWord.trim() + "' ";
		}else if(search == 2) {	// 내용
			sWord = " WHERE CONTENT LIKE '%" + searchWord.trim() + "%' ";
		}else if(search == 3) {	// tag
			sWord = " WHERE TAGNAME LIKE '%" + searchWord.trim() + "%' ";
		}else if(search == 4) {	// 태그네임
			sWord = " AND TAGNAME LIKE '%" + searchWord.trim() + "%' ";
		} 
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getBbsPagingList2 Success");
			
			// 글의 총수알아내고 짤라주는 작업
			String totalSql = " SELECT COUNT(SEQ) FROM QNA " + sWord;
			psmt = conn.prepareStatement(totalSql);
			rs = psmt.executeQuery();
			System.out.println("2/6 getBbsPagingList2 Success");
			
			int totalCount = 0;
			// 1나만 구하기때문에
			rs.next();
			totalCount = rs.getInt(1);	// row의 총 갯수
			
			paging.setTotalCount(totalCount);			// total count들어가고
			paging = PagingUtil.setPagingInfo(paging);	// 계산한수치가 나온다
			
			
			psmt.close();
			rs.close();
			
			// row를 취득
			// REF 정순으로 STEP을 역순으로
			// REF 역순 STEP 정순
			// 시작위치를 정해주고 갯수10개(paging.getCountPerPage())를 가져온다
			String sql = " SELECT * FROM "
						+ " (SELECT * FROM (SELECT * FROM QNA " + sWord + " ORDER BY REF ASC, STEP DESC)"
						+ "  WHERE ROWNUM <=" + paging.getStartNum() + " ORDER BY REF DESC, STEP ASC) "
						+ "WHERE ROWNUM <=" + paging.getCountPerPage();
			
			System.out.println("paging.getStartNum() = " + paging.getStartNum());
			
			psmt = conn.prepareStatement(sql);
			System.out.println("3/6 getBbsPagingList2 Success");
			
			rs = psmt.executeQuery();
			System.out.println("4/6 getBbsPagingList2 Success");
			
			while(rs.next()) {	// 하나의 자료만 가져오므로 if로 해도된다
				QnaDto dto = new QnaDto(
						rs.getInt(1),		//seq,
						rs.getString(2),	//id
						rs.getString(3),	//title
						rs.getString(4),	//tagname
						rs.getString(5),	//content
						rs.getString(6),	//wdate
						rs.getInt(7),		//del
						rs.getInt(8),		//readcount
						rs.getInt(9),		//likecount
						rs.getString(10),	//likeid
						rs.getString(11),	//dislikeid
						rs.getInt(12),		//commentcount
						rs.getInt(13));		//scrapcount
						
						
				list.add(dto);				
			}
			System.out.println("5/6 getBbsPagingList2 Success");			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("getBbsPagingList2 Fail");
		} finally {
			DBClose.close(psmt, conn, rs);
			System.out.println("6/6 getBbsPagingList2 Success");	
		}
		
		return list;
	}
	*/
	
	@Override
	public List<QnaDto> getQnaPagingList(PagingBean paging, String searchWord, int search) {
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		
		List<QnaDto> bbslist = new ArrayList<QnaDto>();
		
		System.out.println("search = " + search);
		
		String sWord = "";		
		if(search == 0) {	// 제목
			sWord = " AND TITLE LIKE '%" + searchWord.trim() + "%' ";
		}else if(search == 1) {	// ID
			sWord = " AND ID='" + searchWord.trim() + "' ";
		}else if(search == 2) {	// 내용
			sWord = " AND CONTENT LIKE '%" + searchWord.trim() + "%' ";
		} 
		else if(search == 3) {	// 태그네임
			sWord = " AND TAGNAME LIKE '%-" + searchWord.trim() + "-%' ";
		} 
		else if(search == 4) {	// 태그네임
			sWord = " AND TAGNAME LIKE '%" + searchWord.trim() + "%' ";
		} 
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getQnaPagingList Success");
			
			// 글의 총수
			String totalSql = " SELECT COUNT(SEQ) FROM QNA WHERE DEL=0 " + sWord;
			psmt = conn.prepareStatement(totalSql);
			rs = psmt.executeQuery();
			System.out.println("2/6 getQnaPagingList Success");
			
			int totalCount = 0;
			rs.next();
			totalCount = rs.getInt(1);	// row의 총 갯수
			
			paging.setTotalCount(totalCount);
			paging = PagingUtil.setPagingInfo(paging);
			
			
			psmt.close();
			rs.close();
			if(search == 0) {	// 제목
				sWord = " WHERE TITLE LIKE '%" + searchWord.trim() + "%' ";
			}else if(search == 1) {	// ID
				sWord = " WHERE ID='" + searchWord.trim() + "' ";
			}else if(search == 2) {	// 내용
				sWord = " WHERE CONTENT LIKE '%" + searchWord.trim() + "%' ";
			} 
			else if(search == 3) {	// 태그네임
				sWord = " WHERE TAGNAME LIKE '%-" + searchWord.trim() + "-%' ";
			} 
			else if(search == 4) {	// 태그네임
				sWord = " WHERE TAGNAME LIKE '%" + searchWord.trim() + "%' ";
			} 
			// row를 취득
			String sql = " SELECT * FROM "
						+ " (SELECT * FROM (SELECT * FROM QNA " + sWord + " ORDER BY SEQ ASC) "
						+ "  WHERE ROWNUM <= " + paging.getStartNum() + " AND DEL=0 ORDER BY SEQ DESC) "
						+ " WHERE ROWNUM <= " + paging.getCountPerPage() + " AND DEL=0 ";
			
			System.out.println("paging.getStartNum() = " + paging.getStartNum());
			
			psmt = conn.prepareStatement(sql);
			System.out.println("3/6 getQnaPagingList Success");
			
			rs = psmt.executeQuery();
			System.out.println("4/6 getQnaPagingList Success");
			
			while(rs.next()) {
				QnaDto dto = new QnaDto(rs.getInt(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getInt(7),
						rs.getInt(8),
						rs.getInt(9),
						rs.getString(10),
						rs.getString(11),
						rs.getInt(12),
						rs.getInt(13)
						);
						// seq, id, title, content, wdate, del, readcount, likecount, scrapcount)
				bbslist.add(dto);				
			}
			System.out.println("5/6 getQnaPagingList Success");			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("getQnaPagingList Fail");
		} finally {
			DBClose.close(psmt, conn, rs);
			System.out.println("6/6 getQnaPagingList Success");	
		}
		
		return bbslist;
	}
	
	@Override
	public boolean qnaupdate(QnaDto dto) {		
		String sql = " UPDATE QNA "
				+ " SET TITLE = ?, CONTENT = ?, TAG = ?"
				+ " WHERE SEQ = ? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
				
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 qnaupdate Success");
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getContent());
			psmt.setString(3, dto.getTagname());
			psmt.setInt(4, dto.getSeq());
			System.out.println("2/6 qnaupdate Success");
			
			count = psmt.executeUpdate();
			System.out.println("count = " + count);
			System.out.println("3/6 qnaupdate Success");
		} catch (SQLException e) {
			System.out.println("qnaupdate Fail");
			e.printStackTrace();
		} finally {
			System.out.println("4/6 qnaupdate Success");
			DBClose.close(psmt, conn, null);		
		}
		return count>0?true:false;
	}
	
	@Override
	public void readcount(int seq) {
		String sql = " UPDATE QNA "
				+ " SET READCOUNT = READCOUNT+1 "
				+ " WHERE SEQ=?";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 readcount Success");
			
			psmt = conn.prepareStatement(sql);			
			psmt.setInt(1, seq);
			System.out.println("2/6 readcount Success");
			
			psmt.executeUpdate();
			System.out.println("3/6 readcount Success");					
			
		} catch (SQLException e) {
			System.out.println("readcount Fail");
			e.printStackTrace();
		}finally {
			System.out.println("4/6 readcount Success");
			DBClose.close(psmt, conn, null);
		}	
	}
	
	@Override
	public QnaDto getBbs(int seq) {
		
		String sql = " SELECT SEQ, ID, TITLE, TAGNAME, CONTENT, WDATE, DEL,"
				+ " READCOUNT, LIKECOUNT, LIKEID, DISLIKEID, COMMENTCOUNT, SCRAPCOUNT "
				+ " FROM QNA"
				+ " WHERE SEQ=? ";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		QnaDto dto = null;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getBbs Success");
			
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			System.out.println("2/6 getBbs Success");
			
			rs = psmt.executeQuery();
			System.out.println("3/6 getBbs Success");
			
			if(rs.next()) {
				dto = new QnaDto(
						rs.getInt(1),		//seq,
						rs.getString(2),	//id
						rs.getString(3),	//title
						rs.getString(4),	//tagname
						rs.getString(5),	//content
						rs.getString(6),	//wdate
						rs.getInt(7),		//del
						rs.getInt(8),		//readcount
						rs.getInt(9),		//likecount
						rs.getString(10),	//likeid
						rs.getString(11),	//dislikeid
						rs.getInt(12),		//commentcount
						rs.getInt(13));		//scrapcount
				// 조회수 올리는 함수
				readcount(dto.getSeq());
			}
			System.out.println("4/6 getBbs Success");
			
		} catch (SQLException e) {
			System.out.println("getBbs Fail");
			e.printStackTrace();
		}finally {
			System.out.println("5/6 getBbs Success");
			DBClose.close(psmt, conn, rs);			
		}		
		return dto;
		
	}
	
	@Override
	public List<QnaDto> getBbsPagingList(PagingBean paging) {
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<QnaDto> bbslist = new ArrayList<QnaDto>();
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getBbsPagingList Success");
			
			// 글의 총수알고 짤라주는 작업
			String totalSql = " SELECT COUNT(SEQ) FROM QNA ";
			psmt = conn.prepareStatement(totalSql);
			rs = psmt.executeQuery();	
			System.out.println("2/6 getBbsPagingList Success");
			
			int totalCount = 0;
			//1나만 구하므로
			rs.next();
			totalCount = rs.getInt(1); 	// row의 총 갯수
			
			paging.setTotalCount(totalCount);		// total count들어가고			
			paging = PagingUtil.setPagingInfo(paging);
			
			psmt.close();
			rs.close();
			
			// row를 취득부분 댓글과 같이 정순으로 가져온다음 엎어야한다
			// REF 정순으로 STEP을 역순으로
			// REF 역순 STEP 정순
			// 시작위치를 정해주고 갯수10개(paging.getCountPerPage())를 가져온다
			String sql = " SELECT * FROM "
					+ " (SELECT * FROM (SELECT*FROM QNA ORDER BY REF ASC, STEP DESC)"
					+ " WHERE ROWNUM <=" +paging.getStartNum() + " ORDER BY REF DESC, STEP ASC) "
					+ " WHERE ROWNUM <=" + paging.getCountPerPage();
			psmt = conn.prepareStatement(sql);
			System.out.println("3/6 getBbsPagingList Success");
			
			rs = psmt.executeQuery();
			System.out.println("4/6 getBbsPagingList Success");
			
			while (rs.next()) {							// 하나만 갖고오므로 while말고 조건문if한다
				
				QnaDto dto = new QnaDto(
						rs.getInt(1),		//seq,
						rs.getString(2),	//id
						rs.getString(3),	//title
						rs.getString(4),	//tagname
						rs.getString(5),	//content
						rs.getString(6),	//wdate
						rs.getInt(7),		//del
						rs.getInt(8),		//readcount
						rs.getInt(9),		//likecount
						rs.getString(10),	//likeid
						rs.getString(11),	//dislikeid
						rs.getInt(12),		//commentcount
						rs.getInt(13));		//scrapcount
				
				bbslist.add(dto);				
			}	
			System.out.println("5/6 getBbsPagingList Success");
			
		} catch (SQLException e) {
			System.out.println("getBbsPagingList Fail");
			e.printStackTrace();
		}finally {
			DBClose.close(psmt, conn, rs);
			System.out.println("6/6 getBbsPagingList Success");
		}
		
		return bbslist;
	}
	
	
	/*@Override
	public List<QnaDto> getBbsPagingList(PagingBean paging) {
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<QnaDto> bbslist = new ArrayList<QnaDto>();
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getBbsPagingList Success");
			
			// 글의 총수알고 짤라주는 작업
			String totalSql = " SELECT COUNT(SEQ) FROM QNA ";
			psmt = conn.prepareStatement(totalSql);
			rs = psmt.executeQuery();	
			System.out.println("2/6 getBbsPagingList Success");
			
			int totalCount = 0;
			//1나만 구하므로
			rs.next();
			totalCount = rs.getInt(1); 	// row의 총 갯수
			
			paging.setTotalCount(totalCount);		// total count들어가고
			paging = qna.PagingUtil.setPagingInfo(paging);	// 계산한수치가 나온다
			
			psmt.close();
			rs.close();
			
			// row를 취득부분 댓글과 같이 정순으로 가져온다음 엎어야한다
			// REF 정순으로 STEP을 역순으로
			// REF 역순 STEP 정순
			// 시작위치를 정해주고 갯수10개(paging.getCountPerPage())를 가져온다
			String sql = " SELECT * FROM "
					+ " (SELECT * FROM (SELECT*FROM QNA ORDER BY REF ASC, STEP DESC)"
					+ " WHERE ROWNUM <=" +paging.getStartNum() + " ORDER BY REF DESC, STEP ASC) "
					+ " WHERE ROWNUM <=" + paging.getCountPerPage();
			psmt = conn.prepareStatement(sql);
			System.out.println("3/6 getBbsPagingList Success");
			
			rs = psmt.executeQuery();
			System.out.println("4/6 getBbsPagingList Success");
			
			while (rs.next()) {							// 하나만 갖고오므로 while말고 조건문if한다
				
				QnaDto dto = new QnaDto(
						rs.getInt(1),		//seq,
						rs.getString(2),	//id
						rs.getString(3),	//title
						rs.getString(4),	//tagname
						rs.getString(5),	//content
						rs.getString(6),	//wdate
						rs.getInt(7),		//del
						rs.getInt(8),		//readcount
						rs.getInt(9),		//likecount
						rs.getString(10),	//likeid
						rs.getString(11),	//dislikeid
						rs.getInt(12),		//commentcount
						rs.getInt(13));		//scrapcount
				
				bbslist.add(dto);				
			}	
			System.out.println("5/6 getBbsPagingList Success");
			
		} catch (SQLException e) {
			System.out.println("getBbsPagingList Fail");
			e.printStackTrace();
		}finally {
			DBClose.close(psmt, conn, rs);
			System.out.println("6/6 getBbsPagingList Success");
		}
		
		return bbslist;
	}
	*/
	
	
	@Override
	public boolean writeQnaBbs(QnaDto dto) {
		
		/*String sql = " INSERT INTO QNA"
				+ " (SEQ, ID, REF, STEP, DEPTH, "
				+ " TITLE, CONTENT, TAG, WDATE, PARENT,"
				+ " DEL, READCOUNT, FAVOR, LVPOINT, ANSWERCOUNT, "
				+ " FAVORID, SCRAPCOUNT ) "
				+ " VALUES"
				+ " (SEQ_QNA.NEXTVAL, ?, (SELECT NVL(MAX(REF),0)+1 FROM QNA), 0, 0, "
				+ " ?, ?, ?, SYSDATE, 0, "
				+ " 0, 0, 0, 0, 0,"
				+ " ?, 0) ";*/
		
		String sql = " INSERT INTO QNA "
				+ " (SEQ, ID, TITLE, TAGNAME, CONTENT, WDATE, DEL, "
				+ " READCOUNT, LIKECOUNT, LIKEID, DISLIKEID, "
				+ " COMMENTCOUNT, SCRAPCOUNT) "
				+ " VALUES"
				+ " (SEQ_QNA.NEXTVAL, ?, ?, ?, ?, SYSDATE, 0, "
				+ " 0, 0, ?, ?, "
				+ " 0, 0) ";
				
		
		Connection conn = null;
		PreparedStatement psmt = null;
		
		int count = 0;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 writeQnaBbs Success");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("2/6 writeQnaBbs Success");
			psmt.setString(1, dto.getId());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getTagname());
			psmt.setString(4,dto.getContent());
			psmt.setString(5, "-admin");
			psmt.setString(6, "-admin");
					
			count = psmt.executeUpdate();
			System.out.println("3/6 writeQnaBbs Success");
			
		} catch (SQLException e) {
			System.out.println("writeQnaBbs Fail");
			e.printStackTrace();
		}finally {
			System.out.println("4/6 writeQnaBbs Success");
			DBClose.close(psmt, conn, null);
		}
					
		return count>0?true:false;
	}	
	

	@Override
	public List<QnaDto> getQnaList() {
		
		/*String sql = " SELECT SEQ, ID, TITLE, TAGNAME, CONTENT, WDATE, DEL,"
				+ " READCOUNT, LIKECOUNT, LIKEID, DISLIKEID, COMMENTCOUNT, SCRAPCOUNT "
				+ " FROM QNA "
				+ " ORDER BY REF DESC, STEP ASC";*/
		String sql = " SELECT * FROM  "
				+ " (SELECT * FROM (SELECT * FROM QNA ORDER BY SEQ ASC) "
				+ "  WHERE ROWNUM >= 1 AND DEL=0 ORDER BY SEQ DESC) "
				+ " WHERE ROWNUM <= 6 AND DEL=0 ";
		
		List<QnaDto> list = new ArrayList<QnaDto>();
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBConnection.getConnection();
			System.out.println("1/6 getQnaList Success");
			
			psmt = conn.prepareStatement(sql);
			System.out.println("2/6 getQnaList Success");
			
			rs = psmt.executeQuery();
			System.out.println("3/6 getQnaList Success");
			
			while(rs.next()) {
				QnaDto dto = new QnaDto(
						rs.getInt(1),		//seq,
						rs.getString(2),	//id
						rs.getString(3),	//title
						rs.getString(4),	//tagname
						rs.getString(5),	//content
						rs.getString(6),	//wdate
						rs.getInt(7),		//del
						rs.getInt(8),		//readcount
						rs.getInt(9),		//likecount
						rs.getString(10),	//likeid
						rs.getString(11),	//dislikeid
						rs.getInt(12),		//commentcount
						rs.getInt(13));		//scrapcount
				list.add(dto);
				
			}
			System.out.println("4/6 getQnaList Success");
			
		} catch (SQLException e) {
			System.out.println("getQnaList Fail");
			e.printStackTrace();
		} finally {
			DBClose.close(psmt, conn, rs);
			System.out.println("5/6 getQnaList Success");
		}
		return list;
	}

	@Override
	public String RemoveHTMLTag(String changeStr){
	    if(changeStr != null && !changeStr.equals("")){
	        changeStr = changeStr.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
	    }else{
	        changeStr = "";
	    }
	    return changeStr;

	}

	@Override
	public void likecountplus(int seq) {
		String sql = " UPDATE QNA SET LIKECOUNT=LIKECOUNT+1 "
				+ " WHERE SEQ=? ";
		Connection conn=null;
		PreparedStatement psmt=null;
		ResultSet rs=null;
		System.out.println(sql);
		try {
			conn = DBConnection.getConnection();	
			psmt=conn.prepareStatement(sql);
			System.out.println("1/6 countplus Success");	
			psmt.setInt(1, seq);
			System.out.println(sql);
			System.out.println("2/6 countplus Success");	
			psmt.executeUpdate();			
			
		} catch (SQLException e) {
			System.out.println("countplus fail");	
			e.printStackTrace();
		}finally{
			DBClose.close(psmt, conn, rs);	
		}		
				
	}

	@Override
	public void dislikecount(int seq) {
		String sql = " UPDATE QNA SET LIKECOUNT=LIKECOUNT-1 "
				+ " WHERE SEQ=? ";
		Connection conn=null;
		PreparedStatement psmt=null;
		ResultSet rs=null;
		
		try {
			conn = DBConnection.getConnection();	
			psmt=conn.prepareStatement(sql);
			psmt.setInt(1, seq);
			System.out.println("2/6 dislikecount Success");	
			psmt.executeUpdate();			
			
		} catch (SQLException e) {
			System.out.println(" dislikecount fail");	
			e.printStackTrace();
		}finally{
			
			DBClose.close(psmt, conn, rs);	
		}		
	}

	@Override
	public boolean delete(int seq) {
		String sql=" UPDATE QNA SET "
				+ " DEL=1"
				+ " WHERE SEQ=? ";
		
		int count = 0;
		Connection conn=null;
		PreparedStatement psmt=null;
		
		try {
			conn = DBConnection.getConnection();			
			psmt=conn.prepareStatement(sql);
			psmt.setInt(1, seq);			
			count = psmt.executeUpdate();
			
		} catch (SQLException e) {			
			e.printStackTrace();
		} finally{
			DBClose.close(psmt, conn, null);			
		}
				
		return count>0?true:false;
	}
	

	@Override
	public void readcountplus(int seq) {
		String sql = " UPDATE QNA SET READCOUNT=READCOUNT+1 "
				+ " WHERE SEQ=? ";
		Connection conn=null;
		PreparedStatement psmt=null;
		ResultSet rs=null;
		System.out.println(sql);
		try {
			conn = DBConnection.getConnection();	
			psmt=conn.prepareStatement(sql);
			System.out.println("1/6 readcountplus Success");	
			psmt.setInt(1, seq);
			System.out.println(sql);
			System.out.println("2/6 readcountplus Success");	
			psmt.executeUpdate();			
			
		} catch (SQLException e) {
			System.out.println("readcountplus fail");	
			e.printStackTrace();
		}finally{
			DBClose.close(psmt, conn, rs);	
		}		
	}

	@Override
	public void commentcountplus(int seq) {
		String sql = " UPDATE QNA SET COMMENTCOUNT=COMMENTCOUNT+1 "
				+ " WHERE SEQ=? ";
		Connection conn=null;
		PreparedStatement psmt=null;
		ResultSet rs=null;
		System.out.println(sql);
		try {
			conn = DBConnection.getConnection();	
			psmt=conn.prepareStatement(sql);
			System.out.println("1/6 commentcountplus Success");	
			psmt.setInt(1, seq);
			System.out.println(sql);
			System.out.println("2/6 commentcountplus Success");	
			psmt.executeUpdate();			
			
		} catch (SQLException e) {
			System.out.println("commentcountplus fail");	
			e.printStackTrace();
		}finally{
			DBClose.close(psmt, conn, rs);	
		}	
	}

	@Override
	public void commentcountminus(int seq) {
		String sql = " UPDATE QNA SET COMMENTCOUNT=COMMENTCOUNT-1 "
				+ " WHERE SEQ=? ";
		Connection conn=null;
		PreparedStatement psmt=null;
		ResultSet rs=null;
		try {
			conn = DBConnection.getConnection();	
			psmt=conn.prepareStatement(sql);
			System.out.println("1/6 commentcountminus Success");	
			psmt.setInt(1, seq);
			System.out.println(sql);
			System.out.println("2/6 commentcountminus Success");	
			psmt.executeUpdate();			
			
		} catch (SQLException e) {
			System.out.println("commentcountminus fail");	
			e.printStackTrace();
		}finally{
			DBClose.close(psmt, conn, rs);	
		}	
	}

	
}
