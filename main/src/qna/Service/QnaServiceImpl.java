package qna.Service;

import java.io.Serializable;
import java.util.List;


import dto.PagingBean;
import qna.dto.QnaDto;

public interface QnaServiceImpl extends Serializable {

	public List<QnaDto> getQnaList();
	public boolean writeQnaBbs(QnaDto dto);
	public List<QnaDto> getQnaPagingList(PagingBean paging, String searchWord, int search);
	
}
