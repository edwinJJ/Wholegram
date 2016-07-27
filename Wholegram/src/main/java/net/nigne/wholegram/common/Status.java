package net.nigne.wholegram.common;

/* 어떠한 상태를 true false로 구분해줄 때 사용되는 곳 */
public class Status {
	private boolean success = false;

	public void setStatus(boolean status) {
		success = status;
	}
	
	public boolean isSuccess() {
		return success; 
	}
}
