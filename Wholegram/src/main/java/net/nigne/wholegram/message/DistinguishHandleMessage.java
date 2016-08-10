package net.nigne.wholegram.message;

public class DistinguishHandleMessage {

	private String result = "";
	
	public void interprePreviousMessage(String message){
		String interpre = message.substring(0, 5);			// 0~5 index 단어 추출
		
		if(interpre.equals("Login")) {						// 추출한 단어가 'Login'일 경우 웹소켓 접속을 알리는 단어용도
			result = interpre;
		} else if(interpre.equals("Notic")) {				// 추출한 단어가 'Notic'일 경우 새로운 채팅방이 만들어졌다는걸 알리는 용도
			result = interpre;
		} else {											// 둘다 아닐 경우 메시지 대화를 위한 용도
			result = "message";
		}
	}
	
	public String getInterPreMessage() {
		return result;
	}
}
