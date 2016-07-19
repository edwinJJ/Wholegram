package net.nigne.wholegram.service;

import net.nigne.wholegram.domain.MemberVO;

public interface MemberService {
	public int compare( MemberVO vo_chk );
	public MemberVO MemInfo(String user_id);
	public int compareId(String id);
	public int compareEmail(String email);
	public void updateUser(MemberVO vo);
	public String checkPasswd(int mem_no);
	public int comparePasswd(String passwd_real, String passwd);
	public void updatePasswd(MemberVO vo);
}
