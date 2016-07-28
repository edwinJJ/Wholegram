
/* Message & news popup */
function showPopup() {
	
	var html = 
		"<div id='message_container' class='panel2 panel-info msg_position' style='overflow:auto;'>"
		+	"<input id='chat_num' type='hidden' value='" + chat_num + "'/>"
		+	"<span onclick='close_message()' class='w3-closebtn'>&times;</span>" 
		+	"<div  class='panel-heading'>Message 보내기</div>"
		+	"<div id='msg_content'></div>" +
		"</div>"
		+	"<input id='send_msg' class='form-control2 msg_content' type='text' onkeypress='if(event.keyCode==13) {send_message(" + chat_num + "); return false;}'>";
		document.getElementById("popup").innerHTML = html;
}


