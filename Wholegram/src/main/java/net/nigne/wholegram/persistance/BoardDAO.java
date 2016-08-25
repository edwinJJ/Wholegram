package net.nigne.wholegram.persistance;

import java.util.List;

import net.nigne.wholegram.common.Criteria;
import net.nigne.wholegram.common.HashTagScrollCriteria;
import net.nigne.wholegram.domain.BoardVO;
import net.nigne.wholegram.domain.MemberVO;

public interface BoardDAO {
	public List<BoardVO> getList( String user_id, int startNum, int pagePerBlock );
	public List<BoardVO> get( List<MemberVO> mList );
	public void heartCount(int board_num, int criteria);
	public List<List<BoardVO>> getbdList( List<MemberVO> mbList );
	public int getHeart(int board_num);
	public int getTotalCount();
	public long getTime( int board_num );
	public void BoardUP(BoardVO vo);
	public List<BoardVO> getUserLimitList(MemberVO vo);
	public List<BoardVO> getScrollList(Criteria cr);
	public int getUserCount(Criteria cr);
	public List<BoardVO> searchIterate(List<String> list);
	public int searchCount(List<String> list);
	public List<BoardVO> SearchScrollIterate(HashTagScrollCriteria list);
	public BoardVO getOne(BoardVO vo);
	public String getThunmnail(int board_num);
	public int getBoardNum(BoardVO vo);
	public BoardVO boardList( int board_num );
	public void report( String user_id, int board_num );
	public void reportCount( int board_num );
	public List<BoardVO> getReportList();
	public void deleteAll( int board_num );
}
