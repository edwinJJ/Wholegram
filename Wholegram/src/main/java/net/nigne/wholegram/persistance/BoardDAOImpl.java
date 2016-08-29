package net.nigne.wholegram.persistance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.nigne.wholegram.common.Criteria;
import net.nigne.wholegram.common.HashTagScrollCriteria;
import net.nigne.wholegram.domain.BoardVO;
import net.nigne.wholegram.domain.MemberVO;

@Repository
public class BoardDAOImpl implements BoardDAO {
	@Inject
	private SqlSession session;
	private static final String namespace="net.nigne.wholegram.mappers.boardMapper";
	private static final String namespace2 = "net.nigne.wholegram.mappers.UploadMapper";
	private static final String namespace3 = "net.nigne.wholegram.mappers.replyMapper";
	private static final String namespace4 = "net.nigne.wholegram.mappers.NoticeMapper";
	private static final String namespace5 = "net.nigne.wholegram.mappers.heartMapper";
	private static final String namespace6 = "net.nigne.wholegram.mappers.AdminMapper";

	
	@Override
	public List<BoardVO> getList( String user_id, int startNum, int pagePerBlock ) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "startNum", startNum );
		map.put( "pagePerBlock", pagePerBlock );
		map.put( "user_id", user_id );

		return session.selectList( namespace + ".getList", map );
	}

	@Override
	public List<BoardVO> get(List<MemberVO> mList) {
		
		Iterator<MemberVO> mIterator = mList.iterator();		
		List<BoardVO> bList = new ArrayList<BoardVO>();
		
		while(mIterator.hasNext()) {		

			MemberVO mv = new MemberVO();
			mv = mIterator.next();
			bList.add( (BoardVO) session.selectOne( namespace + ".get",mv) );
		}
		
		return bList;
	}
	@Override
	public BoardVO getOne( BoardVO vo ) {
		return session.selectOne( namespace + ".getOne", vo );
	}
	
	@Override
	public void heartCount(int board_num, int criteria) {
		
		Map<String, Object> data = new HashMap<>();
		data.put("bnum", board_num);
		data.put("cri", criteria);
		session.selectOne( namespace + ".heartCount", data);
	}

	@Override
	public int getHeart(int board_num) {
		return session.selectOne(namespace + ".getHeart", board_num);
	}

	@Override
	public List<List<BoardVO>> getbdList(List<MemberVO> mbList) {
		Iterator<MemberVO> mIterator = mbList.iterator();		
		List<List<BoardVO>> bdList = new ArrayList<List<BoardVO>>();
		
		while(mIterator.hasNext()) {	
			List<BoardVO> bdList2 = new ArrayList<BoardVO>();
			MemberVO mv2 = new MemberVO();
			mv2 = mIterator.next();
			bdList2 = session.selectList( namespace + ".getbdList", mv2 );
			bdList.add(bdList2);
		}
		
		return bdList;
	}
	
	@Transactional
	@Override
	public void BoardUP(BoardVO vo) {
		session.insert(namespace2 +".Boardup", vo);
	}

	@Override
	public int getTotalCount() {
		return session.selectOne( namespace + ".getTotalCount" );
	}

	@Override
	public long getTime(int board_num) {
		return session.selectOne( namespace + ".getTime", board_num);
	}	
	@Override
	public List<BoardVO> getUserLimitList(MemberVO vo) {
		return session.selectList(namespace+".getUserLimitList",vo);
	}

	@Override
	public List<BoardVO> getScrollList(Criteria cr) {
		return session.selectList(namespace+".getScrollList", cr);
	}

	@Override
	public int getUserCount(Criteria cr) {
		return session.selectOne(namespace+".getUserCount", cr);
	}

	@Override
	public List<BoardVO> searchIterate(List<String> list) {
		return session.selectList(namespace+".SearchIterate",list);
	}

	@Override
	public int searchCount(List<String> list) {
		
		return session.selectOne(namespace+".searchCount", list);
	}

	@Override
	public List<BoardVO> SearchScrollIterate(HashTagScrollCriteria list) {
		return session.selectList(namespace+".SearchScrollIterate",list);
	}

	@Override
	public String getThunmnail(int board_num) {
		return session.selectOne(namespace + ".getThunmnail", board_num);
	}

	@Override
	public int getBoardNum(BoardVO vo) {
		return session.selectOne(namespace+".getBoardNum", vo);
	}

	@Override
    public BoardVO boardList(int board_num) {
       return session.selectOne( namespace + ".boardList", board_num );
    }
	
	@Override
   public void report(String user_id, int board_num) {
      
      BoardVO vo = session.selectOne(namespace + ".getOne", board_num);
      
      Map<String, Object> map = new HashMap<String, Object>();
      map.put( "user_id", user_id );
      map.put( "other_id", vo.getUser_id() );
      map.put( "board_num", board_num );
      session.insert( namespace + ".report", map );
   }

   @Override
   public List<BoardVO> getReportList() {
      return session.selectList( namespace + ".getReportList");
   }

   @Override
   public void reportCount(int board_num) {
      session.selectOne( namespace + ".reportCount", board_num );
   }
   
   @Transactional
   @Override
	public void deleteAll(int board_num) {
	   System.out.println("a");
		session.delete( namespace4 + ".notice_delete", board_num ); // Notice
		System.out.println("b");
		session.delete( namespace5 + ".delete", board_num ); // Heart
		System.out.println("b");
		session.delete( namespace6 + ".delete", board_num ); // Report
		System.out.println("b");
		session.delete( namespace3 + ".replyDelete", board_num ); // Reply
		System.out.println("b");
		session.delete( namespace + ".delete", board_num ); // Board
	}

	@Override
	public void reportDelete(String user_id, int board_num) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("user_id", user_id);
		data.put("board_num", board_num);
		session.delete(namespace4 + ".reportDelete", data);
	}

	@Override
	public void reportDecrease(int board_num) {
		Map<String, Integer> data = new HashMap<String, Integer>();
		
		data.put("board_num", board_num);
		data.put("decrease", -1);
		session.update(namespace + ".reportDecrease", data);
	}

}
