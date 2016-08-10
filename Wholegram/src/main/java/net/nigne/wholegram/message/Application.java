package net.nigne.wholegram.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Repository;
import org.springframework.web.socket.WebSocketSession;

/*웹소켓 접속자들(로그인 되어있는 유저들) Id와 WebSocket session을 관리하고있는 곳ㅁ*/
@Repository
public class Application {
	
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
/*		Iterator<Map<String, Object>> extract = this.userInfo.iterator();
		while(extract.hasNext()) {
			Map<String, Object> extract2 = new HashMap<String, Object>();
			extract2 = extract.next();
			extract2.get(key)
		}
*/		this.userInfo.add(userInfo);

		int count = 0;
		List<Map<String, Object>> test = this.userInfo;
		Iterator<Map<String,Object>> it = test.iterator();
		while(it.hasNext()) {
			Map<String,Object> mp = new HashMap<String, Object>();
			mp = it.next();
			Iterator itt = mp.entrySet().iterator();
			while(itt.hasNext()) {
				Entry entry = (Entry) itt.next();
				System.out.println(count + "(접속)웹소켓 접속자 현황 - ID : " + entry.getKey() + " , session : " + entry.getValue());
				count++;
			}
		}
	}
	
	// 웹소켓 접속이 끊겼을때, 관리 목록에서 제거해준다
	public void delUserInfo(WebSocketSession session) {
		int count = 0;
		int result_count = -1;
		Iterator<Map<String, Object>> extract = userInfo.iterator();
		while(extract.hasNext()) {
			Map<String, Object> data = new HashMap<String, Object>();
			data = extract.next();
			if(data.containsValue(session)) {
				result_count = count;
			}
			count++;
		}
		if(result_count != -1) {
			userInfo.remove(result_count);
		}
		
/*		int c = 0;
		List<Map<String, Object>> test = userInfo;
		Iterator<Map<String,Object>> it = test.iterator();
		while(it.hasNext()) {
			Map<String,Object> mp = new HashMap<String, Object>();
			mp = it.next();
			Iterator itt = mp.entrySet().iterator();
			while(itt.hasNext()) {
				Entry entry = (Entry) itt.next();
				System.out.println(c + "(삭제후)웹소켓 접속자 현황 - ID : " + entry.getKey() + " , session : " + entry.getValue());
				c++;
			}
		}*/
	}
}
