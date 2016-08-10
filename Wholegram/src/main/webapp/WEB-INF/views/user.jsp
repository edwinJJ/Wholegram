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
<!-- 	<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script> -->
	<script type="text/javascript" src="/resources/js/jquery.modal.js"></script>

    <style type="text/css">
		#profile_container{
			margin-left: 25%;
			width: 1000px;
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
		}
		#profile_layout {
			margin-left: 5%;
		}
		#profile_img {
			float: left;
			cursor: pointer;
		}
		#profile_all {
			margin-left: 30%;
		}
		#user_id {
			font-size: 35px;
			float: left;
		}
		#user_id2 {
			font-size: 20px;
			margin-left: -20%;
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
			margin-left: -20%;
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
    </style>

	<style>
		@media all and (max-width: 575px) {
			#profile_intro_scope2{ width: 300px;}
		}
		@media all and (max-width: 768px) {
			#profile_img { 
				width:80px;
				height:80px;
				border-radius: 50%;
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
				margin-left: -20%;
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
				width: 60%;
				height: 60%;
			}
		}
		@media all and (min-width: 769px) and (max-width: 1200px){
			#container{
				margin-left: 8%;
			}
		}
		@media all and (min-width: 769px) and (max-width: 1950px){
			#profile_img { 
				width: 150px;
				height: 150px; 
				border-radius: 50%;
				margin-left: 5%;
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
	</style>
	<script>
		function profile_edit() {
			location.href="/user/update_form";		// 프로필 수정 페이지 이동
		}
		
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
		
		/* 프로필 이미지 변경  */
		$(document).ready(function(){
			$("#file1").change(function() {
				if(file1.value == "") {}
				else {
		            var form = $('FILE_FORM')[0];
		            var formData = new FormData(form);
		            formData.append("fileObj", $("#file1")[0].files[0]);
		            var upu_url = "/user/change_profile";
		            $.ajax({
		                url: upu_url,
                        processData: false,
                        contentType: false,
                        data: formData,
                        type: 'POST',
                        success: function(result){
                        	alert(result);
                        	if(result == null || result == ""){
                        		alert("파일 용량이 너무 큽니다(10MB이하).")
                        	} else {
                            	document.getElementById("profile_img").src = "/user/getByteImage?timestamp=" + new Date().getTime();	// user 페이지 처음 로딩될때 profile_img 태그의 src가 "/user/getByteImage"를 호출하는데, 여기서 이미지 등록 후, 다시 "/user/getByteImage" 를 호출해주면, 기존에 남아있던 캐시를 사용하게됨(값이 안바뀜). 그래서 뒤에 시간에 해당되는 값을 추가(새로운 호출로 인식됨) 
                        	}
                        },
                        error : function(result) {
                        	alert("error : " + result);
                        }
		            });
				}
			});
		});
	
/* 		 브라우저창 끝을 알림 
		$(window).scroll(function() {
		    if($(window).scrollTop() == $(document).height() - $(window).height()) {
		        alert('End of Window');
		    }
		}); 
 */		
/* 		setInterval(function(){
		    $.ajax({ 
		    	url: "/user/test",
		    	datatype: "json",
		    	type:'POST',
		    	success: function(result){
		    		console.log("abc");
		    	},
		    	error: function(result) {
		    		
		    	}
		    });
		}, 3000); */
	</script>
</head>
<body>
<!-- 상단의 head 부분 --><!-- test -->
<%@include file="./header.jsp" %>

<div id="news_box"></div>

<!-- 게시물 사진 나오기 전까지의 프로필정보 / Browser창 size 768전후로 나뉘어짐-->
<div id="profile_container">
	<div id="profile"><br/><br/>
		<div id="profile_layout">
			<form id="FILE_FORM" method="post" enctype="multipart/form-data" action="">
				<input type="file" id="file1" name="file1" style="display:none;"> 
				<img id="profile_img" src="/user/getByteImage" onclick="document.all.file1.click();"/>
			</form>
		</div>
		<div id="profile_all">
			<span id="user_id">${vo.user_id }</span>
			<span id="user_id2">${vo.user_id }</span>
			<a href="#" id="confirm" class="btn btn-default logout2" >. . .</a>
			<span id="profile_btn">
				<button id="profile_btn2" type="button" class="btn btn-default" onclick="profile_edit()">프로필 편집</button> 
				<a href="#" id="confirm" class="btn btn-default logout" >. . .</a>
			</span><br/><br/>
			<span id="profile_name">${vo.user_name }</span><br/>
			<div id="profile_intro_scope">
				<span id="profile_intro">${vo.info }</span>
			</div>
			<span id="profile_name2">${vo.user_name }</span><br/>
			<div id="profile_intro_scope2">
				<span id="profile_intro2">${vo.info }</span><br/>
			</div>
			<button id="profile_btn2-1" type="button" class="btn btn-default">프로필 편집2</button>
			<span id="profile_info">
				<span id="board_count">게시물 n개</span>&nbsp;
				<span id="follower_count">팔로워 n명</span>&nbsp;
				<span id="following_count">팔로잉 n명</span>
			</span>
		</div><br/><br/>
	</div>
</div>

<!-- 게시물 사진들 -->
<div class="w3-main" style="background: #F8F8F8">
	<div id="line2" class="line">　</div><br/>
	<div id="container2" class="w3-content">
		<div class="w3-row-padding">
			<div class="w3-third w3-container w3-margin-bottom">
				<span id="board_count2">게시물 n개</span>
			</div>
			<div class="w3-third w3-container w3-margin-bottom">
				<span id="follower_count2">팔로워 n명</span>
			</div>
			<div class="w3-third w3-container">
				<span id="following_count2">팔로잉 n명</span>
			</div>			
		</div>
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


	
	<script>
	var sessionId = "${vo.user_id }";		// 접속자 ID
	var thisPage = false;					// 메시지 페이지가 아니라는 의미
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
                url: 'user/getNum/'+no+'/'+idx,
                dataType: 'json',
                contentType : 'application/json; charset=utf-8',
	    	    success: function(result) {
	    	    	var prevNext = checkBoard(no);
	    	    	console.log(prevNext);
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
		
		String.prototype.replaceAll = function(org, dest) {
		    return this.split(org).join(dest);
		}
		/* 브라우저 창의 스크롤 끝 부분일때 추가게시물을 가져옴 */
		 $(window).scroll(function() {
			 var no = getBoardCount();
			 var url = 'board/scroll/';
			 if(location.pathname.replaceAll("/","") != "" && location.pathname.replaceAll("/","") != "login"){
				url += location.pathname.replaceAll("/","") +"/";
			 }
		   if(($(window).scrollTop() == $(document).height() - $(window).height())&& FLAG) {
		        $.ajax({ 
		    		type: 'POST',
	                url: "http://"+location.host+"/"+url+no,
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

