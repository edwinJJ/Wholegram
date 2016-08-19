package net.nigne.wholegram.service;

import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.nigne.wholegram.common.Email;
import net.nigne.wholegram.domain.MemberVO;
import net.nigne.wholegram.persistance.MemberDAO;
import net.nigne.wholegram.persistance.SignDAO;

@Service
public class SignServiceImpl implements SignService {
	
	@Inject
	private SignDAO dao;
	
	@Inject
	private MemberDAO mdao;
	
	@Inject
	private Email email;
	
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

	/*Email 인증 보내기*/
	@Transactional
	@Override
	public String sendMail(String emailad1, String emailad2) {
		
		String emailaddress= emailad1 + "." + emailad2;						// 메일 '.'앞뒷부분 합치기
		String authstr = email.sendMail(email.proPerties(), emailaddress);  // 인증 메일 보내기      
		return authstr;
	}

	/*Email로 비밀번호 찾기*/
	@Transactional
	@Override
	public void sendFindMail(String emailaddress) {
		MemberVO vo = new MemberVO();
		String authstr = email.sendFindMail(email.proPerties(), emailaddress);	// 새로운 비밀번호 메일로 보내기
		
		mdao.updateLostPasswd(emailaddress, authstr);							// 이메일주소에 해당되는 유저Id의 비밀번호 변경
	}
}
