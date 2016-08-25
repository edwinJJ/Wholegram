package net.nigne.wholegram.service;

import java.util.List;

import org.springframework.stereotype.Service;

import net.nigne.wholegram.domain.ReportVO;

@Service
public interface ReportService {
	public List<ReportVO> getUserList( int board_num );
	public List<ReportVO> getList();
}
