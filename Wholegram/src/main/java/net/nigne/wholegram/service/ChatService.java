package net.nigne.wholegram.service;

import java.util.HashMap;
import java.util.List;

import net.nigne.wholegram.domain.Chat_userVO;
import net.nigne.wholegram.domain.MessageVO;

public interface ChatService {
	public int chat_room();
	public void user_room(int chat_num, String id_list);
	public void msgStorage(HashMap<String, Object> data);
	public List<MessageVO> msgGet(int chat_num); 
	public List<List<Chat_userVO>> getRoomUsers(String user_id);
}
