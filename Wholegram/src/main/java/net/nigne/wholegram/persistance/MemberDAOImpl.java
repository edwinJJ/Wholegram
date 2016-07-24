package net.nigne.wholegram.persistance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import net.nigne.wholegram.domain.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO {
	@Inject
	private SqlSession session;
	
	private static final String namespace = "net.nigne.wholegram.mappers.MemberMapper";

	@Override
	public int compare(MemberVO vo_chk) {
		return session.selectOne( namespace + ".compare", vo_chk );
	}

	@Override
	public MemberVO MemInfo(String user_id) {
		return session.selectOne( namespace + ".MemInfo", user_id);
	}

	@Override
	public int compareId(String id) {
		return session.selectOne( namespace + ".compareId", id);
	}
	
	@Override
	public int compareEmail(String email) {
		return session.selectOne( namespace + ".compareEmail", email);
	}
	
	@Override
	public void updateUser(MemberVO vo) {
		session.update(namespace + ".updateUser", vo);
	}

	@Override
	public String checkPasswd(int mem_no) {
		return session.selectOne(namespace + ".checkPasswd", mem_no);
	}

	@Override
	public void updatePasswd(MemberVO vo) {
		session.update(namespace + ".updatePasswd", vo);
	}

	@Override
	public List<MemberVO> getFollowinguser_Profile(List<String> user_ids) {
		List<MemberVO> memberProfile_List = new ArrayList<MemberVO>();
		
		for(int i=0; i<user_ids.size(); i++) {
			String user_id = user_ids.get(i);
			MemberVO vo = new MemberVO();
			vo = session.selectOne(namespace + ".getFollowinguser_Profile", user_id);
			memberProfile_List.add(vo);
		}
		return memberProfile_List;
	}

}
