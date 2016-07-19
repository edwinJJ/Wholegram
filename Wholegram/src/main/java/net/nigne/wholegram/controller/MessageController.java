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
@RequestMapping("/message")
public class MessageController {
	
	@Inject
	private MemberService service;
	
	/*message 페이지 이동*/
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView message(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		if((session.getAttribute("user_id")) != null) {
			ModelAndView mav = new ModelAndView();		
			mav.setViewName("message");
			return mav;
		} else {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("login");
			return mav;
		}
	}
}
