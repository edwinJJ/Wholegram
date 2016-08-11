package net.nigne.wholegram.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.nigne.wholegram.persistance.NoticeDAO;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Inject
	private NoticeDAO dao; 
	
	// 누가 누구의 게시글에 좋아요를 눌렀는지 알림
	@Override
	public void noticeHeart(String user_id, int board_num, int flag) {
		System.out.println("1-1");
		dao.noticeHeart(user_id, board_num, flag);
	}

	// 누가 누구의 게시글에 좋아요 눌렀는지 알림 제거
	@Override
	public void rnoticeHeart(String user_id, int board_num) {
		dao.rnoticeHeart(user_id, board_num);
	}



}
