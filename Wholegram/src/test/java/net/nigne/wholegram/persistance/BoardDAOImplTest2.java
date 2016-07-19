package net.nigne.wholegram.persistance;


import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.nigne.wholegram.domain.BoardVO;
import net.nigne.wholegram.domain.MemberVO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class BoardDAOImplTest2 {
	@Inject
	private BoardDAO dao;
	@Test
	public void selectAll() {
		BoardVO vo = new BoardVO();
		vo.setContent("111");
		vo.setMedia("22");
		vo.setUser_id("!1");
		vo.setPlace("22");
		vo.setMedia_type("1");
		vo.setTag("!!");
		dao.BoardUP(vo);
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
}
