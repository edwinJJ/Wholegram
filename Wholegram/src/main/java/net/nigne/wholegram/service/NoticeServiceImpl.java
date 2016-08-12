package net.nigne.wholegram.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.nigne.wholegram.domain.NoticeVO;
import net.nigne.wholegram.persistance.NoticeDAO;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Inject
	private NoticeDAO dao; 
	
	// 누가 누구의 게시글에 좋아요를 눌렀는지 알림 등록
	@Override
	public void insertNoticeHeart(String user_id, int board_num, int flag) {
		dao.insertNoticeHeart(user_id, board_num, flag);
	}

	// 누가 누구의 게시글에 좋아요 눌렀는지 알림 제거
	@Override
	public void deleteNoticeHeart(String user_id, int board_num) {
		dao.deleteNoticeHeart(user_id, board_num);
	}

	// 자신에게 알림이 등록되었는지 확인
	@Override
	public List<NoticeVO> checkNotice(String user_id) {
		return dao.checkNotice(user_id);
	}

	// 게시물 업로드시에 글내용중 다른유저를 언급한경우 (notice)
	@Override
	public void insertFromUpload(NoticeVO vo) {
		dao.insertFromUpload(vo);
	}



}
