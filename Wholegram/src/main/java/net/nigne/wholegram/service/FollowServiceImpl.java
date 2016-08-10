package net.nigne.wholegram.service;

import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import net.nigne.wholegram.domain.FollowVO;
import net.nigne.wholegram.domain.MemberVO;
import net.nigne.wholegram.persistance.FollowDAO;

@Service
public class FollowServiceImpl implements FollowService {

	@Inject
	private FollowDAO dao;
	
	/* �쁽�옱 �젒�냽�븳 �쑀��媛� �뙏濡쒖엵�븯怨좎엳�뒗 user_id瑜� 媛��졇�삩�떎 */
	@Override
	public List<String> getFollowing_Userid(String user_id) {
		return dao.getFollowing_Userid(user_id);
	}

	@Override
	public void followInsert(FollowVO vo) {
		dao.followInsert(vo);
	}

	@Override
	public void followDelete(int follow_num, String following ) {
		dao.followDelete(follow_num, following);
	}

	@Override
	public List<FollowVO> getfwList( FollowVO vo ) {
		return dao.getfwList(vo);
	}
}
