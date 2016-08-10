	function search(){

		var frm = document.getElementById("search_form");
		var text = document.getElementById("autocomplete");
		if(text.value == "")
			alert("검색어를 입력하세요");
		else
			frm.submit();
	}
	
	//'#'감지
	function hashDetector(idx){ 
		var FIRST_POSITION = 0;
		if(idx == FIRST_POSITION)
			return true;
		else
			return false
	}
	// 검색어의 형태에 따라 검색어와 관련된 키워드 또는 게시물의 개수를 가져옴
	$(function(){
	    var url ="";
	    var hashDetect;
	    $("#autocomplete").autocomplete({ 
        	 source: function( request, response ) {  //request는 입력된 데이터 response는 검색창 아래에 뿌려질 데이터
        		 if(hashDetector(request.term.indexOf("#"))){
        			 url = '/search/autocomplete/hashtag/'+encodeURIComponent(request.term);
        			 hashDetect = true;
        		 }
        		 else{        			 
        			 url = '/search/autocomplete/'+request.term;
        			 hashDetect = false;
        		 }
        		  $.ajax({
        			 url : url,
        			 type : "POST",
        			 datatype : 'json',
        			 data : {},
        			 success : function(data){
        				response(data);
        		 }}); 
         	 },
         	 
         	 focus: function (event, ui) { // 마우스가 hover시 검색창에 있는 단어가 hover된 단어로 변경
         		 if(!hashDetect){
         			$( "#autocomplete" ).val( ui.item.user_id );
         		 } else{
         			$( "#autocomplete" ).val( "#"+ui.item.tag );
         		 }
                return false;
             },
             
         	minLength: 2,
         	delay: 500,
	       	 select: function (event, ui) { // 클릭시 해당단어로 변경 또는 해당유저에게 이동
	       		if(!hashDetect){
	       			$( "#autocomplete" ).val( ui.item.user_id );
	       			location.href= "/"+ui.item.user_id; 
	       		} else {
	       			$( "#autocomplete" ).val( "#"+ui.item.tag );
	       		}
	       	 
	               return false;
	        }
	    
         }).autocomplete( "instance" )._renderItem = function( ul, item ,request) { // 결과물을 뿌려줌..
	    	if(!hashDetect){
		        return $( "<li>" )
		        .append( "<div> " + item.user_id + "<br>" + item.user_name + "</div>" )
		        .appendTo( ul );
	    	} else {
	    		return $( "<li>" )
		        .append( "<div> #" + item.tag + "<br>" + item.count + "</div>" )
		        .appendTo( ul );
	    	}
	    };
	});