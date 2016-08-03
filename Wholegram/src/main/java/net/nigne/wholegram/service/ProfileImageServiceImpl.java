package net.nigne.wholegram.service;

import java.util.HashMap;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.nigne.wholegram.persistance.ProfileImageDAO;

@Service
public class ProfileImageServiceImpl implements ProfileImageService {

	@Inject
	ProfileImageDAO dao;
	
	@Override
	public void setProfileImage(HashMap<String, Object> profileImage) {
		dao.setProfileImage(profileImage);
	}

	@Override
	public byte[] getProfileImage(String user_id) {
		return dao.getProfileImage(user_id);
	}

}
