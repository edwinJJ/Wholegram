package net.nigne.wholegram.persistance;

import java.util.List;

import net.nigne.wholegram.domain.BoardVO;
import net.nigne.wholegram.domain.MemberVO;

public interface SearchDAO {
	
	public List<MemberVO> getSearch(String idx);
	public int getHashSearchCount (String idx);
	public List<BoardVO> getHashSearch(String idx);
}
