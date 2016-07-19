package net.nigne.wholegram.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.nigne.wholegram.domain.BoardVO;
import net.nigne.wholegram.domain.HeartVO;
import net.nigne.wholegram.persistance.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService {
	@Inject
	private BoardDAO dao;
	
	@Override
	public List<BoardVO> getList(List<HeartVO> hList) {
		List<BoardVO> bList = dao.getList();
		Iterator<BoardVO> it_bList = bList.iterator();
		ArrayList<BoardVO> bhList = new ArrayList<BoardVO>();
		
		while(it_bList.hasNext()) {
			BoardVO bv = it_bList.next();
			Iterator<HeartVO> it_hList = hList.iterator();
			while(it_hList.hasNext()) {
				HeartVO hv = it_hList.next();
				if(bv.getBoard_num() == hv.getBoard_num()) {
					bv.setAldy_heart(true);
				} 
				else if((bv.getBoard_num() != hv.getBoard_num()) && (bv.isAldy_heart() != true) ){
					bv.setAldy_heart(false);		
				}
			}
			bhList.add(bv);
		}
		
		return bhList;
	}

	@Override
	public BoardVO get( BoardVO vo ) {
		return dao.get( vo );
	}

	/* 게시물 좋아요 (+1/-1) */
	@Override
	public void heartCount(int board_num, int criteria) {
		dao.heartCount(board_num, criteria);
	}

	/* 게시물의 좋아요 수 */
	@Override
	public int getHeart(int board_num) {
		return dao.getHeart(board_num);
	}

	/* 사용자가 이미 좋아요를 누른 게시물인지 구분 */
	@Override
	public List<HeartVO> getHeartList(List<BoardVO> bList, List<HeartVO> hList) {
		Iterator<BoardVO> it_bList = bList.iterator();
		ArrayList<HeartVO> heartList = new ArrayList<HeartVO>();
		
		while(it_bList.hasNext()) {
			BoardVO bv = it_bList.next();
			Iterator<HeartVO> it_hList = hList.iterator();
			while(it_hList.hasNext()) {
				HeartVO hv = it_hList.next();
				if(bv.getBoard_num() == hv.getBoard_num()) {
					heartList.add(hv);
				}
			}
		}
		return heartList;
	}

	@Override
	public void BoardUP(BoardVO vo) {
		dao.BoardUP(vo);
		
	}

}
