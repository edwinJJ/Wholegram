localStorage.setItem("news", "false");
var n_token = localStorage.getItem("news");
var n_start = "true";
var n_fail = "false";

function makeNewsForm(result) {
	var html = "";
	html += 
		"<div class='w3-ul w3-border2 w3-center w3-hover-shadow' style='overflow:auto;'>" + 
			"<div class='w3-container'>";
	$(result).each(function() {
		if(this.flag == 2) {	// 좋아요
			html += "<div class='w3-hover-shadow newsef'>" +
						"<div class='test'>" +
							"<img id='profile_notice_img' src='/user/getByteImage/" + this.user_id + "' /> " + this.user_id + "님이 회원님의 사진을 좋아합니다.   " +
							"<img id='board_img' src='" + this.media + "' />" +
						"</div>" +
					"</div>";
		}
		if(this.flag == 4) {	// 게시물 내용중 언급
			html += "<div class='w3-hover-shadow newsef'>" +
						"<div class='test'>" +
							"<img id='profile_notice_img' src='/user/getByteImage/" + this.user_id + "' /> " + this.user_id + "님이 게시글에서 회원님을 언급했습니다.   " +
							"<img id='board_img' src='" + this.media + "' />" +
						"</div>" +
					"</div>";
		}
	});
	html +=
		"</div>" +
	"</div>";
	document.getElementById("news_box").innerHTML = html;
}

function showNewsForm(result) {
	if((n_token == n_fail) || (n_token == null)) {
/*		var html = "";
		$(result).each(function() {
			html += 
				"<div class='w3-ul w3-border2 w3-center w3-hover-shadow' style='overflow:auto;'>" + 
					"<div class='w3-container'>";
			if(this.flag == 2) {
				html += "<div class='w3-hover-shadow newsef'>" + this.user_id + "님이 회원님의 사진을 좋아합니다.</div>";
			}
					"</div>" +
				"</div>";
			document.getElementById("news_box").innerHTML = html;
*/			document.getElementById("news_box").style.display = "block";
			
			localStorage.setItem("news", "true");
			n_token = localStorage.getItem("news");
	} else {
		localStorage.setItem("news", "false");
		n_token = localStorage.getItem("news");
		closeNewsForm();
	}
}

function closeNewsForm() {
	document.getElementById("news_box").style.display = "none";
}