package net.nigne.wholegram.controller;

import java.util.Locale;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.nigne.wholegram.domain.MemberVO;
import net.nigne.wholegram.service.EncryptService;
import net.nigne.wholegram.service.MemberService;


/**
 * Handles requests for the application home page.
 */
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Inject
	private MemberService service;

	@Inject
	private EncryptService encrypt;
	
	/*로그아웃*/
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.invalidate();
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("redirect:/login");
		return mav;
	}
	
	/*프로필 편집 페이지*/
	@RequestMapping(value = "/update_form", method = RequestMethod.GET)
	public ModelAndView update_form(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		MemberVO vo = service.MemInfo(user_id);
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("user_profile");
		mav.addObject("vo", vo);
		return mav;
	}
	
	/*User_Id 변경시 중복 체크*/
	@RequestMapping(value = "/id_chk/{id}", method = RequestMethod.POST)
	public ResponseEntity<Integer> id_chk(@PathVariable("id") String id, HttpServletRequest request) {

		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		
		ResponseEntity<Integer> entity = null;
		
		if(!(id.equals(user_id))) {
			try{
				entity = new ResponseEntity<>(service.compareId(id), HttpStatus.OK);
			} catch(Exception e) {
				entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} else {
			return entity = new ResponseEntity<Integer>(2, HttpStatus.OK);
		}
		return entity;
	}
	
	/*Email 변경시 중복 체크*/ 
	@RequestMapping(value = "/email_chk/{em}", method = RequestMethod.POST)
	public ResponseEntity<Integer> email_chk(@PathVariable("em") String em, HttpServletRequest request) {
		System.out.println("일로 오니?");
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		em = em + ".com";
		MemberVO vo = service.MemInfo(user_id);
		
		ResponseEntity<Integer> entity = null;
		System.out.println("email : " + vo.getEmail());
		System.out.println("em : " + em);
		if(!(vo.getEmail().equals(em))){
			try{
				entity = new ResponseEntity<>(service.compareEmail(em), HttpStatus.OK);
			} catch(Exception e) {
				entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} else {
			return entity = new ResponseEntity<Integer>(2, HttpStatus.OK);
		}
		return entity;
	}
	
	/*프로필 전체 업데이트*/
	@RequestMapping(value = "/update_user", method = RequestMethod.POST)
	public ModelAndView update_user(MemberVO vo, Model model, HttpServletRequest request) {
		service.updateUser(vo);
		HttpSession session = request.getSession();
		session.setAttribute("user_id", vo.getUser_id());
		return new ModelAndView("redirect:/user/update_form");
	}

	/*비밀번호 변경 페이지*/
	@RequestMapping(value = "/passwd_form", method = RequestMethod.GET)
	public ModelAndView passwd_form(Locale locale, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		MemberVO vo = service.MemInfo(user_id);
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("user_passwd");
		mav.addObject("vo", vo);
		return mav;
	}
	
	/*이전 비밀번호 확인*/
	@RequestMapping(value = "/check_passwd/{mem_no}/{passwd}", method = RequestMethod.POST)
	public ResponseEntity<Integer> update_passwd(@PathVariable("mem_no") int mem_no, @PathVariable("passwd") String passwd) {

		passwd = encrypt.shaEncrypt(passwd);
		
		ResponseEntity<Integer> entity = null;
		try{
			String passwd_real = service.checkPasswd(mem_no);
			entity = new ResponseEntity<>(service.comparePasswd(passwd_real, passwd), HttpStatus.OK);
		}catch(Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	/*비밀번호 변경*/
	@RequestMapping(value = "/change_passwd/{mem_no}/{passwd_new}", method = RequestMethod.POST)
	public ResponseEntity<String> change_passwd(@PathVariable("mem_no") int mem_no, @PathVariable("passwd_new") String passwd_new) {
		
		MemberVO vo = new MemberVO();
		vo.setMem_no(mem_no);
		vo.setPasswd(encrypt.shaEncrypt(passwd_new));
		
		ResponseEntity<String> entity = null;
		try{
			service.updatePasswd(vo);
			entity = new ResponseEntity<>("SUCCESS!!", HttpStatus.OK);
		}catch(Exception e) {
			entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
}
