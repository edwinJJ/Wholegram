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


@RestController
@RequestMapping("/person")
public class PersonController {
	
	/* 사람찾기 / 알 수도 있는 사람 페이지 이동 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView Person(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		ModelAndView mav = new ModelAndView();		
		if(user_id != null) {
			mav.setViewName("new_person");
		} else {
			mav.setViewName("login");
		}
		return mav;
	}
}
