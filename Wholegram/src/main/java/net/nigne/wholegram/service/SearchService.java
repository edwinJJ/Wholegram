package net.nigne.wholegram.service;

import java.util.List;

import net.nigne.wholegram.domain.BoardVO;
import net.nigne.wholegram.domain.MemberVO;

public interface SearchService {

	public List<MemberVO> getSearch(String idx);
	public int getHashSearchCount (String idx);
	public List<BoardVO> getHashSearch(String idx);
}
