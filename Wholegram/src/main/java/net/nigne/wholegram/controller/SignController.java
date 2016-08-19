package net.nigne.wholegram.controller;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.nigne.wholegram.common.Encrypt;
import net.nigne.wholegram.domain.MemberVO;
import net.nigne.wholegram.service.SignService;


@RestController
@RequestMapping("/")
public class SignController{

	@Inject
	private Encrypt encrypt;
	@Inject
	private SignService sign;
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signUp(Locale locale, Model model) {
	
		return new ModelAndView("login");
	}

	/*비밀번호 암호화시킨후 등록*/
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView insertSignUp(MemberVO vo,Locale locale, Model model, HttpServletRequest req) {
		String passwd = vo.getPasswd();
		vo.setPasswd(encrypt.shaEncrypt(passwd));
		sign.insert(vo);
		return new ModelAndView("login");
	}
	
	/*아이디 중복 확인*/
	@RequestMapping(value = "/checkId/{user_id}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> CheckId(@PathVariable("user_id") String id) {
		ResponseEntity<Boolean> entity = null;
		try{
			entity = new ResponseEntity<>(checkValue.status(sign.checkId(id)),HttpStatus.OK);
		}catch(Exception e){
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	/*이메일 중복 확인*/
	@RequestMapping(value = "/checkEmail/{email}", method = RequestMethod.GET)
	public ResponseEntity<Boolean> CheckEmail(@PathVariable("email") String email) {
		System.out.println(sign.checkEmail(email+"%"));
		ResponseEntity<Boolean> entity = null;
		try{
			entity = new ResponseEntity<>(checkValue.status(sign.checkEmail(email+"%")),HttpStatus.OK);
		}catch(Exception e){
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	/*이메일 인증*/
	@RequestMapping(value = "/sendMail/{emailad1}/{emailad2}", method = RequestMethod.GET)
	public ResponseEntity<String> sendMail(@PathVariable("emailad1") String emailad1, @PathVariable("emailad2") String emailad2) {
		
		ResponseEntity<String> entity = null;
		try {
			entity = new ResponseEntity<>(sign.sendMail(emailad1, emailad2), HttpStatus.OK);	
		} catch(Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	/*이메일로 비밀번호 찾기*/
	@RequestMapping(value = "/sendMailFind", method = RequestMethod.POST)
	public ModelAndView sendMailFind(HttpServletRequest request) {
		String emailaddress = request.getParameter("email");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("login");
		sign.sendFindMail(emailaddress);		// 새로운 비밀번호 메일로 전송
		return mav;
	}
	
}

class checkValue{
	// 중복되는 값이 없을 경우('0') TRUE 중복되는 값이 있을 경우 FALSE
	private final static int TRUE = 0;
	public static boolean status(int value){
		if(value==TRUE){
			return true;
		}else{
			return false;
		}
	}
}