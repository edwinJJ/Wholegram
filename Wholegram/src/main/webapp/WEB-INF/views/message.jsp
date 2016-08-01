<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Message</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="/resources/css/bootstrap.css"> 
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="/resources/bootstrap/js/bootstrap.js"></script>
	<link rel="stylesheet" href="/resources/css/message.css">
	<link rel="stylesheet" href="/resources/css/w3.css">
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<style>
		#header-logo {
			margin-left: -4px;
		}
		#header-search {
			margin-left: -2px;
		}
		#header-upload {
			margin-left: -2px;
		}
		#user_search {
			font-size: 32px;
			float:right; 
			margin-top:-0.2%; 
			margin-right: 10px;
			color:#4374D9;
		}
		#chat_header {
			margin-top: 1.5%;
			font-color: blue;
			background-color: #E9FFFF;
		}
		.chat_img {
			width: 65px;
			height: 65px;
		}
		.chat_aname {
			text-decoration:none!important;
		}
		.modal-content {
			
		}
		.modal-body {
			margin-top: -7px;
			margin-bottom: -30px;
		}
		.user_img {
			width: 65px;
			height: 65px;
			margin-bottom: -15px;
			margin-right: 20px;
		}
		.user_nm {
			margin-left: 10px;
			font-size: 20px;
		}
		.chat_name {
			margin-left: 3%;
			font-size: 18px;
		}
		.chat_content {
			width: 65px;
			height: 65px;
			float: right;
		}
		.well {
			background-color: #FFFFFF;
		}
		.add_chat1 {
			margin-top:13px;
			float:right;
		}
		.add_chat2 {
			height:20px; 
		}
		#msg_room {
			margin-top: -10%;
			float: right;
			width:300px;
			height: auto;
			background: #ccc;
		}
		.menu_design {
			float: right;
			
		}
		.form-group input[type="checkbox"] {
		    display: none;
		}

		.form-group input[type="checkbox"] + .btn-group > label span {
		    width: 20px;
		}

		.form-group input[type="checkbox"] + .btn-group > label span:first-child {
		    display: none;
		}
		.form-group input[type="checkbox"] + .btn-group > label span:last-child {
		    display: inline-block;   
		}

		.form-group input[type="checkbox"]:checked + .btn-group > label span:first-child {
		    display: inline-block;
		}
		.form-group input[type="checkbox"]:checked + .btn-group > label span:last-child {
		    display: none;   
		}
	</style>
	<style>
		@media all and (min-width: 1600px) and (max-width: 1800px) {
			.well {
				width: 900px;
			}
		}
		@media all and (min-width: 1301px) and (max-width: 1599px) {
			.well {
				width: 850px;
			}
		}
		@media all and (min-width: 1140px) and (max-width: 1300px) {
			.well {
				width: 800px;
			}
		}
		@media all and (min-width: 940px) and (max-width: 1139px) {
			.well {
				width: 700px;
			}
		}
		@media all and (min-width: 840px) and (max-width: 939px) {
			.well {
				width: 600px;
			}
		}
		@media all and (min-width:769px) and (max-width: 839px) {
			.well {
				width: 500px;
			}
		}
		@media all and (min-width:740px) and (max-width: 768px) {
			.well {
				width: 450px;
			}
		}
 		@media all and (min-width:640px) and (max-width: 739px) {
			.well {
				width: 400px;
			}
		}
		@media all and (max-width: 639px) {
			.well {
				width: 250px;
			}
		}
	</style>
	<script>
		var sessionId = "${sessionId}";				// 접속자 ID
		var thisPage = true;						// 메시지 페이지라는 의미

		/* 채팅 상대 고를 때, 선택 된 유저 아이디를 문자열 형태로 화면에 보여준다 */
		function addReceive(th) {
			if(th != undefined) {				// 메시지 보낼 유저를 선택했을 시
	 			var id_value = th.value;
				if(th.checked) {
					receive_user.value += id_value + ",";
				} else {
	 				var text = receive_user.value.replace(id_value +"," ,"");
					receive_user.value = text;
				}
			} else {							// 창을 닫았을 시
				receive_user.value = "";
			}
		}
		
		/* 채팅 상대 고를 때 현재 팔로우 하고있는 유저 리스트를 가져온다 */
		function followingList() {
			var ig_url = "/message/getFollowing_Userid";
 			$.ajax({
				type : 'POST',
				url : ig_url,
				headers : {
					"Content-Type" : "application/json",
					"X-HTTP-Method-Override":"POST",
				},
				dataType:'JSON',
				data: '',
				success : function(result){
					showFollowingList(result);
				},
				error : function(result){
					alert("e : " + result);
				}
			}); 
		}
		
		/* 팔로우 하고있는 유저 리스트를 화면에 뿌려준다 */
		function showFollowingList(result) {
			var html = "";
			var identify_count = 0;
			$(result).each(function() {
					html +=
					"<div class='modal-body'>"
					+	"<div class='panel panel-default'>"
					+		"<div class='panel-body'>"
					+        	"<div class='[ form-group ]'>"
					+				"<img class='user_img' src='/resources/Image/Penguins.jpg'>"
					+				"<span class='user_nm'>" + this.user_id + "</span>"
					+				"<input type='checkbox' name='fancy-checkbox-success" + identify_count + "' id='fancy-checkbox-success" + identify_count +"' autocomplete='off' value='" + this.user_id + "' onclick='addReceive(this)'/>"
					+				"<div class='[ btn-group ] add_chat1'>"
					+					"<label for='fancy-checkbox-success" + identify_count + "' class='[ btn btn-success ]'>"
					+                   	"<span class='[ glyphicon glyphicon-ok ] add_chat2'></span>"
					+                  		"<span> </span>"
					+                	"</label>"
					+           	"</div>"
					+			"</div>"
					+		"</div>"
					+	"</div>" +
					"</div>";
					identify_count++;
			});
			document.getElementById("followingList").innerHTML = html;
		}
		
		/* 채팅방 삭제 */
		function delRoom(roomNumber) {
			var rDel_url = "/message/delRoom/" + roomNumber;
			$.ajax({
				type : 'POST',
				url : rDel_url,
				headers : {
					"Content-Type" : "application/json",
					"X-HTTP-Method-Override" : "POST"
				},
				dataType:'JSON',
				data : '',
				success : function(result) {
					showRoomList(result);
				}, 
				error : function(result) {
					alert("error : " + result);
				}
			});
		}
	</script>
</head>
<body>
<%@include file="./header.jsp" %>
<div style="background: #F8F8F8; height: 90.5%">
	<div class="container-fluid">
		<div class="row content">
			<div class="col-sm-9">
				<div id="chat_header" class="well">
					<div style="text-align: center;">
						<font style="color: #4375DB; font-size: 20px;">인스타그래머에게</font>
						<!-- <a id="user_search" href="#" data-toggle="modal" data-target="#myModal"><i class="fa fa-search-plus fa-2x" aria-hidden="true"></i></a> -->
						<a id="user_search" class="w3-btn-floating w3-ripple w3-teal2" data-toggle="modal" data-target="#myModal" onclick="followingList()" style="text-decoration:none">+</a>
					</div>
				</div>
				<!-- 채팅방 목록 -->
				<div id="roomList">
					<c:forEach items="${roomInfomation}" var="ri">
						<div class="well">
							<c:choose>
								<c:when test="${ri.msgNotice }">
									<span id="room_popup${ri.chat_chat_num}" class="w3-badge w3-left w3-small w3-red" style="display:block">!</span>
								</c:when>
								<c:otherwise>
									<span id="room_popup${ri.chat_chat_num}" class="w3-badge w3-left w3-small w3-red" style="display:none">!</span>
								</c:otherwise>
							</c:choose>
							<button type="button" class="close" onclick="delRoom(${ri.chat_chat_num})">&times;</button>
							<span><img class="chat_img" src="/resources/Image/Penguins.jpg"></span>
							<a href="#" class="chat_aname" onclick="getChatRoom(${ri.chat_chat_num})" ><span class="chat_name">채팅방 : ${ri.chat_chat_num } </span></a>
							<span>${ri.member_user_id}</span>
							<!-- <span><img class="chat_content" src="/resources/Image/Penguins.jpg"></span> -->
						</div>
					</c:forEach>
				</div>
				<!-- 메시지보낼 친구 찾는 Modal -->
				<div class="modal fade" id="myModal" role="dialog">
					<div class="modal-dialog modal-lg">
						<div class="modal-content">
							<div class="modal-header2">
								<button type="button" class="close" data-dismiss="modal" onclick="addReceive()">&times;</button>
								<h4 class="modal-title">Message 보내기</h4>
							</div>
							<div>
								<input id="receive_user" class="w3-input3" type="text" placeholder="받는 사람"> 
							</div>
							<!-- 사용자가 팔로우하고있는 유저 목록 -->
							<div id="followingList"></div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal" onclick="check_messageform()">메시지 보내기</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="chat_box"></div>
<script src="/resources/js/message.js"></script>
</body>
</html>