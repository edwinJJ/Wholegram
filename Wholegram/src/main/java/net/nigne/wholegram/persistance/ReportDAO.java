package net.nigne.wholegram.persistance;

import java.util.List;

import net.nigne.wholegram.domain.ReportVO;

public interface ReportDAO {
	public List<ReportVO> getUserList( int board_num );
	public List<ReportVO> getList();
}
