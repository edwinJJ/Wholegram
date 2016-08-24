package net.nigne.wholegram.persistance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.nigne.wholegram.domain.FollowVO;

@Repository
public class FollowDAOImpl implements FollowDAO {

	@Inject
	SqlSession session;
	
	private static final String namespace="net.nigne.wholegram.mappers.followMapper";
	
	@Override
	public List<String> getFollowing_Userid(String user_id) {
		return session.selectList(namespace + ".getFollowing_Userid", user_id);
	}

	@Transactional
	@Override
	public void followInsert(FollowVO vo) {
		session.insert( namespace + ".followInsert", vo );
	}

	@Transactional
	@Override
	public void followDelete(int follow_num, String following) {
		Map<String, Object> data = new HashMap<>();
		data.put("follow_num", follow_num);
		data.put("following", following);
		session.delete( namespace + ".followDelete", data );
	}

	@Override
	public List<FollowVO> getfwList() {
		return session.selectList( namespace + ".getfwList");
	}

	@Override
	public Map<String, Integer> getFollowNumberof(String user_id) {
		
		Map<String, Integer> data = new HashMap<String, Integer>();
		
		int following = session.selectOne(namespace + ".getFollowingNumberof", user_id);	// 유저가 팔로잉 하고있는 수
		int follower = session.selectOne(namespace + ".getFollowerNumberof", user_id); 		// 유저를 팔로우 하고있는 다른유저들의 수
		data.put("following", following);
		data.put("follower", follower);
		return data;
	}
	
	@Override
	public List<FollowVO> getMyFollowingList(String user_id) {
		return session.selectList(namespace + ".getMyFollowingList", user_id);
	}

	@Override
	public List<FollowVO> getMyFollowerList(String user_id) {
		return session.selectList(namespace + ".getMyFollowerList", user_id);
	}

	@Override
	public boolean followCheck(Map<String, String> map) {
		
		return ("1".equals(session.selectOne(namespace + ".followCheck", map).toString())?true:false);
	}
	@Transactional
	@Override
	public void userfollowDelete(FollowVO vo) {
		session.delete(namespace+".userfollowDelete", vo);
		
	}
	@Transactional
	@Override
	public void statusUpdate(Map<String, Object> map) {
		session.update(namespace+".statusUpdate", map);
		
	}
}
