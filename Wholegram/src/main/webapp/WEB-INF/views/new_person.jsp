<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- <%@ page session="false" %>--%>
<!DOCTYPE html>
<html>
<head>
	<title>Wholegram</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="/resources/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="/resources/css/bootstrap.css">
	<link rel="stylesheet" href="/resources/css/w3.css">
	<link rel="stylesheet" href="/resources/css/message.css">
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<link href="/resources/css/jquery.modal.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="/resources/js/jquery.modal.js"></script>
	
	<style>
		.scope {
			width: 100%;
			height: 800px;
			margin-left: 25%;
			margin-top:35px;
			background: #F8F8F8;
		}
		.tab_menu {
			margin-left: 34.4%;
			margin-bottom: -41.5px;
		}
		.browse {
			width: 300px;
		}
		.find_person {
			width: 295px;
		}
	</style>
</head>
<body>
<!-- 상단의 head 부분 -->
<%@include file="./header.jsp" %>
<div style="background: #F8F8F8;">　
		<!-- Navbar -->
	<ul class="w3-navbar w3-red2 w3-card-2-2 w3-left-align w3-large tab_menu">
<!--	<li class="w3-hide-medium w3-hide-large w3-opennav w3-right"><a class="w3-padding-large w3-hover-white w3-large w3-red" href="javascript:void(0);" onclick="myFunction()" title="Toggle Navigation Menu"><i class="fa fa-bars"></i></a></li> -->
		<li class="browse"><a href="#" class="w3-padding-jumbo2 w3-white2 w3-hover-shadow">Home</a></li>
		<li class="find_person"><a href="#" class="w3-padding-jumbo2 w3-hover-white w3-hover-shadow">Link 1</a></li>
	</ul>
	<div class="scope">
		<div class="w3-third">
			<div class="w3-ul w3-border2 w3-center w3-hover-shadow">
				<div class="w3-container">
				</div>
			</div>
		</div>
	</div>
</div>
<div id="chat_box"></div>
<script src="/resources/js/message.js"></script>
</body>
</html>

