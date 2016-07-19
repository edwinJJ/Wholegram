package net.nigne.wholegram.WSHandler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WSHandler extends TextWebSocketHandler {

	private static final Logger logger = LoggerFactory.getLogger(WSHandler.class);

	private Set<WebSocketSession> wsSession = new HashSet<WebSocketSession>();
	
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
		//logger.info("recevied message : " + message);
	}

	private void sendMessage(WebSocketSession session, String msg) {
		for(WebSocketSession s : wsSession) {
			if(s.isOpen() && !s.getId().equals(session.getId())) {
				try {
					s.sendMessage(new TextMessage("From Server" + msg));
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		
/*		for(WebSocketSession s : wsSession) {
			if(s.isOpen()) {
				try {
					//session.sendMessage(new TextMessage("From Server" + msg)); // 보낸사람에게 다시보냄
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}*/
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
	
}
