package qna;

import java.io.Serializable;
import java.util.List;




public interface QnaServiceImpl extends Serializable {

	public List<QnaDto> getQnaList();
	public boolean writeQnaBbs(QnaDto dto);
	public List<QnaDto> getQnaPagingList(PagingBean paging, String searchWord, int search);
	public List<QnaDto> getBbsPagingList(PagingBean paging);
	public QnaDto getBbs(int seq);	
	public String RemoveHTMLTag(String changeStr);
	public boolean qnaupdate(QnaDto dto);	
	public boolean answer(int seq, QnaDto dto);
	
	//////////////////////////////////////////
	public boolean writeAnswer(QnaDto dto, int seq);
	

}
