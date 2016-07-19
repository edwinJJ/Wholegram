
	//	function is_hangul_char(event) {
	//		var ch = event.keycode||event.which;
	//		if(!(ch>48&&ch<58))
	//			alert(ch);
	//	}
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
				html += '<input type="text" id="email" name="email" class="mg5" placeholder="이메일" onblur="email_check()" onkeypress="noSpaceForm(this);" onkeyup="noSpaceForm(this)" onchange="noSpaceForm(this);"><div id="check_email" class="check"></div>';
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
			alert(id_check()&&email_check()&&pw_check()&&name_check()&&phone_check());
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
					type:'GET',
					url:i_curl,
					headers:{
						"Content-Type" : "application/json",
						"X-HTTP-Method-Override":"GET",
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
				check.style.color="red";
				check.innerHTML="잘못된 형식의 이메일입니다.";
				flag=false;
			}else{
				$.ajax({
					type:'GET',
					url:e_curl,
					headers:{
						"Content-Type" : "application/json",
						"X-HTTP-Method-Override":"GET",
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
