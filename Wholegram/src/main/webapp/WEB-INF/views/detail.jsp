<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <meta charset="utf-8" />
   <link rel="stylesheet" href="/resources/css/bootstrap.css">
   <link rel="stylesheet" href="/resources/css/w3.css">
   <link rel="stylesheet" href="/resources/css/message.css">
   <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
   <script src="http://code.jquery.com/jquery-1.7.min.js"></script>
   <script src="https://code.jquery.com/ui/1.12.0/jquery-ui.js"></script> 
   <link rel="stylesheet" href="//code.jquery.com/ui/1.12.0/themes/base/jquery-ui.css">
   <script type="text/javascript" src="/resources/js/search.js"></script>
<title></title>
<style>
input[type="button"], input[type="text"] {
   outline-style: none; /* 포커스시 발생하는 효과 제거 */
   -webkit-appearance: none; /* 브라우저별 기본 스타일링 제거 */
   -moz-appearance: none;
   appearance: none;
   border-style: none;
}

input[type="button"] {
   background: #fff;
   color: #b3b3b3;
}

* {
   margin: 0;
   padding: 0;
}

body {
   background: #fafafa;
}

li {
   list-style: none;
}

.fr {
   float: right;
}

.fl {
   float: left;
}

.fwb {
   font-weight: bold;
}

.fc999 {
   color: #999;
}

#wrap:after {
   clear: both;
   content: '';
   display: block;
}

#wrapper {
   margin: 0 auto;
   width: 975px;
   height: auto;
   padding: 40px 20px;
}

#wrap {
   position: relative;
}

.img {
   width: 598px;
   max-height: 598px;
}

#contents {
   position: relative;
   padding: 0px 24px;
   border: 1px solid #eeefef;
   width: 335px;
   background: #fff;
}

#user {
   padding: 20px 0px;
   border-bottom: 1px solid #eeefef;
}

#user_img {
   width: 38px;
   height: 38px;
   border-radius: 50%;
}

#cnt {
   padding: 20px 0px;
}

.title {
   font-size: 15px;
   font-weight: 200;
   margin-top: 10px;
}

#cnt_board_list {
   overflow-y: scroll;
   padding-bottom: 20px;
}

#cnt_reply {
   background: #fff;
   width: 284px;
   height: 60px;
   position: absolute;
   bottom: 0;
   border-top: 1px solid #efefef;
}

#cnt_reply input[type="text"] {
   padding-left: 8px;
}

.test {
   color: #efefef;
}

/* option 팝업 */
#popup_wrapper {
   width: 100%;
}

.popupLayer {
   display: none;
}

.popupLayer .bg {
   position: fixed;
   top: 0;
   left: 0;
   width: 100%;
   height: 100%;
   background-color: #000;
   opacity: 0.7;
}

#popupContents {
   z-index: 9999;
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

#viewpeople {
   position: absolute;
   top: 93%;
   left: 3%;
}
</style>
</head>
<body>
<!-- Header -->
<%@include file="./header.jsp" %>

<!-- 뉴스(소식)  -->
<div id="news_box" style="display: none;"></div>
<div id="wrapper">
   <div id="wrap">
      <c:forEach items="${bdList}" var="bd">
         <c:choose>
            <c:when test="${bd.media_type == 'm'}">
               <video class="lazy fl img" data-src="${bd.media}" preload="metadata" controls></video>
            </c:when>
            <c:otherwise>
               <img class="lazy fl img" id="image${bd.board_num}" src="${bd.media}">
               <div id="tag${bd.board_num}" style="display: none">${bd.tag}</div>
               <i id="viewpeople" class="fa fa-info-circle fa-2x" onclick="viewClick('${bd.board_num}')"></i>
            </c:otherwise>
         </c:choose>
                        
         <div id="contents" class="fr">
            <div id="user">
               <img id="user_img" src="/user/getByteImage/${bd.user_id}">
               <a href="/${bd.user_id}">${bd.user_id}</a>   
            </div>
               <ul id="cnt">
                  <li id="cnt_board_heart${bd.board_num}" class="fl fwb">좋아요 ${bd.heart}개</li>
                  <li class="fr fc999">
                     <a href="#" style="display: none;"> 
                        <c:set var="toDay" value="<%=new java.util.Date() %>" /> 
                        <fmt:parseDate var="regDate" value="${bd.reg_date}" pattern="yyyy-MM-dd HH:mm:ss" /> 
                        <fmt:formatDate value="${regDate}" pattern="yyyyMMddHHmmss" />
                        <fmt:parseNumber value="${toDay.time}" integerOnly="true" var="nowDays" scope="request" /> 
                        <fmt:parseNumber value="${regDate.time}" integerOnly="true" var="oldDays" scope="request" />
                     </a> 
                     <c:if test="${(nowDays-oldDays)/1000 < 60}">
                        <fmt:parseNumber var="sec" value="${(nowDays-oldDays)/1000}" integerOnly="true" />
                           ${sec}초 전
                     </c:if> 
                     <c:if test="${(nowDays-oldDays)/1000 > 60 && (nowDays-oldDays)/1000 < 3600 }">
                        <fmt:parseNumber var="min" value="${((nowDays-oldDays)/(1000 * 60))}" integerOnly="true" /> 
                           ${min}분 전
                     </c:if> 
                     <c:if test="${(nowDays-oldDays)/1000 > 3600 && (nowDays-oldDays)/1000 < 86400 }">
                        <fmt:parseNumber var="hour" value="${(nowDays-oldDays)/(1000 * 60 * 60)}" integerOnly="true" />
                           ${hour}시간 전                        </c:if> 
                     <c:if test="${(nowDays-oldDays)/1000 > 86400 && (nowDays-oldDays)/1000 < 604800 }">
                        <fmt:parseNumber var="day" value="${(nowDays-oldDays)/(1000* 60 * 60 * 24)}" integerOnly="true" />
                           ${day}일 전
                     </c:if> 
                     <c:if test="${(nowDays-oldDays)/1000 > 604800 }">
                        <fmt:formatDate value="${regDate}" pattern="yyyy년 MM월 dd일" />
                     </c:if>
                  </li>
               </ul>
               
               <ul id="cnt_board_list">
                  <li>
                     <h1 class="title">
                        <a class="user_id fwb" href="/${bd.user_id}">${bd.user_id}</a>
                        <span> ${bd.content}</span>
                     </h1>
                  </li>
                  <li id="reply_list${bd.board_num }" class="reply_list">
                     <c:forEach items="${replyResult}" var="rp">
                        <c:if test="${bd.board_num == rp.board_num}">
                           <a class="user_id fwb" href="/${rp.user_id }">${rp.user_id }</a>
                           <span>${rp.content}</span>
                           <c:if test="${sessionId == rp.user_id}">
                              <input type="button" class="deleteBtn fr" value="X" onclick="deleteReply(${bd.board_num},${rp.reply_num})" />                              
                           </c:if>
                           <br />
                        </c:if>
                     </c:forEach>
                  </li>
               </ul>
               
               <div id="cnt_reply">
                  <a class="heart" href="#self"> <br /> 
                     <c:choose>
                        <c:when test="${bd.aldy_heart}">
                           <!-- 게시물에 좋아요가 눌러져있는 경우 -->
                           <i id="heart_full${bd.board_num}" class="test fa fa-heart fa-2x" aria-hidden="true" onclick="heartCount(${bd.board_num})"></i>
                           <i id="heart_empty${bd.board_num}" class="test fa fa-heart-o fa-2x" aria-hidden="true" style="display: none;" onclick="heartCount(${bd.board_num} )"></i>
                        </c:when>
                        <c:otherwise>
                           <!-- 게시물에 좋아요가 안 눌러져있는 경우 -->
                           <i id="heart_full${bd.board_num}" class="test fa fa-heart fa-2x" aria-hidden="true" style="display: none;" onclick="heartCount(${bd.board_num} )"></i>
                           <i id="heart_empty${bd.board_num}" class="test fa fa-heart-o fa-2x" aria-hidden="true" onclick="heartCount(${bd.board_num} )"></i>
                        </c:otherwise>
                     </c:choose>
                  </a> 
                  <input type="hidden" id="board_num" name="board_num" value="${bd.board_num}" /> 
                  <input type="text" id="content${bd.board_num}" name="content${bd.board_num}" style="width: 214px; outline-style: none;" onkeydown="javascript:if( event.keyCode == 13 ) insertReply('${bd.board_num}', '${bd.user_id}')" placeholder="댓글달기..." /> 
                  <a href="#self" onclick="openPopup(${bd.board_num})"> 
                     <i class="fa fa-ellipsis-h fa-2x fr" style="color: #bfbfbf;" aria-hidden="true"></i>
                  </a>
               </div>
            </div>
            
            <!-- option 메뉴 팝업 -->
                  <div id="popup_wrap">
                     <div id="popupLayer${bd.board_num}" class="popupLayer">
                        <div class="bg"></div>
                        <ul id="popupContents">
                           <li><a href="#self">다운로드${bd.board_num}</a></li>
                           <li><a href="#self" onclick="closePopup(${bd.board_num})">취소</a></li>
                        </ul>
                     </div>
                  </div>
      </c:forEach>
   </div>
</div>

<script type="text/javascript">
   var sessionId = '${sessionId}';

   var imgH = $(".img").height();
   $("#contents").css({"height":imgH});
   
   var userH = $("#user").height();
   var replyH = $("#cnt_reply").height();
   var cntH = imgH-userH-replyH-userH-replyH;
   $("#cnt_board_list").css({"height":cntH});
   
   function openPopup( bno ) {
      var popup = document.getElementById("popupLayer" + bno);
      $(popup).fadeIn();
   }
   
   function closePopup( bno ) {
      var popup = document.getElementById("popupLayer" + bno);
      $(popup).fadeOut();
   }
   
   function heartCount( board_num ) {
      var hc_url = "/board/heart/" + board_num;
      $.ajax({
         type : 'GET',
         url : hc_url,
         headers : {
            "Content-Type" : "application/json",
         },
         data : '',
         dataType : 'json',
         success : function(value){
            heartChange(value, board_num);
         },
         error : function(value) {
            alert("insertHeart fail");
         }
      });
   }
   
   function insertReport( board_num ) {
      $.ajax({
         type : 'GET',
         url : "/board/report/" + board_num,
         headers : {
            "Content-Type" : "application/json",
         },
         data : '',
         dataType : 'json',
         success : function(value){
            alert("게시물 신고 완료가 완료되었습니다.");
            var popup = document.getElementById("popupLayer" + board_num);
            $(popup).fadeOut();
         },
         error:function(request,status,error){
              alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
          }
      });
   }
   
   function heartChange(value, board_num) {
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
      cbh.innerHTML = "좋아요 " + value + "개";
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
            alert("deleteReply fail");
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
         }   
      });
   }
   
   String.prototype.replaceAll = function(org, dest) {
          return this.split(org).join(dest);
      }
      
      function insertReply( bno, uid ) {
         var reply_content = ($("#content"+bno).val()).replaceAll("#","%23");
         alert(reply_content);
         var url = "/board/"+ bno +"/" + reply_content + "/" + uid;
         
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
            error : function(request,status,error) {
               alert("insertReply fail");
               alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
            }
         });
      }
      
      function setReplyList(data, bno) {
         var   result   = "";
         $(data).each(function() {   
            result += "<li><a class='user_id fwb' href='/"+ this.user_id +"'>"+ this.user_id + "</a>" + " " + "<span>" + this.content + "</span>";
            if( sessionId == this.user_id ) {
               result += " " + "<input type='button' class='deleteBtn fr' value='X' onclick='deleteReply(" + this.board_num +","+ this.reply_num + ")' /></li>";   
            } 
            $("#content"+bno).val("");
         });

         document.getElementById( "reply_list" + bno ).innerHTML = result;
      }
      
      function viewClick(no){
         $("#tag"+no).toggle();
      }
</script>

<div id="chat_box"></div>
	<script src="/resources/js/message.js"></script>
</body>
</html>