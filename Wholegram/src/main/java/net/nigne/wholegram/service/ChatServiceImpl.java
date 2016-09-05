package net.nigne.wholegram.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.nigne.wholegram.domain.Chat_userVO;
import net.nigne.wholegram.domain.Msg_listVO;
import net.nigne.wholegram.persistance.ChatDAO;

@Service
public class ChatServiceImpl implements ChatService {

	@Inject
	private ChatDAO dao;
	
	/* 채팅방 생성 & 참여 유저 추가*/
	@Transactional
	@Override
	public int chat_room(String id_list) {
		
		boolean flag = dao.checkAldyRoom(id_list);	// 중복된 채팅방인지 체크
		
		if(flag == true) {
			dao.chat_room();						//채팅방 생성
			
			int chat_num = dao.getchat_room();		//채팅방 고유번호 가져옴
			
			dao.user_room(chat_num, id_list);		//참여 유저 등록
			
			return chat_num;						//새로 생긴 채팅방 번호 리턴
		} else {
			return 0;								//채팅방 만들기 실패
		}
	}

	/* msg_list에 메시지 내용 저장 + 메시지 읽은 유저에 본인 추가 */
	@Override
	public void msgStorage(HashMap<String, Object> data) {
		dao.msgStorage(data);
	}

	/* 메시지 내용, 작성자 가져옴 */
	@Override
	public List<Msg_listVO> msgGet(int chat_num) {
		return dao.msgGet(chat_num);
	}

	/*유저가 포함되어있는 각 채팅방에 참여하고 있는 유저 리스트를 가져옴*/
	@Transactional
	@Override
	public List<Chat_userVO> getRoomUsers(String user_id) {
		/*유저가 포함되어있는 채팅방 목록(번호 리스트) 가져옴*/
		List<Integer> roomlist = dao.getRoomNumber(user_id);
		
		/*각 채팅방에 참여하고있는 유저 리스트를 가져옴*/
		return dao.getRoomUser(roomlist);
	}
	
	/* 유저가 포함되어있는 채팅방 번호만 추출 */
	@Override
	public List<Integer> extractRoomNumber(List<Chat_userVO> roomInfo) {
		Iterator<Chat_userVO> extract = roomInfo.listIterator();
		List<Integer> roomNumber = new ArrayList<Integer>();
		while(extract.hasNext()) {
			Chat_userVO cv = extract.next();
			int chatnum = cv.getChat_chat_num();
			roomNumber.add(chatnum);
		}
		return roomNumber;
	}
	
	/* 메시지 읽지 않은 채팅방 확인 */
	@Override
	public List<Integer> checkReadRoom(List<Integer> roomNumber, String user_id) {
		return dao.checkReadRoom(roomNumber, user_id);
	}

	/* 유저가 속한 각 채팅방마다 최신 메시지 내용을 읽었는지 확인 후 알림표시 여부 설정 */
	@Override
	public List<Chat_userVO> setCheckReadRoom(List<Integer> roomList, List<Chat_userVO> roomInfo) {
		List<Chat_userVO> roomInfomation = new ArrayList<Chat_userVO>();
		
		Iterator<Chat_userVO> extract = roomInfo.listIterator();			// roomInfo : 유저가 포함되어있는 각 채팅방의 참여하고 있는 모든 유저 리스트
		while(extract.hasNext()) {
			Chat_userVO cv = extract.next();
			Iterator<Integer> extract2 = roomList.listIterator();			// roomList : 최신 메시지를 읽지 않은 채팅방 리스트
			while(extract2.hasNext()) {
				int number = extract2.next();
				if(cv.getChat_chat_num() == number) {
					cv.setMsgNotice(true);									// 메시지 알림 설정
				} 
			}
			roomInfomation.add(cv);
		}
		return roomInfomation;
	}

	/*채팅방 삭제*/
	@Override
	public void delRoom(int chat_chat_num) {
		dao.delRoom(chat_chat_num);
	}

	/*채팅방에 해당되는 유저들 목록을 가져옴*/
	@Override
	public List<Chat_userVO> userList(int chat_num) {
		return dao.userList(chat_num);
	}

	/* 메시지 읽은 유저목록에 본인 추가 */
	@Override
	public void setRead_user_ids(Map<String, Object> data) {
		dao.setRead_user_ids(data);
	}

	/* 유저가 해당되는 채팅방 번호 리스트 추출 */
	@Override
	public List<Integer> getRoomList(String user_id) {
		return dao.getRoomList(user_id);
	}

	/*채팅방 이름 변경*/
	@Override
	public void changeRoom(int chat_chat_num, String chatName) {
		dao.changeRoom(chat_chat_num, chatName);
	}

	/* 지난 메시지 날짜 알림 () */
	@Transactional
	@Override
	public List<Msg_listVO> addDateInfo(int chat_chat_num) {

		String LastgMsgDate = dao.getLastMsgDate(chat_chat_num);					// 마지막 메시지 등록 날짜
		
		SimpleDateFormat SimpleFormat = new SimpleDateFormat( "yyyy-MM-dd" );		// yyyy-MM-dd 형태로 기준 생성. (DB에서 msg_list 테이블의 date컬럼이 yyyy-mm-dd 형식으로 되어있기 때문에)
		Date currentTime = new Date();												// 현재시각 가져옴(년,월,일,시간,분,초 단위까지 나옴)
		Date pastTime = null;														// DB에 입력되있던 메시지가 등록된 날짜를 담을 변수
		String dateImpl = "";
		dateImpl = SimpleFormat.format(currentTime);								// 현재시각을 'yyyy-mm-dd' 형태로 맞춰줌
		
		try {
			if(!(LastgMsgDate == null || LastgMsgDate.equals(""))) {				// 새로 생긴 채팅방일경우 예외처리 : 방금 생성됫기 때문에 등록된 메시지내용과, 그에 해당되는 날짜가 없다.

				currentTime = SimpleFormat.parse(dateImpl);							// 현재시각 = 'yyyy-mm-dd'형태로 맞춰진 현재시각을 Date타입으로 파싱
				pastTime = SimpleFormat.parse(LastgMsgDate);						// DB저장된 마지막 메시지 등록 시각 = DB에서 각 메시지가 등록된 날짜를 'yyyy-mm-dd'형태의 Date타입으로 파싱

				int compare = pastTime.compareTo(currentTime);						// 날짜 비교. 비교대상의 날짜가(pastTime) 비교할 날짜(currentTime)보다 지난 날짜일경우 -1 , 미래 날짜일경우 +1 리턴
				if(compare < 0 ) {													// 날짜가 하루 이상 지났을경우.
					dao.DateNotice(chat_chat_num, dateImpl);						// 채팅창에 날짜 메시지를 띄어주기 위한 구별 토큰을 입력한다. (메시지 내용없이 작성자만 'admin'으로 입력 후, 나중에 view단에서 작성자가 admin일 경우 해당 날짜를 뿌려주게 된다.)
				}
			} else {
				dao.DateNotice(chat_chat_num, dateImpl);							// 새로생긴 채팅방일 경우 생성날짜 등록해준다.
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}							

		return dao.msgGet(chat_chat_num);											// 변경된 채팅방 데이터를 다시 가져온다.		
	}
}
