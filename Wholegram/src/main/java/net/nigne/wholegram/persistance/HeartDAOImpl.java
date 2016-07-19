package net.nigne.wholegram.persistance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import net.nigne.wholegram.domain.HeartVO;

@Repository
public class HeartDAOImpl implements HeartDAO {
	@Inject
	private SqlSession session;
	private static final String namespace="net.nigne.wholegram.mappers.heartMapper";
	
	@Override
	public void insertHeart(HeartVO vo) {
		session.insert( namespace + ".insertHeart", vo );
	}

	@Override
	public void deleteHeart(HeartVO vo) {
		session.delete( namespace + ".deleteHeart", vo );
	}

	@Override
	public List<HeartVO> getList(int board_num) {
		return session.selectList( namespace + ".getList", board_num );
	}

	@Override
	public int checkHeart(String user_id, int board_num) {
		Map<String, Object> data = new HashMap<>();
		data.put("user_id", user_id);
		data.put("board_num", board_num);
		return session.selectOne(namespace + ".checkHeart", data);
	}

	@Override
	public List<HeartVO> getaldyList(String user_id) {
		return session.selectList(namespace + ".getaldyList", user_id);
	}

}
