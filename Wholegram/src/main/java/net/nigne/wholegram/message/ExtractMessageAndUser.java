package net.nigne.wholegram.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;

import com.google.gson.Gson;

import net.nigne.wholegram.domain.Chat_userVO;
import net.nigne.wholegram.domain.Msg_listVO;
import net.nigne.wholegram.service.ChatService;

@Transactional
public class ExtractMessageAndUser {

	@Inject
	private ChatService chatservice;
	
	@Inject
	private Application application;

	private List<Chat_userVO> userList;
	
	public String InterPre(WebSocketSession session, String msg) {
		System.out.println("test1");
		Interpretation interpre = new Interpretation();					// msg 해석
		interpre.interpre_Msg(msg);								
		System.out.println("test2");
		HashMap<String, Object> data = interpre.getinfo_Msg();			// (채팅방 번호 / 작성자ID / 메시지내용)
		
/*		Iterator<String> it = data.keySet().iterator();
		while(it.hasNext()){
			String temp = it.next();
			System.out.println("key : " + temp);
			System.out.println("value : " + data.get(temp));
		}*/
		
		chatservice.msgStorage(data);									// 본인이 해당된 채팅방에 메시지 저장
		
		int chat_num = interpre.getmsg_Chatnum();						// (채팅방 번호)
		System.out.println("test3");
		List<Msg_listVO> msglist = new ArrayList<Msg_listVO>();			// 본인이 해당된 채팅방으로부터 메시지 꺼내옴	
		System.out.println("test3.5");
		msglist = chatservice.msgGet(chat_num);							
		System.out.println("test4");
		userList = chatservice.userList(chat_num);						// 채팅방에 해당되는 유저 List를 가져옴
		System.out.println("test5");
		MessageJSON mj = new MessageJSON();
		String result = mj.GSON(msglist);								// json으로 변환
		System.out.println("test6");
		return result;
	}	
	
	public List<Map<String, Object>> ExtractUser() {
		
		List<Map<String, Object>> tidyUserInfo = new ArrayList<Map<String, Object>>();		// 메시지 받을 유저를 담을 변수
		
/*		application에 담겨있는 현재 접속되어있는 (유저ID, WebSocket session) -> userInfo 를 가져와서
		메시지를 보낼 유저 ID만을 담고있는 userList와 비교하여, 일치하는 값을 새로운 List로 -> tidyUserInfo로 만들어 return 해준다.*/
		List<Map<String, Object>> userInfo = application.getUserInfo();						
		Iterator<Map<String, Object>> extract = userInfo.iterator();
		Iterator<Chat_userVO> extract2 = userList.iterator();
		while(extract.hasNext()) {															// 1 : WebSocket 접속자들 정보 추출 (id, WebSocket session)
			Map<String, Object> mapData = new HashMap<String, Object>();
			mapData = extract.next();
			while(extract2.hasNext()) {														// 2 : 메시지 받을 유저들 정보 (id)
				Chat_userVO voData= new Chat_userVO();
				voData = extract2.next();
				
				if(mapData.containsKey(voData.getMember_user_id())) {						// 1 과 2를 비교하여 일치할 경우 (현재 접속자들 중에 메시지 받을 유저가 있을경우)
					String key = voData.getMember_user_id();
					WebSocketSession tidySession = (WebSocketSession) mapData.get(key);
					
					Map<String, Object> tidyMapData = new HashMap<>();
					tidyMapData.put(key, tidySession);
					tidyUserInfo.add(tidyMapData);											// 3 : 메시지 받을 유저정보 생성 (id, WebSocket session)
				}
				
			}
		}
		return tidyUserInfo;
	}
	
	
	class MessageJSON {
		public String GSON(List<Msg_listVO> msglist) {	// JSON형태로 변환
			Gson gson = new Gson();
			String result = gson.toJson(msglist);	
			return result;
		}
	}
}
