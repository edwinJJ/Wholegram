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
