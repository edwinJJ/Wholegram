package net.nigne.wholegram.persistance;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import net.nigne.wholegram.domain.MemberVO;
@Repository
public class SignDAOImpl implements SignDAO {

	@Inject
	private SqlSession sqlSession;
	private static final String namespace="net.nigne.wholegram.mapper.signMapper";
	
	@Override
	public void insert(MemberVO vo) {
		sqlSession.insert(namespace+".setMember", vo);
	}

	@Override
	public List<MemberVO> selectAll() {
		return sqlSession.selectList(namespace+".select");
	}

	@Override
	public int checkId(String id) {
		return sqlSession.selectOne(namespace+".check_id", id);
	}

	@Override
	public int checkEmail(String email) {
		return sqlSession.selectOne(namespace+".check_email", email);
	}

}
