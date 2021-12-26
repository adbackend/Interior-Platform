<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- CSS file -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/adminPage.css">
<!-- daum 주소 API -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("mem_address1").value = extraAddr;
                
                } else {
                    document.getElementById("mem_address1").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('mem_zipcode').value = data.zonecode;
                document.getElementById("mem_address1").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("mem_address2").focus();
            }
        }).open();
    }
</script>
<!-- 중앙 내용 시작 -->
<div id="admin-main-width">
	<div id="wide-width" class="wide-table">
		<h4 id="header-main">관리자 수정</h4>
		<form:form action="adminUpdate.do" modelAttribute="adminMemberVO" class="form-inline">
			<form:hidden path="mem_num"/>
			<form:hidden path="mem_id"/>
			<form:hidden path="mem_date"/>
			<form:hidden path="mem_zipcode"/>
			<form:hidden path="mem_address1"/>
			<form:hidden path="mem_address2"/>
			<table class="table table-bordered">
				<tr>
					<th>관리자유형</th>
					<td>
						<form:select path="mem_auth" class="form-control form-control-sm">
							<form:option value="3" label="일반관리자"/>
							<form:option value="4" label="최고관리자"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<th>아이디</th>
					<td>${adminMemberVO.mem_id}</td>
				</tr>
				<tr>
					<th>이름</th>
					<td>
						<form:input path="mem_name" class="form-control form-control-sm"/>
						<form:errors path="mem_name" class="text-danger"/>
					</td>
				</tr>
				<tr>
					<th>연락처</th>
					<td>
						<form:input path="mem_phone" class="form-control form-control-sm"/>
						<form:errors path="mem_phone" class="text-danger"/>
					</td>
				</tr>
				<tr>
					<th>이메일</th>
					<td>
						<form:input path="mem_email" class="form-control form-control-sm"/>
						<form:errors path="mem_email" class="text-danger"/>
					</td>
				</tr>
				<tr>
					<th>등록일</th>
					<td>${adminMemberVO.mem_date}</td>
				</tr>
			</table>
			<div class="element-center text-center div-button">
				<form:button class="btn btn-primary">수정</form:button>
				<input type="button" value="취소" class="btn btn-secondary" onclick="location.href='adminList.do'">
			</div>
		</form:form>
	</div>
</div>
<!-- 중앙 내용 끝 -->