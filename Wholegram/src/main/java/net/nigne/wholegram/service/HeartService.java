package net.nigne.wholegram.service;

import java.util.List;

import org.springframework.stereotype.Service;

import net.nigne.wholegram.domain.HeartVO;

@Service
public interface HeartService {
	public List<HeartVO> getList( int board_num );
	public List<HeartVO> getaldyList(String user_id);
	public void insertHeart( HeartVO vo );
	public void deleteHeart( HeartVO vo );
	public int checkHeart(String user_id, int board_num);
}
