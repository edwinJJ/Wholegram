package net.nigne.wholegram.domain;

public class BoardVO {
	private int board_num;
	private String user_id;
	private String content;
	private String media;
	private String reg_date;
	private String place;
	private int heart;
	private String media_type;
	private String tag;
	private boolean aldy_heart;
	private String media_thumnail;
	private int report;
	
	private int default_profile;		// 게시물 보여줄때, 유저 프로필사진이 있는지 없는지 구분하기위해 추가 (DB member테이블에 있는 컬럼)
	
	public int getBoard_num() {
		return board_num;
	}
	public void setBoard_num(int board_num) {
		this.board_num = board_num;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMedia() {
		return media;
	}
	public void setMedia(String media) {
		this.media = media;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public int getHeart() {
		return heart;
	}
	public void setHeart(int heart) {
		this.heart = heart;
	}
	public String getMedia_type() {
		return media_type;
	}
	public void setMedia_type(String media_type) {
		this.media_type = media_type;
	}
	public void setAldy_heart(boolean aldy_heart) {
		this.aldy_heart = aldy_heart;
	}
	public boolean isAldy_heart() {
		return aldy_heart;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getMedia_thumnail() {
		return media_thumnail;
	}
	public void setMedia_thumnail(String media_thumnail) {
		this.media_thumnail = media_thumnail;
	}
	public int getReport() {
		return report;
	}
	public void setReport(int report) {
		this.report = report;
	}
	public int getDefault_profile() {
		return default_profile;
	}
	public void setDefault_profile(int default_profile) {
		this.default_profile = default_profile;
	}
	
}
