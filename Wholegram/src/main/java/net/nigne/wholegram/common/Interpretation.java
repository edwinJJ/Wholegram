package net.nigne.wholegram.common;

import java.util.HashMap;

public class Interpretation {

	private int chat_num;
	private String msg_content;
	private HashMap<String, Object> data = null;

	/*사용자가 메시지를 입력하면 앞에 {num : '방번호'} 가 붙기 때문에, 
	방번호와 그 이후에 나오는 순수 메시지 내용을 구분하여 추출해준다.*/
	public void interpre_Msg(String msg) {
		chat_num = 0;
		msg_content = "";
		
		int sub_pos = msg.indexOf("}");
		chat_num = Integer.parseInt(msg.substring(7, sub_pos));	// 방번호
		msg_content = msg.substring(sub_pos+1);					// 메시지 내용
		
		data = new HashMap<>();
		data.put("chat_num", chat_num);
		data.put("msg_content", msg_content);
	}
	
	/* 채팅방 번호 return */
	public int getmsg_Chatnum() {
		return chat_num;
	}
	
	public HashMap<String, Object> getinfo_Msg() {
		return data;
	}
}
