package net.nigne.wholegram.persistance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.nigne.wholegram.common.Status;
import net.nigne.wholegram.domain.Chat_userVO;
import net.nigne.wholegram.domain.Msg_listVO;
import net.nigne.wholegram.service.ReadMessageService;

@Repository
public class ChatDAOImpl implements ChatDAO {

	@Inject
	SqlSession session;
	
	@Inject
	ReadMessageService readService;
	
	private static final String namespace="net.nigne.wholegram.mappers.chatMapper";

	@Override
	public void chat_room() {
		session.insert(namespace + ".chat_room");
	}

	@Override
	public int getchat_room() {
		return session.selectOne(namespace + ".getchat_room");
	}

	@Transactional
	@Override
	public void user_room(int chat_num, String id_list) {
		StringTokenizer stiz = new StringTokenizer(id_list, ",");
		HashMap<String, Object> id_map = new HashMap<>();
		
		while(stiz.hasMoreElements()) {
			id_map.put("chat_num", chat_num);
			id_map.put("user_id", stiz.nextToken());
			session.insert(namespace + ".user_room", id_map);
		}
	}

	@Transactional
	@Override
	public void msgStorage(HashMap<String, Object> data) {
		session.insert(namespace + ".msgStorage", data);							// msg_list(DB)에 메시지 내용 저장
		int msg_list_num = session.selectOne(namespace + ".msgCurrentNum", data);	// msg_list의 해당 채팅방의 최신글 글 번호가져옴 (채팅방 번호가 아닌 글번호 (msg_list table primary key))
		
		String read_user_ids = (String) data.get("written_user_id");				// msg_check에 최신글 번호 저장과 본인을 메시지 읽은 유저로 추가
		Map<String, Object> newData = new HashMap<String, Object>();
		newData.put("msg_list_num", msg_list_num);
		newData.put("read_user_ids", read_user_ids);
		session.insert(namespace + ".setMsgCheckUser", newData);				
	}

	@Override
	public List<Msg_listVO> msgGet(int chat_num) {
		return session.selectList(namespace + ".msgGet", chat_num);
	}

	@Override
	public List<Integer> getRoomNumber(String user_id) {
		return session.selectList(namespace + ".getRoomNumber", user_id);
	}

	@Transactional
	@Override
	public List<Chat_userVO> getRoomUser(List<Integer> roomlist) {
		
		Iterator<Integer> room_Iterator = roomlist.listIterator();			// 유저가 포함되어있는 방 번호
		List<Chat_userVO> each_rooms_info = new ArrayList<Chat_userVO>();	// 각 방번호에 속해있는 유저들을 담을 List

		while(room_Iterator.hasNext()) {
			int room_number = room_Iterator.next();														/* ex) 방번호 참여유저
																										 	    1    user1
																										        1    user2
			// 각 방 번호와, 방 번호에 포함되어있는 유저 list (DB구조상 Query 결과값의 방 번호가 중복됨)	ex) ==>>   	        1    user3 */
			List<Chat_userVO> each = session.selectList(namespace + ".getRoomUser", room_number);       
																										/*      2    user2
																										        2    user4 . . . 이런식, 그래서 아래에 있는 작업을 해주게 됨 */
			String each_users = "";
			int result_room_number = 0;
			Chat_userVO each_room_info = new Chat_userVO();
																			//  방번호          참여유저
			// 방번호 1개와, 그 방번호에 해당되는 유저들을 1개의 VO객체로 만들기 위한 과정 ==>  1    user1,user2,user3  이런형태로 만듬 ==> (이렇게 만들어두면 view에서 jstl로 뿌려주기 간편하다) 
			Iterator<Chat_userVO> eachit = each.iterator();
			while(eachit.hasNext()) {
				Chat_userVO users = eachit.next();
				each_users += (" / " + users.getMember_user_id());
				if(!(eachit.hasNext())) {
					result_room_number = users.getChat_chat_num();
				}
			}
			each_room_info.setChat_chat_num(result_room_number);
			each_room_info.setMember_user_id(each_users);
			
			/*방 1개의 정보(방번호, 참여 유저들) ==  VO객체 1개  를 List에 담는다.*/
			each_rooms_info.add(each_room_info);

		}
		return each_rooms_info;
	}

	@Transactional
	@Override
	public void delRoom(int chat_chat_num) {
		session.delete(namespace + ".delMsg_list", chat_chat_num);
		session.delete(namespace + ".delChat_user", chat_chat_num);
		session.delete(namespace + ".delChat", chat_chat_num);
	}

	@Override
	public List<Chat_userVO> userList(int chat_num) {
		return session.selectList(namespace + ".userList", chat_num);
	}

	@Transactional
	@Override
	public void setRead_user_ids(Map<String, Object> data) {
		int msg_list_num = session.selectOne(namespace + ".msgCurrentNum", data);	// msg_list의 해당 채팅방의 최신글 글 번호가져옴 (채팅방 번호가 아닌 글번호 (msg_list table primary key))

		String read_user_ids = (String) data.get("written_user_id");				// msg_check에 최신글 번호 저장과 본인을 메시지 읽은 유저로 추가할것인지 여부 결정
		Map<String, Object> data2 = new HashMap<String, Object>();					
		data2.put("msg_list_num", msg_list_num);
		data2.put("read_user_ids", read_user_ids);
		
		int count =  session.selectOne(namespace + ".getMsgCheckUser", data2);		// 메시지 읽은 유저 등록 중복방지
		Status ReadMessageStatus = readService.ReadMessageStatus(count);
		if(ReadMessageStatus.isSuccess()) {											// 메시지 안읽은 상태면, 읽은 상태로 등록
			session.insert(namespace + ".setMsgCheckUser", data2);
		} else { }																	// 이미 읽은 상태라면, 아무것도 하지 않는다
	}

	@Transactional
	@Override
	public List<Integer> checkReadRoom(List<Integer> roomNumber, String user_id) {
		Iterator<Integer> extract = roomNumber.listIterator();
		List<Integer> roomList = new ArrayList<Integer>();
		
		while(extract.hasNext()) {
			int chat_num = extract.next();
			int lastMsgNum;
			try{
				lastMsgNum = session.selectOne(namespace + ".msgCurrentNum", chat_num);			// msg_list의 해당 채팅방의 최신글 글 번호가져옴 (채팅방 번호가 아닌 글번호 (msg_list table primary key))
			} catch(Exception e) {
				lastMsgNum = 0;
			}
			if(lastMsgNum != 0) {
				Map<String, Object> data = new HashMap<String, Object>();						// 각 채팅방마다 최신 메시지를 읽었는지 확인하기 위한 변수 설정
				String read_user_ids = user_id;	
				data.put("msg_list_num", lastMsgNum);
				data.put("read_user_ids", read_user_ids);
				
				int count = session.selectOne(namespace + ".getMsgCheckUser", data);			// 최신 메시지를 읽었는지 확인
				Status ReadMessageStatus = readService.ReadMessageStatus(count);				// true flase 설정
				if(ReadMessageStatus.isSuccess()) {  											// 최신 메시지를 읽지 않은 상태이므로, 해당 채팅방번호를 List에 담아준다
					roomList.add(chat_num);
				} else {}																		// 최신 메시지를 읽은 상태 이므로 아무것도 해주지 않는다
			}
		}
		return roomList;
	}

	@Override
	public List<Integer> getRoomList(String user_id) {
		return session.selectList(namespace + ".getRoomList", user_id);
	}
}
