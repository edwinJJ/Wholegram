package net.nigne.wholegram.persistance;

import java.util.List;

import net.nigne.wholegram.domain.BoardVO;
import net.nigne.wholegram.domain.ReplyVO;


public interface ReplyDAO {
	public List<ReplyVO> getList( int board_num );
	public void insert( ReplyVO vo );
	public void delete( int reply_num );
}
