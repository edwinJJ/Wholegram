<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
	<script src="http://code.jquery.com/jquery-1.7.min.js"></script>
	<script src="resources/js/jquery.lazyloadxt.js" type="text/javascript"></script>
  	<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script> 
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">
	<script type="text/javascript" src="/resources/js/search.js"></script>

	<style>
		.lazy{
			display: none;
		}
		#image{
   	        left: 22.5%;
   	        
		}
		#cnt_board_img img{
			max-height: 900px;
		}
		#panel{
			position: relative;
		}
		#cnt_board_img{
			position: initial;
		}
		#viewpeople{
			position: absolute;
			top: 91%;
			left: 2%;
		}
		video{
			height: 600px;
		}
		a[class^='.btn-primary']{
			background-color: #253bd3;
    		padding: 3px;
    		border-radius: 20px;
		}
		i[id^='heart_full'] {
			color: red;
		}
	</style>
</head>
<body style="background: #fafafa;">

<%@include file="./header.jsp" %>

<!-- 뉴스(소식)  -->
<div id="news_box" style="display: none;"></div>

<!-- line -->
<div class="line"></div>

	<!-- 게시물 영역 -->
	<div id="section">
		<div id="contents">
			<div id="cnt_board">
				<div id="cnt_board2">

					<c:forEach items="${bList}" var="bd">
						<div id="cnt_header">
							<a id="cnt_user_img" class="fl" href="/${bd.user_id}"> 
							<c:choose>
								<c:when test="${bd.default_profile != 1 }">
									<img src="/user/getByteImage/${bd.user_id}">	
								</c:when>
								<c:otherwise>
									<img src="/resources/upload/user/Default.png">
								</c:otherwise>
							</c:choose>
							</a>
							<div id="cnt_header_user" class="fl">
								<c:if test="${bd.place eq null}">
									<a class="user" style="padding-top: 10px;" href="/${bd.user_id}">
										${bd.user_id}</a>
								</c:if>
								<c:if test="${bd.place ne null}">
									<a class="user" href="/${bd.user_id}"> ${bd.user_id}</a>
									<a class="bd_place" href="#">${bd.place}</a>
								</c:if>
							</div>

							<!-- 현재일에서 등록일간의 차이 구하기 7일이후면 등록일 표기 -->
							<span id="reg_date" class="fr reg_date" value="${bd.board_num}">
								<a href="#" style="display: none;"> <c:set var="toDay"
										value="<%=new java.util.Date() %>" /> <fmt:parseDate
										var="regDate" value="${bd.reg_date }"
										pattern="yyyy-MM-dd HH:mm:ss" /> <fmt:formatDate
										value="${regDate }" pattern="yyyyMMddHHmmss" /> <fmt:parseNumber
										value="${toDay.time}" integerOnly="true" var="nowDays"
										scope="request" /> <fmt:parseNumber value="${regDate.time}"
										integerOnly="true" var="oldDays" scope="request" />
							</a> <c:if test="${(nowDays-oldDays)/1000 < 60}">
									<fmt:parseNumber var="sec" value="${(nowDays-oldDays)/1000}"
										integerOnly="true" />
											${sec}초 전
										</c:if> <c:if
									test="${(nowDays-oldDays)/1000 > 60 && (nowDays-oldDays)/1000 < 3600 }">
									<fmt:parseNumber var="min"
										value="${((nowDays-oldDays)/(1000 * 60))}" integerOnly="true" /> 
											${min}분 전
										</c:if> <c:if
									test="${(nowDays-oldDays)/1000 > 3600 && (nowDays-oldDays)/1000 < 86400 }">
									<fmt:parseNumber var="hour"
										value="${(nowDays-oldDays)/(1000 * 60 * 60)}"
										integerOnly="true" />
											${hour}시간 전
										</c:if> <c:if
									test="${(nowDays-oldDays)/1000 > 86400 && (nowDays-oldDays)/1000 < 604800 }">
									<fmt:parseNumber var="day"
										value="${(nowDays-oldDays)/(1000* 60 * 60 * 24)}"
										integerOnly="true" />
											${day}일 전
										</c:if> <c:if test="${(nowDays-oldDays)/1000 > 604800 }">
									<fmt:formatDate value="${regDate}" pattern="yyyy년 MM월 dd일" />
								</c:if>
							</span>
						</div>
						<!-- 변경-->
						<div id="cnt_board_img">
							<div id="panel">
								<c:choose>
									<c:when test="${bd.media_type == 'm'}">
										<video class="lazy" data-src="${bd.media}" width="600"
											height="600" preload="metadata" controls></video>
									</c:when>
									<c:otherwise>
										<img class="lazy" id="image${bd.board_num}" data-src="${bd.media}" height="600">
										<div id="tag${bd.board_num}" style="display: none">${bd.tag}</div>
										<i id="viewpeople" class="fa fa-info-circle fa-2x" onclick="viewClick('${bd.board_num}')"></i>
									</c:otherwise>
								</c:choose>
							</div>
						</div>

						<div id="cnt_board_content">
							<div id="cnt_board_heart${bd.board_num}" class="fwb">좋아요
								${bd.heart}개</div>
							<ul id="cnt_board_list">
								<li>
									<h1 class="title">
										<a class="user_id fwb" href="/${bd.user_id}">${bd.user_id}</a>
										<span> ${bd.content} </span>
									</h1>
								</li>
								<li id="reply_list${bd.board_num }" class="reply_list">
									<c:forEach items="${replyResult}" var="rp">
										<c:if test="${bd.board_num == rp.board_num}">
											<a class="user_id fwb" href="/${rp.user_id }">${rp.user_id }</a>
											<span>${rp.content}</span>
											<c:if test="${sessionId == rp.user_id}">
												
												<input type="button" class="deleteBtn fr" value="X" onclick="deleteReply(${bd.board_num},${rp.reply_num})" />
												
											</c:if>
											<br />
										</c:if>
									</c:forEach>
								</li>
							</ul>
							<div id="cnt_reply">
								<a class="heart" href="#self"> <br /> 
									<c:choose>
										<c:when test="${bd.aldy_heart}">
											<!-- 게시물에 좋아요가 눌러져있는 경우 -->
											<i id="heart_full${bd.board_num}" class="test fa fa-heart fa-2x" aria-hidden="true" style="color:red;" onclick="heartCount(${bd.board_num})" ></i>
											<i id="heart_empty${bd.board_num}" class="test fa fa-heart-o fa-2x" aria-hidden="true" style="display: none;" onclick="heartCount(${bd.board_num} )"></i>
										</c:when>
										<c:otherwise>
											<!-- 게시물에 좋아요가 안 눌러져있는 경우 -->
											<i id="heart_full${bd.board_num}" class="test fa fa-heart fa-2x" aria-hidden="true" style="display: none;" onclick="heartCount(${bd.board_num} )"></i>
											<i id="heart_empty${bd.board_num}" class="test fa fa-heart-o fa-2x" aria-hidden="true" onclick="heartCount(${bd.board_num} )"></i>
										</c:otherwise>
									</c:choose>
								</a> 
								<input type="hidden" id="board_num" name="board_num" value="${bd.board_num}" /> 
								<input type="text" id="content${bd.board_num}" name="content${bd.board_num}" style="width: 450px; outline-style: none;" onkeydown="javascript:if( event.keyCode == 13 ) insertReply('${bd.board_num}', '${bd.user_id}')" placeholder="댓글달기..." /> 
								<a href="#self" onclick="openPopup(${bd.board_num})"> 
									<i class="fa fa-ellipsis-h fa-2x fr" style="color: #bfbfbf;" aria-hidden="true"></i>
								</a>
							</div>
						</div>

						<!-- option 메뉴 팝업 -->
						<div id="popup_wrap">
							<div id="popupLayer${bd.board_num}" class="popupLayer">
								<div class="bg"></div>
								<ul id="popupContents">
		                           <li><a href="#self" onclick="insertReport(${bd.board_num})">부적절한 콘텐츠 신고</a></li>
		                           <li><a href="${bd.media}" download>다운로드</a></li>
		                           <li><a href="#self" onclick="closePopup(${bd.board_num})">취소</a></li>
		                        </ul>
							</div>
						</div>
					</c:forEach>
					<div id="result"></div>
				</div>
			</div>
		</div>
	</div>
	
<div id="chat_box"></div>
<script src="/resources/js/message.js"></script>
	
<script>
	var bno = '${bd.board_num}';
	var rno = '${rp.reply_num}';
	var sessionId = '${sessionId}';
	var lno = '${likeNum}';
	var thisPage = null;			// 메시지 알림 on/off 용도

	var page; 
	var scrollCurrentTop = 0;
	
	function openPopup( bno ) {
		var popup = document.getElementById("popupLayer" + bno);
		      $(popup).fadeIn();
	}
	
	function closePopup( bno ) {
		var popup = document.getElementById("popupLayer" + bno);
		 $(popup).fadeOut();
	}
	
	if( page == null ) {
		page = 1;
	} 
	
 	$(window).scroll(function() {
 		if( ( $(window).scrollTop() == $(document).height() - $(window).height() ) &&  $(window).scrollTop() != scrollCurrentTop ) {
 			var itemids = $.makeArray($("input[id='board_num']"));
 			scrollCurrentTop = $(window).scrollTop();
			page++;
 			setBoardList( page );
	    }
	});  
	
 	function setBoardList( page ) { 
        $.ajax({
			type : 'GET',
			url : '/board/scroll/page/' + page,
			headers : {
				"Content-Type" : "application/json",
				"X-HTTP-Method-Override" : "GET",
			},
			data : '',
			async: false,
			dataType : 'html',
			success : function( result ){
				$("#result").html(result);
			}, 
			error : function(result) {
				alert( "setBoardList fail!" );
				
			}
		});
        page = page;

 	}

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
				alert("deleteReply fail");
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}	
		});
	}
	
	String.prototype.replaceAll = function(org, dest) {
	       return this.split(org).join(dest);
	   }
	   
	   function insertReply( bno, uid ) {
	      var reply_content = ($("#content"+bno).val()).replaceAll("#","%23");
	      alert(reply_content);
	      var url = "/board/"+ bno +"/" + reply_content + "/" + uid;
	      
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
	         error : function(request,status,error) {
	            alert("insertReply fail");
	            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	         }
	      });
	   }
	
	function heartCount( board_num ) {
		var hc_url = "/board/heart/" + board_num;
		$.ajax({
			type : 'GET',
			url : hc_url,
			headers : {
				"Content-Type" : "application/json",
			},
			data : '',
			dataType : 'json',
			success : function(value){
				heartChange(value, board_num);
			},
			error : function(value) {
				alert("insertHeart fail");
			}
		});
	}

	function heartChange(value, board_num) {
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
		cbh.innerHTML = "좋아요 " + value + "개";
	}
	
	function setReplyList(data, bno) {
		var	result	= "";
		$(data).each(function() {	
			result += "<li><a class='user_id fwb' href='/"+ this.user_id +"'>"+ this.user_id + "</a>" + " " + "<span>" + this.content + "</span>";
			if( sessionId == this.user_id ) {
				result += " " + "<input type='button' class='deleteBtn fr' value='X' onclick='deleteReply(" + this.board_num +","+ this.reply_num + ")' /></li>";	
			} 
			$("#content"+bno).val("");
		});

		document.getElementById( "reply_list" + bno ).innerHTML = result;
	}
	function setScrollBoard(data){
		var result = '<div id="cnt_header">'
					+'<a id="cnt_user_img" class="fl" herf="">'
					+'<img src="/user/getByteImage/">'
					+'</a>';
					
	}
	function viewClick(no){
		$("#tag"+no).toggle();
	}
	
	
	// 신고관련
	function insertReport( board_num ) {
	      $.ajax({
	         type : 'GET',
	         url : "/board/report/" + board_num,
	         headers : {
	            "Content-Type" : "application/json",
	         },
	         data : '',
	         dataType : 'json',
	         success : function(value){
	            alert("게시물 신고 완료가 완료되었습니다.");
	            var popup = document.getElementById("popupLayer" + board_num);
	            $(popup).fadeOut();
	         },
	         error:function(request,status,error){
	              alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	          }
	      });
	   }
	

</script>
</body>
</html>
