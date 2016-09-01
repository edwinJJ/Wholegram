
function passwdLoseForm() {
	var text = "'새로운 비밀번호가 발송되었습니다.!'";
	var html =	'<form id="login_form" action="/sendMailFind" method="post">' +
			   		'<h1 style="color:#3897f0;">비밀번호 발급받기</h1>' +
			   		'<div>' +
			   			'이메일을 통하여' +
			   		'</div>' +
			   		'<div>' +
		   				'새로운 비밀번호를 발급받을 수 있습니다.' +
		   			'</div>' +
			   		'<div style="margin-top:10px;">' +
			   			'<input type="text" id="user_id" name="user_id" class="mg5" placeholder="ID를 입력해주세요."/>' +
			   			'<input type="submit" class="email_submit mg5"/ value="비밀번호 발급받기" onclick="alert(' + text + ');"/>' +
			   		'</div>' +
			   	'</form>';
	$('#login_box').html(html);
	$('#join_box').html("");
}

