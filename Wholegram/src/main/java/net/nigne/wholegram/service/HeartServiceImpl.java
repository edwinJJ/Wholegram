package net.nigne.wholegram.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.nigne.wholegram.domain.HeartVO;
import net.nigne.wholegram.persistance.HeartDAO;

@Service
public class HeartServiceImpl implements HeartService {
	@Inject
	private HeartDAO dao;
	
	@Override
	public void insertHeart(HeartVO vo) {
		dao.insertHeart(vo);
	}

	@Override
	public void deleteHeart(HeartVO vo) {
		dao.deleteHeart(vo);
	}

	@Override
	public List<HeartVO> getList(int board_num) {
		return dao.getList(board_num);
	}

	/* 현재 좋아요 누른 게시물이 유저가 이미 좋아요 누른 게시물이 있는지 확인 */
	@Override
	public int checkHeart(String user_id, int board_num) {
		return dao.checkHeart(user_id, board_num);
	}

	/*'이미 누른 좋아요' 게시물 확인*/
	@Override
	public List<HeartVO> getaldyList(String user_id) {
		return dao.getaldyList(user_id);
	}
}
