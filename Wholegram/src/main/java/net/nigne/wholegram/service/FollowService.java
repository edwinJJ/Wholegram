package net.nigne.wholegram.service;

import java.util.List;

public interface FollowService {
	public List<String> getFollowing_Userid(String user_id);
}
