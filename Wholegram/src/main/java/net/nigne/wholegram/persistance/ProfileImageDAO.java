package net.nigne.wholegram.persistance;

import java.util.HashMap;

public interface ProfileImageDAO {
	public void setProfileImage(HashMap<String, Object> profileImage);
	public byte[] getProfileImage(String user_id);
}
