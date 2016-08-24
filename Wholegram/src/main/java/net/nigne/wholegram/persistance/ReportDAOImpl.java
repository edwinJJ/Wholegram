package net.nigne.wholegram.persistance;

import java.util.List;
import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import net.nigne.wholegram.domain.ReportVO;

@Repository
public class ReportDAOImpl implements ReportDAO {
	@Inject
	private SqlSession session;
	private static final String namespace="net.nigne.wholegram.mappers.AdminMapper";
	
	@Override
	public List<ReportVO> getUserList(int board_num) {	
		return session.selectList( namespace + ".getUserList", board_num );
	}

	@Override
	public List<ReportVO> getList() {
		return session.selectList( namespace + ".getList");
	}

}
