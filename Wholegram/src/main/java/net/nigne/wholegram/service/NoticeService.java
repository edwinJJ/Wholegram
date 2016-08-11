package net.nigne.wholegram.service;

public interface NoticeService {
	public void noticeHeart(String user_id, int board_num, int flag);
	public void rnoticeHeart(String user_id, int board_num);
}
