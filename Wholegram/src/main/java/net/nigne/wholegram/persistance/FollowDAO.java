package net.nigne.wholegram.persistance;

import java.util.List;

import net.nigne.wholegram.domain.FollowVO;
import net.nigne.wholegram.domain.MemberVO;


public interface FollowDAO {
	public List<String> getFollowing_Userid(String user_id);
	public void followInsert( FollowVO vo );
	public void followDelete( int follow_num, String following );
	public List<FollowVO> getfwList( FollowVO vo );
}
