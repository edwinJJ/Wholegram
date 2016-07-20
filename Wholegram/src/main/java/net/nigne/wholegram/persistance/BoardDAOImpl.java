package net.nigne.wholegram.persistance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import net.nigne.wholegram.domain.BoardVO;

@Repository
public class BoardDAOImpl implements BoardDAO {
	@Inject
	private SqlSession session;
	private static final String namespace="net.nigne.wholegram.mappers.boardMapper";
	private static final String namespace2 = "net.nigne.wholegram.mapper.UploadMapper";
	
	@Override
	public List<BoardVO> getList() {
		return session.selectList( namespace + ".getList" );
	}

	@Override
	public BoardVO get( BoardVO vo ) {
		return session.selectOne( namespace + ".get", vo );
	}

	@Override
	public void heartCount(int board_num, int criteria) {
		
		Map<String, Object> data = new HashMap<>();
		data.put("bnum", board_num);
		data.put("cri", criteria);
		session.selectOne( namespace + ".heartCount", data);
	}

	@Override
	public int getHeart(int board_num) {
		return session.selectOne(namespace + ".getHeart", board_num);
	}

	@Override
	public int abc() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void BoardUP(BoardVO vo) {
		session.insert(namespace2 +".Boardup", vo);
		
	}
}
