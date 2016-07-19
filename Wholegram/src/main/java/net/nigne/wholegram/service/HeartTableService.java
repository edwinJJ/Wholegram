package net.nigne.wholegram.service;

import org.springframework.stereotype.Service;

import net.nigne.wholegram.common.Status;

@Service
public class HeartTableService {

	/* HeartTable에 추가 시킬지 여부 결정 */
	public Status HeartTableStatus(int getColumn) {
		Status status = new Status();
		if(getColumn == 0) {
			status.setStatus(true);
		} else {
			status.setStatus(false);
		}
		return status;
	}
}