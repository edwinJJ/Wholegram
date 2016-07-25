package net.nigne.wholegram.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.nigne.wholegram.persistance.FollowDAO;

@Service
public class FollowServiceImpl implements FollowService {

	@Inject
	private FollowDAO dao;
	
	/* 현재 접속한 유저가 팔로잉하고있는 user_id를 가져온다 */
	@Override
	public List<String> getFollowing_Userid(String user_id) {
		return dao.getFollowing_Userid(user_id);
	}

}
