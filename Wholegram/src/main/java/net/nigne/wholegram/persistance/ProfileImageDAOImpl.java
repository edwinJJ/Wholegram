package net.nigne.wholegram.persistance;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileImageDAOImpl implements ProfileImageDAO {

	@Inject
	SqlSession session;
	
	private static final String namespace = "net.nigne.wholegram.mappers.MemberMapper";
	
	@Override
	public void setProfileImage(HashMap<String, Object> profileImage) {
		session.insert(namespace + ".setProfileImage", profileImage);
	}

	@Override
	public byte[] getProfileImage(String user_id) {
		
		byte[] test = session.selectOne(namespace + ".getProfileImage", user_id);

		System.out.println(test);
		System.out.println(test.length);
		
		return session.selectOne(namespace + ".getProfileImage", user_id);
	}

	
}
