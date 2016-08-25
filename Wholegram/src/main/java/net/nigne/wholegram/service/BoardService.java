package net.nigne.wholegram.service;

import java.util.List;

import org.springframework.stereotype.Service;

import net.nigne.wholegram.common.Criteria;
import net.nigne.wholegram.common.HashTagScrollCriteria;
import net.nigne.wholegram.domain.BoardVO;
import net.nigne.wholegram.domain.HeartVO;
import net.nigne.wholegram.domain.MemberVO;

@Service
public interface BoardService {
	public List<BoardVO> getList( List<HeartVO> hList, String user_id, int startNum, int pagePerBlock );
	public List<HeartVO> getHeartList(List<BoardVO> bList, List<HeartVO> hList);
	public List<BoardVO> get( List<MemberVO> mList );
	public List<List<BoardVO>> getbdList( List<MemberVO> mbList );
	public void heartCount(int board_num, int criteria);
	public int getHeart(int board_num);
	public int getTotalCount();
	public long getTime( int board_num );
	public int getBoardNum(BoardVO vo);
	

	public BoardVO getOne(BoardVO vo);
	public void BoardUP(BoardVO vo);
	public List<BoardVO> getUserLimitList(MemberVO vo);
	public List<BoardVO> getScrollList(Criteria cr);
	public int getUserCount(Criteria cr);
	public List<BoardVO> searchIterate(List<String> list);
	public int searchCount(List<String> list);
	public List<BoardVO> SearchScrollIterate(HashTagScrollCriteria list);
	public String getThunmnail(int board_num);
	public BoardVO boardList( int board_num );
	public void report( String user_id, int board_num );
	public void reportCount( int board_num );
	public List<BoardVO> getReportList();
	public void deleteAll( int board_num );
}
