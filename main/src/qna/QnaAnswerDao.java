package qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import db.DBClose;
import db.DBConnection;

//QnaAnswerDto(seq, id, content, wdate, child, del, likecount)
public class QnaAnswerDao implements QnaAnswerDaoImpl {

	@Override
	public boolean writeAnswer(QnaAnswerDto dto, int seq) {
		
		String sql2 = " UPDATE QNA "
				+ " SET DEPTH = DEPTH+1 "
				+ " WHERE SEQ = (SELECT CHILD FROM QNAANSWER WHERE CHILD=? )";
		
		
		
		String sql1 = " INSERT INTO QNAANSWER "
				+ " (SEQ, ID, "
				+ " COMMENT_NUM,"
				+ " CONTENT, WDATE, "
				+ " CHILD, DEL, LIKECOUNT)"
				+ " VALUES"
				+ " (SEQ_QNAANSWER.NEXTVAL, ?,"
				+ " (SELECT DEPTH FROM QNA WHERE SEQ=? )+1 "
				+ " , ?, SYSDATE, "
				+ " ?, 0, 0)";
	
		Connection conn = null;
		PreparedStatement psmt = null;
		
		System.out.println(dto.toString());
		int count = 0;
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			System.out.println("1/6 writeAnswer Success");
			
			psmt = conn.prepareStatement(sql1);
			System.out.println("2/6 writeAnswer Success");
			psmt.setString(1, dto.getId());
			psmt.setInt(2, seq);	
			psmt.setString(3, dto.getContent());
			psmt.setInt(4, dto.getChild());		
				
			count = psmt.executeUpdate();
			System.out.println("3/6 writeAnswer Success");
			
			psmt.clearParameters();
			
			psmt = conn.prepareStatement(sql2);
			psmt.setInt(1, dto.getChild());
			System.out.println("4/6 writeAnswer Success");	
			
			count = psmt.executeUpdate();
			conn.commit();
			System.out.println("5/6 writeAnswer Success");
			
			
		} catch (SQLException e) {
			System.out.println("writeAnswer Fail");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			DBClose.close(psmt, conn, null);
			System.out.println("6/6 writeAnswer Success");
		}		
		
		return count>0;
	}

	@Override
	public List<QnaAnswerDto> getCommentList() {
		
		String sql = " SELECT SEQ, ID, CONTENT, WDATE, "
				+ " CHILD, DEL, LIKECOUNT"
				+ " FROM QNAANSWER"
				+ " ";
		
		
		return null;
	}

	
	

}
