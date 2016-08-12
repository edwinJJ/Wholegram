package net.nigne.wholegram.service;

import java.util.List;

import net.nigne.wholegram.domain.NoticeVO;

public interface NoticeService {
	public void insertNoticeHeart(String user_id, int board_num, int flag);
	public void deleteNoticeHeart(String user_id, int board_num);
	public List<NoticeVO> checkNotice(String user_id);
}
