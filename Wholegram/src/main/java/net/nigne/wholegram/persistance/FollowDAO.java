package net.nigne.wholegram.persistance;

import java.util.List;
import java.util.Map;

import net.nigne.wholegram.domain.FollowVO;


public interface FollowDAO {
	public List<String> getFollowing_Userid(String user_id);
	public void followInsert( FollowVO vo );
	public void followDelete( int follow_num, String following );
	public List<FollowVO> getfwList();
	public Map<String, Integer> getFollowNumberof(String user_id);
}
