<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Web Chatting</title>
</head>
<body onload="init()">
	<script>
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
		}
		
		function onOpen(evt) {
			
		}
		
		function onMessage(evt) {
			showMessage(evt.data);
		}
		
		function onError(evt) {
			
		}
		
		// 화면에 message를 뿌려줌
		function showMessage(msg) {
			var output = document.getElementById("output");
			var msgBox = document.createElement("div");
			msgBox.innerHTML = msg;
			
			output.appendChild(msgBox);
		}
		
		//메시지 입력받음
		function sendMessage() {
			var msg = document.getElementById("message");
			showMessage(msg.value);
			ws.send(msg.value);
			msg.value = "";
		}
	</script>
	<div id="input">
		<input type="text" id="message" name="message" onkeydown="if(event.keyCode == 13) {sendMessage(); return false;}"/>
	</div>
	<div id="output">
	
	</div>
</body>
</html>