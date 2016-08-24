localStorage.setItem("news", "false");				// 알림 창 on/off 구분 토큰
var n_token = localStorage.getItem("news");
var n_start = "true";
var n_fail = "false";

/*알림창에 내용 넣기(알림내용)*/
function makeNewsForm(result) {
	var count = 0;		// Header에 Notice 알림을 띄어주기 위함
	var html = "";
	html += 
		"<div class='w3-ul w3-border2 w3-center w3-hover-shadow' style='overflow:auto;'>" + 
			"<div class='w3-container'>";
	$(result).each(function() {
		html += "<div class='w3-hover-shadow newsef'>";
		if(this.check_notice == n_start) {
			html += "<label id='eachNotice_popup" + this.notice_num + "' class='w3-badge w3-right w3-small w3-red' style='display:none;'>!</label>";
		} else {
			count++;
			html += "<label id='eachNotice_popup" + this.notice_num + "' class='w3-badge w3-right w3-small w3-red' style='display:block;'>!</label>";
		}
			html +=	"<div class='noticeList' onclick='check_Notice_indicate(" + this.notice_num + "," + this.board_num +")'>";
						if(this.flag == 1) {
							if(this.default_profile == 0) {
								html += "<img class='profile_notice_img' src='/user/getByteImage/" + this.user_id + "' /> "; 
							} else {
								html += "<img class='profile_notice_img' src='/resources/upload/user/Default.png' /> ";
							}
								html +=	"<span class='notice_message'>" +
											this.user_id + "님이 회원님을 팔로우하기 시작했습니다."; +
										"</span>";
								html +=	"<span class='notice_message2'>. . . . . .</span>";
						} else if(this.flag == 2) {
							if(this.default_profile == 0) {
								html += "<img class='profile_notice_img' src='/user/getByteImage/" + this.user_id + "' /> "; 
							} else {
								html += "<img class='profile_notice_img' src='/resources/upload/user/Default.png' /> ";
							}
								html +=	"<span class='notice_message'>" + 
											this.user_id + "님이 회원님의 사진을 좋아합니다." +
										"</span>" +
										"<span style='float:right;'>" +
											"<img class='board_img' src='" + this.media + "' />" +
										"</span>";
								html +=	"<span class='notice_message2'>. . . . . .</span>";
						} else if(this.flag == 3) {
							if(this.default_profile == 0) {
								html += "<img class='profile_notice_img' src='/user/getByteImage/" + this.user_id + "' /> "; 
							} else {
								html += "<img class='profile_notice_img' src='/resources/upload/user/Default.png' /> ";
							}
								html +=	"<span class='notice_message'>" +
											this.user_id + "님이 댓글을 남겼습니다." +
										"</span>" +
										"<span style='float:right;'>" + 
											"<img class='board_img' src='" + this.media + "' />" + 
										"</span>";
								html +=	"<span class='notice_message2'>. . . . . .</span>";
						} else if(this.flag == 4) {
							if(this.default_profile == 0) {
								html += "<img class='profile_notice_img' src='/user/getByteImage/" + this.user_id + "' /> "; 
							} else {
								html += "<img class='profile_notice_img' src='/resources/upload/user/Default.png' /> ";
							}
								html +=	"<span class='notice_message'>" +
											this.user_id + "님이 게시글에서 회원님을 언급했습니다." +
										"</span>" +	
										"<span style='float:right;'>" +
											"<img class='board_img' src='" + this.media + "' />" +
										"</span>";
								html +=	"<span class='notice_message2'>. . . . . .</span>";
						} else if(this.flag == 5) {
							if(this.default_profile == 0) {
								html += "<img class='profile_notice_img' src='/user/getByteImage/" + this.user_id + "' /> "; 
							} else {
								html += "<img class='profile_notice_img' src='/resources/upload/user/Default.png' /> ";
							}
								html +=	"<span class='notice_message'>" + 
											this.user_id + "님이 댓글에서 회원님을 언급했습니다." + 
										"</span>" +
										"<span style='float:right;'>" +
											"<img class='board_img' src='" + this.media + "' />" +
										"</span>";
								html +=	"<span class='notice_message2'>. . . . . .</span>";
						}
			html += "</div>" +
				"</div>";
	});
	html +=
		"</div>" +
	"</div>";
	document.getElementById("news_box").innerHTML = html;
	
	if(count != 0) {	// 확인하지 않은 알림이 있을경우
		document.getElementById("news_popup").style.display = "block";	
	}
}

/*알림창 보여주기*/
function showNewsForm() {
	
	// 알림 내용 전체 가져오기
	var uc_url = "/user/checkNotice";
	$.ajax({
		type: 'POST',
		url: uc_url,
		headers:{
			"Content-Type" : "application/json",
			"X-HTTP-Method-Override":"POST",
		},
		dataType:'JSON',
		success : function(result) {
			var test = JSON.stringify(result);
			makeNewsForm(result);
		},
		error : function(result){
			alert("error : " + result);
		}
	});
	
	
	/*메시지 페이지에서 '+' 버튼이 알림창을 뚫고나와서 보여서, 가려주는 코드*/
	if((n_token == n_fail) || (n_token == null)) {
		document.getElementById("news_box").style.display = "block";
		
		localStorage.setItem("news", "true");
		n_token = localStorage.getItem("news");
		
		/*메시지창의 '+' 버튼제거*/
		var user_search = document.getElementById("user_search");
		if(user_search != null) {
			user_search.style.display = "none";
		}
	} else {
		localStorage.setItem("news", "false");
		n_token = localStorage.getItem("news");
		closeNewsForm();
		
		/*메시지창의 '+' 버튼 복구*/
		var user_search = document.getElementById("user_search");
		if(user_search != null) {
			user_search.style.display = "block";
		}
	}

	
}

/* 알림 표시를 읽음으로 변환 (! 제거) + 상세페이지 이동 */
function check_Notice_indicate(notice_num, board_num) {
	/*알림 표시를 읽음으로 변환 (! 제거)*/
	var cnc_url = "/user/check_Notice_indicate/" + notice_num;
	$.ajax({
		type: 'POST',
		url: cnc_url,
		headers:{
			"Content-Type" : "application/json",
			"X-HTTP-Method-Override":"POST",
		},
		dataType:'text',
		success : function(result) {
/*			alert("1 : " + result);
			alert("2 : " + JSON.stringify(result));*/
//			showNewsForm();
			
			location.href = "/board/" + board_num; // 상세페이지 이동 
		},
		error : function(result){
/*			alert("4 : " + result);
			alert("5 : " + JSON.stringify(result));
			alert("6 : " + JSON.parse(result));*/
			alert("error!! : " + result);
		}
	});
}

/*알림창 닫기*/
function closeNewsForm() {
	document.getElementById("news_box").style.display = "none";
}


/* 페이지 이동시 최초 1번 먼저 가져온 후, 알림 메시지 가져옴(1분마다)  */

$.ajax({ 
	url: "/user/checkNotice",
	datatype: "json",
	type:'POST',
	success: function(result){
		makeNewsForm(result);
	},
	error: function(result) {
		alert("error : " + result)
	}
});

setInterval(function(){
	$.ajax({ 
		url: "/user/checkNotice",
		datatype: "json",
		type:'POST',
		success: function(result){
			makeNewsForm(result);
		},
		error: function(result) {
			alert("error : " + result)
		}
	});
}, 60000);