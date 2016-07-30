package net.nigne.wholegram.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.nigne.wholegram.domain.Chat_userVO;
import net.nigne.wholegram.domain.Msg_listVO;
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

	/* msg_list에 메시지 내용 저장 + 메시지 읽은 유저에 본인 추가 */
	@Override
	public void msgStorage(HashMap<String, Object> data) {
		dao.msgStorage(data);
	}

	/* 메시지 내용, 작성자 가져옴 */
	@Override
	public List<Msg_listVO> msgGet(int chat_num) {
		return dao.msgGet(chat_num);
	}

	/*유저가 포함되어있는 각 채팅방에 참여하고 있는 유저 리스트를 가져옴*/
	@Transactional
	@Override
	public List<Chat_userVO> getRoomUsers(String user_id) {
		/*유저가 포함되어있는 채팅방 목록(번호 리스트) 가져옴*/
		List<Integer> roomlist = dao.getRoomNumber(user_id);
		
		/*각 채팅방에 참여하고있는 유저 리스트를 가져옴*/
		return dao.getRoomUser(roomlist);
	}

	/*채팅방 삭제*/
	@Override
	public void delRoom(int chat_chat_num) {
		dao.delRoom(chat_chat_num);
	}

	/*채팅방에 해당되는 유저들 목록을 가져옴*/
	@Override
	public List<Chat_userVO> userList(int chat_num) {
		return dao.userList(chat_num);
	}

	/* 메시지 읽은 유저목록에 본인 추가 */
	@Override
	public void setRead_user_ids(Map<String, Object> data) {
		dao.setRead_user_ids(data);
	}

}
