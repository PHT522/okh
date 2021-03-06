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
	
	
	//////////////////////////////////////////////
	
	@Override
	public boolean writeAnswer(QnaDto dto, int seq) {
		return qnaanswerdao.writeAnswer(dto, seq);
	}
	
	
	
	
	
	
	
}
