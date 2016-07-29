package net.nigne.wholegram.message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;

import net.nigne.wholegram.common.Application;
import net.nigne.wholegram.common.DistinguishHandleMessage;
import net.nigne.wholegram.common.Interpretation;
import net.nigne.wholegram.common.Status;
import net.nigne.wholegram.domain.Chat_userVO;
import net.nigne.wholegram.domain.Msg_listVO;
import net.nigne.wholegram.service.ChatService;

@RequestMapping("/chat")
public class WSHandler extends TextWebSocketHandler {

	private static final Logger logger = LoggerFactory.getLogger(WSHandler.class);

	// 전체 접속자 정보
	private Set<WebSocketSession> wsSession = new HashSet<WebSocketSession>();
	
	@Inject
	private Application application;

	@Inject
	private ChatService chatservice;

	private final String Login = "Login";
	private final String Notic = "Notic";
	
	public WSHandler() {
		logger.info("웹소켓 생성자입니다");
	}
	
	/*연결 됫을 때*/
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("연결");
		//afterPropertiesSet(session);
		
		//사용자 정보를 담는다.
		wsSession.add(session);
		super.afterConnectionEstablished(session);
	}
	
	/*연결이 끊어 졌을 때*/
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("끊김");
		
		// 접속자ID와, WebSocket session 제거
		application.delUserInfo(session);
		
		//사용자 정보 삭제
		wsSession.remove(session);
		super.afterConnectionClosed(session, status);
	}

	/*메시지 전송됫을 때*/
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		
		/* 웹소켓 접속을 알리는 메시지인지, 유저들간의 메시지 통신을 위한 메시지인지 구별과정 */
		String msgtoString = message.getPayload().toString();				// 메시지만을 담은 String으로 변환 
		DistinguishHandleMessage DHM = new DistinguishHandleMessage();
		DHM.interprePreviousMessage(msgtoString);
		String result = DHM.getInterPreMessage();
//		String Interpre = msgtoString.substring(0, 5);			// 0~5 index 단어 추출
		
//		Status status = new Status();							// WebSocket접속을 위한 메시지 전송인지 구별함
//		status.setStatus(Interpre.equals("Login"));				// 0~5 index가 "login"이라는 단어일 경우, 단순한 웹소켓 접속만을 알리는 if문 안으로
		
		if(result.equals(Login)) {								// WebSocket 접속알림 메시지일 경우
			application.setUser_id(msgtoString.substring(8));	// ID 추출 & application에 저장
			
			Map<String, Object> data = new HashMap<>();			// (접속자ID, WebSocket session) 저장
			data.put(application.getUser_id(), session);
			
			application.setUserInfo(data);						// 'data'를 application에 List형식으로 담아둔다
			
		} else if(result.equals(Notic)){						// 새로운 채팅방생성됨을 알리는 용도
			sendNewRoom(msgtoString);
		} else { 												// 접속 후, 유저들간이 메시지 전송용도일 경우
			super.handleMessage(session, message);
			sendMessage(session, message.getPayload().toString());
		}
	}

	private void sendNewRoom(String msgtoString) {
		int chat_num = Integer.parseInt(msgtoString.substring(8));		// 새로만들어진 채팅방 번호 추출
		
		List<Chat_userVO> userList = new ArrayList<Chat_userVO>();		// 채팅방에 해당되는 유저 List를 가져옴
		userList = chatservice.userList(chat_num);
		
		List<Map<String, Object>> tidyUserInfo = new ArrayList<Map<String, Object>>();		// 메시지 받을 유저를 담을 변수
		
/*		application에 담겨있는 현재 접속되어있는 (유저ID, WebSocket session) -> userInfo 를 가져와서
		메시지를 보낼 유저 ID만을 담고있는 userList와 비교하여, 일치하는 값을 새로운 List로 -> tidyUserInfo로 만들어 메시지 보낼때 이를 사용한다 */
		List<Map<String, Object>> userInfo = application.getUserInfo();						
		Iterator<Map<String, Object>> extract = userInfo.iterator();
		while(extract.hasNext()) {															// 1 : WebSocket 접속자들 정보 추출 (id, WebSocket session)
			Map<String, Object> mapData = new HashMap<String, Object>();
			mapData = extract.next();
			
			Iterator<Chat_userVO> extract2 = userList.iterator();
			while(extract2.hasNext()) {														// 2 : 메시지 받을 유저들 정보 (id)
				Chat_userVO voData= new Chat_userVO();
				voData = extract2.next();

				if(mapData.containsKey(voData.getMember_user_id())) {						// 1 과 2를 비교하여 일치할 경우 (현재 접속자들 중에 메시지 받을 유저가 있을경우)
					String key = voData.getMember_user_id();
					WebSocketSession tidySession = (WebSocketSession) mapData.get(key);
					
					Map<String, Object> tidyMapData = new HashMap<>();						// 3 : 메시지 받을 유저정보 생성 (id, WebSocket session)
					tidyMapData.put(key, tidySession);
					tidyUserInfo.add(tidyMapData);											
				}
				
			}
		}
		
		
		/* 채팅방에 해당되는 유저들에게 메시지 보내기 */
		Iterator<Map<String, Object>> extract3 = tidyUserInfo.iterator();		
		while(extract3.hasNext()) {								
			Map<String, Object> Data = new HashMap<String, Object>();
			Data = extract3.next();
			for(WebSocketSession s : wsSession) {
				if(s.isOpen() && Data.containsValue(s)) {
					try {
						s.sendMessage(new TextMessage("NewRoom : " + chat_num));
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		
	}

	@Transactional
	private void sendMessage(WebSocketSession session, String msg) {
		
		Interpretation interpre = new Interpretation();					// msg 해석
		interpre.interpre_Msg(msg);								
		
		HashMap<String, Object> data = interpre.getinfo_Msg();			// 본인이 해당된 채팅방에 메시지 저장
		chatservice.msgStorage(data);									
		
		int chat_num = interpre.getmsg_Chatnum();						// 채팅방 번호 가져옴
		
		List<Chat_userVO> userList = new ArrayList<Chat_userVO>();		// 채팅방에 해당되는 유저 List를 가져옴
		userList = chatservice.userList(chat_num);						

		List<Msg_listVO> msglist = new ArrayList<Msg_listVO>();			// 본인이 해당된 채팅방으로부터 메시지 꺼내옴	
		msglist = chatservice.msgGet(chat_num);
		
		MessageJSON mj = new MessageJSON();								// DB에서 꺼내온 메시지들을 json으로 변환
		String result = mj.GSON(msglist);								
		
		
		
		List<Map<String, Object>> tidyUserInfo = new ArrayList<Map<String, Object>>();		// 메시지 받을 유저를 담을 변수
		
/*		application에 담겨있는 현재 접속되어있는 (유저ID, WebSocket session) -> userInfo 를 가져와서
		메시지를 보낼 유저 ID만을 담고있는 userList와 비교하여, 일치하는 값을 새로운 List로 -> tidyUserInfo로 만들어 메시지 보낼때 이를 사용한다 */
		List<Map<String, Object>> userInfo = application.getUserInfo();						
		Iterator<Map<String, Object>> extract = userInfo.iterator();
		while(extract.hasNext()) {															// 1 : WebSocket 접속자들 정보 추출 (id, WebSocket session)
			Map<String, Object> mapData = new HashMap<String, Object>();
			mapData = extract.next();
			
			Iterator<Chat_userVO> extract2 = userList.iterator();
			while(extract2.hasNext()) {														// 2 : 메시지 받을 유저들 정보 (id)
				Chat_userVO voData= new Chat_userVO();
				voData = extract2.next();

				if(mapData.containsKey(voData.getMember_user_id())) {						// 1 과 2를 비교하여 일치할 경우 (현재 접속자들 중에 메시지 받을 유저가 있을경우)
					String key = voData.getMember_user_id();
					WebSocketSession tidySession = (WebSocketSession) mapData.get(key);
					
					Map<String, Object> tidyMapData = new HashMap<>();						// 3 : 메시지 받을 유저정보 생성 (id, WebSocket session)
					tidyMapData.put(key, tidySession);
					tidyUserInfo.add(tidyMapData);											
				}
				
			}
		}
		
		
/*		ExtractMessageAndUser EMAU = new ExtractMessageAndUser();		// 메시지 해석 & 추출
		String result = EMAU.InterPre(session, msg);
		
		List<Map<String, Object>> tidyUserInfo	= EMAU.ExtractUser();	// 접속자중에서 메시지 받을 사용자 추출
*/
		
		/* 채팅방에 해당되는 유저들에게 메시지 보내기 */
		Iterator<Map<String, Object>> extract3 = tidyUserInfo.iterator();		
		while(extract3.hasNext()) {								
			Map<String, Object> Data = new HashMap<String, Object>();
			Data = extract3.next();
			for(WebSocketSession s : wsSession) {
				if(s.isOpen() && Data.containsValue(s)) {
					try {
						s.sendMessage(new TextMessage(result));
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/*전송 에러*/
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		// TODO Auto-generated method stub
		super.handleTransportError(session, exception);
	}

	/*부분 메시지*/
	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return super.supportsPartialMessages();
	}
	
	
	/*클라이언트에게 스레드로 계속 신호보내기*/
/*	private void Test(String string, WebSocketSession session) {
		for(WebSocketSession s : wsSession) {
			if(s.isOpen()) {
				try {
					session.sendMessage(new TextMessage(string));
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	 public void afterPropertiesSet(WebSocketSession session) throws Exception {
           Thread thread = new Thread(){
                  int i=0;
                  @Override
                  public void run() {
                         while (true){
                               try {
                                      Test("send message index "+ i++, session);
                                      Thread.sleep(1000);
                               } catch (InterruptedException e) {
                                      e.printStackTrace();
                                      break;
                               }
                         }
                  }
           };
           thread.start();
     }*/

	
	 
	/* JSON형태로 변환 */
	class MessageJSON {
		public String GSON(List<Msg_listVO> msglist) {	
			Gson gson = new Gson();
			String result = gson.toJson(msglist);	
			return result;
		}
	}
}
