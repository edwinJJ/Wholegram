<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="/resources/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="/resources/css/bootstrap.css">
	<link rel="stylesheet" href="/resources/css/message.css">
	<link rel="stylesheet" href="/resources/css/person.css">
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
 	<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script> 
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">
	<script src="https://use.fontawesome.com/9fc8d6f50a.js"></script>
	<script type="text/javascript" src="/resources/js/search.js"></script>
	<script src="resources/js/jquery.lazyloadxt.js" type="text/javascript"></script>
	<script type="text/javascript" src="/resources/js/jquery.modal.js"></script>
	<link href="/resources/css/jquery.modal.css" type="text/css" rel="stylesheet" />
<title></title>

</head>
<body>
<%@include file="./admin_header.jsp" %>
		<!-- 게시물 영역 -->
	<div id="section">
		<div id="contents">
			<div id="menu" class="fl fwb">
				둘러보기
			</div>

			<!-- 둘러보기 / 사용자가 following하지 않은 다른 user 리스트 -->
			<div id="titleA">
				<c:forEach items="${boardList}" var="b">
						<a href="/${b.user_id}" class="fl"> 
							<img class="cnt_img" src="${b.media_thumnail}" style="margin-bottom: 16px;" />
						</a>
				</c:forEach>
			</div>
		</div>
	</div>
<script>
	/* 관리자 로그아웃 */
	$(document).ready(function(e) {				
		$('a#confirm').click(function() {		// 
			modal({
				type: 'confirm',
				title: 'Logout',
				text: '로그아웃 하시겠습니까?',
				callback: function(result) {
					if(result == true) {
						close_message();
						location.href="/user/logout";
					} 
				}
			});
		});
	});
</script>
</body>
</html>