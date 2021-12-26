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
<script>
    function sample4_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 참고 항목 변수

                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('mem_zipcode').value = data.zonecode;
                document.getElementById("mem_address1").value = roadAddr;
                
                if(roadAddr !== ''){
                    document.getElementById("mem_address2").value = extraRoadAddr;
                } else {
                    document.getElementById("mem_address2").value = '';
                }
                

                var guideTextBox = document.getElementById("guide");
                // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
                if(data.autoRoadAddress) {
                    var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                    guideTextBox.innerHTML = '(예상 도로명 주소 : ' + expRoadAddr + ')';
                    guideTextBox.style.display = 'block';

                } else if(data.autoJibunAddress) {
                    var expJibunAddr = data.autoJibunAddress;
                    guideTextBox.innerHTML = '(예상 지번 주소 : ' + expJibunAddr + ')';
                    guideTextBox.style.display = 'block';
                } else {
                    guideTextBox.innerHTML = '';
                    guideTextBox.style.display = 'none';
                }
            }
        }).open();
    }
</script>
<!-- 중앙 내용 시작 -->
<div class="page-main">
	<h2>회원정보수정</h2><br><br>
	<form:form id="modify_form" action="update.do" modelAttribute="memberVO">
		<div class="mid_form">
		<ul>
			<li>
				<label for="mem_name">이름 :</label>
				<form:input path="mem_name" class="form-control"/>
				<form:errors path="mem_name" cssClass="error-color"/><br>
			</li>
			<li>
				<br><label for="mem_phone">전화번호 :</label>
				<form:input path="mem_phone" class="form-control"/>
				<form:errors path="mem_phone" cssClass="error-color"/><br>
			</li>
			<li>
				<br><label for="mem_email">이메일 :</label>
				<form:input path="mem_email" class="form-control"/>
				<form:errors path="mem_email" cssClass="error-color"/><br>
			</li>
			<li>
				<br><label for="mem_zipcode">우편번호 :</label>
				<form:input path="mem_zipcode" class="form-control"/>
				<form:errors path="mem_zipcode" cssClass="error-color"/><br>
				<button type="button" class="btn btn-primary" onclick="sample4_execDaumPostcode()">우편번호 찾기</button><br>
			</li>
			<li>
				<br><label for="mem_address1">주소 :</label>
				<form:input path="mem_address1" class="form-control"/>
				<form:errors path="mem_address1" cssClass="error-color"/><br>
			</li>
			<li>
				<br><label for="mem_address2">상세 주소 :</label>
				<form:input path="mem_address2" class="form-control"/>
				<form:errors path="mem_address2" cssClass="error-color"/>
			</li>
		</ul>
		</div><br><br>
		<div class="button">
			<form:button class="btn btn-primary">수정하기</form:button>
		</div><br><br>
	</form:form>
</div>
<!-- 중앙 내용 끝 -->



