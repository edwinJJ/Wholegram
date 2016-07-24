package net.nigne.wholegram.service;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.nigne.wholegram.domain.Chat_userVO;
import net.nigne.wholegram.domain.MessageVO;
import net.nigne.wholegram.persistance.ChatDAO;

@Service
public class ChatServiceImpl implements ChatService {

	@Inject
	private ChatDAO dao;
	
	/* 채팅방 생성 */
	@Transactional
	@Override
	public int chat_room() {
		//채팅방 생성
		dao.chat_room();
		
		//채팅방 고유번호 가져옴
		int chat_num = dao.getchat_room();
		return chat_num;
	}

	/* 채팅방 참여 유저 추가 */
	@Override
	public void user_room(int chat_num, String id_list) {
		dao.user_room(chat_num, id_list);
	}

	/* msg_list에 메시지 내용 저장 */
	@Override
	public void msgStorage(HashMap<String, Object> data) {
		dao.msgStorage(data);
	}

	/* 메시지 내용 가져옴 */
	@Override
	public List<MessageVO> msgGet(int chat_num) {
		return dao.msgGet(chat_num);
	}

	/*유저가 포함되어있는 각 채팅방에 참여하고 있는 유저 리스트를 가져옴*/
	@Override
	public List<Chat_userVO> getRoomUsers(String user_id) {
		/*유저가 포함되어있는 채팅방 목록 가져옴*/
		List<Integer> roomlist = dao.getRoomNumber(user_id);
		
		/*각 채팅방에 참여하고있는 유저 리스트를 가져옴*/
		return dao.getRoomUser(roomlist);
	}
}
