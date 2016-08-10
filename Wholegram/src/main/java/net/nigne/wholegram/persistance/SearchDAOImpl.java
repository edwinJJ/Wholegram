package net.nigne.wholegram.persistance;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import net.nigne.wholegram.domain.BoardVO;
import net.nigne.wholegram.domain.MemberVO;
@Repository
public class SearchDAOImpl implements SearchDAO {
	
	@Inject
	private SqlSession sqlSession;
	
	private static final String namespace="net.nigne.wholegram.mappers.searchMapper";
	
	@Override
	public List<MemberVO> getSearch(String idx) {
		return sqlSession.selectList(namespace+".getSearch",idx);
	}

	@Override
	public int getHashSearchCount(String idx) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(namespace+".getHashSearchCount",idx);
	}

	@Override
	public List<BoardVO> getHashSearch(String idx) {
		return sqlSession.selectList(namespace+".getHashSearch",idx);
	}

}
