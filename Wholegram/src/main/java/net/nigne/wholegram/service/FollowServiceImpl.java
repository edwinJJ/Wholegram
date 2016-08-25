package net.nigne.wholegram.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.nigne.wholegram.domain.FollowVO;
import net.nigne.wholegram.persistance.FollowDAO;
import net.nigne.wholegram.persistance.MemberDAO;

@Service
public class FollowServiceImpl implements FollowService {

	@Inject
	private FollowDAO dao;
	
	@Inject
	private MemberDAO mdao;
	
	/* 유저가 팔로잉하고있는 user_id를 가져온다 */
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
	public List<FollowVO> getfwList() {
		return dao.getfwList();
	}

	@Override
	public Map<String, Integer> getFollowNumberof(String user_id) {
		return dao.getFollowNumberof(user_id);
	}
	
	@Override
	public List<FollowVO> getMyFollwingList(String user_id) {
		return dao.getMyFollowingList(user_id);
	}

	@Override
	public List<FollowVO> getMyFollwerList(String user_id) {
		List<FollowVO> result = new ArrayList<FollowVO>();
		
		List<FollowVO> lfvo = dao.getMyFollowerList(user_id);	// 유저가 팔로우하고있는 유저list
		Iterator<FollowVO> extract = lfvo.iterator();
		while(extract.hasNext()){
			FollowVO fvo = new FollowVO();
			fvo = extract.next();
			int default_profile = mdao.getUserprofileInfo(fvo.getFollower());	// 유저가 팔로우하고있는 각각 유저의 프로필이미지 존재여부
			fvo.setDefault_profile(default_profile);
			result.add(fvo);
		}
		return result;
	}

	@Override
	public boolean followCheck(String user_id, String other_id) {
		Map<String,String> map = new HashMap<>();
		map.put("user_id", user_id);
		map.put("other_id", other_id);
		return dao.followCheck(map);
	}

	@Override
	public void userfollowDelete(FollowVO vo) {
		dao.userfollowDelete(vo);
		
	}

	@Override
	public void statusUpdate(String follower,String following,int flag) {
		Map<String,Object> map = new HashMap<>();
		map.put("follower", follower);
		map.put("following", following);
		map.put("flag", flag);
		dao.statusUpdate(map);
	}
}
