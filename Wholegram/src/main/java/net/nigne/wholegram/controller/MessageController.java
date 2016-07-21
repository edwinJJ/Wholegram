package net.nigne.wholegram.controller;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import net.nigne.wholegram.service.ChatService;

@RestController
@RequestMapping("/message")
public class MessageController {
	
	@Inject
	private ChatService chatservice;
	
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

	/* 채팅방 생성 */
	@RequestMapping(value = "/chatroom/{id}", method = RequestMethod.POST)
	public ResponseEntity<Integer> set_chat_room(@PathVariable("id") String id_list, HttpServletRequest request) {
		
		ResponseEntity<Integer> entity = null;
		try{
			int chat_num = chatservice.chat_room();		//chat table에 채팅방 번호 생성
			chatservice.user_room(chat_num, id_list);	//chat_user table에 채팅방 번호에 맞는 유저아이디 입력
			
			entity = new ResponseEntity<>(chat_num, HttpStatus.OK);
		} catch(Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
		
}
