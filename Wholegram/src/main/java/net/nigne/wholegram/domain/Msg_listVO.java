package net.nigne.wholegram.domain;

public class Msg_listVO {
	private int chat_chat_num;
	private String msg;
	private String written_user_id;
	private String read_user_ids;
	private String date;
	
	public int getChat_chat_num() {
		return chat_chat_num;
	}
	public void setChat_chat_num(int chat_chat_num) {
		this.chat_chat_num = chat_chat_num;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getWritten_user_id() {
		return written_user_id;
	}
	public void setWritten_user_id(String written_user_id) {
		this.written_user_id = written_user_id;
	}
	public String getRead_user_ids() {
		return read_user_ids;
	}
	public void setRead_user_ids(String read_user_ids) {
		this.read_user_ids = read_user_ids;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
}
