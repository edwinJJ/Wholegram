package net.nigne.wholegram.service;

import org.springframework.stereotype.Service;

import net.nigne.wholegram.common.Status;

@Service
public class ReadMessageService {

	/* 메시지를 읽은 유저로 등록할지 여부 결정 */
	public Status ReadMessageStatus(int count) {
		Status status = new Status();
		
		if(count == 0) {
			status.setStatus(true);
		} else {
			status.setStatus(false);
		}
		return status;
	}
}
