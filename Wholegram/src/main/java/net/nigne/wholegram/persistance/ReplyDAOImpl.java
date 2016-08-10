package net.nigne.wholegram.persistance;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import net.nigne.wholegram.common.RepCriteria;
import net.nigne.wholegram.domain.ReplyVO;

@Repository
public class ReplyDAOImpl implements ReplyDAO {
	@Inject
	private SqlSession session;
	private static final String namespace="net.nigne.wholegram.mappers.replyMapper";
	
	@Override
	public List<ReplyVO> getList(int board_num) {
		return session.selectList( namespace + ".getList", board_num );
	}

	@Override
	public void insert(ReplyVO vo) {
		session.insert( namespace + ".insert", vo );
	}

	@Override
	public void delete(int reply_num) {
		session.delete( namespace + ".delete", reply_num);
	}

	@Override
	public List<ReplyVO> getListLimit(RepCriteria rc) {
		return session.selectList( namespace + ".getListLimit", rc );
	}
}
