package net.nigne.wholegram.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import net.nigne.wholegram.common.Criteria;
import net.nigne.wholegram.common.Status;
import net.nigne.wholegram.domain.BoardVO;
import net.nigne.wholegram.domain.MemberVO;
import net.nigne.wholegram.service.BoardService;
import net.nigne.wholegram.service.EncryptService;
import net.nigne.wholegram.service.FollowService;
import net.nigne.wholegram.service.LoginService;
import net.nigne.wholegram.service.MemberService;


@Controller
@RequestMapping("/*")
public class LoginController {
	
	@Inject
	private MemberService service;
	
	@Inject
	private EncryptService encrypt;
	
	@Inject
	private LoginService loginService;
	
	@Inject
	private BoardService bdservice;
	
	@Inject
	private FollowService fservice;
	
	/* Header의 user아이콘을 통해 user페이지로 넘어올 시 */  
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		ModelAndView mav = new ModelAndView();
		
		model.addAttribute( "sessionId", user_id );
		/* user페이지 이동 */
		if(user_id != null) {
			MemberVO vo = service.MemInfo(user_id);
			List<BoardVO> list = bdservice.getUserLimitList(vo);
			Criteria cr = new Criteria();
			cr.setItem(user_id);
			int numberOfBoard = bdservice.getUserCount(cr);
			Map<String, Integer> numberOfFollow= fservice.getFollowNumberof(user_id);
			
			mav.addObject("vo", vo);								// 유저 data
			mav.addObject("list", list);							// 유저가 올린 게시물 data
			mav.addObject("numberOfBoard", numberOfBoard);			// 유저가 올린 게시물 개수
			mav.addObject("numberOfFollow", numberOfFollow);		// 유저가 팔로잉 / 유저를 팔로우 있는 수
			mav.setViewName("user");
		} else {
			mav.setViewName("login");
		}
		return mav;
	}

	/* 최초 로그인을 통해 user페이지로 넘어갈 시 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginCheck(MemberVO vo_chk, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

		/* pw 암호화 및 로그인 가능상태 설정 */
		vo_chk.setPasswd(encrypt.shaEncrypt(vo_chk.getPasswd()));
		Status loginStatus = loginService.LoginStatus(service.compare(vo_chk));

		ModelAndView mav = new ModelAndView();		
		
		/* 로그인 성공 */
		if (loginStatus.isSuccess()) {
			HttpSession session = request.getSession();
			session.setAttribute("user_id", vo_chk.getUser_id());
			String user_id = (String) session.getAttribute("user_id");
			
			MemberVO vo = service.MemInfo(user_id);
			List<BoardVO> list = bdservice.getUserLimitList(vo);
			Criteria cr = new Criteria();
			cr.setItem(user_id);
			int numberOfBoard = bdservice.getUserCount(cr);
			Map<String, Integer> numberOfFollow= fservice.getFollowNumberof(user_id);
			
			// 이동할 페이지, 사용자 정보 설정
			
			mav.addObject("vo", vo);
			mav.addObject("list", list);
			mav.addObject("numberOfBoard", numberOfBoard);			// 유저가 올린 게시물 개수
			mav.addObject("numberOfFollow", numberOfFollow);		// 유저가 팔로잉 / 유저를 팔로우 있는 수
			model.addAttribute("sessionId",user_id);
			mav.setViewName("user");
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
