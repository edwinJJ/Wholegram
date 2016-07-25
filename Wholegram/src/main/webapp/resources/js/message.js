var token = localStorage.getItem("chat");			// token을 "true" or "false"으로 지정
var chat_num = localStorage.getItem("chat_num");	// chat_num - 채팅방 번호
var start = "true";									// start - Message창 띄우기
var fail = "false";									// fail  - Message창 닫기

/* Message창 display 여부 확인 */
function check_messageform() {
	token = localStorage.getItem("chat");
	if(token == fail || token == null) {
		localStorage.setItem("chat", "true");
		token = localStorage.getItem("chat");
	}
	set_chatroom(token);
	
}

/* 채팅방 생성 */
function set_chatroom(token) {
	var ids = document.getElementById("receive_user").value;
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
		success : function(result) {						//result : 채팅방 번호
			localStorage.setItem("chat_num", result);
			chat_num = localStorage.getItem("chat_num");	// 채팅방 번호 localStroage에 저장
			show_messageform(token, chat_num);				// 메시지 창 보여주기
			add_chatroom();									// 채팅방 목록 가져오기
			
			
			//startWebWorker();
		},
		error : function(result){
			alert("e : " + result);
		}
	});
}

/* 채팅방 목록 가져오기 */
function add_chatroom() {

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
		success : function(roomList) {
			showRoomList(roomList);
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
			+	"<span><img class='chat_img' src='/resources/Image/Penguins.jpg'></span>"
			+	"<a href='#' class='chat_aname' onclick='getChatRoom(this.chat_chat_num)'><span class='chat_name'>채팅방 : " + this.chat_chat_num + " </span></a>"
			+	"<span>" + this.member_user_id + "</span>"
			+	"<span><img class='chat_content' src='/resources/Image/Penguins.jpg'></span>" +
			"</div>"
	});
	document.getElementById("roomList").innerHTML = html;
}

/* Message창 보여주기 */
function show_messageform(token, chat_num) {
	document.getElementById("chat_box").style.display = 'block';
	if(token == start) {
		if(typeof(localStorage) !== "undefined") {
			var html = 
				"<div id='message_container' class='panel2 panel-info msg_position' style='overflow:auto;'>"
				+	"<span onclick='close_message()' class='w3-closebtn'>&times;</span>" 
				+	"<div  class='panel-heading'>Message 보내기</div>"
				+	"<div id='msg_content'>"
				+	"</div>"
				+	"<input id='send_msg' class='form-control2 msg_content' type='text' onkeypress='if(event.keyCode==13) {send_message(" + chat_num + "); return false;}'>" +
				"</div>";		
				document.getElementById("chat_box").innerHTML = html;
		} else {
			document.getElementById("result").innerHTML = "Sorry, your browser does not support web storage...";
		}
	}
}



/* (현재 채팅방이 닫혀있는 상태에서) 채팅방 목록중에서 선택한 채팅방의 데이터를 가져온다  or  (페이지이동or새로고침) 했을시에 기존에 열려있던 채팅방의 데이터를 가져온다*/
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
		success : function(result){
			localStorage.setItem("chat", "true");			// localStorage의 chat을 true로 설정 (메시지창을 보여주기 위함, 규칙)
			localStorage.setItem("chat_num", chat_chat_num);// localStorage의 chat_num을 유저가 선택한 채팅방 번호로 설정 (새로고침 or 페이지 이동시에 열어두고있던 채팅방의 정보를 가져오기 위함)
			show_messageform("true", chat_chat_num);		// 메시지창 보여주기
			showMessage(result);							// 메시지 내용 보여주기
		},
		error : function(result){
			alert("error : " + result);
		}
	}); 
}




/*메시지 입력받음*/ 
function send_message(chat_num) {
	var msg1 = "{num : " + chat_num + "}";					// 방 번호 추가
	var msg2 = "[write : " + sessionId + "]";				// 작성자 ID
	var msg3 = document.getElementById("send_msg").value; 	// 메시지 내용
	var msg = (msg1 + msg2 + msg3);
	ws.send(msg); 	 										//서버로 메시지 전송
	
	send_msg.value = "";
}

/*화면에 message를 뿌려줌*/
function showMessage(result) {
	document.getElementById("msg_content").innerHTML = "";
	
	var html = "";
	var object = JSON.parse(result);							// JSON으로 파싱
	$(object).each(function() {									// 대화목록을 화면에 뿌려줌
	
		var msgBox = document.createElement("div");
		if(sessionId == this.written_user_id) {					// 사용자(본인)이 작성한 글이면 오른쪽으로 출력
			msgBox.style.float = "right";
			var textnode = document.createTextNode(this.msg);
		} else {												// 다른 사용자가 작성한 글이면 왼쪽으로 출력
			var textnode = document.createTextNode(this.written_user_id + " : " + this.msg);
		}
		msgBox.appendChild(textnode);
		document.getElementById("msg_content").appendChild(msgBox);
		msgBox.style.clear = "both";
	});
	
	var el = document.getElementById('message_container'); 	// 스크롤 항상 최신(아래)으로 유지
	if (el.scrollHeight > 0) {
		el.scrollTop = el.scrollHeight;
	}
}


// WebSocket Server connection
var wsUrl = "ws://localhost/chat";
var ws;

function init() {
	ws = new WebSocket(wsUrl);  //소켓 객체 생성
	ws.onopen = function(evt) { // 이벤트 발생시 opOpen()을 실행
		onOpen(evt);
	};
	
	ws.onmessage = function(evt) {
		onMessage(evt);
	}
	
	ws.onerror = function(evt) {
		onError(evt);
	}
	ws.onclose = function(evt) {
		console.log("close");
	}
}

function onOpen(evt) {
	
}

//서버로부터 대화목록 받음
function onMessage(evt) {
	showMessage(evt.data);	
//	console.log(evt.data);
}

function onError(evt) {
	
}





/* Message창 닫기 */
function close_message() {
	localStorage.setItem("chat", "false");							// localStorage chat설정 초기화
	localStorage.setItem("chat_num", null);							// localStorage chat_num설정 초기화
	document.getElementById("chat_box").style.display = 'none';		// 메시지 창 닫아주기
}




/* Web Socket Connection */
init();

/*
페이지간 이동시 메시지창이 열린상태에서 이동하면 token == "ture" == start 이다    (=> getChatRoom을 통해 메시지창을 계속 열어준다.)
페이지간 이동시 메시지창이 닫힌상태에서 이동했으면 token == "false" == fail      (=> 메시지창이 열리지 않는다.)*/
if(token == start) {
	getChatRoom(chat_num)
}

