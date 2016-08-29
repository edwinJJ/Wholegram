<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="resources/js/jquery.lazyloadxt.js" type="text/javascript"></script>
<c:forEach items="${bList}" var="bd">
	<div id="cnt_header">
			<a id="cnt_user_img" class="fl" href="/${bd.user_id}"> 
				<c:choose>
					<c:when test="${bd.default_profile != 1 }">
						<img src="/user/getByteImage/${bd.user_id}">	
					</c:when>
					<c:otherwise>
						<img src="/resources/Image/Default.png">
					</c:otherwise>
				</c:choose>
				</a>
			<div id="cnt_header_user" class="fl">
				<c:if test="${bd.place eq null}">
					<a class="user" style="padding-top: 10px;" href="/${bd.user_id}">
						${bd.user_id}</a>
				</c:if>
				<c:if test="${bd.place ne null}">
					<a class="user" href="/${bd.user_id}"> ${bd.user_id}</a>
					<a class="bd_place" href="#">${bd.place}</a>
				</c:if>
			</div>

			<!-- 현재일에서 등록일간의 차이 구하기 7일이후면 등록일 표기 -->
			<span id="reg_date" class="fr reg_date" value="${bd.board_num}">
				<a href="#" style="display: none;"> <c:set var="toDay"
						value="<%=new java.util.Date() %>" /> <fmt:parseDate
						var="regDate" value="${bd.reg_date }"
						pattern="yyyy-MM-dd HH:mm:ss" /> <fmt:formatDate
						value="${regDate }" pattern="yyyyMMddHHmmss" /> <fmt:parseNumber
						value="${toDay.time}" integerOnly="true" var="nowDays"
						scope="request" /> <fmt:parseNumber value="${regDate.time}"
						integerOnly="true" var="oldDays" scope="request" />
			</a> <c:if test="${(nowDays-oldDays)/1000 < 60}">
					<fmt:parseNumber var="sec" value="${(nowDays-oldDays)/1000}"
						integerOnly="true" />
							${sec}초 전
						</c:if> <c:if
					test="${(nowDays-oldDays)/1000 > 60 && (nowDays-oldDays)/1000 < 3600 }">
					<fmt:parseNumber var="min"
						value="${((nowDays-oldDays)/(1000 * 60))}" integerOnly="true" /> 
							${min}분 전
						</c:if> <c:if
					test="${(nowDays-oldDays)/1000 > 3600 && (nowDays-oldDays)/1000 < 86400 }">
					<fmt:parseNumber var="hour"
						value="${(nowDays-oldDays)/(1000 * 60 * 60)}"
						integerOnly="true" />
							${hour}시간 전
						</c:if> <c:if
					test="${(nowDays-oldDays)/1000 > 86400 && (nowDays-oldDays)/1000 < 604800 }">
					<fmt:parseNumber var="day"
						value="${(nowDays-oldDays)/(1000* 60 * 60 * 24)}"
						integerOnly="true" />
							${day}일 전
						</c:if> <c:if test="${(nowDays-oldDays)/1000 > 604800 }">
					<fmt:formatDate value="${regDate}" pattern="yyyy년 MM월 dd일" />
				</c:if>
			</span>
		</div>
	<!-- 변경-->
	<div id="cnt_board_img">
		<div id="panel">
		    <c:choose>
		   		 <c:when test="${bd.media_type == 'm'}">
		   		 	<video class="lazy" data-src="${bd.media}" width="600" height="600" preload="metadata" controls></video>
				 </c:when>
				 <c:otherwise>
					<img class="lazy" id="image${bd.board_num}" data-src="${bd.media}" height="600">
					<div id="tag${bd.board_num}" style="display:none">${bd.tag}</div>
					<i id ="viewpeople" class="fa fa-info-circle fa-2x" onclick="viewClick('${bd.board_num}')"></i>
				 </c:otherwise>
			</c:choose>
		</div>
	</div>

	<div id="cnt_board_content">
		<div id="cnt_board_heart${bd.board_num}" class="fwb">좋아요 ${bd.heart}개</div>
		<ul id="cnt_board_list">
			<li>
				<h1 class="title">
					<a class="user_id fwb" href="">${bd.user_id}</a> 
					<span> ${bd.content} </span>
				</h1>
			</li>								
			<li id="reply_list${bd.board_num}" class="reply_list">
				<c:forEach items="${replyResult}" var="rp">
					<c:if test="${bd.board_num == rp.board_num}">
						<a class="user_id" href="">${rp.user_id}</a> 
						<span>${rp.content}</span>
						<c:if test="${sessionId == rp.user_id}">
							<input type="button" class="deleteBtn fr" value="X" onclick="deleteReply(${bd.board_num},${rp.reply_num})" />
						</c:if>
						<br/>
					</c:if>
				</c:forEach>
			</li>
		</ul>
		<div id="cnt_reply">
			<a class="heart" href="#self">
				<br/>
				<c:choose>
				<c:when test="${bd.aldy_heart}"> <!-- 게시물에 좋아요가 눌러져있는 경우 -->
				<i id="heart_full${bd.board_num}" class="test fa fa-heart fa-2x" aria-hidden="true" onclick="heartCount(${bd.board_num} )"></i> 
				<i id="heart_empty${bd.board_num}" class="test fa fa-heart-o fa-2x" aria-hidden="true" style="display: none;" onclick="heartCount(${bd.board_num})"></i>
				</c:when>
				<c:otherwise>  <!-- 게시물에 좋아요가 안 눌러져있는 경우 -->
				<i id="heart_full${bd.board_num}" class="test fa fa-heart fa-2x" aria-hidden="true" style="display: none;" onclick="heartCount(${bd.board_num})"></i>
				<i id="heart_empty${bd.board_num}" class="test fa fa-heart-o fa-2x" aria-hidden="true" onclick="heartCount(${bd.board_num})"></i> 
				</c:otherwise>
				</c:choose>
			</a>
			<input type="hidden" id="board_num" name="board_num" value="${bd.board_num}" /> 
			<input type="text" id="content${bd.board_num}" name="content${bd.board_num}" style="width: 450px; outline-style: none;" onkeydown="javascript:if( event.keyCode == 13 ) insertReply('${bd.board_num}', '${bd.user_id}')" placeholder="댓글달기..." /> 
			
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
	            <li><a href="#self" onclick="insertReport(${bd.board_num})">부적절한 콘텐츠 신고</a></li>
	            <li><a href="${bd.media}" download>다운로드</a></li>
	            <li><a href="#self" onclick="closePopup(${bd.board_num})">취소</a></li>
	        </ul>
		</div>
	</div>
</c:forEach>