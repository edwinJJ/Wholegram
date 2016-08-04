package net.nigne.wholegram.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import net.nigne.wholegram.domain.Msg_listVO;
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
		
		if(user_id != null) {
			model.addAttribute( "sessionId", user_id );
			mav.setViewName("message");
			
			/* 유저가 포함되어있는 각 채팅방의 참여하고 있는 모든 유저 리스트를 가져옴 */
			List<Chat_userVO> roomInfo = chatservice.getRoomUsers(user_id);

			/* 유저가 포함되어있는 채팅방 번호만 추출 */
			List<Integer> roomNumber = chatservice.extractRoomNumber(roomInfo);
			
			/* 유저가 속한 각 채팅방마다 최신 메시지 내용을 읽었는지 확인 후 알림표시 여부 설정 */
			List<Integer> roomList = chatservice.checkReadRoom(roomNumber, user_id);				// 최신 메시지를 읽지 않은 채팅방 리스트 추출
			List<Chat_userVO> roomInfomation = chatservice.setCheckReadRoom(roomList, roomInfo);	// 채팅방에 '메시지 읽지 않음' 알림을 보여줄 여부 설정
	
			mav.addObject("roomInfomation", roomInfomation);										// 유저가 속한 채팅방 번호, 참여 유저들ID, 최신메시지를 읽었는지 알림 여부 의 데이터가 들어있음 
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
			int chat_num = chatservice.chat_room(id_list);					//chat table에 채팅방 번호 생성
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
	
	/* 채팅방 목록 가져오기(새로운 채팅방 생성 후 다시 채팅방 목록을 가져올 때)  */
	@RequestMapping(value = "/roomList", method = RequestMethod.POST)
	public ResponseEntity<List<Chat_userVO>> getRoomList(HttpServletRequest request) {

		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		
		ResponseEntity<List<Chat_userVO>> entity = null;
		try{
			entity = new ResponseEntity<>(chatservice.getRoomUsers(user_id), HttpStatus.OK);
		} catch(Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	/* 채팅방 정보(대화목록, 작성자) 가져오기 */
	@RequestMapping(value = "/getRoomData/{chat_chat_num}", method = RequestMethod.POST)
	public ResponseEntity<List<Msg_listVO>> getRoomData(@PathVariable("chat_chat_num") int chat_chat_num, HttpServletRequest request) {

		List<Msg_listVO> ml = chatservice.msgGet(chat_chat_num);
		ResponseEntity<List<Msg_listVO>> entity = null;
		try{
			entity = new ResponseEntity<>(chatservice.msgGet(chat_chat_num), HttpStatus.OK);
		} catch(Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	/* 채팅방의 메시지를 읽음으로 등록 */
	@RequestMapping(value = "/readCheck/{chat_chat_num}", method = RequestMethod.POST)
	public ResponseEntity<String> readCheck(@PathVariable("chat_chat_num") int chat_chat_num, HttpServletRequest request) {

		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("chat_num", chat_chat_num);						// 채팅방 번호
		data.put("written_user_id", user_id);						// 본인 Id
		
		ResponseEntity<String> entity = null;
		try{
			chatservice.setRead_user_ids(data);						// 메시지 읽은 유저 갱신
			entity = new ResponseEntity<>("SUCCESS", HttpStatus.OK);
		} catch(Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	/*채팅방 삭제*/
	@RequestMapping(value = "/delRoom/{chat_chat_num}", method = RequestMethod.POST)
	public ResponseEntity<List<Chat_userVO>> delRoom(@PathVariable("chat_chat_num") int chat_chat_num, HttpServletRequest request) {

		HttpSession session = request.getSession();
		String user_id = (String)session.getAttribute("user_id");
		
		chatservice.delRoom(chat_chat_num);							// 채팅방 삭제
		ResponseEntity<List<Chat_userVO>> entity = null;			// 갱신된 전체 채팅방 새로 다시 가져옴
		try{						  
			entity = new ResponseEntity<>(chatservice.getRoomUsers(user_id), HttpStatus.OK);
		} catch(Exception e) {
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
}
