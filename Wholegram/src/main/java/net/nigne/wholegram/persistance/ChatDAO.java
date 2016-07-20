package net.nigne.wholegram.persistance;

public interface ChatDAO {
	public void chat_room();
	public int getchat_room();
	public void user_room(int chat_num, String id_list);
}
