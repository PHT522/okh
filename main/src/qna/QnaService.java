package qna;

import java.util.List;



public class QnaService implements QnaServiceImpl {

	
	private static QnaService qnaService = new QnaService();	
	
	public QnaBbsDaoImpl qnadao;	
	public QnaAnswerDaoImpl qnaanswerdao;
	
	private QnaService() {
		qnadao = new QnaBbsDao();		
		qnaanswerdao = new QnaAnswerDao();
	}	
	public static QnaService getInstance() {
		return qnaService;
	}	
	
	
	@Override
	public List<QnaDto> getQnaPagingList(PagingBean paging, String searchWord, int search) {
		return qnadao.getQnaPagingList(paging, searchWord, search);
	}
	@Override
	public List<QnaDto> getQnaList() {
		return qnadao.getQnaList();
	}
	@Override
	public boolean writeQnaBbs(QnaDto dto) {		
		return qnadao.writeQnaBbs(dto);
	}
	@Override
	public List<QnaDto> getBbsPagingList(PagingBean paging) {		
		return qnadao.getBbsPagingList(paging);
	}
	@Override
	public QnaDto getBbs(int seq) {
		return qnadao.getBbs(seq);
	}
	@Override
	public String RemoveHTMLTag(String changeStr) {
		return qnadao.RemoveHTMLTag(changeStr);
	}
	@Override
	public boolean qnaupdate(QnaDto dto) {
		return qnadao.qnaupdate(dto);
	}
	@Override
	public boolean answer(int seq, QnaDto dto) {
		return qnadao.answer(seq, dto);
	}
	@Override
	public String[] getTagName(String tagname) {		
		return qnadao.getTagName(tagname);
	}
	
	
	@Override
	public List<QnaDto> getqnaBbssortPagingList(PagingBean paging, String whatsort) {
		return qnadao.getqnaBbssortPagingList(paging, whatsort);
	}

	@Override
	public boolean checkcomment(int seq) {		
		return qnadao.checkcomment(seq);
	}
	@Override
	public boolean writeAnswer(QnaDto dto, int seq) {
		return qnaanswerdao.writeAnswer(dto, seq);
	}
	@Override
	public int getSeq() {		
		return qnaanswerdao.getSeq();
	}
	@Override
	public void likecountplus(int seq) {
		qnadao.likecountplus(seq);
		
	}
	@Override
	public void dislikecount(int seq) {
		qnadao.dislikecount(seq);
		
	}
	@Override
	public void readcountplus(int seq) {
		qnadao.readcountplus(seq);
		
	}
	@Override
	public boolean delete(int seq) {
		return qnadao.delete(seq);
	}
	@Override
	public void commentcountplus(int seq) {
		qnadao.commentcountplus(seq);
		
	}
	@Override
	public void commentcountminus(int seq) {
		qnadao.commentcountminus(seq);
		
	}
	
	
	
	
	
	
	
	
	
}
