<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>
<html>
<head>
	<meta charset="utf-8" />
	<title>home</title>
	<link rel="stylesheet" type="text/css" href="/resources/css/header.css">
	<link rel="stylesheet" href="/resources/css/w3.css">
	<link rel="stylesheet" href="/resources/css/bootstrap.css">
	<link rel="stylesheet" href="/resources/css/message.css">
	<link rel="stylesheet" type="text/css" href="/resources/css/home.css">	
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="https://use.fontawesome.com/9fc8d6f50a.js"></script>
	<script type="text/javascript" src="/resources/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="/resources/js/jquery.easing.1.3.min.js"></script>

	<script language="javascript">
	
		var sessionId = "${sessionId}";				// 접속자 ID
		var thisPage = null;
	
		//레이어 팝업
		function popup() {
			$("#openPopup").on("click", function() {
				$("#popupLayer").fadeIn();
			});
			$("#closePopup").on("click", function() {
				$("#popupLayer").fadeOut();
			});
		};
	
		function nullContentNone() {
			var bd_place = document.getElementById( "bd_place" ).value;
			if( bd_place == null || bd_place == "" ) {
				bd_place.style.display("none");
			}
	
		}
	 
	
	$(document).ready(function() {
		popup();
	});
	</script>
</head>
<body>
	<!-- 상단의 header라인 부분 -->
	<%@include file="./header.jsp"%>

	<!-- line -->
	<div class="line"></div>

	<!-- 게시물 영역 -->
	<div id="section">
		<div id="contents">
			<div id="cnt_board">
				<div id="cnt_board2">
					<c:forEach items="${bList}" var="bd">
						<c:choose>
							<c:when test="${bd.aldy_heart}"> <!-- 게시물에 좋아요가 눌러져있는 경우 -->
								<div id="cnt_header">
									<a id="cnt_user_img" class="fl" herf=""> 
										<img src="/resources/Image/Hydrangeas.jpg">
									</a>
									<div id="cnt_header_user" class="fl">
										<a href="#"> ${bd.user_id}</a> 
										<a class="bd_place" href="#">${bd.place}</a>
									</div>
									<a id="cnt_header_date" class="fr" herf="">${bd.reg_date}</a>
								</div>
		
								<div id="cnt_board_img">
									<img src="/resources/Image/Hydrangeas.jpg">
								</div>
		
								<div id="cnt_board_content">
									<div id="cnt_board_heart${bd.board_num}">좋아요 ${bd.heart}개</div>
									<ul id="cnt_board_list">
										<li>
											<h1>
												<a class="user_id" href="">${bd.user_id}</a> 
												<span> ${bd.content} </span>
											</h1>
										</li>								
										<li id="reply_list${bd.board_num }">
											<c:forEach items="${replyResult}" var="rp">
												<c:if test="${bd.board_num == rp.board_num}">
													<a class="user_id" href="">${rp.user_id }</a> 
													<span>${rp.content}</span>
													<c:if test="${sessionId == rp.user_id}">
														<input type="button" class="deleteBtn" value="삭제" onclick="deleteReply(${bd.board_num},${rp.reply_num})" />
													</c:if>
													<br/>
												</c:if>
											</c:forEach>
										</li>
									</ul>
									<div id="cnt_reply">
										<a class="heart" href="#self">
											<br/>
											<i id="heart_full${bd.board_num}" class="test fa fa-heart fa-2x" aria-hidden="true" onclick="heartCount(${bd.board_num} );"></i> 
											<i id="heart_empty${bd.board_num}" class="test fa fa-heart-o fa-2x" aria-hidden="true" style="display: none;"onclick="heartCount(${bd.board_num} );"></i>
										</a>
										<input type="hidden" id="board_num" name="board_num" value="${bd.board_num }" /> 
										<input type="text" id="content${bd.board_num}" name="content${bd.board_num}" style="width: 450px; outline-style: none;" onkeydown="javascript:if( event.keyCode == 13 ) insertReply(${bd.board_num})" placeholder="댓글달기..." /> 
										
										<a href="#self" id="openPopup"> 
											<i class="fa fa-ellipsis-h fa-2x fr" style="color: #bfbfbf;" aria-hidden="true"></i>
										</a>
									</div>
								</div>
							</c:when>
							
							<c:otherwise>  <!-- 게시물에 좋아요가 안 눌러져있는 경우 -->
								<div id="cnt_header">
									<a id="cnt_user_img" class="fl" herf=""> 
										<img src="/resources/Image/Hydrangeas.jpg">
									</a>
									<div id="cnt_header_user" class="fl">
										<a href="#"> ${bd.user_id}</a> 
										<a class="bd_place" href="#">${bd.place}</a>
									</div>
									<a id="cnt_header_date" class="fr" herf="">${bd.reg_date}</a>
								</div>
		
								<div id="cnt_board_img">
									<img src="/resources/Image/Hydrangeas.jpg">
								</div>
		
								<div id="cnt_board_content">
									<div id="cnt_board_heart${bd.board_num}">좋아요 ${bd.heart}개</div>
									<ul id="cnt_board_list">
										<li>
											<h1>
												<a class="user_id" href="">${bd.user_id}</a> 
												<span> ${bd.content} </span>
											</h1>
										</li>								
										<li id="reply_list${bd.board_num }">
											<c:forEach items="${replyResult}" var="rp">
												<c:if test="${bd.board_num == rp.board_num}">
													<a class="user_id" href="">${rp.user_id }</a> 
													<span>${rp.content}</span>
													<c:if test="${sessionId == rp.user_id}">
														<input type="button" class="deleteBtn" value="삭제" onclick="deleteReply(${bd.board_num},${rp.reply_num})" />
													</c:if>
													<br/>
												</c:if>
											</c:forEach>
										</li>
									</ul>
									<div id="cnt_reply">
										<a class="heart" href="#self">
										<csaf>
											<br/>
											<i id="heart_full${bd.board_num}" class="test fa fa-heart fa-2x" aria-hidden="true" style="display: none;" onclick="heartCount(${bd.board_num} );"></i>
											<i id="heart_empty${bd.board_num}" class="test fa fa-heart-o fa-2x" aria-hidden="true" onclick="heartCount(${bd.board_num} );"></i> 
										</csaf>
										</a>
										<input type="hidden" id="board_num" name="board_num" value="${bd.board_num }" /> 
										<input type="text" id="content${bd.board_num}" name="content${bd.board_num}" style="width: 450px; outline-style: none;" onkeydown="javascript:if( event.keyCode == 13 ) insertReply(${bd.board_num})" placeholder="댓글달기..." /> 
										
										<a href="#self" id="openPopup"> 
											<i class="fa fa-ellipsis-h fa-2x fr" style="color: #bfbfbf;" aria-hidden="true"></i>
										</a>
									</div>
								</div>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>

	<!-- option 메뉴 팝업 -->
	<div id="popup_wrap">
		<div id="popupLayer">
			<div class="bg"></div>
			<ul id="popupContents">
				<li><a href="#" id="">부적절한 콘텐츠 신고</a></li>
				<li><a href="#" id="">퍼가기</a></li>
				<li><a href="#" id="">다운로드</a></li>
				<li><a href="#" id="closePopup">취소</a></li>
			</ul>
		</div>
	</div>
	
	<div id="chat_box"></div>
	<script src="/resources/js/message.js"></script>

	<script>
	var bno = '${bd.board_num}';
	var rno = '${rp.reply_num}';
	var sessionId = '${sessionId}';
	var lno = '${likeNum}';
	
	function deleteReply( bno, rno ) {
		$.ajax({
			type : 'delete',
			url : '/board/'+ bno +'/' + rno,
			headers : {
				"Content-Type" : "application/json",
				"X-HTTP-Method-Override" : "DELETE",
			},
			data : '',
			dataType : 'json',
			success : function( result ) {
				setReplyList(result.delList, bno);
			},
			error : function( result ) {
				alert("fail");
			}	
		});
	}
	
	function insertReply( bno ) {
		var reply_content = $("#content"+bno).val();
		var url = "/board/"+ bno +"/" + reply_content;
		
		$.ajax({
			type : 'GET',
			url : url,
			headers : {
				"Content-Type" : "application/json",
			},
			data : 
				JSON.stringify({content:reply_content}),
			dataType : 'json',
			success : function(result){
				setReplyList(result.result, bno);
			},
			error : function(result) {
				alert("fail");
			}
		});
	}
	
	function heartCount(board_num ) {
		var hc_url = "/board/heart/" + board_num;
		$.ajax({
			type : 'GET',
			url : hc_url,
			headers : {
				"Content-Type" : "application/json",
			},
			data : '',
			dataType : 'json',
			success : function(result){
				heartChange(result, board_num);
			},
			error : function(result) {
				alert("insertHeart fail");
			}
		});
	}
	
	function heartChange(result, board_num) {
		var hep = document.getElementById("heart_empty" + board_num);
		var hef = document.getElementById("heart_full" + board_num);

		 if($(hep).css("display") == "none"){
			 $(hef).hide();
		      $(hep).show();
		      
		  } else {
		      $(hep).hide();
		      $(hef).show();
		  }
		
		var cbh = document.getElementById("cnt_board_heart" + board_num);
		cbh.innerHTML = "좋아요 " + result + "개";
	}
	
	function setReplyList(data, bno) {
		var	result	= "";
		$(data).each(function() {	
			result += "<li><a class='user_id' href='#'>"+ this.user_id + "</a>" + " " + "<span>" + this.content + "</span>";
			if( sessionId == this.user_id ) {
				result += "<input type='button' class='deleteBtn' value='삭제' onclick='deleteReply(" + this.board_num +","+ this.reply_num + ")' /></li>";	
			}
		});
		document.getElementById( "reply_list" + bno ).innerHTML = result;
	}
</script>

</body>
</html>
