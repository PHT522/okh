package qna;

import java.util.List;

public interface QnaAnswerDaoImpl {

	public boolean writeAnswer(QnaAnswerDto dto, int seq);
	
	public List<QnaAnswerDto> getCommentList();
	
}
