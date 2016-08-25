package net.nigne.wholegram.domain;

public class FollowVO {
	private int follow_num;
	private String following;
	private String follower;
	private int flag;
	private String reg_date;
	
	private int default_profile; 
	
	public int getFollow_num() {
		return follow_num;
	}
	public void setFollow_num(int follow_num) {
		this.follow_num = follow_num;
	}
	public String getFollowing() {
		return following;
	}
	public void setFollowing(String following) {
		this.following = following;
	}
	public String getFollower() {
		return follower;
	}
	public void setFollower(String follower) {
		this.follower = follower;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public int getDefault_profile() {
		return default_profile;
	}
	public void setDefault_profile(int default_profile) {
		this.default_profile = default_profile;
	}
	
}
