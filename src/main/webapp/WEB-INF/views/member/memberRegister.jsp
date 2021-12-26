<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.min.css">
<script  type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
<script  type="text/javascript" src="${pageContext.request.contextPath}/resources/css/bootstrap/bootstrap.css"></script>
<style type="text/css">
h1{
margin-top:80px;
text-align:center;
}
.page-main{
justify-content:center;
}	
.form-group{
margin-left:80px;
}
.mid_form{
/* display:flex; */
justify-content:center;
width:100%;
width:700px;
margin : 0 auto;
}
.button{
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
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		var checkId = 0;
		
		//아이디 중복 체크
		$('#confirmId').click(function(){
			if($('#mem_id').val().trim()==''){
				$('#message_id').css('color','red').text('아이디를 입력하세요.');
				$('#mem_id').val('').focus();//공백이 있으면 공백을 지우고 포커스를 줌
				return;
			}	
			
		$.ajax({
			url:'confirmId.do',
			type:'post',
			data:{mem_id:$('#mem_id').val()},
			dataType:'json',
			cache:false,
			timeout:30000,
			success:function(param){
				if(param.result == 'idNotFound'){
					$('#message_id').css('color','#000').text('등록가능한 ID입니다.');
					checkId = 1;
				}else if(param.result == 'idDuplicated'){
					$('#message_id').css('color','red').text('중복된 ID입니다.');
					$('#mem_id').val('').focus();
					checkId = 0;
				}else if(param.result == 'notMatchPattern'){
					$('#message_id').css('color','red').text('영문,숫자 4자이상 12자이하 입력');
					$('#mem_id').val('').focus();
					checkId = 0;
				}else{
					checkId = 0;
					alert('ID중복체크 오류입니다.');
				}
			},
			error:function(){
				checkId = 0;
				alert('네트워크 오류 발생');
			}
		}); //end if ajax
		}); //end if click
		
		//아이디 중복 안내 메시지 초기화 및 아이디 중복 값 초기화
		$('#register_form #mem_id').keydown(function(){
			checkId = 0;
			$('#message_id').text('');
		});
		
		//submit 이벤트 발생시 아이디 중복 체크 여부 확인
		$('#register_form').submit(function(){
			if(checkId==0){
				$('#message_id').css('color','red').text('아이디 중복 체크 필수');
				if($('#mem_id').val().trim()==''){
					$('#mem_id').val('').focus();
				}
				return false;
			}
			
			var temp1= $("input[name='YN1']:checked").val();
			if(temp1=='N'){
				alert('회원가입 약관 미동의 시 가입 불가');
				return false;
			}
			
			var temp2 =$("input[name='YN2']:checked").val();
			if(temp2=='N'){
				alert('개인정보 수집 및 이용 미동의 시 가입 불가');
				return false;
			}
		});
		
	});
</script>        
<!-- 중앙 내용 시작 -->
<div class="page-main">
	<h1>회원가입</h1><br><br>
	<form:form id="register_form" action="registerUser.do" modelAttribute="memberVO">
		<div class="mid_form">
		<ul>
			<li>
				<label for="mem_id" class="col-sm-2 col-form-label">아이디</label>&nbsp;&nbsp;&nbsp;&nbsp;
				<form:input path="mem_id" class="form-control" placeholder="4~12자 영문,숫자만 허용" /><br>
				<button type="button" id="confirmId" class="btn btn-primary">ID중복체크</button>
				<span id="message_id"></span>
				<form:errors path="mem_id" cssClass="error-color"/>
			</li>
			<li>
				<label for="mem_name" class="col-sm-2 col-form-label">이름</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<form:input path="mem_name" class="form-control"/>
				<form:errors path="mem_name" cssClass="error-color"/>
			</li>
			<li>
				<label for="mem_passwd" class="col-sm-2 col-form-label">비밀번호</label>
				<form:password path="mem_passwd" class="form-control" placeholder="4~12자 영문,숫자만 허용"/>
				<form:errors path="mem_passwd" cssClass="error-color"/>
			</li>
			<li>
				<label for="mem_phone" class="col-sm-2 col-form-label">전화번호</label>
				<form:input path="mem_phone" class="form-control"/>
				<form:errors path="mem_phone" cssClass="error-color"/>
			</li>
			<li>
				<label for="mem_email" class="col-sm-2 col-form-label">이메일</label>&nbsp;&nbsp;&nbsp;&nbsp;
				<form:input path="mem_email" class="form-control"/>
				<form:errors path="mem_email" cssClass="error-color"/>
			</li>
			<li>
				<label for="mem_zipcode" class="col-sm-2 col-form-label">우편번호</label>
				<form:input path="mem_zipcode" class="form-control"/>
				<form:errors path="mem_zipcode" cssClass="error-color"/><br>
				<button type="button" class="btn btn-primary" onclick="sample4_execDaumPostcode()">우편번호 찾기</button>
			</li>
			<li>
				<label for="mem_address1" class="col-sm-2 col-form-label">지번 주소</label>
				<form:input path="mem_address1" class="form-control"/>
				<form:errors path="mem_address1" cssClass="error-color"/>
			</li>
			<li>
				<label for="mem_address2" class="col-sm-2 col-form-label">상세 주소</label>
				<form:input path="mem_address2" class="form-control"/>
				<form:errors path="mem_address2" cssClass="error-color"/>
			</li>
		</ul>
		</div>
		<div class="form-group">
				<b><label for="provision" class="col-lg-2 control-label">회원가입약관</label></b>
				<div class="col-lg-10" id="provision">
					<textarea class="form-control" rows="8" style="resize: none">약관동의
내일의집 서비스 이용 약관
제 1조 (목적) 내일의집 서비스 이용약관(이하 “약관”이라 합니다)은 주식회사 버킷플레이스(이하 “회사”라 합니다)가 제공하는 서비스와 관련하여 회사와 이용 고객(또는 회원) 간에 서비스의 이용 조건 및 절차, 회사와 회원 간의 권리, 의무 및 책임 사항 기타 필요한 사항을 규정함을 목적으로 합니다.

제 2조 (용어) 본 약관에서 사용하는 용어의 정의는 다음 각 호와 같으며, 정의되지 않은 용어에 대한 해석은 관계법령 및 서비스별 안내에서 정하는 바에 따릅니다.
1. 내일의집 서비스(이하 “서비스”라 합니다): 이용 고객 또는 회원이 PC, 휴대형 단말기, 태블릿PC 등 각종 유무선 기기 또는 프로그램을 통하여 이용할 수 있도록 회사가 제공하는 인테리어 관련 컨텐츠 및 전문가 큐레이션 서비스, SNS 및 그 외 관련된 서비스를 말합니다.
2. 회원: 회사의 서비스에 접속하여 본 약관에 동의하고 ID와 PASSWORD를 발급 받았으며 회사가 제공하는 서비스를 이용하는 고객을 포괄적으로 의미합니다.
3. 전문가: 인테리어 관련 서비스나 제품을 판매하는 직종에 종사하는 회원 중 회사가 지정한 절차 및 검수를 마치고 해당 회원의 서비스와 제품에 대한 정보 및 상담을 제공할 수 있는 회원을 의미합니다. 본 약관에서 별도로 전문가를 언급하지 않는 경우에는 모든 약관의 조항들이 전문가에게도 회원과 동일하게 적용됩니다.
4. 회원정보: 회사가 가입신청자에게 회원가입 신청양식(이하 “신청양식"이라 합니다)에 기재를 요청하는 가입신청자의 개인정보와 회원의 식별과 서비스 이용을 위하여 회원이 입력하고 서비스 내 공개된 개인정보를 의미합니다.
5. 전문가 정보: 전문가가 되기 위해 회사가 지정한 절차 및 검수 과정에서 기재하는 전문가의 정보를 의미합니다.
6. ID(고유번호): 회원 식별과 회원의 본 서비스 이용을 위하여 회원이 선정하고 회사가 승인하는 문자와 숫자의 조합을 말합니다. 본 서비스에서는 E-mail 주소를 ID로 사용합니다.
7. PASSWORD(비밀번호): 회원의 정보 보호를 위해 회원 자신이 설정한 문자와 숫자의 조합을 말합니다.
8. 게시물: 회원이 회사가 제공하는 서비스에 게시 또는 등록하는 부호(URL 포함), 문자, 음성, 음향, 영상(동영상 포함), 이미지(사진 포함), 파일 등 일체의 정보를 말합니다.

제3조 (약관의 효력 및 변경)
① 본 약관은 회원이 쉽게 알 수 있도록 서비스 내 또는 연결화면을 통하여 게시하거나 기타의 방법으로 회원에게 공지함으로써 효력이 발생합니다.
② 회사는 약관의 규제에 관한 법률, 정보통신망 이용촉진 및 정보보호 등에 관한 법률 등 관계법령에 위배되지 않는 범위 내에서 본 약관을 개정할 수 있습니다.
③ 회사는 약관을 개정할 경우 그 개정이유 및 적용일자를 명시하여 현행 약관과 함께 적용일자 7일전부터 적용일 전일까지 제1항의 방법으로 공지합니다. 다만, 회원의 권리 또는 의무에 관한 중요한 규정의 변경은 최소한 30일전에 공지하고 개정약관을 회원이 등록한 E-mail로 발송하여 통지합니다.
④ 회사가 제3항에 따라 개정약관을 공지 또는 통지하였음에도 불구하고 회원이 명시적으로 거부의사를 표시하지 아니하는 경우 회원이 개정약관에 동의한 것으로 봅니다.
⑤ 회원은 변경된 약관에 동의하지 아니하는 경우 서비스의 이용을 중단하고 이용계약을 해지할 수 있습니다.
⑥ 본 약관에 동의하는 것은 서비스를 정기적으로 방문하여 약관의 변경사항을 확인하는 것에 동의함을 의미합니다. 변경된 약관에 대한 정보를 알지 못하여 발생하는 회원의 피해에 대하여 회사는 책임을 지지 않습니다.

제4조 (약관 외 준칙) 본 약관에 명시되지 않은 사항에 대해서는 정보통신망 이용촉진 및 정보보호 등에 관한 법률, 약관의 규제에 관한 법률, 전기통신사업법 등 관계법령 및 회사가 정한 서비스의 세부이용지침 등의 규정에 따릅니다.

제5조 (이용계약의 성립)
① 서비스 이용계약은 회사가 정한 가입 양식에 따라 회원정보(전자우편주소, 비밀번호, 별명 등)를 기입하여 회원가입신청을 하고 회사가 이러한 신청에 대하여 승인함으로써 체결됩니다.
② 전문가 전환의 경우에는 인테리어 관련 서비스나 제품을 판매하는 직종에 종사하는 회원이 회사가 정한 절차에 따라 정보를 기입하여 전문가 전환 신청을 하고 회사가 해당 전문가 전환 신청을 승인하면 전문가로서 서비스를 이용할 수 있습니다.
③ 가입신청자가 회원정보를 제출하는 것은 회원가입 화면에 노출되어 있는 본 약관 및 개인정보처리방침의 내용을 숙지하고, 회사가 서비스 이용을 위해 운영하는 각종 정책(저작권 정책, 운영 방침 등)과 수시로 공지하는 사항을 준수하는 것에 대해 동의하는 것으로 봅니다.

제6조 (이용신청에 대한 승낙 및 제한)
① 회사는 서비스의 이용을 신청한 가입신청자에 대하여 업무상ㆍ기술상 지장이 없는 한 접수순서에 따라 서비스의 이용을 승낙합니다.
② 타인의 개인정보를 도용하는 등 부정한 목적 또는 방법으로 이용신청을 한 회원의 ID는 사전 통지 없이 이용 계약이 해지될 수 있으며, 당해 회원은 관계법령에 따라 처벌을 받을 수 있습니다.
③ 회사는 다음 각 호에 해당하는 이용신청에 대하여는 승낙을 하지 않을 수 있습니다.
1. 기술상 서비스 제공이 불가능한 경우
2. 신청양식의 기재사항을 누락하거나 오기하여 신청하는 경우
3. 사회의 안녕질서 또는 미풍양속을 저해하거나 저해할 목적으로 신청한 경우
4. 회원의 귀책사유에 의하여 회원자격을 상실한 적이 있는 경우. 다만, 자격상실 이후 6개월 이상 경과한 자로 회사의 회원 재가입 승낙을 받은 경우는 예외로 합니다.
5. 기타 회사가 정한 이용요건에 충족되지 않았을 경우
④ 회사는 서비스를 이용하는 회원에 대하여 회원의 종류 및 등급별로 구분하여 이용시간, 이용회수, 서비스 메뉴 등을 세분하여 이용에 차등을 둘 수 있습니다.
⑤ 회사는 전문가 전환을 신청한 회원에 대하여 회사의 업무상ㆍ기술상 또는 서비스 운영정책상 전문가 전환 신청을 승낙하지 않을 수 있습니다.

제7조 (회원정보의 변경)
① 회원은 서비스를 통하여 언제든지 본인의 개인정보를 열람하고 수정할 수 있습니다.
② 회원은 서비스를 이용하면서 회사에 제출한 회원정보가 변경되었을 경우 개인정보 설정 화면에서 회원정보를 수정하거나 고객센터를 통하여 회사에 변경 사항을 통지하여야 합니다.
③ 회원정보를 수정하지 않음으로 인하여 발생하는 모든 책임은 회원에게 있습니다.
④ 전문가가 회사에 제출한 전문가 정보를 변경했을 경우, 회사가 실시하는 변경된 정보에 대해 검수를 통과해야만 변경된 정보가 서비스에 반영됩니다.

제8조 (서비스의 이용)
① 회사는 회원의 이용신청을 승낙한 때부터 서비스를 개시합니다.
② 회사의 업무상ㆍ기술상의 장애로 인하여 서비스를 개시하지 못하는 경우에는 서비스에 공지하거나 회원에게 이를 통지합니다.
③ 서비스의 이용은 연중무휴 1일 24시간을 원칙으로 합니다. 다만, 회사의 업무상ㆍ기술상 또는 서비스 운영정책상 서비스가 일시 중지될 수 있습니다. 이러한 경우 회사는 사전 또는 사후에 이를 공지합니다.
④ 회사는 서비스를 일정범위로 분할하여 각 범위 별로 이용 가능한 시간을 별도로 정할 수 있으며 이 경우 그 내용을 공지합니다.
⑤ 회사는 서비스 내의 개별서비스에 대한 별도의 약관을 둘 수 있으며, 개별서비스에서 별도로 적용되는 약관에 대한 동의는 회원이 개별서비스를 최초로 이용할 경우 별도의 동의절차를 거치게 됩니다.

제9조 (서비스의 변경 및 중지)
① 회사는 서비스(개별서비스 포함)를 변경하여야 하는 상당한 이유가 있는 경우 변경될 서비스의 내용 및 제공일자를 제15조에서 정한 방법으로 회원에게 통지하고 서비스를 변경하여 제공할 수 있습니다.
② 회사는 다음 각 호에 해당하는 경우 서비스의 전부 또는 일부를 제한하거나 중지할 수 있습니다.
1. 서비스용 설비의 보수 등 공사로 인한 부득이한 경우
2. 회원이 회사의 영업활동을 방해하는 경우
3. 정전, 제반 설비의 장애 또는 이용량의 폭주 등으로 정상적인 서비스 이용에 지장이 있는 경우
4. 제휴업체(BP)와의 계약종료 등과 같은 회사의 제반 사정으로 서비스를 유지할 수 없는 경우
5. 기타 천재지변, 국가비상사태 등 불가항력적 사유가 있는 경우

제10조 (정보의 제공 및 광고의 게재)
① 회사는 서비스를 운영함에 있어 각종 정보를 서비스 화면에 게재하거나 E-mail, 서신우편, SMS(MMS)등으로 회원에게 제공할 수 있습니다.
② 회사는 서비스를 운영함에 있어 회사 또는 제휴사의 서비스 관련 각종 광고를 서비스 화면 또는 게시물과 결합하여 게재하거나 회원의 동의를 얻어 E-mail 및 서신 우편, SMS(MMS) 등의 방법으로 회원에게 제공할 수 있습니다.
③ 회원이 서비스상에 게재되어 있는 광고를 이용하거나 서비스를 통한 광고주의 판촉활동에 참여하는 등의 방법으로 교신 또는 거래를 하는 것은 전적으로 회원과 광고주간의 문제입니다. 만약, 회원과 광고주간에 문제가 발생할 경우에도 회원과 광고주가 직접 해결하여야 하며, 이와 관련하여 회사는 어떠한 책임도 지지 않습니다.
④ 회사는 다음 각호에 해당하는 경우 회원의 동의여부와 상관없이 전자우편으로 발송할 수 있습니다.
1. 이용 신청에서 입력한 전자우편 주소의 소유를 확인하기 위해서 인증메일을 발송하는 경우
2. 회원의 정보가 변경되어 확인하기 위해서 인증메일을 발송하는 경우
3. 기타 서비스를 제공함에 있어 회원이 반드시 알아야 하는 중대한 정보라고 회사가 판단하는 경우

제11조 (게시물에 대한 책임)
① 회사는 회원이 게시하거나 전달하는 서비스 내의 게시물이 다음 각 호의 경우에 해당한다고 판단되는 경우 사전 통지 없이 삭제할 수 있으며, 이에 대해 회사는 어떠한 책임도 지지 않습니다.
1. 회사, 다른 회원 또는 제3자를 비방하거나 중상모략으로 명예를 손상시키는 내용인 경우
2. 공공질서 및 미풍양속에 위반되는 내용의 게시물에 해당하는 경우
3. 범죄 행위에 결부된다고 인정되는 내용인 경우
4. 회사 또는 제3자의 저작권, 기타 타인의 권리를 침해하는 내용인 경우
5. 회사가 정한 개별 서비스 별 세부이용지침에 반하는 내용인 경우
6. 회사에서 제공하는 서비스와 관련 없는 내용인 경우
7. 불필요하거나 승인되지 않은 광고, 판촉물을 게재하는 경우
8. 타인의 명의 등을 무단으로 도용하여 작성한 내용이거나, 타인이 입력한 정보를 무단으로 위ㆍ변조한 내용인 경우
9. 동일한 내용을 중복하여 다수 게시하는 등 게시의 목적에 어긋나는 경우
10. 게시물의 정보를 외부 서비스에서 사용하는 행위를 금지하는 사이트에서 URL 정보를 수집하여 게재하는 경우
11. 기타 관계 법령 및 회사의 개별 서비스 별 세부이용지침 등에 위반된다고 판단되는 경우
② 회사는 개별 서비스 별로 게시물과 관련된 세부이용지침을 별도로 정하여 시행할 수 있으며, 회원은 그 지침에 따라 게시물(회원간 전달 포함)을 게재하여야 합니다.
③ 전문가가 아니면서 인테리어 관련 직종에 종사하는 회원이 해당 회원의 서비스와 제품에 대한 정보가 담긴 게시물을 서비스 내에 게재할 경우에는 회사는 해당 게시물을 사전 통지 없이 삭제할 수 있으며, 이에 대해 회사는 어떤 책임도 지지 않습니다.

제12조 (게시물의 저작권 등)
① 회원이 서비스 내에 게시한 게시물의 저작권은 저작권법에 의해 보호를 받으며, 회사가 작성한 저작물에 대한 저작권 및 기타 지적재산권은 회사에 귀속합니다.
② 회원은 자신이 서비스 내에 게시한 게시물을 회사가 국내ㆍ외에서 다음 각 호의 목적으로 사용하는 것을 허락합니다.
1. 서비스(제3자가 운영하는 사이트 또는 미디어의 일정 영역 내에 입점하여 서비스가 제공되는 경우 포함) 내에서 게시물을 사용하기 위하여 게시물의 크기를 변환하거나 단순화하는 등의 방식으로 수정하는 것
2. 회사 또는 관계사가 운영하는 본 서비스 및 연동 서비스 에 게시물을 복제ㆍ전송ㆍ전시하는 것. 다만, 회원이 게시물의 복제ㆍ전송ㆍ전시에 반대 의견을 E-mail을 통해 관리자에게 통지할 경우에는 그러하지 않습니다.
3. 회사의 서비스를 홍보하기 위한 목적으로 미디어, 소셜미디어를 포함한 디지털 마케팅 채널, 통신사 등에게 게시물의 내용을 보도, 방영하게 하는 것.
③ 전 항의 규정에도 불구하고, 회사가 게시물을 전 항 각 호에 기재된 목적 이외에 제3자에게 게시물을 제공하고 금전적 대가를 지급받는 경우에는 사전에 전화, E-mail 등의 방법으로 회원의 동의를 얻습니다. 이 경우 회사는 회원에게 별도의 보상을 제공합니다.
④ 회원이 서비스에 게시물을 게재하는 것은 다른 회원이 게시물을 서비스 내에서 사용하거나, 회사가 검색결과로 사용하는 것을 허락한 것으로 봅니다. 그리고 스마트폰, 태블릿 PC의 서비스 이용자(앱 또는 브라우저로 서비스를 비가입 방문한 경우도 포함)가 소프트웨어(예:앱, 브라우저) 또는 하드웨어(예: 스마트폰, 태블릿PC)에서 제공하는 기능을 이용하여 게시물을 저장한 후 활용하는 것을 허락한 것으로 봅니다.
⑤ 제18조에 의해 이용계약이 해지되는 경우 회원이 서비스에 게시한 게시물은 삭제됩니다. 다만, 다른 회원 또는 제3자에게 의하여 스크랩, 공유 등의 기능을 통해 다시 게시된 게시물 및 댓글 등 다른 회원의 정상적인 서비스 이용에 필요한 게시물은 삭제되지 않습니다.
⑥ 회사는 서비스 운영정책상 또는 회사가 운영하는 사이트간의 통합 등을 하는 경우 게시물의 내용을 변경하지 아니하고 게시물의 게재 위치를 변경ㆍ이전하거나 사이트간 공유로 하여 서비스할 수 있습니다. 다만, 게시물의 이전ㆍ변경 또는 공유를 하는 경우에는 사전에 공지합니다.

제13조 (회사의 의무)
① 회사는 서비스 제공과 관련하여 알고 있는 회원의 회원정보를 본인의 동의 없이 제3자에게 제공하지 않습니다.
② 회사는 회원의 회원정보를 보호하기 위해 보안시스템을 구축 운영하며, "개인정보처리방침"을 공지하고 준수합니다. 또한, 회사는 "개인정보처리방침"에 따라 회원정보를 처리함에 있어 안정성 확보에 필요한 기술적 및 관리적 대책을 수립ㆍ운영합니다.
③ 회사는 서비스와 관련한 회원의 불만사항이 접수되는 경우 이를 신속하게 처리하여야 하며, 신속한 처리가 곤란한 경우 그 사유와 처리 일정을 서비스 화면에 게재하거나 E-mail 등을 통하여 회원에게 통지합니다.
④ 회사가 제공하는 서비스로 인하여 회원에게 손해가 발생한 경우 그러한 손해가 회사의 고의나 중과실에 의해 발생한 경우에 한하여 회사에서 책임을 부담하며, 그 책임의 범위는 통상손해에 한합니다.

제14조 (회원의 의무)
① 회원은 관계법령, 약관, 서비스 이용안내 및 서비스상에 공지한 주의사항, 회사가 서비스 이용과 관련하여 회원에게 통지하는 사항 등을 준수하여야 하며, 기타 회사의 업무에 방해되는 행위를 하여서는 아니 됩니다.
② 회원은 회사에서 공식적으로 인정한 경우를 제외하고는 서비스를 이용하여 상품을 판매하는 영업 활동을 할 수 없으며, 특히 해킹, 광고를 통한 수익, 음란사이트를 통한 상업행위, 상용소프트웨어 불법배포 등을 할 수 없습니다. 이를 위반하여 발생한 영업 활동의 결과 및 손실, 관계기관에 의한 구속 등 법적 조치 등에 관해서는 회사가 책임을 지지 않으며, 회원은 이와 같은 행위와 관련하여 회사에 대하여 손해배상 의무를 집니다.
③ 회원은 서비스의 이용권한, 기타 서비스 이용계약상의 지위를 타인에게 양도, 증여할 수 없으며 이를 담보로 제공할 수 없습니다.

제15조 (회원에 대한 통지)
① 회사는 회원의 서비스 이용에 필요한 권리 및 의무 등에 관한 사항을 회원이 지정한 E-mail, SMS 등으로 통지할 수 있습니다.
② 회사는 불특정 다수 회원에 대한 통지의 경우 서비스에 게시함으로써 개별 통지에 갈음할 수 있습니다.

제16조 (ID와 PASSWORD 관리에 대한 의무와 책임)
① 회원은 자신의 ID와 PASSWORD 관리를 철저히 하여야 합니다. ID와 PASSWORD의 관리 소홀, 부정 사용에 의하여 발생하는 모든 결과에 대한 책임은 회원 본인에게 있습니다.
② 회사"는 회원 ID의 유출 우려가 있거나, 반사회적 또는 미풍양속에 어긋나거나 회사 및 회사의 운영자로 오인할 우려가 있는 경우, 해당 ID의 이용을 제한할 수 있습니다.
③ 회원은 본인의 ID 및 PASSWORD를 타인에게 이용하게 해서는 안되며, 회원 본인의 ID 및 PASSWORD를 도난 당하거나 타인이 사용하고 있음을 인지하는 경우에는 바로 회사에 통보하고 회사의 안내가 있는 경우 그에 따라야 합니다.

                  </textarea>
					<div class="radio">
						<label> 
						<input type="radio" id="YN1" name="YN1" value="Y" autofocus="autofocus" checked>동의합니다.
						</label>
					</div>
					
					<div class="radio">
						<label> 
						<input type="radio" id="YN1" name="YN1" value="N"> 동의하지 않습니다.
						</label>
					</div>
				</div>
			</div>
			<br>
			<div class="form-group">
				<b><label for="memberInfo" class="col-lg-2 control-label">개인정보취급방침</label></b>
				<div class="col-lg-10" id="memberInfo">

<textarea class="form-control" rows="8" style="resize: none">개인정보의 항목 및 수집방법
가. 개인정보의 수집방법 및 수집항목
1. 수집방법 - 회원가입(필수)

나. 수집하는 개인정보의 항목
① 닉네임, 이메일 주소, 비밀번호

다. 개인정보의 보유 및 이용기간
회원탈퇴 및 목적달성 후 지체없이 삭제합니다.<br>

단, 전자상거래 등에서의 소비자보호에 관한 법률 등 관련 법령의 규정에 따라 거래 관계 확인을 위해 개인정보를 일정기간 보유 할 수 있습니다.<br>

또한 부정이용 방지를 위하여 회원 탈퇴 후에도 구매 인증 시 입력한 정보는 6개월 동안 보관합니다.
라. 동의 거부 권리 및 동의 거부에 따른 불이익
위 개인정보의 수집 및 이용에 대한 동의를 거부할 수 있으나, 동의를 거부할 경우 회원 가입이 제한됩니다.
             </textarea>
					<div class="radio">
						<label> 
						<input type="radio" id="YN2"name="YN2" value="Y" checked> 동의합니다.
						</label>
					</div>
					<div class="radio">
						<label> 
						<input type="radio" id="YN2" name="YN2" value="N"> 동의하지 않습니다.
						</label>
					</div>
				</div>
			</div>
		<div class="button">
			 <button type="submit" class="btn btn-primary">회원가입</button>
		</div><br><br>
		</div>
	</form:form>
</div>
<!-- 중앙 내용 끝 -->



