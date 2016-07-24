package net.nigne.wholegram.persistance;

import java.util.List;

import net.nigne.wholegram.domain.MemberVO;

public interface MemberDAO {
	public int compare( MemberVO vo_chk );
	public MemberVO MemInfo(String user_id);
	public int compareId(String id);
	public int compareEmail(String email);
	public void updateUser(MemberVO vo);
	public String checkPasswd(int mem_no);
	public void updatePasswd(MemberVO vo);
	public List<MemberVO> getFollowinguser_Profile(List<String> user_ids);
}
