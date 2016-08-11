package net.nigne.wholegram.persistance;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import net.nigne.wholegram.domain.BoardVO;

@Repository
public class NoticeDAOImpl implements NoticeDAO {

	@Inject
	private SqlSession session;
	
	private static final String namespace = "net.nigne.wholegram.mappers.NoticeMapper";
	private static final String namespace2 = "net.nigne.wholegram.mappers.boardMapper";
	
	@Override
	public void noticeHeart(String user_id, int board_num, int flag) {
		
		BoardVO vo = session.selectOne(namespace2 + ".getOne", board_num);			// 해당 게시물 작성자Id 가져오기
		
		Map<String, Object> data = new HashMap<String, Object>();					// Notice(알림)에 등록
		data.put("user_id", user_id);
		data.put("other_id", vo.getUser_id());
		data.put("board_num",  board_num);
		data.put("media", vo.getMedia());
		data.put("flag", flag);
		session.insert( namespace + ".noticeHeart", data);
	}

	@Override
	public void rnoticeHeart(String user_id, int board_num) {
		Map<String, Object> data = new HashMap<String, Object>();					// Notice(알림) 제거
		data.put("user_id", user_id);
		data.put("board_num", board_num);
		session.delete(namespace + ".rnoticeHeart", data);
	}

}
