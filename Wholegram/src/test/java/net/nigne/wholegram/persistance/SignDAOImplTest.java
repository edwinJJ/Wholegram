package net.nigne.wholegram.persistance;


import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.nigne.wholegram.domain.MemberVO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class SignDAOImplTest {
	@Inject
	private SignDAO dao;
	@Test
	public void selectAll() {
		dao.selectAll();
	}
//	@Test
//	public void setMember(){
//		MemberVO vo = new MemberVO();
//		vo.setUser_id("33");
//		vo.setUser_name("¿Ã∏ß");
//		vo.setPasswd("111");
//		vo.setInfo("1");
//		vo.setEmail("!1");
//		vo.setPhone("1111");
//		vo.setGender("M");
//		vo.setRecommand(1);
//		dao.insert(vo);
//	}
	@Test
	public void checkId(){
		MemberVO vo = new MemberVO();
		vo.setUser_id("33");
		System.out.println(dao.checkId("33"));
	}

	@Test
	public void checkEmail(){
		MemberVO vo = new MemberVO();
		vo.setEmail("asda@naver.com");
		System.out.println(dao.checkEmail("asda@naver.com"));
	}
}
