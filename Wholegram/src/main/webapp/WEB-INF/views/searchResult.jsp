<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="/resources/bootstrap/css/bootstrap.css">
	<link rel="stylesheet" href="/resources/css/bootstrap.css">
	<link rel="stylesheet" href="/resources/css/w3.css">
	<link rel="stylesheet" href="/resources/css/message.css">
	
	
	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
  	<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script> 
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">
	<link rel="stylesheet" href="/resources/css/header.css"> 
	<script src="https://use.fontawesome.com/9fc8d6f50a.js"></script>
	<script type="text/javascript" src="/resources/js/search.js"></script>

	<link href="/resources/css/jquery.modal.css" type="text/css" rel="stylesheet" />
<!-- 	<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script> -->
	<script type="text/javascript" src="/resources/js/jquery.modal.js"></script>

    <style type="text/css">
    	.col-lg-4, .col-md-4, .col-sm-4, .col-xs-4{
    		    margin-left: 0px !important;
    		    padding-bottom: 10px;
    		    
    	}
		#profile_container{
			margin-left: 25%;
			width: 100%;
			height: 100%;
			margin:0 auto;
		}
 		#container2 {
			padding-left: 5%;
		} 
		#profile{
			height: 30%;
			width: 100%;
			float:left;
			text-align:center;
		}
		#profile_layout {
			margin-left: 25%;
		}
		#profile_img {
			float: left;
		}
		#profile_all {
			margin-left: 36%;
		}
		#user_id {
			font-size: 35px;
			float: left;
		}
		#user_id2 {
			font-size: 20px;
			margin-left: 0;
		}
		#profile_btn {
			margin-left: 1%;
		}
		#profile_btn2 {
			margin-top: 1%;
		}
		.btn-group {
			margin-top: 1.5%;
			margin-left: 1.5%;
		}
		#profile_btn2-1 {
			margin-left: 10%;
		}
		.logout {
			margin-top: 0.8%;
		}
		.logout2 {
			margin-top: -1.6%;
			margin-left: 1%;
		}
		#profile_intro_scope {
			width: 400px;
		}
		#profile_intro {
			overflow:hidden;
	        height:auto;
			font-size: 20px;
		}
		#profile_intro_scope2 {
			margin-left: -20%;
			width: 400px;
		}
		#profile_intro2 {
			overflow:hidden;
	        height:auto;
			font-size: 20px;
		}
		#profile_info {
			font-size: 20px;
		}
		#follower_count {
			margin-left: 5%;
		}
		#following_count {
			margin-left: 5%;
		}
		.items {
			margin-left: 6.1%;
		}
		#board_count {
			text-align: center;
		}
		.modal-body{
			padding-left: 9px;
    		padding-right: 9px;
		}
		.modal-dialog{
			width:620px;
		}
		.carousel {
			text-align:center;
		}
		.carousel-inner {
			display: inline-block;
		}
		#content #cnt_reply{
			padding:10px;
		}
		#content{
			border-bottom: 2px solid#eee;
		}
		li{
			list-style: none;
			
		}
		ul{
			padding-left: 0px;
			
		}
		#li{
			max-height: 400px;
    		overflow: auto;
		}	
		video{
			width:295px;
			height:295px;
			background: white;
			border: 1px solid #ddd;
  		  	border-radius: 4px;
		}
		.row{
			margin: 100px;
		}
		#image > video {
			max-width:600px;
			max-height:900px;
			width:600px !important;
			height:600px !important;
		}
		.modal-content{
			width:680px;
		}
		.carousel-control{
			width:5%;
		}
		#viewpeople{
			left: 5%;
  			top: 92%;
   			position: absolute;
			cursor:pointer;
		}
		#tagText{
			font-size: xx-large;
    		font-style: oblique;
		}
		.thumbnail{
			margin:auto;
		}
		@media all and (max-width: 575px) {
			#profile_intro_scope2{ width: 300px;}
		}
		
		@media all and (max-width: 768px) {
			#profile_img { 
				width:80px;
				height:80px;
				border-radius: 50%;
				margin: 0 5%;
			}
			#profile_info {
				margin-top: 15%;
			}
			#user_id{display: none;}
			#profile_btn2{display: none;}
			.logout{display: none;}
			#profile_name{display: none;}
			#profile_name2{
				font-size: 20px; 
				
				padding-top: -50%;
				margin-top: - 50%;
			}
			#profile_intro{display: none;}
			#line2{
				padding-top: -50px;
				/* border-bottom:1px solid #DADADA;   */
			}
			#board_count{display: none;}
			#follower_count{display: none;}
			#following_count{display: none;}
			.board_items {
				width: 129px;
    			height: 129px;
			}
		}
		
		@media all and (min-width: 769px) and (max-width: 1200px){
			#container{
				margin-left: 8%;
			}
		}
		
		@media all and (min-width: 769px) {
			#profile_img { 
				width: 150px;
				height: 150px; 
				border-radius: 50%;
				margin: 0 5%;
			}
			#user_id2{display: none;}
			#profile_btn2-1{display: none;}
			.logout2{display: none;}
			#profile_name{font-size: 20px;}
			#profile_name2{display: none;}
			#profile_intro2{display: none;}
			#line2{display: none;}
			#board_count2{display: none;}
			#follower_count2{display: none;}
			#following_count2{display: none;}
		}
		
		@media all and (min-width: 769px) and (max-width: 1179px){
 			.board_items{
				width: 255px;
				height: 255px;
			} 
		}
		
		@media all and (min-width: 1180px) and (max-width: 1950px){
 			.board_items{
				width: 295px;
				height: 295px;
			} 
		}
		
		 /*4K 이상 해상도용 */
		@media all and (min-width: 1950px){ 
 			.board_items{
				width: 395px;
				height: 395px;
			} 
			.col-lg-4, .col-md-4, .col-sm-4, .col-xs-4{
    		    margin-left: 0px !important;
    		}
			.container {
				width: 1580px;
	   		 	margin-left: 15%;
    		}
    		.w3-content{
    			max-width: 1980px;
    		}
    	}
	</style>

<title>Insert title here</title>
</head>
<body>
<%@include file="./header.jsp" %>

<!-- 게시물 사진 나오기 전까지의 프로필정보 / Browser창 size 768전후로 나뉘어짐-->
<div id="profile_container">
	<div id="profile">
	<span id="tagText"><h1><c:out value="${searchTag}"></c:out></h1></span>
	<span id="searchCount"><h3>검색된 결과는 <c:out value="${count}"></c:out>개 입니다.</h3></span>
	</div>
</div>

<!-- 게시물 사진들 -->
<div class="w3-main" style="background: #F8F8F8">
		<div class="container bootstrap snippet">
			<div class="row">
			<form name="frm" id="frm">
			<c:forEach items="${list}" var="list">
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4">
					<a title="Image 1" href="#">
						<c:choose>
							<c:when test="${list.media_type == 'm'}">
								<img class="thumbnail img-responsive board_items" src="${list.media_thumnail}" onclick="read(${list.board_num},0)">
							</c:when>
							<c:otherwise>
								<img class="thumbnail img-responsive board_items" src="${list.media}" onclick="read(${list.board_num},0)">
							</c:otherwise>
						</c:choose>
						<input type="hidden" id="bn" name="bn" value="${list.board_num}">
					</a>
				</div>
			</c:forEach><!--  -->
			</form>
			</div>
			
			<!-- Modal 생성 -->
			<div class="modal" id="myModal" role="dialog">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button class="close" type="button" data-dismiss="modal">×</button>
							<h3 class="modal-title"></h3>
						</div>
						<div class="modal-body">
							<div id="modalCarousel" class="carousel">
								<div id="image" class="carousel-inner"></div><div id="tag"></div>
								<a id = "prev" class="carousel-control left" href="#modalCarousel" data-slide="prev" onclick=""> <i class="glyphicon glyphicon-chevron-left"></i></a> 
								<a id = "next" class="carousel-control right" href="#modalCarousel" data-slide="next" onclick=""> <i class="glyphicon glyphicon-chevron-right"></i></a>
							</div>
							<div id="content"></div>
							<div id="li">
							<ul id="cnt_reply"></ul>
							</div>
							<div id="rep_inp"></div>
						</div>
						<div class="modal-footer">
							<button class="btn btn-default" data-dismiss="modal">Close</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="chat_box"></div><script src="/resources/js/message.js"></script>

	
	<script>
	var sessionId = "${sessionId}";				// 접속자 ID
	var thisPage = false;						// 메시지 페이지가 아니라는 의미
	var VIDEO = "m";
	var IMAGE = "i";
	var FLAG = true;
		function getBoardCount(){
			return frm.bn.length;
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
					alert("fail");
				}	
			});
		}
	
		function viewclick(){
			$("#tag").toggle();
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
				result += "<li id='rep'><a class='user_id' href='#'>"+ this.user_id + "</a>" + " " + "<span>" + this.content + "</span>";
				if( sessionId == this.user_id ) {
					result += "<input type='button' class='deleteBtn' value='삭제' onclick='deleteReply(" + this.board_num +","+ this.reply_num + ")' /></li>";	
				}
			});
			document.getElementById( "cnt_reply" ).innerHTML = result;
		}
		
		function addReplyList(data, bno) {
			var	result	= "";

			$(data).each(function() {	
				result += "<li id='rep'><a class='user_id' href='#'>"+ this.user_id + "</a>" + " " + "<span>" + this.content + "</span>";
				if( sessionId == this.user_id ) {
					result += "<input type='button' class='deleteBtn' value='삭제' onclick='deleteReply(" + this.board_num +","+ this.reply_num + ")' /></li>";	
				}
			});
			document.getElementById( "cnt_reply" ).innerHTML += result;
		}
		/* activate the carousel */
		$('#modalCarousel').carousel({
			interval : false
		});
		
		/* change modal title when slide changes */
		$('#modalCarousel').on('slid.bs.carousel', function() {
			$('.modal-title').html($(this).find('.active').attr("title"));
		})
		
		function checkBoard(no){  // modal을 띄운후 현재게시물을 기준으로 이전게시물과 다음게시물의 target을 설정
			var page=function(){var prev; var next;};	
			
			for(var i=0;i<frm.bn.length;i++){
				if(no == frm.bn[i].value){
					if(i == 0){ // 현재게시물이 첫번째인 경우 이전 페이지 이동은 없음
						page.prev=no;
						page.next=frm.bn[i+1].value;
					}else if(i == frm.bn.length-1){ //현재게시물이 마지막 페이지인 경우 다음페이지에서 이동이 없음.
						page.prev=frm.bn[i-1].value;
						page.next=no;
					}else{ 
						page.prev=frm.bn[i-1].value;
						page.next=frm.bn[i+1].value;
					}
				}
			}
			return page;
		}
		
		/* when clicking a thumbnail */
		function read(no,idx) {
			
			$('#myModal').modal('show'); // show the modal
			$.ajax({ 
	    		type: 'GET',
                url: '/user/getNum/'+no+'/'+idx,
                dataType: 'json',
                contentType : 'application/json; charset=utf-8',
	    	    success: function(result) {
	    	    	var prevNext = checkBoard(no);
	    	    	if(result.bd.media_type == IMAGE)
	    	    		$("#image").html('<img src="'+result.bd.media+'">'+'<i id ="viewpeople" class="fa fa-info-circle fa-2x" onclick="viewclick()"></i>');
	    	    	else
	    	    		$("#image").html('<video src="'+result.bd.media+'" controls>');
		    	    	$("#content").html(result.bd.user_id+" "+result.bd.content);
		    	    	$("#tag").html(result.bd.tag).css("display","none");
		    	    	$("#prev").attr("onclick","read("+prevNext.prev+",0)");
		    	    	$("#next").attr("onclick","read("+prevNext.next+",0)");
		    	    	setReplyList(result.rp, no);
		    	    	$("#rep_inp").html('<input type="hidden" id="board_num" name="board_num" value="'+result.bd.board_num+'" /> ');
		    	    	$("#rep_inp").html($("#rep_inp").html()+'<input type="text" id="content'+result.bd.board_num+'" name="content'+result.bd.board_num+'" style="width: 450px; outline-style: none;" onkeydown="javascript:if( event.keyCode == 13 ) insertReply('+result.bd.board_num+')" placeholder="댓글달기..." />');						
	    	    	
	    	    },
	    	    error:function(request,status,error){
    	    	    alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    	   	 	}
	    	});
		}
		
		function readReply(data){
			var result ="";
			for(var d in data){
				result += "<li id='rep'><a href='/"+data[d].user_id+"'>"+data[d].user_id+"</a> "+data[d].content+"</li>";
			}
			return result;
		}
	
		function setScrollBoard(data){
			for(var d in data){
				if(data[d].media_type == IMAGE)
					var input='<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4"><a title="Image 1" href="#"><img class="thumbnail img-responsive board_items" src="'+data[d].media+'" onclick="read('+data[d].board_num+',0)"><input type="hidden" id="bn" name="bn" value="'+data[d].board_num+'"></a></div>';
				else
					var input='<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4"><a title="Image 1" href="#"><img class="thumbnail img-responsive board_items" src="'+data[d].media_thumnail+'" onclick="read('+data[d].board_num+',0)"><input type="hidden" id="bn" name="bn" value="'+data[d].board_num+'"></a></div>';
					document.getElementById("frm").innerHTML += input;
			}
		}
		
		/* 브라우저 창의 스크롤 끝 부분일때 추가게시물을 가져옴 */
		 $(window).scroll(function() {
			 var no = getBoardCount();
			 var hash = window.location.pathname.split( '/' );
		   if(($(window).scrollTop() == $(document).height() - $(window).height())&& FLAG) {
		        $.ajax({ 
		    		type: 'GET',
	                url: '/scroll/hash/'+hash[2]+'/'+no,
	                dataType: 'json',
	                contentType : 'application/json; charset=utf-8',
		    	    success: function(result) {
		    	   			setScrollBoard(result.list);
		    	   			FLAG = result.flag;
		    	    },
		    	    error:function(request,status,error){
	    	    	    alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	    	    	}
		    	});
		   }
		});  
		
		 /* 댓글리스트 끝을 알림 */
		 $("#li").scroll(function() {
			 var itemids = $.makeArray($("li").map(function(){
				    return $(this).attr("id");
				}));
			 
			 var no = $("#board_num").val()
		   if($("#li").scrollTop() == $("#li").prop("scrollHeight")-$("#li").height()) {
				   $.ajax({ 
			    		type: 'GET',
		                url: 'rep/'+no+"/"+itemids.length,
		                dataType: 'json',
		                contentType : 'application/json; charset=utf-8',
			    	    success: function(result) {
			    	    	addReplyList(result.rp, no);
			    	    },
			    	    error:function(request,status,error){
		    	    	    alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		    	    	}
			    	});
		   }
		});  
		 
		 
	</script>
	<div id="chat_box"></div>
	<script src="/resources/js/message.js"></script>
</body>
</html>