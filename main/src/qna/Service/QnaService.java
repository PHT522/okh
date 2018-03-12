package qna.Service;

import java.util.List;

import dto.PagingBean;
import qna.dao.qnaBbsDao;
import qna.dao.qnaBbsDaoImpl;
import qna.dto.QnaDto;

public class QnaService implements QnaServiceImpl {

	private static QnaService qnaService = new QnaService();
	
	public qnaBbsDaoImpl qnadao;	
	
	private QnaService() {
		qnadao = new qnaBbsDao();		
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
	
	
	
}
