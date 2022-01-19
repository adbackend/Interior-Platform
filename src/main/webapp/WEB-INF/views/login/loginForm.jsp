<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 메인 시작 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/template/login_template.css">

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.min.css">
<script  type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
<script  type="text/javascript" src="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.css"></script>
<script type="text/javascript">
		window.history.forward();
		function noBack() {
			window.history.forward();
		}
</script>

<style type="text/css">
#menu ul li a{
	color:black;
}

.header-menu ul li a{
	color:black;
}

.form__container{
	display:flex;
	justify-content:center;
	width:100%;
	padding:50px;
	margin-top:80px;
	
	
}

.login-form{
width:300px;
margin-top:80px;

}

.login .top li label {
	display:inline-block;
	width:100px;
	font-size:18px;
	color:#464646;
	font-family:dotum;
	font-weight:bold;
	
}
.login .top li input{
	width:200px;
	height:35px;
	border-color:#d9d9d9;
}

ul{
	list-style:none;
}

.login .top{ position:relative; }

.login .top li .a{
	width:111px; height:69px;
	background:#328ba8;
	display:block;
	color:white;
	font-size:17px;
	text-align:center;
	line-height:69px;
	font-wight:bold;
	border:1px;
}

.login .top li.btn {
	position: absolute; right:-80px; top:-6px; 
}

.main-login{
	text-align:center;
	font-size:30px;
	padding-bottom:10px;
	
}

.col1{ 
 	text-align:center; 
 	padding-bottom:50px;
}



</style>
<form class="form__container" action="${pageContext.request.contextPath}/login/loginAction.do" method="post">

	<fieldset>
		<legend class="main-login">로그인</legend>
		<div class="col1">
		<b>내일의집 홈페이지를 방문해 주셔서 감사합니다.<br>
		로그인 하시면 보다 편리하게 서비스 이용이 가능합니다.</b>
		</div>
		<div class="login">
		<ul class="top">
			<li><label for="txt1">아이디</label><input type="text" name="mem_id" id="txt1"/></li>
			<li><label for="txt2">비밀번호</label><input type="password" name="mem_passwd" id="txt2"/></li>
			
<!-- 			<li class="btn"><a href="#">로그인</a></li> -->
			<li class="btn"><button  class="a" type="submit">로그인</button></li>
		</ul>
		
		</div>
		
	</fieldset>

</form>
<!-- 메인 끝 -->
