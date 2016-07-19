package net.nigne.wholegram.service;

import java.util.List;

import net.nigne.wholegram.domain.MemberVO;

public interface SignService {
	public void insert(MemberVO vo);
	public List<MemberVO> selectAll();
	public int checkId(String id);
	public int checkEmail(String email);
}
