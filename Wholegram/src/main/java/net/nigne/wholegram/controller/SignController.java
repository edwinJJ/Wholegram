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

import net.nigne.wholegram.service.EncryptService;
import net.nigne.wholegram.service.SignService;
import net.nigne.wholegram.domain.MemberVO;


@RestController
@RequestMapping("/")
public class SignController{

	@Inject
	private EncryptService encrypt;
	@Inject
	private SignService sign;
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public ModelAndView signUp(Locale locale, Model model) {
	
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView insertSignUp(MemberVO vo,Locale locale, Model model, HttpServletRequest req) {
		String passwd = vo.getPasswd();
		vo.setPasswd(encrypt.shaEncrypt(passwd));
		sign.insert(vo);
		return new ModelAndView("login");
	}
	
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