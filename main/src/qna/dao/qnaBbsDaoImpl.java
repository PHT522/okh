package qna.dao;

import java.util.List;

import dto.PagingBean;
import qna.dto.QnaDto;

public interface qnaBbsDaoImpl {

	public List<QnaDto> getQnaList();
	
	public boolean writeQnaBbs(QnaDto dto);
	
	public List<QnaDto> getQnaPagingList(PagingBean paging, String searchWord, int search);
	
}
