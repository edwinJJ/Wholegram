package net.nigne.wholegram.service;

import org.springframework.stereotype.Service;

import net.nigne.wholegram.common.Status;

@Service
public class LoginService {

	/* 로그인 성공 여부 설정*/
	public Status LoginStatus(int getMember) {
		Status status = new Status();
		
		if(getMember == 1) {
			status.setStatus(true);
		} else {
			status.setStatus(false);
		}
		return status;
	}
}
