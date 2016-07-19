package net.nigne.wholegram.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import net.nigne.wholegram.domain.MemberVO;
import net.nigne.wholegram.persistance.SignDAO;

@Service
public class SignServiceImpl implements SignService {
	
	@Inject
	private SignDAO dao;
	
	/*회원 정보 등록*/
	@Override
	public void insert(MemberVO vo) {
		dao.insert(vo);
	}

	@Override
	public List<MemberVO> selectAll() {
		return dao.selectAll();
	}

	/*Id중복 체크*/
	@Override
	public int checkId(String id) {
		return dao.checkId(id);
	}

	/*Email중복 체크*/
	@Override
	public int checkEmail(String email) {
		return dao.checkEmail(email);
	}
}
