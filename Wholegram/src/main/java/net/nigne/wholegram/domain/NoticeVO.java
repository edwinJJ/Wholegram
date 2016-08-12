package net.nigne.wholegram.domain;

public class NoticeVO {
	private int notice_num;
	private String user_id;
	private String other_id;
	private String refer_content;
	private int board_num;
	private String media;
	private int flag;
	private String date;
	
	public int getNotice_num() {
		return notice_num;
	}
	public void setNotice_num(int notice_num) {
		this.notice_num = notice_num;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getOther_id() {
		return other_id;
	}
	public void setOther_id(String other_id) {
		this.other_id = other_id;
	}
	public String getRefer_content() {
		return refer_content;
	}
	public void setRefer_content(String refer_content) {
		this.refer_content = refer_content;
	}
	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	public String getMedia() {
		return media;
	}
	public void setMedia(String media) {
		this.media = media;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
}
