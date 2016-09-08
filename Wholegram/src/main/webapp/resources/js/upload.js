
   		var canvas = document.getElementById("myCanvas"); 	// 캔버스 
   		var context = canvas.getContext("2d"); 				// 캔버스에 출력할 이미지 타입 설정 -> context자체가 이미지
   	 	var img = document.getElementById("img"); 			// 이미지 태그 전체를 가져옴
   	 	var ini = document.getElementById("int"); 			// 이미지 태그 전체를 가져옴
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
		var MAX_SIZE = 50000000; 							//upload limit 50mb
		var MEGABYTE = 1048576;
		var VIDEOTYPE = ["mp4","avi"];
		var IMAGETYPE = ["jpg","jpeg","gif","png","bmp"];
	    panelInit();
	    set_init_size(ini,canvas,context,panel);
	    
	    $(document).ready(function(){
			$('#file').change(function(e) {
				  var file    = document.querySelector('input[type=file]').files[0];											// input type='file' 불러옴 (input태그의 type이 file인것들 모두 불러오는데, 그중에서 첫번째 것)
				  var reader  = new FileReader();
				  var img = document.getElementById("img");
				  if(file.size > MAX_SIZE){
					  alert("제한용량(50MB)을 초과하였습니다. 현재파일용량"+parseInt(file.size/MEGABYTE)+"MB");
				  }else{
					  reader.readAsDataURL(file);																				// file을 DataURL형태로 읽음.
					  reader.onload = function  () {
						if(VIDEOTYPE.indexOf(file.type.substring(file.type.indexOf("/")+1,file.type.length))!=FALSE){		 	// 파일 타입이 동영상(비디오)일 경우 -> file type을 "mp4" or "avi" 둘중 하나라도 있는지 체크함
							  cvs.style.display = "none";
							  vss.style.display = "block";
							  document.getElementById("videotype").src=reader.result;											// file의 dataURL을 video태그 src에 넣어줌
							  dataurl = reader.result;																			// file의 dataURL을 dataurl 전역 변수에 넣어줌.
							  $("#delete2").css("display","block");
							  $("#navi-tag").css("display","none");
							  $("#navi-filter").css("display","none");
							  $("#setTag").css("display","none");
							  $("#filter").css("display","none");
							  $("#direct").css("display","block");
							  type = "m";																						// media 타입(동영상타입)
							  
						} else if(IMAGETYPE.indexOf(file.type.substring(file.type.indexOf("/")+1,file.type.length))!=FALSE){	// 파일타입이 사진(이미지)일 경우 -> "jpg","jpeg","gif","png","bmp" 중에 하나라도 있는지 체크함
								  img.src = reader.result;
								  dataurl = reader.result;
								  $("#delete").css("display","block");
								  $("#navi-tag").css("display","block");
								  $("#navi-filter").css("display","block");
								  $("#setTag").css("display","block");
								  $("#filter").css("display","none");
								  $("#direct").css("display","none");
								  type = "i";																					// Image 타입(사진타입)
					    } else{
					    	alert("지원하지 않는 파일 형식 입니다.")
						}
					  }
				  }
			});
		});
	    
	    // 사진 선택 후, 이미지가 로딩이 완료된 이후 캔버스에다가 이미지를 삽입	
		document.getElementById("img").onload = function() {						// document.getElementById("img").onload -> img의 src에 dataurl이 모두load되었을시 동작
		    set_size(img,canvas,context,panel);
			//dataurl = canvas.toDataURL();
		}; 
	    
	    /* 브라우저 가 ie인지 아닌지 탐색*/
	    $.browser={};(function(){ 
	        jQuery.browser.msie=false;
	        $.browser.version=0;if(navigator.userAgent.match(/MSIE ([0-9]+)\./)){
	        $.browser.msie=true;jQuery.browser.version=RegExp.$1;}
	    })();
	    
	    function deleteButtonShow(){ //삭제 버튼을 누를 시 업로드 창 상태를 초기화면으로 되돌림
	    	var width = 300;
	    	var height = 300;
	    	document.getElementById("videotype").src= NULL;
	    	img.src = NULL;
			dataurl = NULL;
			panel.style.display="none";
			atag = NULL;
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
		    set_init_size(ini,canvas,context,panel);
	    }
	    
	    function canvasEvent(){ // 파일 업로드시 체크
	    	if(dataurl==NULL){
	    		file.click();
	    	}else{
	    		alert("X버튼을 눌러서 초기화 시키세요");
	    	}
	    }
	    
	    panel.addEventListener('click', function(e) { // 사람 태그를 누르고 검은 화면을 누를시 마우스의 좌표를 불러옴
	        var pos = getMousePos(canvas, e),		// canvas 범위 내에서 마우스 위치 체크
	            x = pos.x,
	            y = pos.y;
	        	setTag(x,y);
	    }, false);
	    
		function replaceAll(str, searchStr, replaceStr) { //java replaceAll처럼 정의

		    return str.split(searchStr).join(replaceStr);
		}
		
		function allTrim(str) { // 공백 전체를 없어지게함...

		    return str.split(" ").join("");
		}
		
		function convertTohashTag(){ // @와 #를 각각 타입에 맞게 변환함..
		   var tx = document.getElementById("content").value;
		   tx = replaceAll(tx,"#"," #");
		   tx = replaceAll(tx,"@"," @");
		   var temp = tx.split(" ");
		   var text ="";
		   var temp2="";
		   var aa = tx;
		   for(var tm in temp){
			    if(temp[tm].indexOf("#")!=-1 || temp[tm].indexOf("@")!=-1){
			    	 temp2 =temp[tm].split("#");
				     if(temp2[0].indexOf("@")!=-1){ // @일경우 해당 유저 페이지로 이동하도록 처리
				    	 tx =tx.replace(temp2[0], " <a href='/"+temp2[0].substring(1)+"'>"+temp2[0]+" </a>");
				     }
				     for(var i =1;i<temp2.length;i++){ // #일경우 해시태그 검색결과창으로 갈수 있도록 처리
				    	 tx =tx.replace("#"+temp2[i], " <a href='/hash/%23"+temp2[i]+"'>#"+temp2[i]+" </a>");
				     } 
			    }
		   }// 공백단위로 분리 -> 분리된 값들에 #이 있는지 없는지 확인 -> 있으면 #으로 분리 ->그걸 a태그로처리
		   content = tx;
		  }
		
		//Formdata로 처리 예정
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
		
		function dataURItoBlob(dataURI,type) { //dataurl을 blob로 변환
		    var binary = atob(dataURI.split(',')[1]);
		    var array = [];
		    for(var i = 0; i < binary.length; i++) {
		        array.push(binary.charCodeAt(i));
		    }
		    return new Blob([new Uint8Array(array)], {type: 'image/'+type});
		}
		
		/*이미지 or 동영상 및 텍스트들을 올림*/
	    function upload(){
	    	var nStart = new Date().getTime();	// 단순 업로드 시간 체크하기 위함(업로드하는 로직상 굳이 필요없음)
	    	if(type=="i"){
				dataurl = canvas.toDataURL();	// 이미지 타입일 경우 캔버스에서 데이터를 뽑아옴   -->> 최초 input type='file' 통해서 사진을 올렸을 때, dataurl에 값을 한번 넣어주고, 그 이후 이미지에 filter 처리 해주었을 때는, dataurl 값이 바뀌지 않는다. 
																							// (filter 효과를 줄때마다 dataurl값을 바꿔주면 처리량이 많아서 속도가 저하 프리징(멈춤현상)이 일어 날 수 있기 때문에 사진 업로드 하기 직전에 한 번만 바꿔준다.) 
	    	}
	    	if($("#file").val() == NULL){
	    		alert("사진을 불러오세요");
	    		file.click();
	    	}else if($("#content").val()==NULL){
	    		alert("내용을 작성하세요");
	    	}else{
	    		var content = document.getElementById("content").value;						// 업로드할때 작성한 글 내용 가져옴
		    	var json = JSON.stringify({													// 업로드할 데이터를 json형태로 설정 -> ??? stringify로 해주면 json형태의 text인데, parse로 하면 Object 형태가되서, ajax로 값을 못넘겨줘서 stringify로 해준건가?
		    		
		    			dataurl : dataurl,													// dataurl (사진or동영상의 dataurl)
		    			atag : atag,														// atag    (사진에 유저를 태그했을 경우)
		    			content : content,													// 글내용    
		    			type : type															// 사진or동영상 타입
		    	});	
		    	modal.style.display = "block";												// 업로드하는 동안에 background에서 이미 돌아가고있지만 숨겨져있던 로딩이미지를 보여준다.
		    	$.ajax({ 
		    		type: 'POST',
	                url: '/uploads',
	                data: json,
	                dataType: 'json',
	                timeout: 50000,
	                contentType : 'application/json; charset=utf-8',
		    	    success: function(result) {
		    	       if(result){
		    	    	   var nEnd =  new Date().getTime();      //종료시간 체크(단위 ms) 단순 업로드 시간 체크하기 위함(업로드하는 로직상 굳이 필요없음)
		    		       var nDiff = nEnd - nStart;     		  //두시간차 계산(단위 ms) 단순 업로드 시간 체크하기 위함(업로드하는 로직상 굳이 필요없음)
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
	    
	    function panelInit(){ // 사진 선택후 사람태그 버튼을 누를시 나오는 패널(검은바탕)을 현재 이미지의 크기에 맞게 조절 (여기서 사실상 '+' 모양의 맞게 조절됨. 초기화시켜준다라는 생각으로)
			$("#panel").css("width",canvas.width + "px").css("height",canvas.height + "px").css("margin-left",-1*canvas.width/2+ "px");
			$("#canvas").css("height",canvas.height+ "px");
		}
	    
	    // 사진에 사람 태그한것을 a태그 형태로 만들어서 넣어주기
	    function modalOK(){
	    	var a = document.createElement('a');
	    	$(a).attr('href',"/"+$("#tag").val()+"/").attr("text",$("#tag").val()).attr("title",$("#tag").val()).attr("class",".btn-primary");
	    	a.innerHTML = $("#tag").val();																			// a태그 사이에 값을 써줌. (ex - <a> 여기에 써줌 </a>)
	    	$(a).css("position","absolute").css("top",(($("#gety").val()/canvas.height)*100)+"%");					// y좌표 위치지정을 canvas내에서 %화 시켜준다. -> (사진 업로드시에는 문제 없지만, 다른 페이지(home or user페이지)에서는 사진의 크기가 변경된 경우가 있으니, 그 크기에 맞춰서 user태그된 위치를 알맞게 보여주려면 이런식으로 %화 시키주는 작업이 필요하다.
	    	$(a).css("left",(($("#getx").val()/canvas.width)*100)+"%").css("color","white").css("z-index","333");	// x좌표도 마찬가지.
	    	atag += a.outerHTML;	// 여기까지 a태그 값을  업로드할때 보내준다.
	    	
	    	
	    	$(a).attr("onclick","return false;");	// 여기서 a태그의 속성을 추가한 이유 -> 사진업로드 직전에는 사진에 태그한 다른 user를 클릭해도 href속성이 작동하지 않게끔 하기 위해서...! 즉, 사진 업로드 직전에 사람태그하는 과정의 화면에서만 적용된다.
	    	$("#panel").append(a);					// panel에 a태그 추가
	    	$("#myModal").modal('hide');			// modal 닫아주기
	    }
	    
	    // 클릭시 태그 입력 띄우기
	    function setTag(x,y){
	    	$("#myModal").modal('show');
	    	$("#getx").val(parseInt(x)); // x좌표 삽입  // 넘어온 x,y값이 double or float 타입이어서 Integer로 형변환
	    	$("#gety").val(parseInt(y)); // y좌표 삽입
	    }
	    
	    // 사진에 태그넣는 div가 나오도록함
		function showtag(){
	    	if($("#file").val()==NULL){
	    		alert("사진을 불러오세요");
	    		file.click();
	    	}else{	    		
				$("#panel").toggle();										// panel를 display none과 block 사이를 toggle 시켜줌
				console.log("test");
				$.ajax({ 
		    		type: 'POST',
	                url: '/getFollowingUser',
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
		}
		
		function followingclick(data){  // 팔로워 클릭시 입력
			$("#tag").val(data);
		} 
		
	    function setFollowingList(result){ // 팔로워데이터 목록을 받아와 정렬
	    	var temp = "";
	    	$(result.list).each(function(){
	    		temp += "<div class='f_list'>";
	    		if(this.default_profile == 0) {
	    			temp +=	"<img id='thumbnail' src='/user/getByteImage/"+this.follower+"'/>";
	    		} else {
	    			temp +=	"<img id='thumbnail' src='/resources/Image/Default.png'/>";
	    		}
	    		temp += "<span class='f_text'>"+this.follower+"</span><input type='radio' name='fradio' class='fradio' id='fradio' onclick='followingclick(\""+this.follower+"\")' value='"+this.follower+"'></div>";
	    	});
	    	document.getElementById("follower_list").innerHTML= temp;
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
		
		/* 필터링 처리부분 클릭시 */
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
		
		// 캔버스에 이미지가 로드될때 사진의 크기를 파악하여 캔버스의 크기 변경 및 선택한 사진으로 이미지 초기화
		function set_size(img,canvas,context,panel){
			var MAX_WIDTH_SIZE=600;
			var MAX_HEIGHT_SIZE=900;
			var width = img.width;						// 선택한 사진의 가로길이
			var height = img.height;					// 선택한 사진의 세로길이
			
			if(width > 600 || height > 600){			// 가로 세로 길이 600 이상일 경우
				
				if(width == height){					// 가로 세로 길이 알맞게 지정( 가로==세로 일경우)
					width = MAX_WIDTH_SIZE;			
					height = MAX_WIDTH_SIZE;
				}
				
				if(width > height){						// 가로 세로 길이 알맞게 지정 (가로 > 세로 일경우)
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
				
				if(height > width){						// 가로 세로 길이 알맞게 지정 (가로 < 세로 일경우)
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
			context.drawImage(img, 0, 0,width,height);		// 2D 이미지 만들기 drawImage(Image , 시작 x좌표, 시작 y좌표, 이미지 가로크기, 이미지 세로크기)

			panel.style.width=(width+2)+"px";				// 사진 선택후 사람태그 버튼을 누를시 나오는 패널(검은바탕)을 현재 이미지의 크기에 맞게 조절
			panel.style.height=(height+2)+"px";
			
			/* 이미지포함,  나타내는 영역들 크기 지정~ */
			$("#canvas").css("height",height+ "px");		
			$("#myCanvas").css("width",width+ "px");
			$("#myCanvas").css("height",height+ "px");
			$("#myCanvas").css("margin-left",-1*canvas.width/2+ "px");
			$("#panel").css("margin-left",-1*canvas.width/2+ "px");
		}
		
		// 색상반전 필터
		function color_reverse(){
			 var imgData = context.getImageData(0, 0, canvas.width, canvas.height);		// context.getImageData -> (R, G, B) 형식의 3차원배열? 형식의 데이터를 리턴함 -> (시작 x좌표, 시작 y좌표, 이미지 가로크기, 이미지 세로크기)
			   
			 // invert colors
			 /*색상 반전 시켜주는 로직*/
			 var i;
			 for (i = 0; i < imgData.data.length; i += 4) {
				 imgData.data[i] = 255 - imgData.data[i]; 	  // R
				 imgData.data[i+1] = 255 - imgData.data[i+1]; // G
				 imgData.data[i+2] = 255 - imgData.data[i+2]; // B
				 imgData.data[i+3] = 255; 					  // A -> 투명도
			 }
			 context.putImageData(imgData, 0, 0);			  // 변경된 ImageData를 다시 적용
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
		
		// 사진을 누렇게
		function sepia(){ 
			 var imgData = context.getImageData(0, 0, canvas.width, canvas.height);
			    
			 var i;
			 for (i = 0; i < imgData.data.length; i += 4) {
				 imgData.data[i] = imgData.data[i] +25; // R
				 imgData.data[i+1] = imgData.data[i+1]+15 ; // G
				 imgData.data[i+2] = imgData.data[i+2] - 35; // B
			 }
			 	context.putImageData(imgData, 0, 0);
		}