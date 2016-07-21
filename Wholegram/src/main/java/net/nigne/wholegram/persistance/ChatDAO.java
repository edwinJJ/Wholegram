package net.nigne.wholegram.persistance;

import java.util.HashMap;
import java.util.List;

import net.nigne.wholegram.domain.MessageVO;

public interface ChatDAO {
	public void chat_room();
	public int getchat_room();
	public void user_room(int chat_num, String id_list);
	public void msgStorage(HashMap<String, Object> data);
	public List<MessageVO> msgGet(int chat_num);
	
}
