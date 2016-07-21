package net.nigne.wholegram.message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import net.nigne.wholegram.common.Interpretation;
import net.nigne.wholegram.domain.MessageVO;
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

		//사용자 정보를 담는다.
		wsSession.add(session);
		super.afterConnectionEstablished(session);
	}
	
	/*연결이 끊어 졌을 때*/
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		
		//사용자 정보 삭제
		wsSession.remove(session);
		super.afterConnectionClosed(session, status);
	}

	/*메시지 전송됫을 때*/
	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		
		super.handleMessage(session, message);
		sendMessage(session, message.getPayload().toString());
		logger.info("recevied message : " + message);
	}

	@Transactional
	private void sendMessage(WebSocketSession session, String msg) {
		
		Interpretation interpre = new Interpretation();
		interpre.interpre_Msg(msg);								// msg 해석
		HashMap<String, Object> data = interpre.getinfo_Msg();	// (채팅방 번호 / 메시지내용)
		int chat_num = interpre.getmsg_Chatnum();				// (채팅방 번호)
		
		List<MessageVO> msglist = new ArrayList<MessageVO>();
		chatservice.msgStorage(data);							// 본인이 해당된 채팅방에 메시지 저장
		msglist = chatservice.msgGet(chat_num);					// 본인이 해당된 채팅방으로부터 메시지 꺼내옴
		
		
		
//		Map<String, Object> map = new HashMap<>();
//		map.put("message", msglist);
//		ResponseEntity<Map<String,Object>> entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
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
					System.out.println("test");
					MSG m = new MSG();
					m.id="아이디";
					//s.sendMessage(new TextMessage(m.toJson()));
					session.sendMessage(new TextMessage((CharSequence) msglist)); // 보낸사람에게 다시보냄
					//session.sendMessage((WebSocketMessage<?>) new ResponseEntity(msglist, HttpStatus.OK));
					//session.sendMessage((WebSocketMessage<?>) msglist);
					//session.sendMessage(new TextMessage((CharSequence) map));
					//session.sendMessage((WebSocketMessage<?>) entity);
					
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
	
	class MSG{
		String id;
		List<String> msg;
		
	}
	
}
