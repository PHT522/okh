package qna;

import java.io.Serializable;
import java.util.List;

import qna.PagingBean;




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
	
	public String[] getTagName(String tagname);
	public boolean checkcomment(int seq);
	public List<QnaDto> getqnaBbssortPagingList(PagingBean paging, String whatsort);
	
	public int getSeq();
	public void likecountplus(int seq) ;
	public void dislikecount(int seq);
	public boolean delete(int seq);
	public void readcountplus(int seq);
	public void commentcountplus(int seq) ;
	public void commentcountminus(int seq); 
}
