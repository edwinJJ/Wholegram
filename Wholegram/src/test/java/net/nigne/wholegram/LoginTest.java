package net.nigne.wholegram;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.nigne.wholegram.domain.MemberVO;
import net.nigne.wholegram.persistance.MemberDAO;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"} )
public class LoginTest {
	@Inject
	private MemberDAO service;
	
	@Test
	public void test() {
		MemberVO vo = new MemberVO();
		vo.setUser_id( "ksj" );
		vo.setPasswd( "1234" );
		service.compare(vo);
	}

}
