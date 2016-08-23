package net.nigne.wholegram.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.nigne.wholegram.domain.Chat_userVO;
import net.nigne.wholegram.domain.Msg_listVO;

public interface ChatService {
	public int chat_room(String id_list);
	public void msgStorage(HashMap<String, Object> data);
	public List<Msg_listVO> msgGet(int chat_num); 
	public void setRead_user_ids(Map<String, Object> data);
	public List<Chat_userVO> getRoomUsers(String user_id);
	public List<Integer> extractRoomNumber(List<Chat_userVO> roomInfo);
	public List<Integer> checkReadRoom(List<Integer> roomNumber, String user_id);
	public List<Chat_userVO> setCheckReadRoom(List<Integer> roomList, List<Chat_userVO> roomInfo);
	public void delRoom(int chat_chat_num);
	public void changeRoom(int chat_chat_num, String chatName);
	public List<Chat_userVO> userList(int chat_num);
	public List<Integer> getRoomList(String user_id);
}
