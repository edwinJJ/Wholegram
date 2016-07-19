package net.nigne.wholegram.common;
 
public class Status {
	private boolean success = false;

	public void setStatus(boolean status) {
		success = status;
	}
	
	public boolean isSuccess() {
		return success; 
	}
}
