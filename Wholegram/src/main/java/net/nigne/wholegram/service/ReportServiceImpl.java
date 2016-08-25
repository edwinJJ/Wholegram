package net.nigne.wholegram.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.nigne.wholegram.domain.ReportVO;
import net.nigne.wholegram.persistance.ReportDAO;

@Service
public class ReportServiceImpl implements ReportService {
	@Inject
	private ReportDAO dao;
	
	@Override
	public List<ReportVO> getUserList(int board_num) {
		return dao.getUserList(board_num);
	}

	@Override
	public List<ReportVO> getList() {
		return dao.getList();
	}

}
