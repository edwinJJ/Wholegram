var token = localStorage.getItem("chat");			// token을 "true" or "false"으로 지정 (true == 메시지창 보여주기, false == 메시지창 닫기)
var chat_num = localStorage.getItem("chat_num");	// chat_num - 채팅방 번호
var start = "true";									// start - Message창 띄우기
var fail = "false";									// fail  - Message창 닫기

/* Message창 display 여부 확인 */
function check_messageform() {
	var param;
	var ids = document.getElementById("receive_user").value;			// 채팅방에 참여되는 유저들 목록
	if(ids != "") {
		token = localStorage.getItem("chat");
		if(token == start) {											// param == 다른 채팅방 메시지창이 열려있을경우 새로운 토큰 표시(채팅방 중복생성 방지시 필요함)
			param = "on";
		}
		if(token == fail || token == null) {
			localStorage.setItem("chat", "true");
			token = localStorage.getItem("chat");
			param = "off";
		}
		set_chatroom(token, ids, param);
	} else {
		alert("대화 상대를 선택해주세요.");
	}
}

/* 채팅방 생성 */
function set_chatroom(token, ids, param) {
	var cr_url = "/message/chatroom/" + ids;
	$.ajax({
		type: 'POST',
		url: cr_url,
		headers:{
			"Content-Type" : "application/json",
			"X-HTTP-Method-Override":"POST",
		},
		dataType:'JSON',
		data: '',
		success : function(result) {										// result : 채팅방 번호
			if(result != 0) {
				localStorage.setItem("chat_num", result);					// 채팅방 번호 localStroage에 저장
				
				show_messageform(token, localStorage.getItem("chat_num"));	// 메시지 창 보여주기
				getRoomList(null);											// 전체 채팅방 목록 다시 가져오기 (방금 유저가 새로 만든 방이 있으니깐)
				
				ws.send("Notic : " + sessionId + " : " + result);			// WebSocket서버를 통해 채팅방에 참여되는 유저들의 채팅방리스트를 갱신한다
			} else {
				if(param == "off") {
					localStorage.setItem("chat", "false");					// 채팅방 생성 안됫으니, 메시지창을 열어주지 않는다.
					token = localStorage.getItem("chat");
				}
				alert("이미 채팅방이 있습니다.");
			}
		},
		error : function(result){
			alert("error : " + result);
		}
	});
	document.getElementById("receive_user").value = "";
}

/* 채팅방 전체 목록 가져오기 */
function getRoomList(Item) {
	
	var rl_url = "/message/roomList";
	$.ajax({
		type: 'POST',
		url: rl_url,
		headers:{
			"Content-Type" : "application/json",
			"X-HTTP-Method-Override":"POST",
		},
		dataType:'JSON',
		data: '',
		success : function(result) {				// result : 채팅방 전체 번호 목록 + 참여 유저 Id
			if(Item == null) {
				showRoomList(result);				// 채팅방 목록만 뿌려줄 경우
			} else {
				checkReadRoomList(result);			// 메시지 읽은방 리스트 확인 용도
			}
		},
		error : function(BadRequest){
			alert("error : " + BadRequest);
		}
	});
}

/* 새로운 채팅방 생성과 동시에 채팅방 목록 새로 갱신 */
function showRoomList(roomList) {
	var html = "";
	$(roomList).each(function() {
		html +=
			"<div class='well'>"
			+   "<span id='room_popup" + this.chat_chat_num + "' class='w3-badge w3-left w3-small w3-red' style='display:none;'>!</span>"
			+	"<button type='button' class='close' onclick='delRoom(" + this.chat_chat_num + ")'>&times;</button>"
			+	"<span><img class='chat_img' src='/resources/Image/Message.png'></span>";
		if(this.chat_name == null) {
			html += "<a href='#' class='chat_aname' onclick='getChatRoom(" + this.chat_chat_num + ")'><span id='room_name" + this.chat_chat_num + "' class='chat_name'>채팅방 : " + this.chat_chat_num + " </span></a>";
		} else {
			html += "<a href='#' class='chat_aname' onclick='getChatRoom(" + this.chat_chat_num + ")'><span id='room_name" + this.chat_chat_num + "' class='chat_name'>" + this.chat_name + " </span></a>";
		}
			html +=
				"<span>" + this.member_user_id + "</span>" + 
				"<a href='#' data-toggle='modal' onclick='chatNameModal(" + this.chat_chat_num + ")'><img class='chat_name_change' src='/resources/Image/chat_room_name.jpg'></a>" +
			"</div>";
	});
	document.getElementById("roomList").innerHTML = html;
}

/* 유저가 속한 채팅방 상태 확인(헤더의 알림 메시지 띄우는 용도) */
function checkReadRoomList(result) {
	var count = 0;
	if(thisPage == true) {
		$(result).each(function() {																// 유저가 속한 채팅방의 메시지를 다 읽었는지 확인해준다
			var roomNumber = this.chat_chat_num;
			if(document.getElementById("room_popup" + roomNumber).style.display != "none") {
				count++;
			}
		});
	}
	if(count == 0) {																			// 다 읽었으면 헤더의 알림표시 제거
		document.getElementById("header_popup").style.display = "none";
	}
}

/* Message창 보여주기 */
function show_messageform(token, chat_num) {
	document.getElementById("chat_box").style.display = 'block';
	if(token == start) {
		if(typeof(localStorage) !== "undefined") {
			var html = 
				"<div class='panel2 panel-info msg_position' >"
				+	"<input id='chat_num' type='hidden' value='" + chat_num + "'/>"
				+	"<span onclick='close_message()' class='w3-closebtn'>&times;</span>" 
				+	"<div class='panel-heading'>Message 보내기</div>"
				+	"<div id='message_container' style='height:355px; overflow:auto; overflow-x:hidden;'>"
				+		"<div id='msg_content'></div>"
				+	"</div>"
				+	"<input id='send_msg' class='form-control2 msg_content2' type='text' onkeypress='if(event.keyCode==13) {send_message(" + chat_num + "); return false;}'>";
				"</div>"
				
				document.getElementById("chat_box").innerHTML = html;
		} else {
			document.getElementById("result").innerHTML = "Sorry, your browser does not support web storage...";
		}
	}
}



/* (현재 채팅방이 닫혀있는 상태에서) 채팅방 목록중에서 선택한 채팅방의 데이터를 가져온다  
  								or  
 * (페이지이동or새로고침) 했을시에 기존에 열려있던 채팅방의 데이터를 가져온다*/
function getChatRoom(chat_chat_num) {
	var roomInfo_url = "/message/getRoomData/" + chat_chat_num;
		$.ajax({
		type : 'POST',
		url : roomInfo_url,
		headers : {
			"Content-Type" : "application/json",
			"X-HTTP-Method-Override":"POST",
		},
		dataType:'text',
		data: '',
		success : function(result){								// result : 해당 채팅방의 메시지 내용 
			localStorage.setItem("chat", "true");				// localStorage의 chat을 true로 설정 (메시지창을 보여주기 위함, 규칙)
			localStorage.setItem("chat_num", chat_chat_num);	// localStorage의 chat_num을 유저가 선택한 채팅방 번호로 설정 (새로고침 or 페이지 이동시에 열어두고있던 채팅방의 정보를 가져오기 위함)
			
			token = localStorage.getItem("chat");
			chat_num = localStorage.getItem("chat_num");
			
			show_messageform(token, chat_num);					// 메시지창 보여주기
			showMessage(result);								// 메시지 내용 보여주기
			getRoomList(sessionId);								// 유저가 속한 채팅방 전체 목록 가져와서 메시지 읽은방 리스트 확인 용도
		},
		error : function(result){
			alert("error : " + result);
		}
	}); 
}




/* 메시지 보내기 */ 
function send_message(chat_num) {
	var msg1 = "{num : " + chat_num + "}";						// 방 번호 추가
	var msg2 = "[write : " + sessionId + "]";					// 작성자 ID
	if(chat_num != 0) {
		var msg3 = document.getElementById("send_msg").value; 	// 메시지 내용
		if(msg3 == "") {								
			return false;										// 메시지 내용이 아무것도없을 경우 아무일도 해주지 않음
		}
		send_msg.value = "";
	}
	var msg = (msg1 + msg2 + msg3);
	ws.send(msg); 	 											//서버로 메시지 전송
}

/* 화면에 message를 뿌려줌 */
function showMessage(result) {
	var impl = result.substring(0,7);													// 새로운 채팅방 생성을 알리는 용도 
	
	var chat_room;																		// 메시지가 속한 채팅 방 번호
	var object = JSON.parse(result);													// JSON으로 파싱
	
	$(object).each(function() {
		chat_room = this.chat_chat_num;													// 메시지가 속해있는 채팅방 번호 추출
	});
		
	if(document.getElementById("chat_num") != null) {									// 메시지창이 열려있지 않은경우네는 id가 chat_num인 태그가 없으므로
		var nowShowingMsgForm = document.getElementById("chat_num").value;				
	}
	
	if((token != fail) && (nowShowingMsgForm == chat_room)) {							// 메시지창이 채팅방이 열려있을 경우 && 열려있는 메시지창의 방 번호가 메시지가 속해있는 방번호와 일치 할 경우만 내용을 뿌려준다
		
		document.getElementById("msg_content").innerHTML = "";							// 메시지창 내용 비워주기(전체 내용을 다시쓰기위해)
		if(thisPage == true) {															// 메시지 페이지일 경우
			document.getElementById("room_popup" + chat_room).style.display = "none";	// 메시지를 읽었으면 알림 표시를 지워준다
		}
		
		var chat_chat_num;																// 메시지 확인 체크하기위한 변수
		$(object).each(function() {														// 대화목록을 화면에 뿌려주는 작업
			
			var msgBox = document.createElement("div");									// 대화 한마디 한마디 마다 div 라인 생성. 길이는 200으로 제한 (나 or 상대방 말풍선이 시작되는 벽까지 닿으면 디자인이 이상해지므로)
			msgBox.style.width = "200px";												 
			
			var msgBoxSpan = document.createElement("span");							// 대화내용 텍스트를 담을 span
			var textnode;																// 텍스트 담을 변수
			
			if(sessionId == this.written_user_id) {										// 사용자(본인)이 작성한 글이면 오른쪽으로 출력
				
				msgBox.style.float = "right";											// 오른쪽 정렬
				msgBoxSpan.style.float = "right";										//     ""
				msgBoxSpan.style.textAlign = "right";									//     ""
				msgBoxSpan.style.marginRight = "10px";									// 말풍선 꼬리를 보여주기 위해 간격 벌려놓음
				msgBoxSpan.classList.add('balloon_right');								// class추가
				textnode = document.createTextNode(this.msg);							// 텍스트 생성
				
			} else if(this.written_user_id == "admin") {										// 날짜를 알려주는 경우
				msgBox.style.width = "300px";
				msgBox.style.marginTop = "10px";
				msgBox.style.marginBottom = "10px";
				msgBox.style.textAlign = "center";
				textnode = document.createTextNode("-   " + this.date.substring(0,10) + "   -");
			} else {																			// 다른 사용자가 작성한 글이면 왼쪽으로 출력
				
				msgBoxSpan.style.marginLeft = "10px";											// 말풍선 꼬리를 보여주기 위해 간격 벌려놓음
				msgBoxSpan.classList.add('balloon_left');										// class추가
				textnode = document.createTextNode(this.written_user_id + " : " + this.msg);	// 텍스트 생성
			}
			msgBoxSpan.appendChild(textnode);													// Span에 대화내용을 담는다
			msgBox.appendChild(msgBoxSpan);														// div에 Span(대화내용) 추가
			document.getElementById("msg_content").appendChild(msgBox);
			msgBox.style.clear = "both";

			chat_chat_num = this.chat_chat_num;
		});
		
		var el = document.getElementById('message_container'); 							// 스크롤 항상 최신(아래)으로 유지
		if (el.scrollHeight > 0) {
			el.scrollTop = el.scrollHeight;
		}
		
		var rc_url= "/message/readCheck/" + chat_chat_num; 								// 메시지 읽은 유저 등록하기
		$.ajax({
			type : 'POST',
			url : rc_url,
			headers : {
				"Content-Type" : "application/json",
				"X-HTTP-Method-Override":"POST",
			},
			dataType:'text',
			data: '',
			success : function(result){},
			error : function(result){
				alert("error : " + result);
			}
		}); 
		
	} else if((token != fail) && (nowShowingMsgForm != chat_room)) {					// 메시지창이 열려있지만 다른 채팅방의 메시지창일 경우 메시지왔다고 알려준다
		document.getElementById("header_popup").style.display = "block";
		if(document.getElementById("room_popup" + chat_room) != null) {					// 기존에 이미 만들어져 있던 채팅방으로부터 메시지가 왔을경우
			document.getElementById("room_popup" + chat_room).style.display = "block";
		} 
	} else {																			// 메시지창이 열려있지 않은 경우, 메시지왔다고 알려준다.
		document.getElementById("header_popup").style.display = "block";
		if(document.getElementById("room_popup" + chat_room) != null) {					//	기존에 이미 만들어져 있던 채팅방으로부터 메시지가 왔을경우
			document.getElementById("room_popup" + chat_room).style.display = "block";
		} 
		
	}
}

var hostname = location.hostname;								// ip
var port = location.port;										// port
var wsUrl;														// full url
var ws;

if(port == "") {
	wsUrl = "ws://" + hostname  + "/chat/init";					// port가 80일경우 값이 ""로 찍힘
} else {
	wsUrl = "ws://" + hostname + ":" + port + "/chat/init";	
}

function init() {
	ws = new WebSocket(wsUrl);  								//소켓 객체 생성
	ws.onopen = function(evt) { 								// 이벤트 발생시 opOpen()을 실행
		onOpen(evt);
		console.log("sessionId : " + sessionId);
		console.log("websocket connection : " + sessionId);
		ws.send("Login : " + sessionId);						// 웹소켓 접속을 알림
	};
	
	ws.onmessage = function(evt) {
		onMessage(evt);
	}
	
	ws.onerror = function(evt) {
		onError(evt);
	}
	ws.onclose = function(evt) {
		console.log("close");
		init();													// 세션 타임아웃으로 인한 웹소켓 서버와 연결이 끊겼을 경우 재접속 해준다
	}
}

function onOpen(evt) {
	
}

//서버로부터 메시지 받음
function onMessage(evt) {
	var impl = evt.data.substring(0,7);
	if(impl == "NewRoom") {															// 새로 방 만들어졌다는걸 알릴 때
		getRoomList(null);
	} else if(impl == "Message") {													// 방금 접속하거나 다른 페이지에 있는데, 그동안 새로운 메시지가 와서 헤더에 메시지 알림 띄울 때
		document.getElementById("header_popup").style.display = "block";
	} else {																		// 메시지 채팅 주고받을 때
		showMessage(evt.data);
	}
}

function onError(evt) {
	
}


/* Message창 닫기 */
function close_message() {
	localStorage.setItem("chat", "false");							// localStorage chat설정 초기화
	token = localStorage.getItem("chat");
	
	localStorage.setItem("chat_num", null);							// localStorage chat_num설정 초기화
	chat_num = localStorage.getItem("chat_num");
	
	document.getElementById("chat_box").style.display = 'none';		// 메시지 창 닫아주기
}

/* Web Socket Connection */
init();

/*
페이지간 이동시 메시지창이 열린상태에서 이동하면 token == "true" == start 이다    (=> getChatRoom을 통해 메시지창을 계속 열어준다.)
페이지간 이동시 메시지창이 닫힌상태에서 이동했으면 token == "false" == fail      (=> 메시지창이 열리지 않는다.)*/
if(token == start) {
	getChatRoom(chat_num);
}