<link rel="stylesheet" href="/resources/css/header.css">
<link rel="stylesheet" href="/resources/css/news.css">    
<script src="https://use.fontawesome.com/9fc8d6f50a.js"></script>
<script src="/resources/js/news.js"></script>
<!-- <link rel="stylesheet" href="/resources/css/w3.css"> --> 

<div id="header" style="background: #FFFFFF; width: 100%">
   <a href="#" onclick="javascript: location.href='/board'"><span id="header-logo"><input type="image" src="/resources/Image/Wholegram.png" name="logo" class="header-logo_img"></span></a>
	<form id = "search_form" name = "search_form" action="/search/" method="post" onsubmit="return false">
		<span id="header-search">
			<input type="search" id="autocomplete" name="searchValue" placeholder="search" onkeydown="javascript:if( event.keyCode == 13 ) search();" >
		</span>
	</form>
   <span id ="btn_group">
      <a id="header-upload" href="/upload"><i class="fa fa-upload fa-2x" style="color:black;"></i></a>
      <a id="header-message" href="/message">
         <i class="fa fa-weixin fa-2x" style="color:black;">
            <span id="header_popup" class="w3-badge w3-right w3-small w3-red" style="display:none;">!</span>
         </i>
      </a>
      <a id="header-new_person" href="/person"><i class="fa fa-users fa-2x" style="color:black;"></i></a>
      <a id="header-news" href="#" onclick="showNewsForm()">
      	<i class="fa fa-heart-o fa-2x" style="color:black;">
			<span id="news_popup" class="w3-badge w3-right w3-small w3-red" style="display:none;">!</span>      	
      	</i>
      </a>
      <a id="header-user" href="/login"><i class="fa fa-user fa-2x" style="color:black;"></i></a>
   </span>
</div>
<!-- line -->
<div class="line"></div>
