package net.nigne.wholegram.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@RestController
@RequestMapping("/message")
public class MessageController {
	
	

	
	
	
	
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
