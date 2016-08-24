package net.nigne.wholegram.service;

import java.util.List;
import java.util.Map;

import net.nigne.wholegram.domain.FollowVO;

public interface FollowService {
	public List<String> getFollowing_Userid(String user_id);
	public void followInsert( FollowVO vo );
	public void followDelete( int follow_num, String following );
	public List<FollowVO> getfwList();
	public Map<String, Integer> getFollowNumberof(String user_id);
	public List<FollowVO> getMyFollwingList(String user_id);
	public List<FollowVO> getMyFollwerList(String user_id);
	public boolean followCheck(String user_id,String other_id);
	public void userfollowDelete(FollowVO vo);
	public void statusUpdate(String follower,String following,int flag);
}
