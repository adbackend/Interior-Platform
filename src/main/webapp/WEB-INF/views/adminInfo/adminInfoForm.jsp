<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 마이페이지</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.min.css">
<script  type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
<script  type="text/javascript" src="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.css"></script>
<style type="text/css">
#adminInfoForm{
width:600px;
margin-top:80px;
margin-left:480px;
}
	
</style>
<script type="text/javascript">
	
	var session1 = '<%=(Integer)session.getAttribute("mem_num")%>';
	if(session1==null){
		
		window.history.forward();
		function noBack() {
			window.history.forward();
		}
		console.log('오잉오잉');
		console.log(session1+'-----함수안 세션값');
	}
	
	console.log(session1+'함수밖 세션값');
</script>	
</head>
<body>
<table class="table" id="adminInfoForm">
  <tbody>
  
    <tr>
    
      <th scope="row">구분</th>
      <c:if test="${vo.mem_auth==2}">
      	<td>일반회원</td>
      </c:if>
      <c:if test="${vo.mem_auth==3}">
      	<td>일반관리자</td>
      </c:if>
      <c:if test="${vo.mem_auth==4}">
      	<td>최고관리자</td>
      </c:if>
      
    </tr>
    
    <tr>
      <th scope="row">아이디</th>
      <td>${vo.mem_id}</td>
    </tr>
    
    <tr>
      <th scope="row">이름</th>
      <td>${vo.mem_name}</td>
    </tr>

    <tr>
      <th scope="row">휴대폰</th>
      <td>${vo.mem_phone}</td>
    </tr>
    <tr>
      <th scope="row">이메일</th>
      <td>${vo.mem_email}</td>
    </tr>

    <tr>
      <th scope="row">우편번호</th>
      <td>${vo.mem_zipcode}</td>
    </tr>
    <tr>
      <th scope="row">주소</th>
      <td>${vo.mem_address1}</td>
    </tr>
    <tr>
      <th scope="row">상세주소</th>
      <td>${vo.mem_address2}</td>
    </tr>
    <tr>
      <th scope="row">가입일</th>
      <td>${vo.mem_date}</td>
    </tr>
    <tr>
    	<td colspan="2">
  			<input type="hidden" name="mem_num" value="${vo.mem_num}"/>
			<button type="button" class="btn btn-primary" onclick="location.href='adminInfoUpdateForm.do?mem_num=${vo.mem_num}'">수정</button>
			<input type="button" class="btn btn-primary" value="비밀번호 변경" onclick="location.href='adminInfoPasswdUpdate.do?mem_num=${vo.mem_num}'"/>
    	</td>
    </tr>
    
  </tbody>
  
</table>

</body>
</html>