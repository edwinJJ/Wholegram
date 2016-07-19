package net.nigne.wholegram.persistance;

import java.util.List;

import net.nigne.wholegram.domain.MemberVO;

public interface SignDAO {
	public void insert(MemberVO vo);
	public List<MemberVO> selectAll();
	public int checkId(String id);
	public int checkEmail(String email);
}
