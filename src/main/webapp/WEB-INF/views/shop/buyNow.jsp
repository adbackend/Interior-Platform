<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>   
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/order.css">
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script>
    //본 예제에서는 도로명 주소 표기 방식에 대한 법령에 따라, 내려오는 데이터를 조합하여 올바른 주소를 구성하는 방법을 설명합니다.
    function sample4_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 참고 항목 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
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
                document.getElementById('order_zipcode').value = data.zonecode;
                document.getElementById("order_address1").value = roadAddr;
                document.getElementById("sample4_jibunAddress").value = data.jibunAddress;
                
                // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
                if(roadAddr !== ''){
                    document.getElementById("order_address2").value = extraRoadAddr;
                } else {
                    document.getElementById("order_address2").value = '';
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
    
$(document).ready(function(){
	//핸드폰 번호 정규식
	var regExpPhone =/(01[016789])[-]([1-9]{1}[0-9]{2,3})[-]([0-9]{4})$/;
	
	//폼 유효성 체크
	$('#insert_form').submit(function(){
		if($('#order_name').val().trim()==''){
			alert('이름을 입력해주세요!');
			$('#order_name').focus();
			$('#order_name').val('');
			return false;
		}
			
		if($('#order_phone').val().trim()==''){
			alert('연락처를 입력해주세요!');
			$('#order_phone').focus();
			$('#order_phone').val('');
			return false;		
		}else if(!regExpPhone.test($('#order_phone').val().trim())){ //정규식을 이용한 핸드폰번호 유효성 검사
			alert('000-0000-0000 형식으로 입력해주세요.');
			return false;
		}
		
		if($('#order_zipcode').val().trim()==''){
			alert('우편번호를 입력해주세요!');
			$('#order_zipcode').focus();
			$('#order_zipcode').val('');
			return false;
		}
		if($('#order_address1').val().trim()==''){
			alert('주소를 입력해주세요!');
			$('#order_address1').focus();
			$('#order_address1').val('');
			return false;
		}
		if($('#order_address2').val().trim()==''){
			alert('상세주소를 입력해주세요!');
			$('#order_address2').focus();
			$('#order_address2').val('');
			return false;
		}
		
	});
});
</script>
<div class="top_menu_info">
	<div>
	홈 > 주문결제
	</div>
</div>
<div id="main-width">
<form:form id="insert_form" action="orderNow.do" modelAttribute="orderVO" class="form mt-3" >
	<div class="top-order-heading mt-3">
		주문결제
	</div>
	<div class="top-order-step mb-2">
		<span>01 장바구니</span> > <span style="color:#35c5f0;">02 주문결제</span> > <span>03 결제완료</span>
	</div>
	<div class="order-main pt-4 pb-5">
		<div class="order-container-title pb-2">
				주문 상품
		</div>
		<c:set var="order_pay" value="0"/>
			<div class="order-container">
					<div class="order-content">
						<div class="order-img" onclick="location.href='${pageContext.request.contextPath}/shop/productDetail.do?p_no=${product.p_no}'">
							<img src="${pageContext.request.contextPath}/product/photoView.do?p_no=${product.p_no}" width="100" height="100">
						</div>
						<div class="order-name-cart ml-3 pr-2 pt-3">
							<div class="order-name-text">
								${product.p_name}
							</div>
							<div class="order-amount-text">
								수량 : ${cartVO.cart_amount}개
							</div>
							
						</div>
					<div class="order-pay">
						<div id="order-pay-div" class="order-pay-div">
							<c:if test="${product.p_discount != 0}">
								<c:set var="cart_pay" value="${cart_pay+((product.p_price-(product.p_price*product.p_discount/100))*cartVO.cart_amount)}"/>
							<fmt:formatNumber value="${(product.p_price-(product.p_price*product.p_discount/100))*cartVO.cart_amount}" pattern="#,###"/>원	
						</c:if>
						<c:if test="${product.p_discount == 0}">
							<c:set var="cart_pay" value="${cart_pay+(product.p_price*cartVO.cart_amount)}"/>
							<fmt:formatNumber value="${product.p_price*cartVO.cart_amount }" pattern="#,###"/>원
						</c:if>
						</div>
					</div>
				</div>
			</div>
		<div class="order-container-title pb-2 pt-4">
			배송정보
		</div>
		<div class="order-container mt-3">
			<div class="form-group row">
				<label for="order_name" class="col-sm-2 col-form-label">받는 사람</label>
				<div class="col-sm-5">
					<input type="text" class="form-control" id="order_name" name="order_name" value="${member.mem_name}"  placeholder="받으시는 분의 성함을 입력하세요."/>
				</div>
			 </div>
			 <div class="form-group row">
			 	<label for="order_phone" class="col-sm-2 col-form-label">휴대전화</label>
			 	<div class="col-sm-5">
			    	<input type="text" class="form-control" id="order_phone" name="order_phone" value="${member.mem_phone}" placeholder="휴대전화 번호를 입력하세요.">
			    </div>
			</div>
			<div class="form-group row">
			    <label for="order_address" class="col-sm-2 col-form-label">배송지 주소</label>
			    <div class="col-sm-5">
			    	<div class="row g-3 pb-1">
			      		<input type="text" class="form-control col-sm-7" id="order_zipcode" name="order_zipcode" value="${member.mem_zipcode}" placeholder="우편번호를 입력하세요.">
						<button type="button" class="btn btn-outline-dark col-sm-4 ml-1 bt" onclick="sample4_execDaumPostcode()">우편번호 찾기</button>
			      	</div>
			      	<input type="text" class="form-control mb-2" id="order_address1" name="order_address1" value="${member.mem_address1}" placeholder="주소를 입력하세요.">
			      	<input type="text" class="form-control mb-2" id="order_address2" name="order_address2" value="${member.mem_address2}" placeholder="상세주소를 입력하세요.">
			    </div>
			</div>
		</div>
		<div class="order-container-title pb-2 pt-4">
			최종 결제금액
		</div>
		<div class="order-container">
			<div class="order-price-content">
				<div class="order-price-text pl-3">
					총 상품 금액
				</div>
				<div class="order-price-money ml-3">
				<fmt:formatNumber value="${cart_pay}" pattern="#,###"/>원
				</div>
			</div>
			<div class="order-price-content">
				<div class="order-price-text pl-3">
					배송비
				</div>
				<div class="order-price-money ml-3">
					<c:if test="${cart_pay<50000}">
						<c:set var="delivery_pay" value="3000"/>
						<fmt:formatNumber value="${delivery_pay}" pattern="#,###"/>원
					</c:if>
					<c:if test="${cart_pay>=50000}">
						<c:set var="delivery_pay" value="0"/>
						무료
					</c:if>
				</div>
			</div>
			<div class="order-price-content">
				<div class="order-price-text font-weight-bold pl-3">
					총 결제금액
				</div>
				<div class="order-price-money font-weight-bold ml-3">
					<fmt:formatNumber value="${cart_pay+delivery_pay}" pattern="#,###"/>원
				</div>
			</div>
		</div>
		<div class="order-container-title pb-2 pt-4">
			결제방법
		</div>
		<div class="order-container pt-2 pb-2">
			<div class="form-check">
				<input class="form-check-input" type="radio" name="order_method" id="order_method" value="0" checked="checked">
				<label class="form-check-label font-weight-bold pl-2" for="order_method_card">
					<img src="${pageContext.request.contextPath}/resources/images/card.png" width="21" height="auto">
				</label>
				<label class="form-check-label font-weight-bold pl-1" for="order_method_card">
					카드결제
				</label>
			</div>
			<div class="form-check pt-2">
				<input class="form-check-input" type="radio" name="order_method" id="order_method" value="1">
				<label class="form-check-label font-weight-bold pl-2" for="order_method_cash">
					<img src="${pageContext.request.contextPath}/resources/images/coin.png" width="21" height="auto">
				</label>
				<label class="form-check-label font-weight-bold pl-1" for="order_method_cash">
		    		현금결제
		  		</label>
			</div>
		</div>
	</div>
	<div class="pt-4 pb-4" style="text-align: center;">
		위 주문 내용을 확인하였으며 결제에 동의합니다.
	</div>
	<div class="pb-4" style="text-align: center;">
		<input type="submit" class="btn btn-primary order-pay-btn" style="width:450px; height:50px;" value="결제하기">
	</div>
	<fmt:parseNumber var= "order_pay" integerOnly= "true" value= "${cart_pay}" />
	<fmt:parseNumber var= "delivery_pay" integerOnly= "true" value= "${delivery_pay}" />
	<input type="hidden" name="order_pay" id="order_pay" value="${order_pay}">
	<input type="hidden" name="delivery_pay" id="delivery_pay" value="${delivery_pay}">
	<input type="hidden" name="p_no" id="p_no" value="${product.p_no}">
	<input type="hidden" name="cart_amount" id="cart_amount" value="${cartVO.cart_amount}">
	</form:form>
	
</div>