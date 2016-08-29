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

	<script type="text/javascript" src="/resources/js/search.js"></script>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">
	<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script>

	<link href="/resources/css/jquery.modal.css" type="text/css" rel="stylesheet" />
<!-- 	<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script> -->
	<script type="text/javascript" src="/resources/js/jquery.modal.js"></script>
	<script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script> 
	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">

    <style type="text/css">
		* {
		   padding: 0;
		   margin: 0;
		}
   		input[id^=content]{
    		width:80% !important;
    		margin-left:2%;
    		margin-right:7%;
    	}
    	#content,#li{
    		margin:10px;
    	}
    	#userfollow{
    		margin-top: 10px;
		    background: #fff;
		    width: 110px;
		    height: 26px;
		    border-radius: 3px;
		    border: 1px solid #86E57F;
		    color: #FFFFFF;
		    background-color:#86E57F;
		    font-weight: bold;
		    position: absolute;
		    left: 20%;
		    top: 5%
    	}
    	#notuserfollow{
   			margin-top: 10px;
		    background: #fff;
		    width: 110px;
		    height: 26px;
		    border-radius: 3px;
		    border: 1px solid #3897f0;
		    color: #3897f0;
		    font-weight: bold;
		    position: absolute;
		    left: 20%;
		    top: 5%
    	}
    	.following{
    		margin-top: 10px !important;
		    background: #fff !important;
		    width: 110px !important;
		    height: 26px !important;
		    border-radius: 3px !important;
		    border: 1px solid #86E57F !important;
		    color: #FFFFFF !important;
		    background-color:#86E57F !important;
		    font-weight: bold !important;
		    position: absolute;
		    left: 80%;
		    top: 5%
    	}
    	.follow{
    		margin-top: 10px !important;
		    background: #fff !important;
		    width: 110px !important;
		    height: 26px !important;
		    border-radius: 3px !important;
		    border: 1px solid #3897f0 !important;
		    color: #3897f0 !important;
		    font-weight: bold !important;
		    position: absolute;
		    left: 80%;
		    top: 5%
    	}
		.f_list{
		   	margin-top:10px;
		   	margin-bottom:20px;
		   	position:relative;
		   	height:10%;
		}
		.f_text{
			position:absolute;
			left:10%;
			top: 20%;
		}
		.fradio{
			position:absolute;
			left:80%;
			top: 20%;
		}
		#thumbnail{
		   	width:40px;
		   	height:40px;
		   	border-radius:10px;
		   	position:absolute;
		   	left:3%;
		   	top: -8%;
		}    	
		/* option �뙘�뾽 */
		#popup_wrapper {
			width: 100%;
		}
		#profile_all{
			height: 100px;
		}
		.popupLayer {
			display: none;
		}
		#popupContents {
			z-index: 19999;
			position: fixed;
			top: 50%;
			left: 50%;
			width: 514px;
			height: 205px;
			margin: -102.5px 0 0 -257px;
		}
		
		#popupContents  li {
			border: 1px solid #efefef;
			background: #fff;
			width: 478px;
			height: 50px;
			padding: 0px 16px;
			text-align: center;
			line-height: 50px;
		}
		
		
		#popupContents  li:hover {
			background: #efefef;
			font-weight: 600;
		}
		
		#popupContents li:nth-child(1), #popupContents li:nth-child(2),
			#popupContents li:nth-child(3) {
			border-bottom: none;
		}
	    .popupLayer .bg {
			position: fixed;
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
			background-color: #000;
			opacity: 0.7;
			z-index:9999;
		}
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
		#profile_notice_img {
			float: left;
			width: 30px;
			height: 30px;
			border-radius: 50%;
		}
		#board_img {
			width: 40px;
			height: 40px;
		}
		#profile_all {
			margin-left: 30%;
			position: relative;
		}
		#user_id {
			font-size: 35px;
			float: left;
			position: absolute;
		}
		#user_id2 {
			font-size: 20px;
			margin-left: -20%;
		}
		#profile_btn {
			margin-left: 1%;
			position: absolute;
		    left: 18%;
		    top: 7%;
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
			width: 100%;
		}
		#follower_count {
			margin-left: 5%;
			cursor:pointer;
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
		input[type="button"], input[type="text"] {
			outline-style: none; /* �룷而ㅼ뒪�떆 諛쒖깮�븯�뒗 �슚怨� �젣嫄� */
			-webkit-appearance: none; /* 釉뚮씪�슦��蹂� 湲곕낯 �뒪���씪留� �젣嫄� */
			-moz-appearance: none;
			appearance: none;
			border-style: none;
		}
		input[type="button"] {
			background: #fff;
			color:  #b3b3b3;
		}
		.test {
			color: #bfbfbf;
		}
    </style> 
    
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
      #modalCarousel >#image> img{
         max-width: 600px;
      }
      #profile_layout {
         margin-left: 5%;
      }
      #profile{
         height: 30%;
         width: 100%;
         float:left;
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
         position: absolute;
		 left: 11.5%;
		 top: 66%;
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
      #image > video {
         max-width:600px;
         max-height:600px;
         width:600px !important;
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
      #profile_name{
		top: 50%;
      }
      #profile_info{
   		top: 100%;
      }
      #gender_img {
      	width:25px;
      	height:25px;
      	margin-top:-12px;
      }
      #gender_img2 {
      	width:15px;
      	height:15px;
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
	   color:  #b3b3b3;
	}
	.fr {
	   float: right;
	}
	
	.fl {
	   float: left;
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
                width: 200px;
                
   				height: 150px;
         }
         /* 사이즈 조절시 추가 */
         .modal-dialog{
         	width: 100%;
         }
         .modal-content{
         	width:95%;
         }
         #modalCarousel >#image> img {
            width: 90%;
         }
         input[id^="content"]{
         	width:80% !important;
         	margin-left: 10px;
         }
         #gender_img {
         	display:none;
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
         #gender_img2 {
         	display: none;
         }
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
			$("#upload").change(function() {
				if(this.value == "") {}
				else {
		            var form = $('FILE_FORM')[0];
		            var formData = new FormData(form);
		            formData.append("fileObj", $("#upload")[0].files[0]);
		            var upu_url = "/user/change_profile";
		            $.ajax({
		                url: upu_url,
                        processData: false,
                        contentType: false,
                        data: formData,
                        type: 'POST',
                        success: function(result){
                        	alert("프로필 사진이 변경되었습니다.");
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
 
 		/* 프로필 이미지 클릭 메뉴 */
	 	function profile_menu(flag, flag2) {
		    var x = document.getElementById("menu_list");
		    if (x.className.indexOf("w3-show") == -1) {
		        x.className += " w3-show";
		    } else if(flag) {
		    	x.className = x.className.replace(" w3-show", "");
		    } else {
		        x.className = x.className.replace(" w3-show", "");
		    }
		    
		    if(flag2 != undefined) {
		    	var upload = document.getElementById("upload");
		    	upload.click();
		    }
		}
	 	
 		/* 프로필 이미지를 기본 이미지로 변경 */
	 	function defaultSet() {
 			var defaultImg_url = "/user/change_default_profile";
	 		$.ajax({
				type: 'POST',
				url: defaultImg_url,
				headers:{
					"Content-Type" : "application/json",
					"X-HTTP-Method-Override":"POST",
				},
				dataType:'text',
				success : function(result) {
					alert("프로필 사진을 기본으로 변경하였습니다.")
					document.getElementById("profile_img").src = "/resources/upload/user/Default.png";		// 프로필 이미지를 Default로 변경
				},
				error : function(result){
					alert("error : " + result);
				}
			});
	 	}
	</script>
</head>
<body>
<!-- 상단의 head 부분 -->
<c:choose>
<c:when test="${sessionId eq 'admin' }">
	<%@include file="./admin_header.jsp" %>
</c:when>
<c:otherwise>
	<%@include file="./header.jsp" %>
</c:otherwise>
</c:choose>

<!-- 뉴스(소식)  -->
<div id="news_box" style="display: none;"></div>

<!-- 게시물 사진 나오기 전까지의 프로필정보 / Browser창 size 768전후로 나뉘어짐-->
<div id="profile_container">
   <div id="profile"><br/><br/>
      <div id="profile_layout">
         <c:choose>
            <c:when test="${sessionId == vo.user_id}">
               <form id="FILE_FORM" method="post" enctype="multipart/form-data" action="">
                  <input type="file" id="upload" name="upload" style="display:none;"> 
                  <c:choose>
                        <c:when test="${vo.default_profile != 1 }">
                           <img id="profile_img" src="/user/getByteImage" onclick="profile_menu();"/>
                           <div id="menu_list" class="w3-dropdown-content w3-card-4">
                               <a href="#" onclick="profile_menu('cancel', 'click');">프로필 사진 변경</a>
                               <a href="#" onclick="profile_menu('cancel'); defaultSet();">기본 이미지</a>
                               <a href="#" onclick="profile_menu('cancel');">취소</a>
                           </div>
                        </c:when>
                        <c:otherwise>               
                           <img id="profile_img" src="/resources/upload/user/Default.png" onclick="profile_menu();"/>
                           <div id="menu_list" class="w3-dropdown-content w3-card-4">
                               <a href="#" onclick="profile_menu('cancel', 'click');">프로필 사진 변경</a>
                               <a href="#" onclick="profile_menu('cancel'); defaultSet();">기본 이미지</a>
                               <a href="#" onclick="profile_menu('cancel');">취소</a>
                           </div>
                        </c:otherwise>
                     </c:choose>
                  </form>
            </c:when>
            <c:otherwise>
            	<c:choose>
	            	<c:when test="${vo.default_profile != 1 }">
	            		<img id="profile_img" src="/user/getByteImage"/>
	            	</c:when>
	            	<c:otherwise>
		            	<img id="profile_img" src="/resources/upload/user/Default.png"/>
	            	</c:otherwise>
            	</c:choose>
            </c:otherwise>
         </c:choose>
      </div>
      <div id="profile_all">
         <span id="user_id">${vo.user_id }</span>
         <span id="user_id2">${vo.user_id }</span>
         <c:choose>
            <c:when test="${sessionId == vo.user_id}">
               <a href="#" id="confirm" class="btn btn-default logout2" >. . .</a>
               <span id="profile_btn">
                  <button id="profile_btn2" type="button" class="btn btn-default" onclick="profile_edit()">프로필 편집</button> 
                  <a href="#" id="confirm" class="btn btn-default logout" >. . .</a>
               </span><br/><br/>
            </c:when>
            <c:otherwise>      
               <c:choose>
                  <c:when test="${followCheck}">
                     <input type='button' id='userfollow' onclick='unfollowClick("${vo.user_id }","${sessionId}")' value='팔로잉'>
                  </c:when>
                  <c:otherwise>
                     <input type='button' id='notuserfollow' onclick='followClick("${vo.user_id }")' value='팔로우'>
                  </c:otherwise>
               </c:choose>
            </c:otherwise>
         </c:choose>
         <span id="profile_name">${vo.user_name }</span><br/>
         <c:choose>
            <c:when test="${vo.gender == 'm'}">
               <img id="gender_img" src="/resources/upload/user/man.jpg"/><br/>
            </c:when>
            <c:when test="${vo.gender == 'w'}">
               <img id="gender_img" src="/resources/upload/user/woman.jpg"/><br/>
            </c:when>
         </c:choose>
         <div id="profile_intro_scope">
            <span id="profile_intro">${vo.info }</span>
         </div>
         <span id="profile_name2">${vo.user_name }</span><br/>
         <c:choose>
            <c:when test="${vo.gender == 'm'}">
               <img id="gender_img2" src="/resources/upload/user/man.jpg"/><br/>
            </c:when>
            <c:when test="${vo.gender == 'w'}">
               <img id="gender_img2" src="/resources/upload/user/woman.jpg"/><br/>
            </c:when>
         </c:choose>
         <div id="profile_intro_scope2">
            <span id="profile_intro2">${vo.info }</span><br/>
         </div>
         <button id="profile_btn2-1" type="button" class="btn btn-default" onclick="profile_edit()">프로필 편집2</button>
         <span id="profile_info">
            <span id="board_count">게시물 ${numberOfBoard }개</span>&nbsp;
            <span id="follower_count" onclick="followerShow()">팔로워 ${numberOfFollow.follower }명</span>&nbsp;
            <span id="following_count" onclick="followingShow()">팔로잉 ${numberOfFollow.following }명</span>
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
				<span id="board_count2">게시물 ${numberOfBoard }개</span>
			</div>
			<div class="w3-third w3-container w3-margin-bottom">
				<span id="follower_count2" onclick="followerShow()">팔로워 ${numberOfFollow.follower }명</span>
			</div>
			<div class="w3-third w3-container">
				<span id="following_count2" onclick="followingShow()">팔로잉 ${numberOfFollow.following }명</span>
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
							<div id="content">
							<div id="cnt_board_heart"></div>
							<div id="text"></div>
							</div>
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
			
			<!-- Modal 생성 -->
			<div class="modal" id="myModal2" role="dialog">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button class="close" type="button" data-dismiss="modal">×</button>
							<h3 id="headerText" class="modal-title"></h3>
						</div>
						<div class="modal-body">
							<div id="body-container" style="height:400px;overflow:auto">
							</div>
						</div>			
						<div class="modal-footer">
							<button class="btn btn-default" data-dismiss="modal">Close</button>
						</div>
					</div>
				</div>
			</div>
			
			<div id="popup_wrap"> 
				<div id="popupLayer" class="popupLayer">
					<div class="bg"></div>
					<ul id="popupContents">
						<c:choose>
							<c:when test="${sessionId == vo.user_id}">
								<li><a id="delete" href="#" id="">삭제</a></li>
							</c:when>
						</c:choose>
						<li><a href="#" id="">다운로드</a></li>
						<li><a href="#self" onclick="closePopup()">취소</a></li>
					</ul>
				</div>
			</div>
			
		</div>
	</div>
</div>


	
	<script>
	var sessionId = "${sessionId}";		// 접속자 ID
	var currentId = "${vo.user_id}"
	var thisPage = false;					// 메시지 페이지가 아니라는 의미
	var VIDEO = "m";
	var IMAGE = "i";
	var FLAG = true;
		
		function followClick(idx){
			if(sessionId != ""){
				 $.ajax({
					type : 'GET',
					url : '/user/' + idx,
					headers : {
						"Content-Type" : "application/json",
					},
					data : '',
					dataType : 'json',
					success : function(result){
						$("#notuserfollow").attr("id","userfollow").attr("value","팔로잉").attr("onclick","unfollowClick('"+currentId+"','"+sessionId+"')");
					},
					error:function(request,status,error){
						alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
					}
				}); 
			}
		}
		function unfollowClick(idx1,idx2){
			if(sessionId != ""){
				 $.ajax({
					type : 'GET',
					url : '/user/' + idx1 + '/'+idx2,
					headers : {
						"Content-Type" : "application/json",
					},
					data : '',
					dataType : 'json',
					success : function(result){
						$("#userfollow").attr("id","notuserfollow").attr("value","팔로우").attr("onclick","followClick('"+currentId+"')");
					},
					error:function(request,status,error){
						alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
					}
				}); 
			}
		}
	    function setFollowList(result){ // 팔로우show에서 받아온 데이터를 알맞게 정렬함
	    	var temp = "";
	    	$(result.list).each(function(){
	    		if(this.following != sessionId){
	    			if( this.follower == sessionId && this.flag == 1)
	    				temp += "<div class='f_list'><img id='thumbnail' src='/user/getByteImage/"+this.following+"'/><span class='f_text'><a href='/"+this.following+"'>"+this.following+"</a></span><input type='button' class='following' id='following"+this.following+"' onclick='unfollowingClick(\""+this.following+"\")' value='팔로잉'></div>";
    				else
	    				temp += "<div class='f_list'><img id='thumbnail' src='/user/getByteImage/"+this.following+"'/><span class='f_text'><a href='/"+this.following+"'>"+this.following+"</a></span><input type='button' class='follow' id='follow"+this.following+"' onclick='followingClick(\""+this.following+"\")' value='팔로우'></div>";
	    		}else{
    				temp += "<div class='f_list'><img id='thumbnail' src='/user/getByteImage/"+this.following+"'/><span class='f_text'><a href='/"+this.following+"'>"+this.following+"</a></span></div>";
	    		}
    		});
	    	document.getElementById("body-container").innerHTML= temp;
	    }
	    function unfollowingClick(follower,idx2){ // 언팔로우 할때 처리
	    	 $.ajax({
					type : 'GET',
					url : '/user/' + follower + '/'+idx2,
					headers : {
						"Content-Type" : "application/json",
					},
					data : '',
					dataType : 'json',
					success : function(result){
						$("#following"+follower).attr("class","follow").attr("id","follow"+follower).attr("value","팔로우").attr("onclick","followingClick('"+follower+"')");
					},
					error:function(request,status,error){
						alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
					}
				}); 
	    }
	    function followingClick(follower){ // 팔로잉 할떄 처리
	    	 $.ajax({
					type : 'GET',
					url : '/user/' + follower,
					headers : {
						"Content-Type" : "application/json",
					},
					data : '',
					dataType : 'json',
					success : function(result){
						$("#follow"+follower).attr("class","following").attr("id","following"+follower).attr("value","팔로잉").attr("onclick","unfollowingClick('"+follower+"')")
					},
					error:function(request,status,error){
						alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
					}
				});  
	    }
	    function setFollowingList(result){ // 팔로잉show에서 받아온 데이터를 알맞게 정렬함
	    	var temp = "";
	    	$(result.list).each(function(){
	    		if(this.follower != sessionId)
	    			if( this.following == sessionId){
	    				temp += "<div class='f_list'><img id='thumbnail' src='/user/getByteImage/"+this.follower+"'/><span class='f_text'><a href='/"+this.follower+"'>"+this.follower+"</a></span><input type='button' class='following' id='following"+this.follower+"' onclick='unfollowingClick(\""+this.follower+"\")' value='팔로잉'></div>";
	    			}else{
	    				temp += "<div class='f_list'><img id='thumbnail' src='/user/getByteImage/"+this.follower+"'/><span class='f_text'><a href='/"+this.follower+"'>"+this.follower+"</a></span><input type='button' class='follow' id='follow"+this.follower+"' onclick='followingClick(\""+this.follower+"\")' value='팔로우'></div>";
	    			}
	    		else
	    			temp += "<div class='f_list'><img id='thumbnail' src='/user/getByteImage/"+this.follower+"'/><span class='f_text'><a href='/"+this.follower+"'>"+this.follower+"</a></span></div>";
	    	});
	    	document.getElementById("body-container").innerHTML= temp;
	    }
		function followerShow(){ // 팔로워 목록을 불러옴
			$('#myModal2').modal('show'); // show the modal
			$('#headerText').text("Follower");
			$.ajax({ 
	    		type: 'GET',
                url: '/user/getFollowing/'+ currentId,
                dataType: 'json',
                contentType : 'application/json; charset=utf-8',
	    	    success: function(result) {
	    	    	console.log(result.list);
	    	    	setFollowList(result);
	    	    },
	    	    error:function(request,status,error){
	    	    	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	    	    }
	    	});
		}

		function followingShow(){ // 팔로잉유저의 목록을 불러옴
			$('#myModal2').modal('show'); // show the modal
			$('#headerText').text("Following");
			$.ajax({ 
	    		type: 'GET',
                url: '/user/getFollower/'+ currentId,
                dataType: 'json',
                contentType : 'application/json; charset=utf-8',
	    	    success: function(result) {
	    	    	console.log(result.list);
	    	    	setFollowingList(result);
	    	    },
	    	    error:function(request,status,error){
	    	    	console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	    	    }
	    	});
		}
		function getBoardCount(){
			return frm.bn.length;
		}
		function ondelete(bno){
			$.ajax({
				type : 'delete',
				url : '/board/'+ bno,
				headers : {
					"Content-Type" : "application/json",
					"X-HTTP-Method-Override" : "DELETE",
				},
				data : '',
				dataType : 'json',
				success : function( result ) {
					alert("삭제되었습니다.");
					location.reload(true);
				},
				error : function( result ) {
					alert("fail");
					console.log(result)
				}	
			});
		}
		function openPopup( bno ) {
			var popup = document.getElementById("popupLayer"); 
			$("#delete").attr("onclick","javascript:ondelete("+bno+")");
			$(popup).fadeIn();
		}
		function closePopup() {
			var popup = document.getElementById("popupLayer");     
			$(popup).fadeOut();
			      
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
		function insertReply( bno, uid ) {
			var reply_content = $("#content"+bno).val();
			var url = "/board/"+ bno +"/" + reply_content + "/" + uid;
			if(reply_content.trim() != ""){
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
						$("#content"+bno).val("");
					},
					error : function(result) {
						alert("fail");
					}
				});
			}
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
			
			var cbh = document.getElementById("cnt_board_heart");
			cbh.innerHTML = "좋아요 " + result + "개";
		}
		
		function setReplyList(data, bno) {
			var	result	= "";

			$(data).each(function() {	
				result += "<li id='rep'><a class='user_id' href='/"+this.user_id+"'>"+ this.user_id + "</a>" + " " + "<span>" + this.content + "</span>";
				if( sessionId == this.user_id ) {
					result += "<input type='button' class='deleteBtn fr' value='X' onclick='deleteReply(" + this.board_num +","+ this.reply_num + ")' /></li>";	
				}
			});
			document.getElementById( "cnt_reply" ).innerHTML = result;
		}
		
		function addReplyList(data, bno) {
			var	result	= "";

			$(data).each(function() {	
				result += "<li id='rep'><a class='user_id' href='/"+this.user_id+"'>"+ this.user_id + "</a>" + " " + "<span>" + this.content + "</span>";
				if( sessionId == this.user_id ) {
					result += "<input type='button' class='deleteBtn fr' value='X' onclick='deleteReply(" + this.board_num +","+ this.reply_num + ")' /></li>";	
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
		
		function read(no,idx) { // Grid Layer 이미지중 하나를 클릭하였을시 해당되는 내용을 불러옴
			$('#myModal').modal('show'); // show the modal
			$.ajax({ 
	    		type: 'GET',
                url: 'user/getNum/'+no+'/'+idx,
                dataType: 'json',
                contentType : 'application/json; charset=utf-8',
	    	    success: function(result) {
	    	    	var prevNext = checkBoard(no);
	    	    	if(result.bd.media_type == IMAGE)
	    	    		$("#image").html('<img src="'+result.bd.media+'">'+'<i id ="viewpeople" class="fa fa-info-circle fa-2x" onclick="viewclick()"></i>');
	    	    	else
	    	    		$("#image").html('<video src="'+result.bd.media+'" controls>');
	    	    		$("#cnt_board_heart").html("좋아요 " + result.bd.heart + "개");
		    	    	$("#text").html(result.bd.user_id+" "+result.bd.content);
		    	    	$("#tag").html(result.bd.tag).css("display","none");
		    	    	if(no != prevNext.prev){
		    	    		$("#prev").attr("onclick","read("+prevNext.prev+",0)");
		    	    	}else{
		    	    		$("#prev").attr("onclick","");
		    	    	}
		    	    	if(no != prevNext.next){
		    	    		$("#next").attr("onclick","read("+prevNext.next+",0)");
		    	    	}else{
		    	    		$("#next").attr("onclick","");
		    	    	}
		    	    	if(typeof prevNext.next == "undefined"){
		    	    		$("#prev").attr("onclick","");
		    	    	}

		    	    	if(typeof prevNext.prev == "undefined"){
		    	    		$("#next").attr("onclick","");
		    	    	}
		    	    	setReplyList(result.rp, no);
		    	    	$("#rep_inp").html("<a class='heart' href='#self'> <br />");
		    	    	if(result.ht == "1"){
		    	    		$("#rep_inp").html($("#rep_inp").html()+"<i id='heart_full"+result.bd.board_num+"' class='test fa fa-heart fa-2x' aria-hidden='true' onclick='heartCount("+result.bd.board_num+")'></i>");
		    	    		$("#rep_inp").html($("#rep_inp").html()+"<i id='heart_empty"+result.bd.board_num+"' class='test fa fa-heart-o fa-2x' aria-hidden='true' style='display: none;' onclick='heartCount("+result.bd.board_num+")'></i>")
		    	    	}else{
		    	    		$("#rep_inp").html($("#rep_inp").html()+"<i id='heart_full"+result.bd.board_num+"' class='test fa fa-heart fa-2x' aria-hidden='true' style='display: none;' onclick='heartCount("+result.bd.board_num+")'></i>");
		    	    		$("#rep_inp").html($("#rep_inp").html()+"<i id='heart_empty"+result.bd.board_num+"' class='test fa fa-heart-o fa-2x' aria-hidden='true'  onclick='heartCount("+result.bd.board_num+")'></i>")
		    	    	}
		    	    	$("#rep_inp").html($("#rep_inp").html()+"</a>");
		    	    	$("#rep_inp").html($("#rep_inp").html()+'<input type="hidden" id="board_num" name="board_num" value="'+result.bd.board_num+'" /> ');
		    	    	$("#rep_inp").html($("#rep_inp").html()+"<input type=\"text\" id=\"content" + result.bd.board_num+"\" name=\"content"+result.bd.board_num+"\" style=\"width: 450px; outline-style: none;\" onkeydown=\"javascript:if( event.keyCode == 13 ) insertReply('" + result.bd.board_num +"' , '"+result.bd.user_id+"')\" placeholder=\"댓글달기...\" />");						
		    	    	$("#rep_inp").html($("#rep_inp").html()+"<i class='fa fa-ellipsis-h fa-2x fr' onclick='openPopup("+result.bd.board_num+")' style='color: #bfbfbf;' aria-hidden='true'></i>");

	    	    },
	    	    error:function(request,status,error){
    	    	    alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
    	   	 	}
	    	});
		}
		
		function readReply(data){ // 받아온 댓글을 정렬
			var result ="";
			for(var d in data){
				result += "<li id='rep'><a href='/"+data[d].user_id+"'>"+data[d].user_id+"</a> "+data[d].content+"</li>";
			}
			return result;
		}
	
		function setScrollBoard(data){ // 스크롤바 끝까지 스크롤시 받아온 데이터를 정렬
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

