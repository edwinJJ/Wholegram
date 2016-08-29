package net.nigne.wholegram.common;

import org.springframework.stereotype.Service;

@Service
public class TableService {

	/* HeartTable에 추가 시킬지 여부 결정 */
	public Status TableStatus(int getColumn) {
		Status status = new Status();
		if(getColumn == 0) {
			status.setStatus(true);
		} else {
			status.setStatus(false);
		}
		return status;
	}
}