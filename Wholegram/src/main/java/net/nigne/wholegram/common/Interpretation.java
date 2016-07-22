package net.nigne.wholegram.common;

import java.util.HashMap;

import org.springframework.transaction.annotation.Transactional;

public class Interpretation {

	private int chat_num;
	private String msg_content;
	private String written_user_id;
	private HashMap<String, Object> data = null;

	/*사용자가 메시지를 입력하면 앞에 {num : '방번호'}[write : '작성자Id'] 가 붙기 때문에, 
	방번호와 작성자Id, 그 이후에 나오는 순수 메시지 내용을 구분하여 추출해준다.*/
	@Transactional
	public void interpre_Msg(String msg) {
		chat_num = 0;
		msg_content = "";
		written_user_id = "";
		
		int sub_pos = msg.indexOf("}");
		chat_num = Integer.parseInt(msg.substring(7, sub_pos));		// 방번호
		
		int sub_pos2 = msg.indexOf("]");
		written_user_id = msg.substring(19, sub_pos2);				// 작성자

		msg_content = msg.substring(sub_pos2+1);					// 메시지 내용
		
		data = new HashMap<>();
		data.put("chat_num", chat_num);
		data.put("written_user_id", written_user_id);
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
