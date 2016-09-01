<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
   <title>Wholegram</title>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <meta name="viewport" content="width=800,user-scalable=no">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script type="text/javascript" src="resources/js/jquery.easing.1.3.min.js"></script>
    <script type="text/javascript" src="resources/js/jquery.bxslider.js"></script>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
   <script src="/resources/js/news.js"></script>
   <script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script> 
   <link rel="stylesheet" href="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">
   <link rel="stylesheet" href="/resources/css/header.css"> 
   <link rel="stylesheet" href="/resources/css/message.css"> 
   <script type="text/javascript" src="/resources/js/search.js"></script>
   <link rel="stylesheet" href="/resources/css/w3.css">
 
   
</head>
<style>
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
		left:90%;
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
   .nav-tabs>li>a{
      color: #337ab7;
   }
   .tag>table>tbody>tr>td{
      width:60px
   }
   .container{
      margin-top: 20px;
      background-color: white;
      border-radius: 20px;
      padding: 5% 8%;
   }
   textarea{
      margin-top: 20px;
      height:80px;
   }
   body{
      background-color: #eee;
   }
   .form-control{
      resize:none;
      height:20%
   }
   .nav{
      margin-bottom:20px;
   }
   .canvas{
      position: relative;
      z-index: 2;
   }
   canvas{
      position: absolute;
      left:50%;
      margin-left:-150px;
      cursor:pointer;
   }
   table{
       text-align:center;
   }
   td{
      padding: 0 20px;
      cursor:pointer;
      
   }
   .videoshow{
      position: relative;
      z-index: 2;
   }
   .panel{
      position: absolute;
      z-index: 1;
      top: 0;
      opacity: 0.8;
      left:50%;
      background:black;
   }
   .nav > li{
      width:33%;
      text-align: center;
      font-size: 17px;
   }
   .nav-tabs > li{
      width:33%;
      text-align: center;
      font-size: 17px;
      cursor:pointer;
   }
   .inputForm{
      padding: 0 80px;
   }
   input[type="file"]{
      display:none;
   }
   #submitbtn{
      margin-top:25px;
   }
   #tag,#filter,#submitbtn{
      overflow:auto;
   }
   #textline{
      border-bottom: 1px solid#eee;
      margin-bottom: 20px;
   }
   #textline >h2{
      padding-left:100px;
   }
   video{
      margin-left: 150px;
       z-index: 2;
   }
   #delete{
       left: 89%;
       z-index: 100;
       position: absolute;
   }
   #delete2{
       left: 89%;
       z-index: 100;
       position: absolute;
   }
   .modal2 {
       display: none; /* Hidden by default */
       position: fixed; /* Stay in place */
       z-index: 10000; /* Sit on top */
       left: 0;
       top: 0;
       width: 100%; /* Full width */
       height: 100%; /* Full height */
       overflow: auto; /* Enable scroll if needed */
       background-color: rgb(0,0,0); /* Fallback color */
       background-color: rgba(0,0,0,0.2); /* Black w/ opacity */
       -webkit-animation-name: fadeIn; /* Fade in the background */
       -webkit-animation-duration: 0.4s;
       animation-name: fadeIn;
       animation-duration: 0.4s
   }
   
   #circle {
      position: absolute;
      display: block;
      width: 200px;
      height: 200px;
      border: 3px solid #111;
      border-radius: 100%;
      left: 45%;
      top: 45%;
      z-index: 1;
   }
   
   .a, .b, .c {
      animation-name: rotate;
      animation-iteration-count: infinite;
      animation-timing-function: linear;
      z-index: 1;
   }
   
   .a {
      animation-duration: 1500ms;
       z-index: 1;
   }
   
   .b {
      animation-duration: 4500ms;
      transform-origin: 63% 63%;
      width: 70px !important;
      height: 70px !important;
       z-index: 1;
   }
   
   .c {
      animation-duration: 9000ms;
      transform-origin: 74% 74%;
      width: 20px !important;
      height: 20px !important;
       z-index: 1;
   }
   
   
   @keyframes rotate {
     0% {
       transform: rotateZ(0deg);
     }
     100% {
       transform: rotateZ(360deg);
     }
   }

   .center {
      position: absolute;
      top: 50%;
      left: 50%;
      margin-top: -33px;
      margin-left: -33px;
   }
   @media screen and (max-width: 600px){
      .inputForm{
         padding: 0 40px;
      }
      #canvas{
         overflow:auto;
      }
   }
   .msg_position {
 		margin-bottom: -1px;
   }
   .msg_content2 {
   		width:250px;
   }
</style>
<body>
<%@ include file="./header.jsp" %>

<!-- 뉴스(소식)  -->
<div id="news_box" style="display: none;"></div>

      <div id="myModal2" class="modal2">
         <span id="circle" class="a">
               <span id="circle" class="b">
                   <span id="circle" class="c"></span>
               </span>
         </span>
      </div>
      
   <div class = "container">
      
      <div id="textline"><h2>글쓰기</h2></div>
      <div id = "canvas" class="canvas" >
         <i class="fa fa-refresh fa-3x" aria-hidden="true" id ="delete" style="display:none" onclick="deleteButtonShow()"></i> <!-- 파일 업로드시 나타남 -->
         
         <img id="int" src="/resources/Image/plus-sign.jpg" style="display:none"> <!-- 캔버스에 넣을 이미지 버퍼 (보이지않음) --> 
         <!-- image buffer -->
         <img id="img" src="/resources/Image/plus-sign.jpg" style="display:none"> <!-- 캔버스에 넣을 이미지 버퍼 (보이지않음) --> 
         <!-- view buffer -->
         <canvas id="myCanvas"  height="300px" width="300px" onclick="canvasEvent()"> <!-- 이미지태그에 있는 이미지를 불러올 장소 -->
         Your browser does not support the HTML5 canvas tag.</canvas>
         <canvas id="myCanvas2"  height="300px" width="300px" style="display:none"></canvas>
         <div id ="panel" class="panel" style="display:none"> </div> <!-- 사람 태그 패널 -->
      </div>
      <div id="videoshow" class="videoshow" style="display:none"> <!-- 비디오 타입이 올라왔을경우 캔버스는 사라지고 이태그가 나타남. -->
         <i class="fa fa-refresh fa-3x" aria-hidden="true" id ="delete2" style="display:none" onclick="deleteButtonShow()"></i> <!-- 파일 업로드시 나타남 -->
         <video id="videotype" width="600" height="400" src="" controls>   </video>
      </div>
      
      <!-- Modal -->
      <div class="modal fade" id="myModal" role="dialog"> <!-- Panel부분을 클릭시 나타날 사용자 입력 장소 -->
            <div class="modal-dialog modal-lg">
               <div class="modal-content">
                 <div class="modal-header">
                      <button type="button" class="close" data-dismiss=   "modal">&times;</button>
                 </div>
                 <div class="modal-body">
                 <form>
                  <div style="width:100%;height:300px;overflow:auto;border-bottom:1px solid#cfcfcf;" id="follower_list">textarea</div>
                  </form>
                  <h4 class="modal-title">태그할 사용자를 입력하세요!</h4>
                  <input type="hidden" id="getx">
                  <input type="hidden" id="gety">
                  <input type="text" id="tag">
                 </div>
                 <div class="modal-footer">
                  <button type="button" class="btn btn-default" onclick="modalOK()">OK</button>
                  <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
               </div>
            </div>
         </div>
      </div>
      
      <div class="inputForm">
         <!-- upload -->
            <textarea class="form-control" id = "content" name="content" cols="10"></textarea>
            <input type="file" id="file" name="file" accept="video/avi,video/mp4,image/jpeg,image/png,image/gif">
            
            
         
         <!-- function navi -->
         <ul class="nav nav-tabs">
             <li id ="navi-tag" style="display:block"><a onclick= "clickTag()" >Tag</a></li>
             <li id ="navi-filter" style="display:block;"><a onclick= "clickFileter()" >Filter</a></li>
         </ul>
         
         <div id = "setTag" class = "tag" style="display:block; ">
            <table>
               <tr>
                  <td onclick= "showtag();return false;"><i class="fa fa-tags fa-5x" aria-hidden="true" ></i><br>사람태그</td>
                  <td onclick= "deleteTag()"><i class="fa fa-eraser fa-5x" aria-hidden="true"></i><br>태그 삭제</td>
               </tr>
            </table>
         </div>
         
         <div id="filter" class = "image_filter" style="display:none; ">
            <table>
               <tr>
                  <td onclick="restore()"><i class="fa fa-picture-o fa-5x" aria-hidden="true"></i><br>원본</td>
                  <td onclick="color_reverse()"><i class="fa fa-eye-slash fa-5x" aria-hidden="true"></i><br>색반전</td>
                  <td onclick="threshold()"><i class="fa fa-star-half-o fa-5x" aria-hidden="true"></i><br>흑백</td>
                  <td onclick="grayscale()"><i class="fa fa-th fa-5x" aria-hidden="true"></i><br>그레이스케일</td>
                  <td onclick="brighten()"><i class="fa fa-spinner fa-5x" aria-hidden="true"></i><br>밝게</td>
                  <td onclick="sepia()"><i class="fa fa-spinner fa-5x" aria-hidden="true"></i><br>세피아</td>
               </tr>
            </table>
         </div>
         <div id="submitbtn" class="submitbtn"> 
            <table>
               <tr>
                  <td onclick="upload()"><i class="fa fa-paper-plane fa-2x" aria-hidden="true" ></i></td>
               </tr>
            </table>
         </div>
      </div>
   </div>
	<script type="text/javascript" src="resources/js/upload.js"></script>
	<script>
		var sessionId = "${sessionId}";				// 접속자 ID
		var thisPage = false;						// 메시지 페이지가 아니라는 의미
	</script>
	<div id="chat_box"></div>
	<script src="/resources/js/message.js"></script>
</body>
</html>