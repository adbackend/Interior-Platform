<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>   
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/order.css">
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
                    document.getElementById("order_address1").value = extraAddr;
                
                } else {
                    document.getElementById("order_address1").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('order_zipcode').value = data.zonecode;
                document.getElementById("order_address1").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("order_address2").focus();
            }
        }).open();
    }
 </script>

<script type="text/javascript">
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
<form:form id="insert_form" action="order.do" modelAttribute="orderVO" class="form mt-3" >
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
		<c:forEach var="cartList" items="${cartList}">
			<div class="order-container">
					<div class="order-content">
						<div class="order-img" onclick="location.href='${pageContext.request.contextPath}/shop/productDetail.do?p_no=${cartList.p_no}'">
							<img src="${pageContext.request.contextPath}/product/photoView.do?p_no=${cartList.p_no}" width="100" height="100">
						</div>
						<div class="order-name-cart ml-3 pr-2 pt-3">
							<div class="order-name-text">
								${cartList.p_name}
							</div>
							<div class="order-amount-text">
								수량 : ${cartList.cart_amount}개
							</div>
							
						</div>
					<div class="order-pay">
						<div id="order-pay-div" class="order-pay-div">
							<c:if test="${cartList.p_discount != 0}">
							<c:set var="cart_pay" value="${cart_pay+((cartList.p_price-(cartList.p_price*cartList.p_discount/100))*cartList.cart_amount)}"/>
							<fmt:formatNumber value="${(cartList.p_price-(cartList.p_price*cartList.p_discount/100))*cartList.cart_amount}" pattern="#,###"/>원	
						</c:if>
						<c:if test="${cartList.p_discount == 0}">
							<c:set var="cart_pay" value="${cart_pay+(cartList.p_price*cartList.cart_amount)}"/>
							<fmt:formatNumber value="${cartList.p_price*cartList.cart_amount }" pattern="#,###"/>원
						</c:if>
						</div>
					</div>
				</div>
			</div>
		</c:forEach> 
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
						<button type="button" class="btn btn-outline-dark col-sm-4 ml-1 bt" onclick="sample6_execDaumPostcode()">우편번호 찾기</button>
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
	</form:form>
	
</div>