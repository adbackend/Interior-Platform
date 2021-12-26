<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.min.css">
<script  type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
<script  type="text/javascript" src="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.css"></script>
<style type="text/css">

#adminModify{
	margin-top:400;
	width:50%;
	margin:0 auto;
	padding: 85px 0;
}

/* table tr:last-child{ */
/* 	display:none; */
/* } */
</style>
<script type="text/javascript">
	function check_pw(){
		
		var mem_passwd = document.getElementById('mem_passwd').value;
		
		if(mem_passwd.length<4 || mem_passwd.length>12){
			window.alert('비밀번호는 4~12자만 입력해주세요!');
			document.getElementById('mem_passwd').val()='';
		}
		
		if(document.getElementById('mem_passwd').value!='' && document.getElementById('mem_passwd2').value!=''){
			if(document.getElementById('mem_passwd').value==document.getElementById('mem_passwd2').value){
				document.getElementById('check').innerHTML='비밀번호가 일치 합니다.';
				document.getElementById('check').style.color='blue';
			}else{
				document.getElementById('check').innerHTML='비밀번호가 일치하지 않습니다.';
				document.getElementById('check').style.color='red';
			}
		}
		
	}
	
</script>
<form id="adminModify" action="${pageContext.request.contextPath}/admin/adminInfoPwUpdateAction.do" method="post">
<table class="table">
  <h2>변경할 비밀번호 입력</h2>
    <tr>
      <th scope="row">아이디</th>
      <td>${vo.mem_id}</td>
    </tr>
    
<!--     <tr> -->
<!--       <th scope="row">비밀번호</th> -->
<%--       <td><input type="text" name="mem_name" id="mem_name" value="${vo.mem_name}"/></td> --%>
<!--     </tr> -->

    <tr>
      <th scope="row">비밀번호</th>
      <td><input type="password" name="mem_passwd" id="mem_passwd" onchange="check_pw()"/></td>
    </tr>
    
    <tr>
      <th scope="row">비밀번호 확인</th>
      <td><input type="password" id="mem_passwd2" onchange="check_pw()"/> &nbsp;<span id="check"></span></td>
    </tr>

</table>
  <input type="submit" class="btn btn-primary" value="수정완료" />
  <input type="button" class="btn btn-primary" value="취소" onclick="location.href='${pageContext.request.contextPath}/admin/adminInfoForm.do'"/>
  
  <input type="hidden" name="mem_num" value="${vo.mem_num}"/>
  <input type="hidden" name="mem_id" value="${vo.mem_id}" />
  
</form>
