package net.nigne.wholegram.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.nigne.wholegram.common.Criteria;
import net.nigne.wholegram.common.HashTagScrollCriteria;
import net.nigne.wholegram.domain.BoardVO;
import net.nigne.wholegram.domain.HeartVO;
import net.nigne.wholegram.domain.MemberVO;
import net.nigne.wholegram.persistance.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService {
	@Inject
	private BoardDAO dao;
	
/*	전체(혹은 일부)게시물 목록을 담고있는 bList와 사용자가 좋아요를 누른 게시물 목록만 가지고있는 hList를 비교하여
	같은 게시물 번호를 있다면 그 게시물에 setAldy_heart를 true를 적용시켜, view단에서 이를 활용하여, 좋아요 누른 게시물, 누르지 않은 게시물을 나타내준다.*/
	@Transactional
	@Override
	public List<BoardVO> getList(List<HeartVO> hList, String user_id, int startNum, int pagePerBlock ) {
		List<BoardVO> bList = dao.getList( user_id, startNum, pagePerBlock );
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
	@Transactional
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

	//전체 게시물의 개수 가져오기
	@Override
	public int getTotalCount() {
		return dao.getTotalCount();
	}

	
	@Override
	public long getTime(int board_num) {
		return dao.getTime(board_num);
	}

	
	@Override
	public List<BoardVO> get(List<MemberVO> mList) {
		return dao.get(mList);
	}

	
	@Override
	public List<List<BoardVO>> getbdList(List<MemberVO> mbList) {
		return dao.getbdList(mbList);
	}

	// 게시물 업로드
	@Override
	public void BoardUP(BoardVO vo) {
		dao.BoardUP(vo);	
	}

	// user 화면 로딩 되었을경우 첫번째로 게시물을 가져옴
	@Override
	public List<BoardVO> getUserLimitList(MemberVO vo) {
		return dao.getUserLimitList(vo);
	}

	
	// user 화면에서 스크롤 끝가지 갔을 경우 게시물을 추가로 가져옴
	@Override
	public List<BoardVO> getScrollList(Criteria cr) {
		return dao.getScrollList(cr);
	}

	// 해당유저의 모든 게시물의 갯수 가져옴
	@Override
	public int getUserCount(Criteria cr) {
		return dao.getUserCount(cr);
	}

	// 해시태그 키워드들을 검색
	@Override
	public List<BoardVO> searchIterate(List<String> list) {
		return dao.searchIterate(list);
	}

	// 해시태그 키워드를 사용한 게시물 개수
	@Override
	public int searchCount(List<String> list) {
		return dao.searchCount(list);
	}

	// 해시태그 검색결과에서 스크롤 처리
	@Override
	public List<BoardVO> SearchScrollIterate(HashTagScrollCriteria list) {
		return dao.SearchScrollIterate(list);
	}

	//user화면에서 유저의 정보 가져옴
	@Override
	public BoardVO getOne(BoardVO vo) {
		return	dao.getOne( vo );
	}

}
