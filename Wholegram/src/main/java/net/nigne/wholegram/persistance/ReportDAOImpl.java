package net.nigne.wholegram.persistance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import net.nigne.wholegram.domain.ReportVO;

@Repository
public class ReportDAOImpl implements ReportDAO {
	@Inject
	private SqlSession session;
	private static final String namespace="net.nigne.wholegram.mappers.AdminMapper";
	private static final String namespace2= "net.nigne.wholegram.mappers.ReportMapper";
	
	@Override
	public List<ReportVO> getUserList(int board_num) {	
		return session.selectList( namespace + ".getUserList", board_num );
	}

	@Override
	public List<ReportVO> getList() {
		return session.selectList( namespace + ".getList");
	}

	@Override
	public int checkReport(String user_id, int board_num) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("user_id", user_id);
		data.put("board_num", board_num);
		
		return session.selectOne(namespace2 + ".checkReport", data);
	}
	
}
