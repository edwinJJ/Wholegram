localStorage.setItem("news", "false");
var n_token = localStorage.getItem("news");
var n_start = "true";
var n_fail = "false";

/*알림창에 내용 넣기(알림내용)*/
function makeNewsForm(result) {
	var html = "";
	html += 
		"<div class='w3-ul w3-border2 w3-center w3-hover-shadow' style='overflow:auto;'>" + 
			"<div class='w3-container'>";
	$(result).each(function() {
		html += "<div class='w3-hover-shadow newsef'>" +
					"<div class='noticeList'>";
						if(this.flag == 1) {
							if(this.default_profile == 0) {
								html += "<img class='profile_notice_img' src='/user/getByteImage/" + this.user_id + "' /> "; 
							} else {
								html += "<img class='profile_notice_img' src='/resources/upload/user/Default.png' /> ";
							}
								html +=	"<span class='notice_message'>" +
											this.user_id + "님이 회원님을 팔로우하기 시작했습니다."; +
										"</span>";
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
						}
				html += 
					"</div>" +
				"</div>";
	});
	html +=
		"</div>" +
	"</div>";
	document.getElementById("news_box").innerHTML = html;
}

/*알림창 보여주기*/
function showNewsForm(result) {
	
	// 알림 내용 가져오기
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
			makeNewsForm(result);
		},
		error : function(result){
			alert("error : " + result);
		}
	});
	
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

/*알림창 닫기*/
function closeNewsForm() {
	document.getElementById("news_box").style.display = "none";
}


/* 알림 메시지 가져옴(1분마다)  */
setInterval(function(){
$.ajax({ 
	url: "/user/checkNotice",
	datatype: "json",
	type:'POST',
	success: function(result){
		console.log(result);
		makeNewsForm(result);
	},
	error: function(result) {
		alert("error : " + result)
	}
});
}, 60000);