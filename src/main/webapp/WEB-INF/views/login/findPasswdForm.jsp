<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<style type="text/css">
.form-horizontal{
	text-align:center;
	margin-top:50px;
}
.fineMain{
	margin:0 auto;
	display:inline-block;
	width:600px;
	
}
</style>
<script type="text/javascript">
	$(document).ready(function(){
		
	});

</script>
</head>
<body>
<form class="form-horizontal" action="${pageContext.request.contextPath}/login/passwdCodeSend.do" method="post">

<h1>비밀번호 찾기</h1>
<h3>내 정보에 등록된 이메일로 찾기</h3>
<div class="fineMain">
  <div class="form-group">
    <label class="control-label col-sm-2" for="mem_name">아이디:</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" name="mem_name" id="mem_name" placeholder="아이디">
    </div>
  </div>
  <div class="form-group">
    <label class="control-label col-sm-2" for="mem_email">이메일:</label>
    <div class="col-sm-10">
      <input type="email" class="form-control" name="mem_email" id="mem_email" placeholder="이메일">
    </div>
  </div>
  
  
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <button type="submit" class="btn btn-default">인증번호 전송</button>
    </div>
  </div>
</div>
</form>
</body>
</html>