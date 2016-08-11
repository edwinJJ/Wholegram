package net.nigne.wholegram.persistance;

public interface NoticeDAO {
	public void noticeHeart(String user_id, int board_num, int flag);
	public void rnoticeHeart(String user_id, int board_num);
}
