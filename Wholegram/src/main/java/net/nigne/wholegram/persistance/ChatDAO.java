package net.nigne.wholegram.persistance;

import java.util.HashMap;
import java.util.List;

import net.nigne.wholegram.domain.Chat_userVO;
import net.nigne.wholegram.domain.MessageVO;
import net.nigne.wholegram.domain.Msg_listVO;

public interface ChatDAO {
	public void chat_room();
	public int getchat_room();
	public void user_room(int chat_num, String id_list);
	public void msgStorage(HashMap<String, Object> data);
	public List<Msg_listVO> msgGet(int chat_num);
	public List<Integer> getRoomNumber(String user_id);
	public List<Chat_userVO> getRoomUser(List<Integer> roomlist);
	public void delRoom(int chat_chat_num);
	public List<Chat_userVO> userList(int chat_num);
}
