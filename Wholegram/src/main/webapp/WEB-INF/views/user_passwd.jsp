<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<title>Insert title here</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="/resources/css/w3.css">
	<link rel="stylesheet" href="/resources/css/message.css">
	<link rel="stylesheet" href="/resources/css/bootstrap.css">
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<style>
		.scope {
			width: 1445px;
			height: 815px;
			margin-left: 25%;
			margin-top:35px;
			background: #F8F8F8
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
		function update_passwd(){
			check_passwd();
		}
		/* 이전 비밀번호 확인 */
		function check_passwd() {
			var passwd = document.getElementById("passwd").value;
			var mem_no = document.getElementById("mem_no").value;
			var passwd_new = document.getElementById("passwd_new").value;
			var cp_url = "/user/check_passwd/" + mem_no + "/" + passwd;
			if((passwd != null) && (passwd != "") && (passwd_new.length > 5)) {
				$.ajax({
					type: 'POST',
					url: cp_url,
					headers:{
						"Content-Type" : "application/json",
						"X-HTTP-Method-Override":"POST",
					},
					dataType:'JSON',
					data: '',
					success : function(result) {
						if(result == 0) {
							//실패
							message.style.color = "red";
							message.innerHTML = "이전 비밀번호를 다시 확인해주세요.";
						} else {
							change_passwd(mem_no);			
						}
					},
					error : function(result){
						alert("Error_chk");
					}
				})
			} else {
				alert("비밀번호 입력을 다시 확인해주세요.");
			}
		}
		/* 비밀번호 변경 */
		function change_passwd(mem_no) {
			var passwd_new = document.getElementById("passwd_new").value;
			var passwd_newck = document.getElementById("passwd_newck").value;
			var chg_url = "/user/change_passwd/" + mem_no + "/" + passwd_new;
			if((passwd_new == passwd_newck) && (passwd_new != null) && (passwd_new != "")) {
				$.ajax({
					type: 'POST',
					url: chg_url,
					headers:{
						"Content-Type" : "application/json",
						"X-HTTP-Method-Override":"POST",
					},
					dataType:'text',
					data: '',
					success : function(result) {
						message.style.color = "blue";
						message.innerHTML = "비밀번호가 변경되었습니다.";
						document.getElementById("passwd").value = "";
						document.getElementById("passwd_new").value = "";
						document.getElementById("passwd_newck").value = "";
					},
					error : function(result){
						alert("Error_chg : " + result);
					}
				})
			} else {
				message.style.color = "red";
				message.innerHTML = "새로운 비밀번호를 다시 확인해주세요.";
			}
		}
		/* 새 비밀번호 길이 체크 */
		function pwleng_check() {
			var pw = document.getElementById("passwd_new").value;
			var pl = pw.length;
			if(pl < 6) {
				message.style.color = "red";
				message.innerHTML = "비밀번호는 6자리 이상이어야 합니다.";
			} else {
				message.innerHTML = "";
			}
		}
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
			</ul>
		</div>
		<div class="w3-third menu_content">
			<div class="w3-ul w3-border w3-center w3-hover-shadow">
				<div class="w3-container">
					<h4 class="w3-center" style="margin-top: 4.5%;">${vo.user_id }</h4>
					<c:choose>
						<c:when test="${vo.default_profile != 1 }">
							<p class="w3-center"><img src="/user/getByteImage" class="w3-circle" style="height: 106px; width: 106px"></p>
						</c:when>
						<c:otherwise>
							<p class="w3-center"><img src="/resources/upload/user/Default.png" class="w3-circle" style="height: 106px; width: 106px"></p>
						</c:otherwise>
					</c:choose>
				</div>
				<form id="passwd_edit" onSubmit="return false">
					<input type="hidden" id="mem_no" name="mem_no" value="${vo.mem_no }">
					<div class="input_scope">
						<div class="w3-group">
							<input id="passwd" name="passwd" class="w3-input" type="password" required> <label class="w3-label w3-validate">이전 비밀번호</label>
						</div>
						<div class="w3-group">
							<input id="passwd_new" name="passwd_new" class="w3-input" type="password" required onblur="pwleng_check()" > <label class="w3-label w3-validate">새 비밀번호</label>
						</div>
					   	<div class="w3-group">
							<input id="passwd_newck" name="passwd_newck" class="w3-input" type="password" required> <label class="w3-label w3-validate">새 비밀번호 확인</label>
						</div>
						<button class="btn btn-info" style="margin-top:20px;" onclick="update_passwd()">비밀번호 변경</button>
						<div id="message" style="margin-top:10px;"></div>
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
