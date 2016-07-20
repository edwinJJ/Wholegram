var token = localStorage.getItem("chat");	// token을 "true" or "false"으로 지정
var start = "true";							// start - Message창 띄우기
var fail = "false";							// fail  - Message창 닫기

/* Message창 display 여부 확인 */
function check_messageform() {
	token = localStorage.getItem("chat");
	if(token == fail || token == null) {
		localStorage.setItem("chat", "true");
		token = localStorage.getItem("chat");
	}
	show_message(token);
	set_chatroom();
}



/* 채팅방 생성 */
function set_chatroom() {
	var id = document.getElementById("receive_user").value;
	console.log(id);
	var cr_url = "/message/chatroom/" + id;

	$.ajax({
		type: 'POST',
		url: cr_url,
		headers:{
			"Content-Type" : "application/json",
			"X-HTTP-Method-Override":"POST",
		},
		dataType:'JSON',
		data: '',
		success : function(result) {
			if(result == 1) {
				user_id_label.style.color = "red";
				user_id_label.innerHTML = "Id가 이미 있습니다.";
			} else {
				user_id_label.style.color = "#009688";
				user_id_label.innerHTML = "User_ID"
			}
		},
		error : function(result){
			alert("e : " + result);
		}
	})
	
}

/* Message창 보여주기 */
function show_message(token) {
	document.getElementById("chat_box").style.display = 'block';
	if(token == start) {
		if(typeof(localStorage) !== "undefined") {
			var html = 
				"<div id='message_container' class='panel2 panel-info msg_position' style='overflow:auto;'>"
				+	"<span onclick='close_message()' class='w3-closebtn'>&times;</span>" 
				+	"<div class='panel-heading'>Message 보내기</div>"
				+	"<div id='msg_content'>"
				+	"</div>"
				+	"<input id='send_msg' class='form-control2 msg_content' type='text' onkeypress='if(event.keyCode==13) {send_message(); return false;}'>" +
				"</div>";		
				document.getElementById("chat_box").innerHTML = html;
		} else {
			document.getElementById("result").innerHTML = "Sorry, your browser does not support web storage...";
		}
	}
}

/*메시지 입력받음*/ 
function send_message() {
	var user_name = receive_user.value;
	var msg = document.getElementById("send_msg");
	showMessage(msg.value);
	ws.send(msg.value); 	 //서버로 메시지 전송
	
	send_msg.value = "";
}

/*화면에 message를 뿌려줌*/
function showMessage(msg) {
	var msgBox = document.createElement("div");
	var textnode = document.createTextNode(msg);
	msgBox.appendChild(textnode);
	document.getElementById("msg_content").appendChild(msgBox);
	
	var el = document.getElementById('message_container'); 
	if (el.scrollHeight > 0) {
		el.scrollTop = el.scrollHeight;
	}
}



var wsUrl = "ws://localhost:8082/chat";
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
}

function onOpen(evt) {
	
}

function onMessage(evt) {
	showMessage(evt.data);
}

function onError(evt) {
	
}



/* Message창 닫기 */
function close_message() {
	localStorage.setItem("chat", "false");
	document.getElementById("chat_box").style.display = 'none';
}

init();
show_message(token);