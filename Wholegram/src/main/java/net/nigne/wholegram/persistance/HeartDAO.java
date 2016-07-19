package net.nigne.wholegram.persistance;

import java.util.List;

import net.nigne.wholegram.domain.HeartVO;

public interface HeartDAO {
	public List<HeartVO> getList( int board_num );
	public List<HeartVO> getaldyList(String user_id);
	public void insertHeart( HeartVO vo );
	public void deleteHeart( HeartVO vo );
	public int checkHeart(String user_id, int board_num);
}
