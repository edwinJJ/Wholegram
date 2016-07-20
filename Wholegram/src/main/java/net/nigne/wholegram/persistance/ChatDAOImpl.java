package net.nigne.wholegram.persistance;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ChatDAOImpl implements ChatDAO {

	@Inject
	SqlSession session;
	
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
}
