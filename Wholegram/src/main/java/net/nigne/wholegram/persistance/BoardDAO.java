package net.nigne.wholegram.persistance;

import java.util.List;

import net.nigne.wholegram.domain.BoardVO;

public interface BoardDAO {
	public List<BoardVO> getList();
	public BoardVO get( BoardVO vo );
	public void heartCount(int board_num, int criteria);
	public int getHeart(int board_num);
	public int abc();
	public void BoardUP(BoardVO vo);
}
