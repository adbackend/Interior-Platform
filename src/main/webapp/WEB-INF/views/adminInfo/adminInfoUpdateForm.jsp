<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.min.css">
<script  type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
<script  type="text/javascript" src="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.css"></script>

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
<style>

#adminModify{
	width:50%;
	margin:0 auto;
	padding: 85px 0;
}

table tr:last-child{
	display:none;
}

</style>
<script type="text/javascript">
$(document).ready(function(){
	$('#adminModify').submit(function(){
		
		if($('#mem_name').val().trim()==''){
			alert('이름을 입력하세요');
			$('#mem_name').focus();
			$('#mem_name').val('');
			return false;
		}
        var n_RegExp = /^[가-힣]{2,15}$/; //이름 유효성검사 정규식
        
        if(!n_RegExp.test($('#mem_name').val())){
            alert("이름은 한글만 입력해주세요.(특수문자,영어,숫자 사용불가)");
            return false;
        }
        
        
        


		if($('#mem_phone').val().trim()==''){
			alert('휴대폰번호를 입력하세요');
			$('#mem_phone').focus();
			$('#mem_phone').val('');
			return false;
		}
		
        var patternPhone = RegExmp(/01[016789]-[^0][0-9]{2,3}-[0-9]{3,4}/);
		if(!patternPhone.test($('#mem_phone').val())){ 
			alert('전화번호형식에 맞게 입력해주세요'); 
			$('#mem_phone').val(''); 
			$('#mem_phone').focus(); 
			return false; 
		}
		

		if($('#mem_email').val().trim()==''){
			alert('이메일 입력하세요!');
			$('#mem_email').focus();
			return false;
		}
		
		var getMail = RegExp(/^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/); //이메일 유효성
		if(!getMail.test($('#mem_email').val())){ 
			alert('이메일형식에 맞게 입력해주세요'); 
			$('#mem_email').val(''); 
			$('#mem_email').focus(); 
			return false; 
		}
		
		
		if($('#mem_zipcode').val().trim()==''){
			alert('우편번호를 체크하세요!');
			$('#mem_zipcode').focus();
			return false;
		}
		
		if($('#mem_address1').val().trim()==''){
			alert('주소를 입력하세요!');
			$('#mem_address1').focus();
			return false;
		}
		
		if($('#mem_address2').val().trim()==''){
			alert('상세주소를 입력하세요!');
			$('#mem_address2').focus();
			return false;
		}
		
		
		
	});
});
</script>

<form id="adminModify" action="${pageContext.request.contextPath}/admin/adminInfoUpdateAction.do" method="post">
<table class="table">
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
      <td><input type="text" name="mem_name" id="mem_name" value="${vo.mem_name}"/></td>
    </tr>

    <tr>
      <th scope="row">휴대폰</th>
      <td><input type="text" name="mem_phone" id="mem_phone" value="${vo.mem_phone}"/></td>
    </tr>

    <tr>
      <th scope="row">이메일</th>
      <td><input type="text" name="mem_email" id="mem_email" value="${vo.mem_email}"/></td>
    </tr>

    <tr>
      <th scope="row">우편번호</th>
      <td><input type="text" name="mem_zipcode" id="mem_zipcode" value="${vo.mem_zipcode}" readonly/>&nbsp;
		<button type="button" class="btn btn-outline-dark" onclick="sample6_execDaumPostcode()">우편번호 찾기</button>
      </td>
    </tr>
    <tr>
      <th scope="row">주소</th>
      <td><input type="text" name="mem_address1" id="mem_address1" value="${vo.mem_address1}"/></td>
    </tr>
    <tr>
      <th scope="row">상세주소</th>
      <td><input type="text" name="mem_address2" id="mem_address2" value="${vo.mem_address2}"/></td>
    </tr>
    <tr>
      <th scope="row">가입일</th>
      <td>${vo.mem_date}</td>
    </tr>
    <tr>
      <th scope="row">
      </th>
 		<td>
 		</td>
		
    </tr>
    
  </tbody>
</table>
  <input type="submit" class="btn btn-primary" value="수정완료" />
  
  <input type="hidden" name="mem_num" value="${vo.mem_num}"/>
  <input type="hidden" name="mem_passwd" value="${vo.mem_passwd}" />
  <input type="hidden" name="mem_id" value="${vo.mem_id}" />
  <input type="hidden" name="mem_date" value="${vo.mem_date}" />
  
</form>
  
