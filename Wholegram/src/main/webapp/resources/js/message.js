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
			
			
			
			startWebWorker();
		},
		error : function(result){
			alert("e : " + result);
		}
	})
}

/* Message창 보여주기 */
function show_messageform(token, chat_num) {
	document.getElementById("chat_box").style.display = 'block';
	if(token == start) {
		if(typeof(localStorage) !== "undefined") {
			var html = 
				"<div id='message_container' class='panel2 panel-info msg_position' style='overflow:auto;'>"
				+	"<span onclick='close_message()' class='w3-closebtn'>&times;</span>" 
				+	"<div class='panel-heading'>Message 보내기</div>"
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

	var object = JSON.parse(result);					// JSON으로 파싱
	$(object).each(function() {							// 대화목록을 화면에 뿌려줌
		var msgBox = document.createElement("div");
		var textnode = document.createTextNode(this.msg);
		msgBox.appendChild(textnode);
		document.getElementById("msg_content").appendChild(msgBox);
		var el = document.getElementById('message_container'); 
		if (el.scrollHeight > 0) {
			el.scrollTop = el.scrollHeight;
		}
	});

	
/*	var msgBox = document.createElement("div");
	var textnode = document.createTextNode(msg);
	msgBox.appendChild(textnode);
	document.getElementById("msg_content").appendChild(msgBox);
	
	var el = document.getElementById('message_container'); 
	if (el.scrollHeight > 0) {
		el.scrollTop = el.scrollHeight;
	}
*/
}







function startWebWorker() {
	var w;
	if(typeof(Worker) !== "undefined") {
        if(typeof(w) == "undefined") {
        	alert("a");
            w = new Worker("/resources/js/test.js");
        }
        w.postMessage("test");
        w.onmessage = function(event) {
        	alert(event.data);
        };
    } else {
        document.getElementById("result").innerHTML = "Sorry, your browser does not support Web Workers...";
    }
}






















// WebSocket Server connection
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





/*if(token == start) {
	console.log("a");
} else {
	console.log("b");
	init();	
}*/

init();
if(token == start) {
	show_messageform(token, chat_num);
	//ws.send("init");
}