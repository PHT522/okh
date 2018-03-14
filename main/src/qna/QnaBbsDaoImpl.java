package qna;

import java.util.List;





public interface QnaBbsDaoImpl {

	public List<QnaDto> getQnaList();
	
	public boolean writeQnaBbs(QnaDto dto);
	
	public List<QnaDto> getBbsPagingList(PagingBean paging);
	
//	public List<QnaDto> getQnaPagingList(PagingBean paging, String searchWord, int search);
	
	public QnaDto getBbs(int seq);
	public void readcount(int seq);
		
	public boolean qnaupdate(QnaDto dto); //detail에서 update사용
	
	
	public String RemoveHTMLTag(String changeStr);	
	
	
}
