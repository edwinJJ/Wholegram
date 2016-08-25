package net.nigne.wholegram.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import net.nigne.wholegram.domain.BoardVO;
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
	public List<MemberVO> getFollowinguser_Profile(List<String> user_ids);
	public List<MemberVO> getNewPerson( String user_id );
	public List<MemberVO> getKnowablePerson( String user_id );
	public byte[] reSizeProfileImg(MultipartFile mpf);
	public void setDefaultProfileImage(String user_id);
	public List<MemberVO> getRandomUser( String user_id );
}
