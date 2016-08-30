<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="false"%>

<!DOCTYPE html>
<html>
<head>
   <title>Wholegram</title>
   <meta name="viewport" content="width=device-width, initial-scale=1">
   <link rel="stylesheet" href="/resources/bootstrap/css/bootstrap.css">
   <link rel="stylesheet" href="/resources/css/bootstrap.css">
   <link rel="stylesheet" href="/resources/css/w3.css">
   <link rel="stylesheet" href="/resources/css/message.css">
   <link rel="stylesheet" href="/resources/css/new_person.css">
   <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
   <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script> 
   <link rel="stylesheet" href="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">
   <link rel="stylesheet" href="/resources/css/header.css"> 
   <script type="text/javascript" src="/resources/js/search.js"></script>
   <script src="resources/js/jquery.lazyloadxt.js" type="text/javascript"></script>
   <style>
      .lazy{
         display: none;
      }

   </style>
   <script>
      // 탭메뉴
      function showTab(val) {
         if( val == 1 ) {
            $("#1stMenu").css( "border-bottom", "1px solid #fff");
            $("#2ndMenu").css( "border-bottom", "1px solid #efefef");
            $("#1stMenu").css( "font-weight", "bolder");
            $("#2ndMenu").css( "font-weight", "lighter");
            $("#titleB").css( "display", "none");
            $("#titleA").css( "display", "block");
            
         } else if (val == 2) {
            $("#2ndMenu").css("border-bottom", "1px solid #fff");
            $("#1stMenu").css("border-bottom", "1px solid #efefef");
            $("#1stMenu").css("font-weight", "lighter");
            $("#2ndMenu").css("font-weight", "bolder");
            $("#titleA").css( "display", "none");
            $("#titleB").css( "display", "block");
         }
      }
      
   </script>
</head>
<body>

<!-- 상단의 head 부분 -->
<%@include file="./header.jsp" %>

<!-- 뉴스(소식)  -->
<div id="news_box" style="display: none;"></div>

   <!-- 게시물 영역 -->
   <div id="section">
      <div id="contents">
         <ul id="tebMenu">
            <li id="1stMenu" class="fl" onclick="showTab(1);">둘러보기</li>
            <li id="2ndMenu" class="fr" onclick="showTab(2);">알 수도 있는 사람</li>
         </ul>

         <!-- 둘러보기 / 사용자가 following하지 않은 다른 user 리스트 -->
         <div id="titleA">
            <c:forEach items="${bList}" var="b">
               <a href="/${b.user_id}" class="fl"> 
                  <img class="cnt_img" src="${b.media_thumnail}" style="margin-bottom: 16px;">
               </a>
            </c:forEach>
         </div>
         
         <!-- 알 수도 있는 사람 / 사용자가 following한 유저의 following user 리스트 -->
         <div id="titleB">
            <c:forEach items="${mbList}" var="mb">
               <c:if test="${sessionId ne mb.user_id}">
                  <div id="wrapper">
                     <div id="b_header">
                        <a id="cnt_user_img" class="fl" href="${mb.user_id}">
                        	<c:choose>
	                        	<c:when test="${mb.default_profile != 1}">
	                           		<img id="${mb.user_profile}" src="/user/getByteImage/${mb.user_id}" >
	                           	</c:when> 
	                           	<c:otherwise>
		                           	<img id="${mb.user_profile}" src="/resources/Image/Default.png" >
	                           	</c:otherwise>
                           	</c:choose>
                        </a> 
                        <input type="button" id="followBtn${mb.user_id}" name="followBtn${mb.user_id}" class="followBtn fr" value="팔로우" onclick="insertFollow('${mb.user_id}','${mb.mem_no}')" />
                        
                        <div id="delBtn${mb.user_id}"></div>
                        
                        <div id="cnt_header_user" class="fl">
                           <c:if test="${ mb.info eq ''}">
                              <a class="user" href="${mb.user_id}" style="margin-top: 10px;">${mb.user_id}</a>
                           </c:if>
                           <c:if test="${ mb.info ne ''}">
                              <a class="user" href="${mb.user_id}"> ${mb.user_id}</a>
                              <p class="info" style="text-align: left;">${mb.info}</p>
                           </c:if>
                        </div>
                        <br />
                     </div>

                     <div id="b_contents">
                        <div id="b_cnt_imgbox">
                           <c:forEach items="${bdList}" var="bl">
                              <c:forEach items="${bl}" var="bd">
                                 <c:if test="${bd.user_id == mb.user_id}">
                                 <c:if test="${bd.media_type == 'i'}">
                                    <img id="${bd.user_id}" class="cnt_img ${bd.board_num} fl" src="${bd.media}" />
                                 </c:if>
                                 </c:if>
                              </c:forEach>
                           </c:forEach>
                        </div>
                     </div>
                  </div>
               </c:if>
            </c:forEach>
         </div>
      </div>
   </div>
      <div id="chat_box"></div>

<script>


var fno = '${f.follow_num}';
var mno = '${mb.mem_no}';
var sessionId = '${sessionId}';
var thisPage = false;                  // 메시지 페이지가 아니라는 의미


function insertFollow( uid, mno ) {
   $.ajax({
      type : 'POST',
      url : '/person/' + uid,
      headers : {
         "Content-Type" : "application/json",
         "X-HTTP-Method-Override" : "POST",
      },
      data : '',
      dataType : 'json',
      success : function(result){
         Follow(result.list, uid);
         var fol = document.getElementById( "followBtn" + uid );
         $(fol).hide();
      },
      error:function(request,status,error){
         alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
      }
   });
}

function Follow(data, uid) {
   var result = "";
   $(data).each(function() {   
      if( sessionId == this.following && uid == this.follower ){
         result += "<input type='button' id='unFollowBtn" + this.follower + "' name='unFollowBtn" + this.follower + "' class='unFollowBtn fr' value='팔로잉' onclick=\"deleteFollow(" + this.follow_num + ",'" +this.follower+ "')\" />";
      }
   });
   document.getElementById( "delBtn" + uid ).innerHTML = result;
}

function deleteFollow( fno, user) {
   $.ajax({
      type : 'DELETE',
      url : '/person/dele/' + fno + '/' + user,
      headers : {
         "Content-Type" : "application/json",
         "X-HTTP-Method-Override" : "DELETE",
      },
      data : '',
      dataType : 'json',
      success : function(result){
         unFollow(result.delList, user);
      },
      error:function(request,status,error){
         alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
      }
   });
}

function unFollow(data, user) {
   $(data).each(function() {
      if( user == this.follower ) {
         var unfol = document.getElementById( "unFollowBtn" + this.follower);
         var fol = document.getElementById( "followBtn" + this.follower );
      }
      $(unfol).hide();
      $(fol).show();
   });
}
</script>

<script src="/resources/js/message.js"></script>
   
</body>
</html>
