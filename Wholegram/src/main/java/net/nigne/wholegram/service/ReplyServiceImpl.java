package net.nigne.wholegram.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.nigne.wholegram.common.RepCriteria;
import net.nigne.wholegram.domain.ReplyVO;
import net.nigne.wholegram.persistance.ReplyDAO;

@Service
public class ReplyServiceImpl implements ReplyService {
	@Inject
	private ReplyDAO dao;
	
	@Override
	public List<ReplyVO> getList(int board_num) {
		return dao.getList( board_num );
	}

	@Override
	public int insert(ReplyVO vo) {
		return dao.insert(vo);
	}

	@Override
	public void delete(int reply_num) {
		dao.delete(reply_num);	
	}
	
	@Override
	public List<ReplyVO> getListLimit(RepCriteria rc) {
		// TODO Auto-generated method stub
		return dao.getListLimit(rc);
	}
}
