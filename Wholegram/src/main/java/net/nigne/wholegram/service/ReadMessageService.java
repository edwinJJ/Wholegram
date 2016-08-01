package net.nigne.wholegram.service;

import org.springframework.stereotype.Service;

import net.nigne.wholegram.common.Status;

@Service
public class ReadMessageService {

	/*  1. 메시지를 읽은 유저로 등록할지 여부 결정 -> 0이면 등록이 안되있는 상태인 것  */	
	/*	2. 최신 메시지를 읽은 채팅방인지 구별      -> 0이면 최신 메시지를 읽지 않은 것 */	
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
