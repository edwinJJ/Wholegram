package net.nigne.wholegram.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.web.socket.WebSocketSession;

@Repository
public class Application {
//	private static List<String> user_list = new ArrayList<>();
	
	// 접속자 ID 임시 저장 변수
	private String user_id = "";
	
	// 접속자 ID , session 정보를 List에 담아둔다
	private List<Map<String, Object>> userInfo = new ArrayList<Map<String, Object>>();
	
	// 생성방지
	private Application(){}
	
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public List<Map<String, Object>> getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(Map<String, Object> userInfo) {
		this.userInfo.add(userInfo);
	}
	
	public void delUserInfo(WebSocketSession session) {
		int count = 0;
		int result_count = 0;
		Iterator<Map<String, Object>> extract = userInfo.iterator();
		while(extract.hasNext()) {
			Map<String, Object> data = new HashMap<String, Object>();
			data = extract.next();
			if(data.containsValue(session)) {
				result_count = count;
			}
			count++;
		}
		userInfo.remove(result_count);
	}
	
	
}
