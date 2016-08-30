package net.nigne.wholegram.persistance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.nigne.wholegram.domain.BoardVO;
import net.nigne.wholegram.domain.FollowVO;
import net.nigne.wholegram.domain.MemberVO;
import net.nigne.wholegram.domain.NoticeVO;

@Repository
public class NoticeDAOImpl implements NoticeDAO {

	@Inject
	private SqlSession session;
	
	private static final String namespace = "net.nigne.wholegram.mappers.NoticeMapper";
	private static final String namespace2 = "net.nigne.wholegram.mappers.boardMapper";
	private static final String namespace3 = "net.nigne.wholegram.mappers.MemberMapper";
	
	@Override
	public void insertNoticeHeart(String user_id, int board_num, int flag) {
		
		BoardVO vo = session.selectOne(namespace2 + ".getOne", board_num);			// 해당 게시물 작성자Id 가져오기
		
		if(!(user_id.equals(vo.getUser_id()))) {									// 본인이 본인 글에 좋아요 누른게 아닐경우에만
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

		List<NoticeVO> Finaldata = new ArrayList<NoticeVO>();									// 최종적으로 리턴해줄 알림 data

		List<NoticeVO> data = session.selectList(namespace + ".checkNotice", user_id);
		Iterator<NoticeVO> extract = data.iterator();
		
		while(extract.hasNext()) {
			NoticeVO data_vo = new NoticeVO();													// Notice의 행위자 id 추출
			data_vo = extract.next();
			String id = data_vo.getUser_id();
			
			MemberVO mvo = session.selectOne(namespace3 + ".MemInfo", id);						// 행위자의 프로필 image가 있는지 여부 추출
			data_vo.setDefault_profile(mvo.getDefault_profile());								// 알림 내용에 추가
			Finaldata.add(data_vo);																
		}
		return Finaldata;
	}
	
	@Override
	public void insertFromUpload(NoticeVO vo) {
		session.insert(namespace+".insertFromUpload", vo);
	}

	@Transactional
   @Override
   public void rnInsert(String user_id, String user, int board_num, String content, int flag, int reply_num) {
      BoardVO vo = session.selectOne( namespace2 + ".getOne", board_num );
      Map<String, Object> map = new HashMap<>();
      
      if(flag == 5) {
         map.put("user_id", user_id); // 언급하는 사용자
         map.put("other_id", user);   // 언급 당하는 사용자
      } else {
         map.put("user_id", user_id);
         map.put("other_id", vo.getUser_id());
      }
      map.put("refer_content", content);
      map.put("media", vo.getMedia_thumnail());
      map.put("flag", flag);
      map.put("reply_num", reply_num);
      map.put("board_num", board_num);
      session.insert( namespace + ".rnInsert", map);
   }

	@Override
	public void rnDelete( int reply_num ) {
		session.delete( namespace + ".rnDelete", reply_num);
	}

	@Override
	public void insertFollow(FollowVO vo, int flag) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("vo", vo);
		data.put("flag", flag);
		session.insert(namespace + ".insertFollow", data);
	}

	@Override
	public void RemoveNotice(int notice_num) {
		session.update(namespace + ".RemoveNotice", notice_num);
	}
	
	@Override
	public void followDelete(NoticeVO vo) {
		session.delete(namespace+ ".followDelete",vo);
	}
}
