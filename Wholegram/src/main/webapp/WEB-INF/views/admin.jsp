<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<link rel="stylesheet" href="/resources/css/bootstrap.css">
	<link rel="stylesheet" href="/resources/css/message.css">
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<script src="http://code.jquery.com/jquery-1.7.min.js"></script>
	<script src="resources/js/jquery.lazyloadxt.js" type="text/javascript"></script>
  	<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script> 
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">
	<script type="text/javascript" src="/resources/js/search.js"></script>
<title></title>
<style>
* {
	margin: 0;
	padding: 0;
}

input[type="button"], input[type="text"] {
	outline-style: none; /* 포커스시 발생하는 효과 제거 */
	-webkit-appearance: none; /* 브라우저별 기본 스타일링 제거 */
	-moz-appearance: none;
	appearance: none;
	border-style: none;
}

input[type="button"] {
	background: #fff;
	color: #b3b3b3;
}

li {
	list-style: none;
}

.container {
	margin-top: 50px;
}

table, th, td {
	text-align: center;
}

.img {
	width: 50px;
	height: 50px;
}

.fr {
	float: right;
}

.fl {
	float: left;
}

.tar {
	text-align: right;
}

#user_img {
	width: 38px;
	height: 38px;
	border-radius: 50%;
}

/* option 팝업 */

.popupLayer {
	display: none;
}

#popupContents {
	z-index: 9999;
	position: fixed;
	top: 50%;
	right: 0;
	width: 200px;
	margin: -102.5px 0 0 -257px;
	border: 1px solid #efefef;
}

#popupContents  li {
	background: #fff;
	width: 200px;
	height: 50px;
	padding: 0px 16px;
	line-height: 50px;
}

#popupContents  li:hover {
	background: #efefef;
	font-weight: 600;
}

</style>
</head>
<body>
<%@include file="./admin_header.jsp" %>
<div class="container">
	<table class="table table-hover">
		<thead>
			<tr>
				<th>미리보기</th>
				<th>게시물 번호</th>
				<th>게시자</th>
				<th>신고 수</th>
				<th>삭제</th>
			</tr>	
		</thead>
		<tbody id="tbody">
			<c:forEach items="${bList}" var="b">
				<tr>
					<td>
						<a href="/admin/${b.board_num}"> 
							<img class="img" src="${b.media_thumnail}" />
						</a>
					</td>
					<td>${b.board_num}</td>
					<td>${b.user_id}</td>
					<td onclick="openPopup(${b.board_num})">${b.report}</td>
					<td><input type="button" id="deleteBtn" onclick="deleteAll(${b.board_num})" value="X" /></td>
				</tr>
				<div id="popupLayer${b.board_num}" class="popupLayer">
					<ul id="popupContents">
						<c:forEach items="${rpList}" var="r">
							<c:if test="${b.board_num eq r.board_num}">
								<li >
									<div id="user">
										<img id="user_img" src="/user/getByteImage/${r.user_id}">
										<a href="/${r.user_id}">${r.user_id}</a>
									</div>
								</li>
							</c:if>
						</c:forEach>
						<li class="tar" onclick="closePopup(${b.board_num})">X</li>
					</ul>
				</div>
			</c:forEach>
		</tbody>
	</table>
</div>


<script>

function openPopup( bno ) {
	var popup = document.getElementById("popupLayer" + bno);
	$(popup).show();
 }
 
function closePopup( bno ) {
	var popup = document.getElementById("popupLayer" + bno);
	$(popup).fadeOut();
}

function deleteAll( bno ) {
	$.ajax({
		type : 'delete',
		url : '/admin/delete/'+ bno,
		headers : {
			"Content-Type" : "application/json",
			"X-HTTP-Method-Override" : "DELETE",
		},
		data : '',
		dataType : 'json',
		success : function( result ) {
			setList(result.boardList);
		},
		error : function(request,status,error) {
			alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}	
	});
}

function setList(data) {
	var	result	= "";
	$(data).each(function() {	
		result += "<tr><td><a href='/admin/"+ this.board_num +"'><img class='img' src='"+ this.media_thumnail +"'/></a></td><td>"+ this.board_num+"</td><td>"+ this.user_id+"</td><td onclick='openPopup("+this.board_num+")'>"+ this.report+"</td><td><input type='button' id='deleteBtn' onclick='deleteAll("+this.board_num+")' value='X'/></td></tr>";
	});
	document.getElementById( "tbody" ).innerHTML = result;
}

</script>
</body>

</html>