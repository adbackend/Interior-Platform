<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.min.css">
<script  type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
<script  type="text/javascript" src="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.css"></script>
<style type="text/css">
h2{
text-align:center;
margin-top:120px;
}
ul{

justify-content:center;
width:100%;
}	
.button{
display:flex;
justify-content:center;
width:100%;
}
.table {
      border-collapse: collapse;
      border-top: 3px solid #168;
      margin-left:auto; 
      margin-right:auto; 
      width: 1000px;
}  
.table th {
      color: #168;
      background: #f0f6f9;
      text-align: center;
}
.table th, .table td {
      padding: 10px;
      border: 1px solid #ddd;
}
.table th:first-child, .table td:first-child {
      border-left: 0;
}
.table th:last-child, .table td:last-child {
      border-right: 0;
}
.table tr td:first-child{
      text-align: center;
}
.table caption{caption-side: bottom; display: none;}
</style>	
<!-- 중앙 내용 시작 -->
<div class="page-main">
	<h2>회원 상세 정보</h2><br><br>
	<form:form id="mypage_form" action="myPage.do" modelAttribute="memberVO">
	<table class="table">
    
	    <tr><th>이름</th><th>전화번호</th><th>이메일</th><th>우편번호</th><th>주소</th><th>상세주소</th><th>가입날짜</th></tr>
	    <tr><td>${member.mem_name}</td><td>${member.mem_phone}</td><td>${member.mem_email}</td><td>${member.mem_zipcode}</td><td>${member.mem_address1}</td><td>${member.mem_address2}</td><td>${member.mem_date}</td></tr>
  	</table><br><br>

	<hr size="1" width="100%">
	<div class="button">
		<input type="button" value="회원정보수정" class="btn btn-primary" onclick="location.href='update.do'">&nbsp;&nbsp;
		<input type="button" value="회원탈퇴" class="btn btn-primary" id="delete_btn" onclick="location.href='delete.do'">
			<script type="text/javascript">
			var delete_btn = document.getElementById('delete_btn');
			delete_btn.onclick=function(){
				var choice = confirm('탈퇴 하시겠습니까?');
				if(choice){
					location.replace('delete.do?mem_num=${member.mem_num}');
				}
			};
			</script>
	</div>
	</form:form>
</div>
<!-- 중앙 내용 끝 -->
