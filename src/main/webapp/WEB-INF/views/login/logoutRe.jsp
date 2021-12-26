<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
.logout-main{
	text-align:center;
	font-size:40px;
	margin-top:130px;
}

</style>
<script type="text/javascript">



alert('로그아웃 되었습니다.');
window.history.forward();
function noBack() {
	window.history.forward();
}

// if (self.name != 'reload') {
// alert('로그아웃 되었습니다.');
//     self.name = 'reload';
//     self.location.reload(true);
// }
// else self.name = ''; 

</script>

</head>
<body>
<div class="logout-main">
<img src="${pageContext.request.contextPath}/resources/images/logout2.png" width="210" height="210">
<br><br>	로그아웃이 정상 처리 되었습니다. <br><h4>내일의 집을 방문해주셔서 감사합니다.</h4>
</div>
</body>
</html>

