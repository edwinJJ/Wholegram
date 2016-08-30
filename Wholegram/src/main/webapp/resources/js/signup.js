var authstr = "";	// 이메일 인증 문자


	function noSpaceForm(obj) { // 공백사용못하게
	    var str_space = /\s/;  // 공백체크
	    if(str_space.exec(obj.value)) { //공백 체크
	        obj.focus();
	        obj.value = obj.value.replace(' ',''); // 공백제거
	        return false;
	    }
	}
	function change_signup(){
		var html = '<form id="login_form" action="/signup" method="post" onsubmit="return false">';
			html += '<input type="text" id="user_id" name="user_id" class="mg5" placeholder="아이디 : 6자 이상 입력하세요" onblur="id_check()" onkeypress="noSpaceForm(this);" onkeyup="noSpaceForm(this)" onchange="noSpaceForm(this);" ><div id="check_id" class="check"></div>'
			html += '<input type="password" id="passwd" name="passwd"  class="mg5" placeholder="비밀번호 : 6자 이상 입력하세요" onblur="pw_check()" onkeypress="noSpaceForm(this);" onkeyup="noSpaceForm(this)" onchange="noSpaceForm(this);"><div id="check_pw" class="check"></div>';
			html += '<input type="text" id="user_name" name="user_name" class="mg5" maxlength="20" placeholder="이름" onblur="name_check()" onkeypress="noSpaceForm(this);" onkeyup="noSpaceForm(this)" onchange="noSpaceForm(this);">';
			html += '<div><input type="text" id="email" name="email" class="mg5" placeholder="이메일" onblur="email_check()" onkeypress="noSpaceForm(this);" onkeyup="noSpaceForm(this)" onchange="noSpaceForm(this);"><div id="check_email" class="check"></div><input type="button" class="btn btn-info" onclick="sendMail();" value="이메일 인증"></div>';
			html += '<input type="text" id="auth" name="auth" style="margin-left:15px; margin-top:5px; display:none;" class="mg5" maxlength="11" placeholder="Email 인증번호" onblur="auth_check()" onkeypress="noSpaceForm(this);" onkeyup="noSpaceForm(this)" onchange="noSpaceForm(this);"><div id="check_auth" class="check"></div>';
			html += '<input type="text" id="phone" name="phone" class="mg5" maxlength="11" placeholder="전화번호 ex)0000000000" onkeypress="noSpaceForm(this);" onkeyup="noSpaceForm(this)" onchange="noSpaceForm(this);">';
			html += '<input type="button" class="btn btn-info" onclick="checkSignUp()" value="가입">';
			html += '</form>';
		var change_login = '<div class="center">';
			change_login += '계정이 있으신가요? <a href="#" onclick="change_login()">로그인</a>';
			change_login +='</div>';
		$('#login_box').html(html);
		$('#join_box').html(change_login);
	}
	function change_login(){
		var html = '<form id="login_form" action="/login" method="post">';
			html += '<h1>Wholegram</h1>';
			html += '<input type="text" id="user_id" name="user_id" class="mg5" placeholder=" 사용자 이름" /><br />';
			html += '<div id="pw_box">';
			html += '<input type="password" id="passwd" name="passwd" class="mg5" placeholder=" 비밀번호" />';
			html += '<a class="passfg" href="">비밀번호를 잊으셨나요?</a> <br />';
			html += '</div>';
			html += '<input type="button" id="btn_login" value="로그인" onclick="doLogin()" />';
			html += '</form>';
		var change_signup = '<div class="center">';
		    change_signup += '계정이 없으신가요? <a href="#" onclick="change_signup()">가입하기</a>';
		    change_signup += ' </div>';
		$('#login_box').html(html);
		$('#join_box').html(change_signup);
	}
	function checkSignUp(){
		if(id_check()&&email_check()&&pw_check()&&name_check()&&phone_check()){
			alert("가입완료");
			document.getElementById("login_form").submit();
		}else{
			if(!id_check()){
				alert("아이디 항목에 V표시가 나오도록 하세요!");
			}else if(!email_check()){
				alert("이메일에 V표시가 나오도록 하세요!");
			}else if(!pw_check()){
				alert("비밀번호 6자리 이상 입력 하세요!");
			}else if(!name_check()){
				alert("이름을 올바르게 작성하세요");
			}else{
				alert("전화번호를 올바르게 입력하세요");
			}
		}
	}
	function id_check(){
		var text = document.getElementById("user_id").value;
		var check = document.getElementById("check_id");
		var length = text.length;
		var i_curl = 'checkId/'+text;
		var flag = true;
		if(length>5){
			$.ajax({
				type:'POST',
				url:i_curl,
				headers:{
					"Content-Type" : "application/json",
					"X-HTTP-Method-Override":"POST",
				},
				dataType:'JSON',
				data: '',
				success : function(result){
					if(result){
						check.style.color = "green";
						check.innerHTML = "V";
						flag = true;
					}
					else{
						check.style.color = "red";
						check.innerHTML = "X"
						flag = false;
					}
				}
			});
		}else{
			flag=false;
			check.style.color = "red";
			check.innerHTML = "X"
		}
		return flag;
	}
	function pw_check(){
		var flag = true;
		var text = document.getElementById("passwd").value;
		var length = text.length;
		var check = document.getElementById("check_pw");
		if(length<6){
			check.style.color = "red";
			check.innerHTML = "비밀번호가 6자 이상이어야 합니다.";
			flag = false;
		}else{
			check.style.color = "green";
			check.innerHTML = "V";
			flag = true;
		}
		return flag;
	}
	function name_check(){
		var flag = true;
		var text = document.getElementById("user_name").value;
		var length = text.length;
		if(length<2){
			flag = false;
		}else{
			flag = true;
		}
		return flag;
	}
	function email_check(){
		var flag = true;
		var text = document.getElementById("email").value;
		var length = text.length;
		var index = text.indexOf('.');
		var text_sub = text.substring(0,index);
		var e_curl = 'checkEmail/'+text_sub;
		var check = document.getElementById("check_email");
		if(text.indexOf('@')==-1 || text.indexOf('.') == -1){
			flag=false;
		}else{
			$.ajax({
				type:'POST',
				url:e_curl,
				headers:{
					"Content-Type" : "application/json",
					"X-HTTP-Method-Override":"POST",
				},
				dataType:'JSON',
				data: '',
				success : function(result){
					if(result){
						check.style.color = "green";
						check.innerHTML = "V";
						flag=true;
					}
					else{
						check.style.color = "red";
						check.innerHTML = "사용중인 이메일 입니다."
						flag=false;
					}
				}
			});
		}
		return flag;
	}
	function phone_check(){
		var flag = true;
		var text = document.getElementById("phone").value;
		var length = text.length;
		if(length<10){
			flag=false;
		}else{
			flag=true;
		}
		return flag;
	}
	
	function sendMail() {
		var text = document.getElementById("email").value;
		var index = text.indexOf('.');
		var text_dot = text.indexOf('@');
		var text_sub = text.substring(0,index);
		var text_sub2 = text.substring(index+1);

		if((text == "") || (index == -1) || (text_dot == -1) || (text_sub2 == "") ) {
			var check = document.getElementById("check_email");
			check.style.color="red";
			check.innerHTML="잘못된 형식의 이메일입니다.";
			return alert("Email을 다시 입력해주세요.");
		}
		
		var emailSend_url = "/sendMail/" + text_sub + "/" + text_sub2;
		$.ajax({
			type:'POST',
			url: emailSend_url,
			headers:{
				"Content-Type" : "application/json",
				"X-HTTP-Method-Override":"POST",
			},
			dataType:'text',
			success : function(result){	
				alert("인증 메일을 발송했습니다.");
				authstr = result;											// 메일 인증번호 담기
				document.getElementById("auth").style.display = "block";	// 인증번호 입력칸 보여주기
			},
			error : function(result){
				alert("error : 없는 메일 주소 입니다.");
			}
		});
	}
	
	function auth_check() {
		var auth = document.getElementById("auth").value;
		var check = document.getElementById("check_auth");
		var flag = false;
		if(authstr == auth) {									// 인증번호 일치할경우
			check.style.color="green";
			check.innerHTML = "V";
			flag = true;
		} else {												// 인증번호 불일치
			check.style.color="red";
			check.innerHTML = "V";
		}
		return flag;
	}
