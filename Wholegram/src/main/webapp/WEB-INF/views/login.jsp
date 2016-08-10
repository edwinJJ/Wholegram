<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
   <meta charset="utf-8" />
   <title>Login</title>
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
   <script type="text/javascript" src="resources/js/jquery.easing.1.3.min.js"></script>
   <script type="text/javascript" src="resources/js/jquery.bxslider.js"></script>
   <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
   <script src ="resources/js/signup.js"></script>
   <script type="text/javascript">

   localStorage.setItem("chat", "true");
   
   function slider(){
      $('.slide').bxSlider({
         mode: 'fade',
         auto: true,
         pager: false,
         controls: false,
         speed:1800
      });
   };
   
   function doLogin() {
	   if( login_form.user_id.value == "" ) {
		   alert( "아이디를 입력해주세요." );
		   return;
	   }
	   
	   if( login_form.passwd.value == "" ) {
		   alert( "패스워드를 입력해주세요." );
		   return;
	   }
	   
	   login_form.submit();
   }

   $(document).ready(function(){
      slider();
   });
   </script>
   
   <style>
      body {
         background: #fafafa;
      }

      #wrapper {
         width: 100%;
      }
      
      #contents {
         width: 680px;
         position: absolute;
         top: 50%; 
         left: 50%;
         margin: -300px 0 0 -340px;
      }

      #contents:after {
         display: block;
         clear: both;
      }
      
      #img_box {
         width: 464px;
         position: relative;
      }

      #img_box #img_cnt1 {
         float: left;
         left: 50%;
         width: 464px;
         height: 618px;
         background: url('resources/Image/login(1).png') no-repeat; 
         margin: 0 0 0 -25%;
      }

       #img_box #img_cnt2 {
         width: 240px;
         height: 427px;
         float: left;
         position: absolute;
         top: 99px;
         left: 35px;
      }   
      
      ul, li {
      	list-style: none; 
      	margin: 0; 
      	padding: 0;
      }
      
      #login_box {
         background: #fff;
         margin-top: 50px;
         width: 300px;   
         height: 400px;
         float: right;
         display: table;
         
      }
      
      #login_form {
         text-align: center;
         position: relative;
         display: table-cell;
         vertical-align: middle;
      }
      
      input {
         width: 270px;
         height: 35px;
         border-radius: 5px;
         border: 1px solid #afafaf;
      }
      
      #pw_box {
         width: 270px;
         position: relative;
         margin: 0 auto;
      }
      
      .mg5 {
         margin-bottom: 5px;
      }
      
      
      .passfg {
         font-size: 13px;
         display: inline-block;
         position: absolute;
         top: 8px;
         right: 10px;
         color: #003569;
         text-decoration: none;

      }
      
      #join_box {
         background: #fff;
         width: 300px;   
         height: 100px;
         float: right;
         display: table;
         font-size: 14px;
      }
      
      #join_box > .center {
         display: table-cell;
         vertical-align: middle;
         text-align: center;
      }

      #join_box > .center > a {
         text-decoration: none;
         color: #3897f0;
      }

      #btn_login {
         background: #3897f0;
         color: #fff;
         border: 1px solid #3897f0;
         font-size: 16px;
         font-weight: bold;
         text-align: center;
      }

      @media all and ( max-width: 874px ) and ( min-width: 451px ) {
         #img_box {
            display: none;
         }

         #contents {
            width: 300px;
            margin-left: -150px;
         }

         #join_box, #login_box {
            clear: both;
         }
      }

      @media all and ( max-width: 450px ) and (min-width: 1px ) {
         #img_box {
            display: none;
         }

         #contents {
            margin-left: -150px;
            top: 0%;
            margin-top: 0;
            width: 300px;
         }

         #login_box {
            margin-top: 0px;
         }
            
         #join_box, #login_box {
            clear: both;
         }
      }

   </style>
</head>
<body>
<div id="wrapper"> 
<div id="contents">
   <div id="img_box">
      <div id="img_cnt1"></div> 
      <div id="img_cnt2">
      	<ul class="slide">
            <li><img src="resources/Image/login_img (1).jpg" alt="" /></li>
            <li><img src="resources/Image/login_img (2).jpg" alt="" /></li>
            <li><img src="resources/Image/login_img (3).jpg" alt="" /></li>
            <li><img src="resources/Image/login_img (4).jpg" alt="" /></li>
         </ul>
      </div>
   </div>
   
   <div id="login_box">
      <form id="login_form" action="/login" method="post">
         <h1>Wholegram</h1>
         <input type="text" id="user_id" name="user_id" class="mg5" placeholder=" 사용자 이름" /><br />
         <div id="pw_box">
               <input type="password" id="passwd" name="passwd" class="mg5" placeholder=" 비밀번호" />
               <a class="passfg" href="">비밀번호를 잊으셨나요?</a> <br />
         </div>
         <input type="button" id="btn_login" value="로그인" onclick="doLogin()" />
      </form>   
   </div>
      
   <div id="join_box">
      <div class="center">
         계정이 없으신가요? <a href="#" onclick="change_signup()">가입하기</a>
      </div>
   </div>
</div> 
</div>
</body>
</html>