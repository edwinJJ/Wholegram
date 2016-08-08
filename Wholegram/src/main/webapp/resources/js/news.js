localStorage.setItem("news", "false");
var n_token = localStorage.getItem("news");
var n_start = "true";
var n_fail = "false";

function showNewsForm() {
	if((n_token == n_fail) || (n_token == null)) {
		var html = "";
		html += 
	/*		"<div class='modal2 fade' id='myModal' role='dialog'>" +
				"<div class='modal-dialog2 modal-lg2'>" +
					"<div class='modal-content'>" +
						"<div class='modal-header2'>" +
							"<button type='button' class='close' data-dismiss='modal' onclick='addReceive()'>&times;</button>" +
							"<h4 class='modal-title'>Message 보내기</h4>" +
						"</div>" +
						"<div>" +
							"<input id='receive_user' class='w3-input3' type='text' placeholder='받는 사람'>" + 
						"</div>" +
						"<div id='followingList'></div>" +
						"<div class='modal-footer'>" +
							"<button type='button' class='btn btn-default' data-dismiss='modal' onclick='check_messageform()'>메시지 보내기</button>" +
						"</div>" +
					"</div>" +
				"</div>" +
			"</div>";*/      /**/
			"<div class='w3-ul w3-border2 w3-center w3-hover-shadow' style='overflow:auto;'>" + 
				"<div class='w3-container'>" +
					"<div class='w3-hover-shadow newsef'>123</div>" +
					"<div class='w3-hover-shadow newsef'>456</div>" +
					"<div class='w3-hover-shadow newsef'>789</div>" +
					"<div class='w3-hover-shadow newsef'>789</div>" +
					"<div class='w3-hover-shadow newsef'>123</div>" +
					"<div class='w3-hover-shadow newsef'>456</div>" +
					"<div class='w3-hover-shadow newsef'>789</div>" +
					"<div class='w3-hover-shadow newsef'>789</div>" +
					"<div class='w3-hover-shadow newsef'>789</div>" +
					"<div class='w3-hover-shadow newsef'>123</div>" +
					"<div class='w3-hover-shadow newsef'>456</div>" +
					"<div class='w3-hover-shadow newsef'>789</div>" +
					"<div class='w3-hover-shadow newsef'>789</div>" +
				"</div>" +
			"</div>";
		document.getElementById("news_box").innerHTML = html;
		document.getElementById("news_box").style.display = "block";
		
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