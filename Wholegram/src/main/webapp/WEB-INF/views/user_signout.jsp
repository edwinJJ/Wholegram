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
			margin-top: 50px;
			margin-left: 25%;
			width: 50%;
		}
		textarea {
			margin-top: 30px;
			width: 100%;
			resize: none;
		}
		#auth {
			margin-left:15px; 
			margin-top:5px; 
		}
	

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
	
		var authstr = "";							// 메일 인증번호
		
		function phclear(self) {
			self.placeholder = "";
		}
	
		//메일 보내기
		function sendMail() {
			var emailSend_url = "/sendMail/Signout";
			$.ajax({
				type:'POST',
				url: emailSend_url,
				headers:{
					"Content-Type" : "application/json",
					"X-HTTP-Method-Override":"POST",
				},
				dataType:'text',
				success : function(result){	
					alert("인증 메일을 발송했습니다.");
					authstr = result;												// 메일 인증번호 담기
					document.getElementById("emailarea").style.display = "block";	// 인증번호 입력칸 보여주기
				},
				error : function(result){
					alert("error : 없는 메일 주소 입니다.");
				}
			});
		}
		
		// 메일 인증번호 확인
		function auth_check() {
			var auth = document.getElementById("emailauth").value;
			var emailchk = document.getElementById("emailchk");
			if(authstr == auth) {									// 인증번호 일치할경우
				emailchk.style.color="#009688";
				var pwinput = document.getElementById("pwinput");
				pwinput.style.display="block";
			} else {												// 인증번호 불일치
				emailchk.style.color="#f44336";
			}
		}
		
		//회원 탈퇴
		function signOut() {
			var passwd = document.getElementById("passwd").value;
			var mem_no = document.getElementById("mem_no").value;
			var cp_url = "/user/check_passwd/" + mem_no + "/" + passwd;
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
					if(result == 0) {	//실패
						alert("비밀번호가 틀렸습니다.");
					} else {
						signOut2();
					}
				},
				error : function(result){
					alert("Error_chk");
				}
			});
		}
		
		function signOut2() {
			var sout_url = "/signout";
			$.ajax({
				type: 'POST',
				url: sout_url,
				headers:{
					"Content-Type" : "application/json",
					"X-HTTP-Method-Override":"POST",
				},
				dataType:'text',
				data: '',
				success : function(result) {
					alert("회원 탈퇴가 완료되었습니다. 감사합니다.");
					location.href="/login";
				},
				error : function(result){
					alert("Error_chk");
				}
			});
		}
	</script>
<body>
<!-- 상단의 head 부분 -->
<%@include file="./header.jsp" %>

<!-- 뉴스(소식)  -->
<div id="news_box" style="display: none;"></div>

<div style="background: #F8F8F8;">　
	<div class="scope">
		<div class="w3-third menu">
			<ul class="w3-ul w3-border w3-center w3-hover-shadow">
				<li class="w3-padding-16"><a href="/user/update_form" style="text-decoration: none;">프로필 편집</a></li>
				<li class="w3-padding-16"><a href="/user/passwd_form" style="text-decoration: none;">비밀번호 변경</a></li>
				<li class="w3-padding-16"><a href="/user/passwd_singout" style="text-decoration: none;">회원탈퇴</a></li>
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
							<p class="w3-center"><img src="/resources/Image/Default.png" class="w3-circle" style="height: 106px; width: 106px"></p>
						</c:otherwise>
					</c:choose>
				</div>
				<form id="passwd_edit" onSubmit="return false">
					<input type="hidden" id="mem_no" name="mem_no" value="${vo.mem_no }">
					<div class="input_scope">
						<button type="button" class="btn btn-primary" onclick="sendMail();">이메일 인증</button>
 						<div id="emailarea" class="w3-group" style="display:none;">
							<input id="emailauth" name="passwd" class="w3-input" type="password" placeholder="Email 인증번호" onclick="phclear(this);" onblur="auth_check();" required> 
							<label id="emailchk" style="color:#f44336">이메일 확인</label>
						</div>
						<div id="pwinput" class="w3-group" style="display:none;">
							<input id="passwd" name="passwd" class="w3-input" type="password" placeholder="비밀번호 입력" onclick="phclear(this);" required> <label class="w3-label w3-validate">비밀번호 확인</label><br>
							<button type="button" class="btn btn-danger" onclick="signOut();">회원탈퇴</button>
							<div id="message" style="margin-top:10px;"></div>
						</div>
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
