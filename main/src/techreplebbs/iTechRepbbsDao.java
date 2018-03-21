package techreplebbs;

import java.util.List;




public interface iTechRepbbsDao {
	public List<TechRepbbsDto> getRepBbsList(int seq);
	public boolean writeBbs(TechRepbbsDto bbs);
	
	public boolean repupdate(int seq, String content);
	public boolean repdelete(int seq);
}
