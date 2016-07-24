package net.nigne.wholegram.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

import net.nigne.wholegram.domain.Chat_userVO;
import net.nigne.wholegram.domain.MemberVO;
import net.nigne.wholegram.service.ChatService;
import net.nigne.wholegram.service.FollowService;
import net.nigne.wholegram.service.MemberService;

@RestController
@RequestMapping("/message")
public class MessageController {
	
	@Inject
	private ChatService chatservice;
	
	@Inject
	private FollowService fservice;
	
	@Inject
	private MemberService mservice;
	
	/* message 페이지 이동 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView message(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		ModelAndView mav = new ModelAndView();
		
		if( user_id != null) {
			model.addAttribute( "sessionId", user_id );
			mav.setViewName("message");
			
			/*유저가 포함되어있는 각 채팅방에 참여하고 있는 유저 리스트를 가져옴*/
			List<Chat_userVO> roominfo = chatservice.getRoomUsers(user_id);
			mav.addObject("roominfo", roominfo);
		} else {
			mav.setViewName("login");
		}
		return mav;
	}

	/* 채팅방 생성 */
	@RequestMapping(value = "/chatroom/{ids}", method = RequestMethod.POST)
	public ResponseEntity<Integer> set_chat_room(@PathVariable("ids") String id_list, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		id_list += user_id;
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
		
	/* 채팅상대 고를 때(팔로잉 유저 목록 불러오기) */
	@RequestMapping(value = "/getFollowing_Userid", method = RequestMethod.POST)
	public ResponseEntity<List<MemberVO>> get_following_ids(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("user_id");
		ResponseEntity<List<MemberVO>> entity = null;
		
		try{
			// 내가 팔로우 하고 있는 user_id목록을 가져옴
			List<String> user_ids = fservice.getFollowing_Userid(user_id);
			
			// 각 user_id마다 profile(id, profile)정보를 가져옴
			entity = new ResponseEntity<>(mservice.getFollowinguser_Profile(user_ids), HttpStatus.OK);
		} catch(Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
}
