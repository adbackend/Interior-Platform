<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> 
<style type="text/css">
h2{
text-align:center;
margin-top:120px;
}
.button{
display:flex;
justify-content:center;
width:100%;
}
.mid_form{
display:flex;
	justify-content:center;
	width:100%;
}
ul{
   list-style:none;
}
</style>    
<!-- 중앙 내용 시작 -->
<div class="page-main">
	<h2>회원탈퇴</h2><br><br>
	<form:form id="delete_form" action="delete.do" modelAttribute="memberVO">
		<form:errors element="div" cssClass="error-color"/>
		<div class="mid_form">
		<ul>
			<li>
				<label for="mem_id">아이디</label>
				<form:input path="mem_id" class="form-control" placeholder="4~12자 영문,숫자만 허용"/>
				<form:errors path="mem_id" cssClass="error-color"/><br><br>
			</li>
			<li>
				<label for="mem_passwd">비밀번호</label>
				<form:password path="mem_passwd" class="form-control" placeholder="4~12자 영문,숫자만 허용"/>
				<form:errors path="mem_passwd" cssClass="error-color"/>
			</li>
		</ul>
		</div><br>
		<div class="button">
			<form:button class="btn btn-primary">탈퇴하기</form:button>
		</div><br><br>
	</form:form>
</div>
<!-- 중앙 내용 끝 -->



