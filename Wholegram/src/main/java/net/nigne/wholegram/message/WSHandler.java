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
import net.nigne.wholegram.common.Interpretation;
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

	public WSHandler() {
//		List<String> user_list = Application.getUserList();
		
		logger.info("웹소켓 생성자입니다");
	}
	
	/*연결 됫을 때*/
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("연결");
		//afterPropertiesSet(session);
		
		// 접속자ID와, WebSocket session 저장
		Map<String, Object> data = new HashMap<>();
		data.put(application.getUser_id(), session);

		// 'data'를 List에 담아둔다
		application.setUserInfo(data);
		
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

		super.handleMessage(session, message);
		sendMessage(session, message.getPayload().toString());
		//logger.info("recevied message : " + message);
	}

	@Transactional
	private void sendMessage(WebSocketSession session, String msg) {
		
		Interpretation interpre = new Interpretation();
		interpre.interpre_Msg(msg);										// msg 해석
		HashMap<String, Object> data = interpre.getinfo_Msg();			// (채팅방 번호 / 작성자ID / 메시지내용)
		int chat_num = interpre.getmsg_Chatnum();						// (채팅방 번호)
		
		List<Msg_listVO> msglist = new ArrayList<Msg_listVO>();
		chatservice.msgStorage(data);									// 본인이 해당된 채팅방에 메시지 저장
		msglist = chatservice.msgGet(chat_num);							// 본인이 해당된 채팅방으로부터 메시지 꺼내옴
		
//		List<Chat_userVO> userList = chatservice.userList(chat_num);	// 채팅방에 해당되는 유저 List를 가져옴
		MessageJSON mj = new MessageJSON();
		String result = mj.GSON(msglist);								// json으로 변환

		/*<접속자ID, Session> 의 Map형태를 List로 담아둔 변수를 가지고있는 application class의 'userInfo' 변수와
		접속자 전체를 담고있는 wsSession의 객체를 비교하여, 일치하면 메시지 뿌려줌 (즉 현재는, 채팅방과 상관없이 접속자면 무조건 뿌려줌)*/
		List<Map<String, Object>> userInfo = application.getUserInfo();
		Iterator<Map<String, Object>> extract = userInfo.iterator();
		while(extract.hasNext()) {
			Map<String, Object> mapData = new HashMap<String, Object>();
			mapData = extract.next();
			for(WebSocketSession s : wsSession) {
				if(s.isOpen() && mapData.containsValue(s)) {
					try {
						System.out.println(result);
						s.sendMessage(new TextMessage(result));	// 자신빼고 접속한 모두에게
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		
		
	/*	for(WebSocketSession s : wsSession) {
			if(s.isOpen() && !s.getId().equals(session.getId())) {
				try {
					s.sendMessage(new TextMessage("From Server" + msg));	// 자신빼고 접속한 모두에게
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}*/
		for(WebSocketSession s : wsSession) {
			if(s.isOpen()) {
				try {
					session.sendMessage(new TextMessage(result));			// 자신에게만
				} catch(IOException e) {
					e.printStackTrace();
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
	private void Test(String string, WebSocketSession session) {
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
     }

	
	 
	 
	 
	
	
	class MessageJSON {
		public String GSON(List<Msg_listVO> msglist) {
			Gson gson = new Gson();
			String result = gson.toJson(msglist);	
			return result;
		}
	}
	
}
