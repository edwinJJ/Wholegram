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
		var sessionId = "${sessionId}";				// 접속자 ID
	
		function profile_edit() {
			location.href="/user/update_form";
		}
		
		$(document).ready(function(e) {
			//confirm
			$('a#confirm').click(function() {
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
		
		function fileUpload() {
			console.log("Abc");
/*             function uploadFile(){
                var form = $('FILE_FORM')[0];
                var formData = new FormData(form);
                formData.append("fileObj", $("#FILE_TAG")[0].files[0]);
                formData.append("fileObj2", $("#FILE_TAG2")[0].files[0]);
 
                $.ajax({
                    url: '',
                            processData: false,
                            contentType: false,
                            data: formData,
                            type: 'POST',
                            success: function(result){
                                alert("업로드 성공!!");
                            }
                    });
            } */
		}
		
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
                            document.getElementById("profile_img").src = "/user/getByteImage?timestamp=" + new Date().getTime();	// user 페이지 처음 로딩될때 profile_img 태그의 src가 "/user/getByteImage"를 호출하는데, 여기서 이미지 등록 후, 다시 "/user/getByteImage" 를 호출해주면, 기존에 남아있던 캐시를 사용하게됨(값이 안바뀜). 그래서 뒤에 시간에 해당되는 값을 추가(새로운 호출로 인식됨) 
                        },
                        error : function(result) {
                        	alert("error : " + result);
                        }
		            });
				}
			});
		});
	
		/* 브라우저창 끝을 알림 */
		$(window).scroll(function() {
		    if($(window).scrollTop() == $(document).height() - $(window).height()) {
		        alert('End of Window');
		    }
		}); 
		
		
	</script>
</head>
<body>
<!-- 상단의 head 부분 --><!-- test -->
<%@include file="./header.jsp" %>

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
				<!-- First Photo Grid-->
	    		<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4"><a title="Image 1" href="#"><img class="thumbnail img-responsive board_items" src="/resources/Image/Penguins.jpg"></a></div>
	    		<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4"><a title="Image 2" href="#"><img class="thumbnail img-responsive board_items" src="/resources/Image/test.jpg"></a></div>
	    		<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4"><a title="Image 3" href="#"><img class="thumbnail img-responsive board_items" src="/resources/Image/test2.jpg"></a></div>
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4"><a title="Image 4" href="#"><img class="thumbnail img-responsive board_items" src="/resources/Image/test3.jpg"></a></div>
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4"><a title="Image 5" href="#"><img class="thumbnail img-responsive board_items" src="/resources/Image/test3.jpg"></a></div>
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4"><a title="Image 6" href="#"><img class="thumbnail img-responsive board_items" src="/resources/Image/test3.jpg"></a></div>
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4"><a title="Image 7" href="#"><img class="thumbnail img-responsive board_items" src="/resources/Image/test2.jpg"></a></div>
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4"><a title="Image 8" href="#"><img class="thumbnail img-responsive board_items" src="/resources/Image/test.jpg"></a></div>
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4"><a title="Image 9" href="#"><img class="thumbnail img-responsive board_items" src="/resources/Image/Penguins.jpg"></a></div>
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4"><a title="Image 10" href="#"><img class="thumbnail img-responsive board_items" src="/resources/Image/Penguins.jpg"></a></div>
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4"><a title="Image 11" href="#"><img class="thumbnail img-responsive board_items" src="/resources/Image/test3.jpg"></a></div>
				<div class="col-lg-4 col-md-4 col-sm-4 col-xs-4"><a title="Image 12" href="#"><img class="thumbnail img-responsive board_items" src="/resources/Image/test3.jpg"></a></div>
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
								<div class="carousel-inner"></div>
								<a class="carousel-control left" href="#modalCarousel" data-slide="prev"> <i class="glyphicon glyphicon-chevron-left"></i></a> 
								<a class="carousel-control right" href="#modalCarousel" data-slide="next"> <i class="glyphicon glyphicon-chevron-right"></i></a>
							</div>
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
<div id="chat_box"></div>
<script src="/resources/js/message.js"></script>

	
	<script>
	/* Board-Modal 생성 스크립트  */	
	
		/*<![CDATA[*//* copy loaded thumbnails into carousel */
		$('.row .thumbnail').on('load', function() {

		}).each(function(i) {
			if (this.complete) {
				var item = $('<div class="item"></div>');
				var itemDiv = $(this).parents('div');
				var title = $(this).parent('a').attr("title");

				item.attr("title", title);
				$(itemDiv.html()).appendTo(item);
				item.appendTo('.carousel-inner');
				if (i == 0) { // set first item active
					item.addClass('active');
				}
			}
		});

		/* activate the carousel */
		$('#modalCarousel').carousel({
			interval : false
		});

		/* change modal title when slide changes */
		$('#modalCarousel').on('slid.bs.carousel', function() {
			$('.modal-title').html($(this).find('.active').attr("title"));
		})

		/* when clicking a thumbnail */
		$('.row .thumbnail').click(function() {
			var idx = $(this).parents('div').index();
			var id = parseInt(idx);
			$('#myModal').modal('show'); // show the modal
			$('#modalCarousel').carousel(id); // slide carousel to selected

		});/*]]>*/
	</script>
</body>
</html>

