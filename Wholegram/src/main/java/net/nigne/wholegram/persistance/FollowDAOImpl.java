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
	public List<FollowVO> getfwList( FollowVO vo ) {
		return session.selectList( namespace + ".getfwList", vo );
	}
}
