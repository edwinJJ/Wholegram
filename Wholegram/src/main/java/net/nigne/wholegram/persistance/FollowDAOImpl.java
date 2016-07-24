package net.nigne.wholegram.persistance;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class FollowDAOImpl implements FollowDAO {

	@Inject
	SqlSession session;
	
	private static final String namespace="net.nigne.wholegram.mappers.followMapper";
	
	@Override
	public List<String> getFollowing_Userid(String user_id) {
		return session.selectList(namespace + ".getFollowing_Userid", user_id);
	}

}
