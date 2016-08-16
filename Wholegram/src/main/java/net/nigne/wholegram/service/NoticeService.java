package net.nigne.wholegram.service;

import java.util.List;

import net.nigne.wholegram.domain.FollowVO;
import net.nigne.wholegram.domain.NoticeVO;

public interface NoticeService {
	public void insertFromUpload(NoticeVO vo);
	public void insertNoticeHeart(String user_id, int board_num, int flag);
	public void deleteNoticeHeart(String user_id, int board_num);
	public List<NoticeVO> checkNotice(String user_id);
	public void rnInsert( String user_id, int board_num, String content, int flag, int reply_num );
	public void rnDelete( int reply_num );
	public void insertFollow(FollowVO vo, int flag);
}
