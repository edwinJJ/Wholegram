<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Insert title here</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 	<link rel="stylesheet" href="/resources/bootstrap/css/bootstrap.css"> -->
	<link rel="stylesheet" href="/resources/css/bootstrap.css">
	<link rel="stylesheet" href="/resources/css/w3.css">
	<link rel="stylesheet" href="/resources/css/message.css">
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="/resources/js/signup.js"></script>
	<style>
		.scope {
			width: 1445px;
			height: 815px;
			margin-left: 25%;
			margin-top:35px;
			background: #F8F8F8;
		}
		.menu {
			width: 17%;
			border-right: #FFFFFF;
			background: #FFFFFF;
		}
		.menu_content {
			width: 48%;
			border-right: 1px solid #ccc;
			background: #FFFFFF;
		}
		#profile_img { 
			margin-top: 5%;
			margin-left: 20%;
			width:50px;
			height:50px;
			border-radius: 50%;
			float: left;
			background: #0054FF;
		}
		.input_scope {
			margin-left: 25%;
			width: 50%;
		}
		textarea {
			margin-top: 30px;
			width: 100%;
			resize: none;
		}
	</style>
	<style>
		@media all and (min-width: 1075px) and (max-width: 1260px){
 			.scope{
				width: 1245px;
			} 
		}
		@media all and (min-width: 920px) and (max-width: 1075px){
 			.scope{
				width: 1045px;
			} 
		}
		@media all and (min-width: 700px) and (max-width: 919px){
			.menu{display: none;}
 			.scope{
				width: 1045px;
			} 
		}
		@media all and (min-width: 500px) and (max-width: 699px){
			.menu{display: none;}
 			.scope{
				width: 745px;
			} 
		}
		@media all and (max-width: 499px){
			.menu{display: none;}
 			.scope{
 				margin-left: -1%;
				width: 745px;
			} 
		}
	</style>
	<script>
		var sessionId = "${sessionId}";				// 접속자 ID
		var thisPage = false;						// 메시지 페이지가 아니라는 의미
		function msg() { 
			if((user_name.value != "") && (user_id.value != "") && (info.value != "") && (email.value != "") && (phone.value != "") && (gender.value != "")) {
				alert("프로필을 제출하였습니다.");
			}
		}
		/* Id 중복체크 */
		function id_chk() {
			var id = document.getElementById("user_id").value;
			var ic_url = "/user/id_chk/" + id;
			if(id.length > 5) {
				$.ajax({
					type: 'POST',
					url: ic_url,
					headers:{
						"Content-Type" : "application/json",
						"X-HTTP-Method-Override":"POST",
					},
					dataType:'JSON',
					data: '',
					success : function(result) {
						/* 바꾸려는 Id가 원래 Id이면 2 / 중복된 Id가 있으면 1, 없으면 0 */
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
		}
		/* Email 중복체크 */
/* 		function email_chk() {
			var em = document.getElementById("email").value;
			var ec_url = "/user/email_chk/" + em;
			alert(ec_url);
			if(em.indexOf('@')== -1 || em.indexOf('.') == -1){
				email_label.style.color="red";
				email_label.innerHTML="잘못된 형식의 이메일입니다.";
			} else{
				$.ajax({
					type:'POST',
					url:ec_url,
					headers:{
						"Content-Type" : "application/json",
						"X-HTTP-Method-Override":"POST",
					},
					dataType:'JSON',
					data: '',
					success : function(result){
						// 바꾸려는 Email가 원래 Email이면 2 / 중복된 Id가 있으면 1, 없으면 0 
						if(result == 1) {
							email_label.style.color = "red";
							email_label.innerHTML = "Email이 이미 있습니다.";
						} else {
							email_label.style.color = "#009688";
							email_label.innerHTML = "E-mail";
						}
					}
				});
			}
		} */
	</script>
<body>
<!-- 상단의 head 부분 -->
<%@include file="./header.jsp" %>
<div style="background: #F8F8F8;">　
	<div class="scope">
		<div class="w3-third menu">
			<ul class="w3-ul w3-border w3-center w3-hover-shadow">
				<li class="w3-padding-16"><a href="/user/update_form" style="text-decoration: none;">프로필 편집</a></li>
				<li class="w3-padding-16"><a href="/user/passwd_form" style="text-decoration: none;">비밀번호 변경</a></li>
				<li class="w3-padding-16"><a href="#" style="text-decoration: none;">허가된 앱</a></li>
				<li class="w3-padding-16"><a href="#" style="text-decoration: none;">이메일 기본 설정</a></li>
			</ul>
		</div>
		<div class="w3-third menu_content">
			<div class="w3-ul w3-border w3-center w3-hover-shadow">
				<div class="w3-container">
					<h4 class="w3-center" style="margin-top: 4.5%;">${vo.user_id }</h4>
					<p class="w3-center"><img src="/resources/Image/Penguins.jpg" class="w3-circle" style="height: 106px; width: 106px"></p>
				</div>
				<form id="pro_edit" action="/user/update_user" method="post">
					<input type="hidden" id="mem_no" name="mem_no" value="${vo.mem_no }">
					<div class="input_scope">
						<div class="w3-group">
							<input id="user_name" name="user_name" class="w3-input" type="text" required value="${vo.user_name }"> 
							<label class="w3-label w3-validate">Name</label>
						</div>
						<div class="w3-group">
							<input id="user_id" name="user_id" class="w3-input" type="text" required value="${vo.user_id }" onblur="id_chk()"> 
							<label id="user_id_label" class="w3-label w3-validate">User_ID</label>
						</div>
						<div class="w3-group">
					    	<textarea id="info" name="info" class="form-control w3-input2" required rows="3">${vo.info }</textarea>
					    	<label class="w3-label w3-validate2">소개</label>
					    </div>
					   	<div class="w3-group">
							<input id="email" name="email" class="w3-input" type="text" required value="${vo.email }" onblur="email_chk()"> 
							<label id="email_label" class="w3-label w3-validate">E-mail</label>
						</div>
					   	<div class="w3-group">
							<input id="phone" name="phone" class="w3-input" type="text" required value="${vo.phone }"> 
							<label class="w3-label w3-validate">Phone</label>
						</div>
					   	<div class="w3-group">
							<input id="gender" name="gender" class="w3-input" type="text" required value="${vo.gender }"> 
							<label class="w3-label w3-validate">Gender</label>
						</div>
					    <input id="recommend" name="recommend" class="w3-check" type="checkbox" style="margin-top:10px;" value="1">
						<label class="w3-validate">계정추천</label>
						<button type="submit" class="btn btn-info" style="margin-left: 50px" onclick="msg()">프로필 제출</button>
 					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<div id="chat_box"></div>
<script src="/resources/js/message.js"></script>
</body>
</html>
