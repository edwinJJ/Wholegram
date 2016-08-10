
   		var canvas = document.getElementById("myCanvas"); // 캔버스 
   		var context = canvas.getContext("2d"); // 캔버스에 출력할 이미지 타입 설정
   	 	var img = document.getElementById("img"); // 이미지 태그 전체를 가져옴
	    var panel = document.getElementById("panel");
	    var cvs = document.getElementById("canvas");
	    var vss = document.getElementById("videoshow");
	    var modal = document.getElementById('myModal2');
	    var dataurl="";
		var atag="";
		var content="";
		var type ="";
		var FALSE = -1;
		var NULL ="";
		var MAX_SIZE = 50000000; //upload limit 50mb
		var MEGABYTE = 1048576;
		var VIDEOTYPE = ["mp4","avi"];
		var IMAGETYPE = ["jpg","jpeg","gif","png","bmp"];
	    panelInit();
	    set_init_size(img,canvas,context,panel);
	    $(document).ready(function(){
			$('#file').change(function(e) {
				  var file    = document.querySelector('input[type=file]').files[0];
				  console.log(VIDEOTYPE.indexOf(file.type.substring(file.type.indexOf("/")+1,file.type.length)));
				  var reader  = new FileReader();
				  var img = document.getElementById("img");
				  if(file.size > MAX_SIZE){
					  alert("제한용량(50MB)을 초과하였습니다. 현재파일용량"+parseInt(file.size/MEGABYTE)+"MB");
				  }else{
					  reader.readAsDataURL(file);
					  reader.onload = function  () {  
						  if(VIDEOTYPE.indexOf(file.type.substring(file.type.indexOf("/")+1,file.type.length))!=FALSE){
							  cvs.style.display = "none";
							  vss.style.display = "block";
							  document.getElementById("videotype").src=reader.result;
							  dataurl = reader.result;
							  $("#delete2").css("display","block");
							  $("#navi-tag").css("display","none");
							  $("#navi-filter").css("display","none");
							  $("#setTag").css("display","none");
							  $("#filter").css("display","none");
							  $("#direct").css("display","block");
							  type = "m";
							  } else if(IMAGETYPE.indexOf(file.type.substring(file.type.indexOf("/")+1,file.type.length))!=FALSE){
								  img.src = reader.result;
								  dataurl = reader.result;
								  $("#delete").css("display","block");
								  $("#navi-tag").css("display","block");
								  $("#navi-filter").css("display","block");
								  $("#setTag").css("display","block");
								  $("#filter").css("display","none");
								  $("#direct").css("display","none");
								  type = "i";
							  }	else{
								  alert("지원하지 않는 파일 형식 입니다.")
							  }
					  }
				  }
			});
		});
	    /* 브라우저 가 ie인지 아닌지 탐색*/
	    $.browser={};(function(){ 
	        jQuery.browser.msie=false;
	        $.browser.version=0;if(navigator.userAgent.match(/MSIE ([0-9]+)\./)){
	        $.browser.msie=true;jQuery.browser.version=RegExp.$1;}
	    })();
	    
	    function deleteButtonShow(){
	    	var width = 300;
	    	var height = 300;
	    	document.getElementById("videotype").src= NULL;
	    	img.src = NULL;
			dataurl = NULL;
			if ($.browser.msie) {
				// ie 일때 input[type=file] init.
				$("#file").replaceWith( $("#filename").clone(true) );
			} else {
				// other browser 일때 input[type=file] init.
				$("#file").val("");
			}
	    	cvs.style.display = "block";
			vss.style.display = "none";
			$("#delete2").css("display","none");
			$("#delete").css("display","none");
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
						text += " <a href='/hash/%23"+temp2[i]+"'>#"+temp2[i]+" </a>";
					}	
				}else{
					text += " "+temp[tm];
				}
				content = text;
				console.log(content);
			}// 공백단위로 분리 -> 분리된 값들에 #이 있는지 없는지 확인 -> 있으면 #으로 분리 ->그걸 a태그로처리
			
		}
		
		//test예정
		function uploads(){
			var nStart = new Date().getTime();
			var form = new FormData();
			form.append("atag", atag);
	        form.append("content", content);
	        form.append("type", type);	//파일 한개
	        form.append("data",dataURItoBlob(dataurl,type));
	        $.ajax({
	        	dataType : 'json',
                url : "/uploadw",
                data : form,
                type : "POST",
                enctype: 'multipart/form-data',
                processData: false, 
                contentType: false,
                success : function(result) {
                    //...;
                },
                error : function(result){
                  	console.log(result);
                }
            });
	        var nEnd =  new Date().getTime();      //종료시간 체크(단위 ms)
		    var nDiff = nEnd - nStart;      //두 시간차 계산(단위 ms)
		    console.log(nDiff + "ms");
	        
		}
		
		function dataURItoBlob(dataURI,type) {
		    var binary = atob(dataURI.split(',')[1]);
		    var array = [];
		    for(var i = 0; i < binary.length; i++) {
		        array.push(binary.charCodeAt(i));
		    }
		    return new Blob([new Uint8Array(array)], {type: 'image/'+type});
		}
		
	    function upload(){
	    	var nStart = new Date().getTime();
	    	if(type=="i"){
				dataurl = canvas.toDataURL(); // 이미지 타입일 경우 캔버스에서 데이터를 뽑아옴
	    	}
	    	if($("#file").val() == NULL){
	    		alert("사진을 불러오세요");
	    		file.click();
	    	}else if($("#content").val()==NULL){
	    		alert("내용을 작성하세요");
	    	}else{
		    	convertTohashTag();
		    	var json = JSON.stringify({
		    			dataurl : dataurl,
		    			atag : atag,
		    			content : content,
		    			type : type
		    	});	
		    	modal.style.display = "block";
		    	$.ajax({ 
		    		type: 'POST',
	                url: '/uploads',
	                data: json,
	                dataType: 'json',
	                timeout: 50000,
	                contentType : 'application/json; charset=utf-8',
		    	    success: function(result) {
		    	       if(result){
		    	    	   var nEnd =  new Date().getTime();      //종료시간 체크(단위 ms)
		    		       var nDiff = nEnd - nStart;      //두 시간차 계산(단위 ms)
		    		       console.log(nDiff + "ms");
		    		       window.location.replace('/board');
		    	       }else
		    	    	   alert("사진/동영상을 첨부하세요");
		    	    },
		    	    error:function(request,status,error){
		    	    	    console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
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
	    	$(a).css("position","absolute").css("top",(($("#gety").val()/canvas.height)*100)+"%");
	    	$(a).css("left",(($("#getx").val()/canvas.width)*100)+"%").css("color","white");
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
		//초기화면 설정
		function set_init_size(img,canvas,context,panel){
			var width = img.width;
			var height = img.height;
			canvas.width = width;
			canvas.height = height;
			context.drawImage(img, 1, 1,width,height);
			panel.style.width=(width+2)+"px";
			panel.style.height=(height+2)+"px";
			$("#canvas").css("height",height+ "px");
			$("#myCanvas").css("width",width+ "px");
			$("#myCanvas").css("height",height+ "px");
			$("#myCanvas").css("margin-left",-1*canvas.width/2+ "px");
			$("#panel").css("margin-left",-1*canvas.width/2+ "px");
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
					if(width/height < 1.2){
						aa = 0.9;
					}else if(width/height < 1.4){
						aa = 0.7;
					}
						
					width = MAX_WIDTH_SIZE;
					height = width * aa
					if(height>900)
						height = 900;
				}
				
				if(height > width){
					var aa = (height/width)*0.4;
					if(height>900)
						height = 900;
					if(height/width < 1.2){
						aa = 0.9;
					}else if(height/width < 1.4){
						aa = 0.7;
					}
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

		}
		// 원상복구
		function restore(){
		    set_size(img,canvas,context,panel);
		}
		
		// 그레이 스케일 필터
		function grayscale(){
			var imgData = context.getImageData(0, 0, canvas.width, canvas.height);
			var i;
		 	for (i = 0; i < imgData.data.length; i += 4) {
				var r = imgData.data[i]; // R
				var g = imgData.data[i+1]; // G
				var b = imgData.data[i+2]; // B
				var v = 0.2126*r + 0.7152*g + 0.0722*b; //grayscale 값으로 전환
				imgData.data[i]=imgData.data[i+1]=imgData.data[i+2]=v;
			 }
			 	context.putImageData(imgData, 0, 0);

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
		}