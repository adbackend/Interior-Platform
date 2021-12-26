<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>안내</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/template/shop_template.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.min.css"> 
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
</head>
<body>
	<!-- 헤더 -->
	<jsp:include page="/WEB-INF/views/template/shop/header.jsp"/>
	<!-- 메인  -->
	<div id="main-width">
		<div class="card my-5">
			<div class="card-body text-center my-5">
				<h2 class="card-title text-danger">잘못된 접근입니다!</h2>
				<div class="card-text my-4">
					요청하신 페이지가 존재하지 않거나 잘못된 접근입니다.<br>
					다시 확인하시고 이용해 주시기 바랍니다.
				</div>
				<input type="button" value="메인으로" onclick="location.href='${pageContext.request.contextPath}/shop/main.do'" class="btn btn-primary">
				<input type="button" value="이전 페이지로" onclick="history.go(-1)" class="btn btn-secondary">			
			</div>
		</div>
	</div>
</body>
</html>