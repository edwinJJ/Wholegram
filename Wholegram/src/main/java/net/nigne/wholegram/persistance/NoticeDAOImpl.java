package net.nigne.wholegram.persistance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import net.nigne.wholegram.domain.BoardVO;
import net.nigne.wholegram.domain.NoticeVO;

@Repository
public class NoticeDAOImpl implements NoticeDAO {

	@Inject
	private SqlSession session;
	
	private static final String namespace = "net.nigne.wholegram.mappers.NoticeMapper";
	private static final String namespace2 = "net.nigne.wholegram.mappers.boardMapper";
	
	@Override
	public void insertNoticeHeart(String user_id, int board_num, int flag) {
		
		BoardVO vo = session.selectOne(namespace2 + ".getOne", board_num);			// 해당 게시물 작성자Id 가져오기
		
		if(user_id != vo.getUser_id()) {											// 본인이 본인 글에 좋아요 누른게 아닐경우에만
			Map<String, Object> data = new HashMap<String, Object>();				// Notice(알림)에 등록
			data.put("user_id", user_id);
			data.put("other_id", vo.getUser_id());
			data.put("board_num",  board_num);
			System.out.println("media : " + vo.getMedia());
			System.out.println("media_thumb : " + vo.getMedia_thumnail());
			data.put("media", vo.getMedia_thumnail());
			data.put("flag", flag);
			session.insert( namespace + ".insertNoticeHeart", data);
		} else {}
	}

	@Override
	public void deleteNoticeHeart(String user_id, int board_num) {
		Map<String, Object> data = new HashMap<String, Object>();					// Notice(알림) 제거
		data.put("user_id", user_id);
		data.put("board_num", board_num);
		session.delete(namespace + ".deleteNoticeHeart", data);
	}

	@Override
	public List<NoticeVO> checkNotice(String user_id) {
		return session.selectList(namespace + ".checkNotice", user_id);
	}
	
	@Override
	public void insertFromUpload(NoticeVO vo) {
		session.insert(namespace+".insertFromUpload", vo);
	}

}
