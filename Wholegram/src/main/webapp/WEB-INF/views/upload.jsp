<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
	<title>Insert title here</title>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<meta name="viewport" content="width=device-width, user-scalable=no">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script type="text/javascript" src="resources/js/jquery.easing.1.3.min.js"></script>
    <script type="text/javascript" src="resources/js/jquery.bxslider.js"></script>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>
<style>
	
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
	}
	#delete{
		margin-left:800px;
	}
	@media screen and (max-width: 600px){
		.inputForm{
			padding: 0 40px;
		}
		#canvas{
			overflow:auto;
		}
	}
	
</style>
<body>
<%@ include file="header.html" %>

	<div class = "container">
		<div id="textline"><h2>글쓰기</h2></div>
		<i class="fa fa-refresh fa-3x" aria-hidden="true" id ="delete" value="삭제버튼" style="display:none" onclick="deleteButtonShow()"></i>
		<div id = "canvas" class="canvas" >
			<!-- image buffer -->
			<img id="img" style="display:none"> 
			<!-- view buffer -->
			<canvas id="myCanvas"  style="border:1px solid #d3d3d3;" height="300px" width="300px" onclick="canvasEvent()">
			Your browser does not support the HTML5 canvas tag.</canvas>
			<div id ="panel" class="panel" style="display:none"> </div> <!-- 사람 태그 패널 -->
		</div>
		<div id="videoshow" class="videoshow" style="display:none">
			<video id="videotype" width="600" height="400" src="" autoplay controls>	</video>
		</div>
		<!-- Modal -->
		<div class="modal fade" id="myModal" role="dialog">
		   	<div class="modal-dialog modal-lg">
		      	<div class="modal-content">
		        	<div class="modal-header">
		          		<button type="button" class="close" data-dismiss="modal">&times;</button>
		          		<h4 class="modal-title">태그할 사용자를 입력하세요!</h4>
		        	</div>
		        	<div class="modal-body">
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
			    <li><a onclick= "clickTag()" >Tag</a></li>
			    <li><a onclick= "clickFileter()" >Filter</a></li>
			    <li><a onclick= "clickDirect()" >Direct</a></li>
			</ul>
			
			<div id = "tag" class = "tag">
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
			
			<div id="direct" class = "direct" style="display:none">
				<table>
					<tr>
						<td onclick="restore()"><i class="fa fa-comments fa-5x" aria-hidden="true" ></i></td>
					</tr>
				</table>
			</div>
			
			<div id="submitbtn" class="submitbtn"> 
				<table>
					<tr>
						<td onclick="upload()"><i class="fa fa-paper-plane fa-2x" aria-hidden="true" ></i> &nbsp ajax 전송</td>
						<td onclick="convertTohashTag()" ><input type="button" value="Tag test"></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	
	<!-- 모듈화 시킬것 -->
	<script>
   		var canvas = document.getElementById("myCanvas"); // 캔버스 
   		var context = canvas.getContext("2d"); // 캔버스에 출력할 이미지 타입 설정
   	 	var img = document.getElementById("img"); // 이미지 태그 전체를 가져옴
	    var panel = document.getElementById("panel");
	    var cvs = document.getElementById("canvas");
	    var vss = document.getElementById("videoshow");
	    var button = document.getElementById("delete");
	    var dataurl="";
		var atag="";
		var content="";
		var FALSE = -1;
		var NULL ="";
		var MAX_SIZE = 50000000; //upload limit 50mb
		var MEGABYTE = 1048576;
	    panelInit();

	    $(document).ready(function(){
			$('#file').change(function(e) {
				  var file    = document.querySelector('input[type=file]').files[0];
				  console.log(file.type.substring(file.type.indexOf("/")+1,file.type.length));
				  var reader  = new FileReader();
				  var img = document.getElementById("img");
				  if(file.size > MAX_SIZE){
					  alert("제한용량(50MB)을 초과하였습니다. 현재파일용량"+parseInt(file.size/MEGABYTE)+"MB");
				  }else{
					  reader.readAsDataURL(file);
					  reader.onload = function  () {  
						  if(file.type.substring(file.type.indexOf("/")+1,file.type.length)=="mp4"){
							  cvs.style.display = "none";
							  vss.style.display = "block";
							  document.getElementById("videotype").src=reader.result;
							  dataurl = reader.result;
							  } else{
								  img.src = reader.result;
								  dataurl = reader.result;
							  }	
						  button.style.display = "block";
					  }
				  }
			});
		});
	    function deleteButtonShow(){
	    	var width = 300;
	    	var height = 300;
	    	document.getElementById("videotype").src= NULL;
	    	img.src = NULL;
			dataurl = NULL;
	    	cvs.style.display = "block";
			vss.style.display = "none";
			button.style.display = "none";
			context.clearRect(0, 0, 600, 900);
			panel.style.width=(width+2)+"px";
			panel.style.height=(height+2)+"px";
			$("#canvas").css("height",height+ "px");
			$("#myCanvas").css("width",width+ "px");
			$("#myCanvas").css("height",height+ "px");
			$("#myCanvas").css("margin-left",-1*width/2+ "px");
			$("#panel").css("margin-left",-1*width/2+ "px");
	    }
	    function canvasEvent(){
	    	if(dataurl==NULL){
	    		file.click();
	    	}else{
	    		alert("X버튼을 눌러서 초기화 시키세요");
	    	}
	    }
	    
	    panel.addEventListener('click', function(e) {
	        var pos = getMousePos(canvas, e),
	            x = pos.x,
	            y = pos.y;
	        	setTag(x,y);
	    }, false);
	    

		//입력한 파일이 변할시 이미지변경 하도록함.
		
		// 이미지가 로딩이 완료된 이후 캔버스에다가 이미지를 삽입	
		document.getElementById("img").onload = function() {
		    set_size(img,canvas,context,panel);
			//dataurl = canvas.toDataURL();
		}; 
		
		function convertTohashTag(){
			var tx = $("#content").val();
			var temp = tx.split(" ");
			var text ="";
			var temp2="";
			for(var tm in temp){
				if(temp[tm].indexOf("#")!=FALSE ){
					temp2 =temp[tm].split("#");
					for(var i =1;i<temp2.length;i++){
						text += " <a href='/"+temp2[i]+"'>#"+temp2[i]+"</a>";
					}	
				}else{
					text += " "+temp[tm];
				}
				content = text;
				console.log(content);
			}// 공백단위로 분리 -> 분리된 값들에 #이 있는지 없는지 확인 -> 있으면 #으로 분리 ->그걸 a태그로처리
			
		}
		
	    function upload(){
	    	if(dataurl == NULL){
	    		alert("사진을 불러오세요");
	    		file.click();
	    	}else if($("#content").val()==NULL){
	    		alert("내용을 작성하세요");
	    	}else{
		    	convertTohashTag();
		    	var json = JSON.stringify({
		    			dataurl : dataurl,
		    			atag : atag,
		    			content : content
		    	});	
		    	$.ajax({ 
		    		type: 'POST',
	                url: '/uploads',
	                data: json,
	                dataType: 'json',
	                contentType : 'application/json; charset=utf-8',
		    	    success: function(result) {
		    	       if(result)
		    	    	   window.location.replace('/board');
		    	       else
		    	    	   alert("사진/동영상을 첨부하세요");
		    	    },
		    	    error:function(request,status,error){
		    	        alert(json);
		    	    }
		    	});
	    	}
	 	} 
	    
	    function panelInit(){
			$("#panel").css("width",canvas.width + "px").css("height",canvas.height + "px").css("margin-left",-1*canvas.width/2+ "px");
			$("#canvas").css("height",canvas.height+ "px");
		}
	    
	    // 사진에 태그넣기
	    function modalOK(){
	    	var a = document.createElement('a');
	    	$(a).attr('href',"/"+$("#tag").val()+"/").attr("text",$("#tag").val()).attr("title",$("#tag").val()).attr("class",".btn-primary");
	    	a.innerHTML = $("#tag").val();
	    	$(a).css("position","absolute").css("top",$("#gety").val()+"px").css("left",$("#getx").val()+"px").css("color","white");
	    	atag += a.outerHTML;
	    	$(a).attr("onclick","return false;");
	    	$("#panel").append(a);
	    	$("#myModal").modal('hide');
	    }
	    
	    // 클릭시 태그 입력 띄우기
	    function setTag(x,y){
	    	$("#myModal").modal('show');
	    	$("#getx").val(parseInt(x)); // x좌표 삽입
	    	$("#gety").val(parseInt(y)); // y좌표 삽입
	    }
	    
	    // 사진에 태그넣는 div가 나오도록함
		function showtag(){
	    	if($("#file").val()==NULL){
	    		alert("사진을 불러오세요");
	    		file.click();
	    	}else	    		
				$("#panel").toggle();
		}
	    
	    // 사진에 달려있는 태그 전체를 삭제
	    function deleteTag(){
	    	if($("#file").val()==NULL){
	    		alert("사진을 불러오세요");
	    		file.click();
	    	}else{
		    	$("#panel *").remove();
		    	atag = $("#panel").text();
	    	}
	    	
	    }
	    
	    // panel위에서 마우스 클릭시 클릭한 위치를 잡아주는 함수
	    function getMousePos(canvas, e) {
	        var rect = canvas.getBoundingClientRect();
	        return {x: e.clientX - rect.left, y: e.clientY - rect.top};
	    }
		
		function clickTag(){
			$(".tag").show();
			$("#filter").hide();
			$("#direct").hide();
		}
		
		function clickFileter(){
			if($("#file").val()==NULL){
	    		alert("사진을 불러오세요");
	    		file.click();
	    	}else{	 
				$("#panel").hide();
				$(".tag").hide();
				$("#filter").show();
				$("#direct").hide();
	    	}
		}
		
		function clickDirect(){
			if($("#file").val()==NULL){
	    		alert("사진을 불러오세요");
	    		file.click();
	    	}else{	
				$("#panel").hide();
				$(".tag").hide();
				$("#filter").hide();
				$("#direct").show();
	    	}
		}		
		// 캔버스에 이미지가 로드될때 사진의 크기를 파악하여 캔버스의 크기 변경 
		function set_size(img,canvas,context,panel){
			var MAX_WIDTH_SIZE=600;
			var MAX_HEIGHT_SIZE=900;
			var width = img.width;
			var height = img.height;
			if(width > 600 || height > 600){
				if(width == height){
					width = MAX_WIDTH_SIZE;
					height = MAX_WIDTH_SIZE;
				}
				
				if(width > height){
					var aa = (width/height)*0.4;
					width = MAX_WIDTH_SIZE;
					height = width * aa
				}
				
				if(height > width){
					var aa = (height/width)*0.4;
					if(height>900)
						height = 900;
					
					width = height*aa;
					
					if(width>MAX_WIDTH_SIZE)
						width = MAX_WIDTH_SIZE;
				}
			}
			canvas.width = width;
			canvas.height = height;
			context.drawImage(img, 0, 0,width,height);
			panel.style.width=(width+2)+"px";
			panel.style.height=(height+2)+"px";
			$("#canvas").css("height",height+ "px");
			$("#myCanvas").css("width",width+ "px");
			$("#myCanvas").css("height",height+ "px");
			$("#myCanvas").css("margin-left",-1*canvas.width/2+ "px");
			$("#panel").css("margin-left",-1*canvas.width/2+ "px");
			dataurl = canvas.toDataURL();
		}
		// 색상반전 필터
		function color_reverse(){
			 var imgData = context.getImageData(0, 0, canvas.width, canvas.height);
			    // invert colors
			 var i;
			 for (i = 0; i < imgData.data.length; i += 4) {
				 imgData.data[i] = 255 - imgData.data[i]; // R
				 imgData.data[i+1] = 255 - imgData.data[i+1]; // G
				 imgData.data[i+2] = 255 - imgData.data[i+2]; // B
				 imgData.data[i+3] = 255; // A -> 투명도
			 }
			 	context.putImageData(imgData, 0, 0);
				dataurl = canvas.toDataURL();

		}
		// 원상복구
		function restore(){
		    set_size(img,canvas,context,panel);
			dataurl = canvas.toDataURL();

		}
		// 그레이 스케일 필터
		function grayscale(){
			var imgData = context.getImageData(0, 0, canvas.width, canvas.height);
			    // invert colors
			var i;
		 	for (i = 0; i < imgData.data.length; i += 4) {
				var r = imgData.data[i]; // R
				var g = imgData.data[i+1]; // G
				var b = imgData.data[i+2]; // B
				var v = 0.2126*r + 0.7152*g + 0.0722*b;
				imgData.data[i]=imgData.data[i+1]=imgData.data[i+2]=v;
			 }
			 	context.putImageData(imgData, 0, 0);
				dataurl = canvas.toDataURL();

		}
		
		// Threshold(흑,백) 필터
		function threshold(){
			 var imgData = context.getImageData(0, 0, canvas.width, canvas.height);
			    // invert colors
			 var i;
			 for (i = 0; i < imgData.data.length; i += 4) {
				 var r = imgData.data[i]; // R
				 var g = imgData.data[i+1]; // G
				 var b = imgData.data[i+2]; // B
				 var v = (0.2126*r + 0.7152*g + 0.0722*b >= 128)?255:0;
				 imgData.data[i]=imgData.data[i+1]=imgData.data[i+2]=v;
			 }
			 	context.putImageData(imgData, 0, 0);
				dataurl = canvas.toDataURL();
		}
		
		//밝게하는 필터
		function brighten(){
			 var imgData = context.getImageData(0, 0, canvas.width, canvas.height);
			    // invert colors
			 var i;
			 for (i = 0; i < imgData.data.length; i += 4) {
				 imgData.data[i] = imgData.data[i] + 5; // R
				 imgData.data[i+1] = imgData.data[i+1] + 5; // G
				 imgData.data[i+2] = imgData.data[i+2] + 5; // B
			 }
			 	context.putImageData(imgData, 0, 0);
				dataurl = canvas.toDataURL();
		}
		
		function sepia(){
			 var imgData = context.getImageData(0, 0, canvas.width, canvas.height);
			    // invert colors
			 var i;
			 for (i = 0; i < imgData.data.length; i += 4) {
				 imgData.data[i] = imgData.data[i] +25; // R
				 imgData.data[i+1] = imgData.data[i+1]+15 ; // G
				 imgData.data[i+2] = imgData.data[i+2] - 35; // B
			 }
			 	context.putImageData(imgData, 0, 0);
				dataurl = canvas.toDataURL();
		}
	</script>

</body>
</html>