package net.nigne.wholegram.service;

import java.util.HashMap;
import java.util.Map;

public interface ProfileImageService {
	public void setProfileImage(HashMap<String, Object>	profileImage);
	public byte[] getProfileImage(String user_id);
}
