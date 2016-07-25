package net.nigne.wholegram.message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

import net.nigne.wholegram.common.Interpretation;
import net.nigne.wholegram.domain.MessageVO;
import net.nigne.wholegram.domain.Msg_listVO;
import net.nigne.wholegram.service.ChatService;

@RequestMapping("/chat")
public class WSHandler extends TextWebSocketHandler {

	private static final Logger logger = LoggerFactory.getLogger(WSHandler.class);

	private Set<WebSocketSession> wsSession = new HashSet<WebSocketSession>();

	@Inject
	private ChatService chatservice;

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
		interpre.interpre_Msg(msg);								// msg 해석
		HashMap<String, Object> data = interpre.getinfo_Msg();	// (채팅방 번호 / 작성자ID / 메시지내용)
		int chat_num = interpre.getmsg_Chatnum();				// (채팅방 번호)
		
		List<Msg_listVO> msglist = new ArrayList<Msg_listVO>();
		chatservice.msgStorage(data);							// 본인이 해당된 채팅방에 메시지 저장
		msglist = chatservice.msgGet(chat_num);					// 본인이 해당된 채팅방으로부터 메시지 꺼내옴
		
		MessageJSON mj = new MessageJSON();
		String result = mj.GSON(msglist);						// json으로 변환

		/*		for(WebSocketSession s : wsSession) {
			if(s.isOpen() && !s.getId().equals(session.getId())) {
				try {
					s.sendMessage(new TextMessage("From Server" + msg));
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}*/
		for(WebSocketSession s : wsSession) {
			if(s.isOpen()) {
				try {
					session.sendMessage(new TextMessage(result));
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
