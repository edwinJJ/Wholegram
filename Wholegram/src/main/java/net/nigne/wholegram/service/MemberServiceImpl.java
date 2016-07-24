package net.nigne.wholegram.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.nigne.wholegram.domain.MemberVO;
import net.nigne.wholegram.persistance.MemberDAO;

@Service
public class MemberServiceImpl implements MemberService {
	@Inject
	private MemberDAO dao;

	/*로그인 시, id/pw 비교*/
	@Override
	public int compare(MemberVO vo_chk) {
		return dao.compare( vo_chk );
	}

	/*user페이지 , 프로필 편집시 유저정보 가져옴*/
	@Override
	public MemberVO MemInfo(String user_id) {
		return dao.MemInfo(user_id);
	}

	/*유저 정보 업데이트시 Id중복 확인*/
	@Override
	public int compareId(String id) {
		return dao.compareId(id);
	}
	
	/*유저 정보 업데이트시 Email중복 확인*/
	@Override
	public int compareEmail(String email) {
		return dao.compareEmail(email);
	}
	
	/*유저 정보 업데이트*/
	@Override
	public void updateUser(MemberVO vo) {
		dao.updateUser(vo);
	}

	/*비밀번호 변경시 - 원래 비밀번호 가져오기*/
	@Override
	public String checkPasswd(int mem_no) {
		return dao.checkPasswd(mem_no);
	}

	/*비밀번호 변경시 - 원래 비밀번호와 이전 비밀번호 일치확인*/
	@Override
	public int comparePasswd(String passwd_real, String passwd) {
		int result;
		if(passwd_real.equals(passwd)) {
			result = 1;
		} else {
			result = 0;
		}
		return result;
	}

	/*비밀번호 변경*/
	@Override
	public void updatePasswd(MemberVO vo) {
		dao.updatePasswd(vo);
	}

	/*Following 하고있는 유저 정보(id, profile사진)을 가져온다*/
	@Override
	public List<MemberVO> getFollowinguser_Profile(List<String> user_ids) {
		return dao.getFollowinguser_Profile(user_ids);
	}
}
