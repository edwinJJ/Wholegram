package net.nigne.wholegram.domain;

public class Chat_userVO {
	private int chat_chat_num;
	private String member_user_id;
	
	private boolean msgNotice;		// 메시지 알림을 위해 DB에 없는 컬럼을 추가
	
	public int getChat_chat_num() {
		return chat_chat_num;
	}
	public void setChat_chat_num(int chat_chat_num) {
		this.chat_chat_num = chat_chat_num;
	}
	public String getMember_user_id() {
		return member_user_id;
	}
	public void setMember_user_id(String member_user_id) {
		this.member_user_id = member_user_id;
	}
	public boolean isMsgNotice() {
		return msgNotice;
	}
	public void setMsgNotice(boolean msgNotice) {
		this.msgNotice = msgNotice;
	}
	
	
}
