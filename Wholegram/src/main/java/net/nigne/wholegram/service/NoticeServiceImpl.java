package net.nigne.wholegram.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.nigne.wholegram.domain.FollowVO;
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

	// 댓글 달았을시 notice(알림) 테이블에 추가
	@Override
	public void rnInsert( String user_id, int board_num, String content, int flag, int reply_num ) {
		dao.rnInsert(user_id, board_num, content, flag, reply_num);
	}
	
	// 댓글 지웠을시 notice(알림) 테이블에 삭제
	@Override
	public void rnDelete(int reply_num) {
		dao.rnDelete(reply_num);
	}

	// 누가 누구를 팔로우 하고있는 noitce(알림) 테이블에 추가
	@Override
	public void insertFollow(FollowVO vo, int flag) {
		System.out.println("abc1-1");
		dao.insertFollow(vo, flag);
	}

	//알림 표시 제거
	@Override
	public void RemoveNotice(int notice_num) {
		dao.RemoveNotice(notice_num);
	}

	/*팔로우 취소했을 때*/
	@Override
	public void followDelete(NoticeVO vo) {
		dao.followDelete(vo);
	}
}
