package net.nigne.wholegram.persistance;

import java.util.List;

import net.nigne.wholegram.common.RepCriteria;
import net.nigne.wholegram.domain.ReplyVO;


public interface ReplyDAO {
	public List<ReplyVO> getList( int board_num );
	public int insert( ReplyVO vo );
	public void delete( int reply_num );
	public List<ReplyVO> getListLimit(RepCriteria rc);
}
