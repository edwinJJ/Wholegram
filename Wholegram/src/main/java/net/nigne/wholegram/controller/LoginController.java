package net.nigne.wholegram.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import net.nigne.wholegram.domain.MemberVO;
import net.nigne.wholegram.service.EncryptService;
import net.nigne.wholegram.service.LoginService;
import net.nigne.wholegram.service.MemberService;
import net.nigne.wholegram.common.Status;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/*")
public class LoginController {
	
	private static final int SUCCESS = 1;
	private static final int FAIL = 0;

	@Inject
	private MemberService service;
	
	@Inject
	private EncryptService encrypt;
	
	@Inject
	private LoginService loginService;
	
	/* Header의 user아이콘을 통해 user페이지로 넘어올 시 */  
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		ModelAndView mav = new ModelAndView();
		
		/* user페이지 이동 */
		if(user_id != null) {
			MemberVO vo = service.MemInfo(user_id);
			mav.setViewName("user");
			mav.addObject("vo", vo);
		} else {
			mav.setViewName("login");
		}
		return mav;
	}

	/* 최초 로그인을 통해 user페이지로 넘어갈 시 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginCheck(MemberVO vo_chk, HttpServletRequest request, HttpServletResponse response) throws IOException {

		/* pw 암호화 및 로그인 가능상태 설정 */
		vo_chk.setPasswd(encrypt.shaEncrypt(vo_chk.getPasswd()));
		Status loginStatus = loginService.LoginStatus(service.compare(vo_chk));

		ModelAndView mav = new ModelAndView();		
		
		/* 로그인 성공 */
		if (loginStatus.isSuccess()) {
			HttpSession session = request.getSession();
			session.setAttribute("user_id", vo_chk.getUser_id());
			MemberVO vo = service.MemInfo((String)session.getAttribute("user_id"));
			
			// 이동할 페이지, 사용자 정보 설정
			mav.setViewName("user");
			mav.addObject("vo", vo);
		} else { 
			/* 로그인 실패 */
			response.setCharacterEncoding("utf-8");
			PrintWriter writer = response.getWriter();
			
			//TODO - 데이터 리소스에서 불러서 사용할 것(xml 사용 / CDATA이용)
/*			writer.println("데이터 리소스에서 불러서 사용할 것");
			writer.flush();*/
			writer.println("<html><head><meta charset='utf-8' /><script>");
			writer.println("alert( '아이디와 비밀번호를 확인해주세요' )");
			writer.println("</script></head></html>");
			writer.flush();

			mav.setViewName("login");
		}
		return mav;
	}
}
