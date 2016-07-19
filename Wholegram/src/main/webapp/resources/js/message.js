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
}

function send_message() {
	send_msg.value = "";
}

/* Message창 보여주기 */
function show_message(token) {
	document.getElementById("chat_box").style.display = 'block';
	if(token == start) {
		if(typeof(localStorage) !== "undefined") {
			var html = 
				"<div class='panel2 panel-info msg_position'>"
				+	"<span onclick='close_message()' class='w3-closebtn'>&times;</span>" 
				+	"<div class='panel-heading'>Message 보내기</div>"
				+	"<div class='panel-body'>Panel Content</div>"
				+	"<input id='send_msg' class='form-control2 msg_content' type='text' onkeypress='if(event.keyCode==13) {send_message(); return false;}'>" +
				"</div>";		
				document.getElementById("chat_box").innerHTML = html;
		} else {
			document.getElementById("result").innerHTML = "Sorry, your browser does not support web storage...";
		}
	}
}

/* Message창 닫기 */
function close_message() {
	localStorage.setItem("chat", "false");
	document.getElementById("chat_box").style.display = 'none';
}

show_message(token);