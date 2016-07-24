package net.nigne.wholegram.persistance;

import java.util.List;

public interface FollowDAO {
	public List<String> getFollowing_Userid(String user_id);
}
