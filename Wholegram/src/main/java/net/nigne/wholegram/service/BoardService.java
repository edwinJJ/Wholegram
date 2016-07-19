package net.nigne.wholegram.service;

import java.util.List;

import org.springframework.stereotype.Service;

import net.nigne.wholegram.domain.BoardVO;
import net.nigne.wholegram.domain.HeartVO;

@Service
public interface BoardService {
	public List<BoardVO> getList(List<HeartVO> hList);
	public List<HeartVO> getHeartList(List<BoardVO> bList, List<HeartVO> hList);
	public BoardVO get( BoardVO vo );
	public void heartCount(int board_num, int criteria);
	public int getHeart(int board_num);
	public void BoardUP(BoardVO vo);
}
