<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>   
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/cart.css">
 <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/order.css">
 <script>
 	$(document).ready(function(){
 		$("button[id^='delete_btn']").on('click', function(e) {
 			var choice = confirm('해당 상품을 삭제하시겠습니까?');
 			if(choice){
 				var p_no = $(this).attr('id').substring(11);
 				location.replace('${pageContext.request.contextPath}/shop/deleteCart.do?p_no='+p_no); 
 			}else {
 				return;
 			}
 		  });
 		
 		$("select[id^='select_amount']").on('change', function(e) {
 				var p_no = $(this).attr('id').substring(14);
 				var cart_amount = $(this).children("option:selected").text();
 				
 				var data = {
 						p_no : p_no,
 						cart_amount : cart_amount
 					};
 				
 				$.ajax({
 					url:'${pageContext.request.contextPath}/shop/updateCart.do',
 					type:'post',
 					data:data,
 					dataType:'json',
 					catch:false,
 					success:function(param){
 						if(param.result == 'logout'){
 							alert('로그인 후 사용하세요');
 							$('#select_amount').val('cart_amount');
 						}else if(param.result == 'success'){
 							alert('상품 수량이 변경되었습니다');
 							$('#select_amount').val('cart_amount');
 							window.location.href = '${pageContext.request.contextPath}/shop/cart.do';
 						}
 					},
 					error:function(){
 						alert("네트워크 오류");
 					}
 					
 				});

 		  });	
 	});
 </script>  
 <div class="top_menu_info">
	<div>
	홈 > 장바구니
	</div>
</div>
<div id="main-width">
	<div class="top-cart-heading mt-3">
		장바구니
	</div>
	<div class="top-cart-step mb-2">
		<span style="color:#35c5f0;">01 장바구니</span> > <span>02 주문결제</span> > <span>03 결제완료</span>
	</div>
	<c:if test="${count == 0}">
		<div class="cart-main pt-4">
			<div class="cart-container mt-5 mb-5" style="text-align:center; height:auto; border: none;">
				<div class="pb-4" style="font-weight: bold; font-size: 17px">
					장바구니에 담긴 상품이 없습니다.
				</div>
				<div class="pb-5">
					<button class="btn btn-outline-primary shop-btn" onclick="location.href='${pageContext.request.contextPath}/shop/main.do'">쇼핑하러가기</button>
				</div>
			</div>
		</div>
	</c:if>
	<c:if test="${count > 0}">
		<div class="cart-main pt-4">
		<c:set var="cart_pay" value="0"/>
		<c:forEach var="list" items="${list}">
			<div class="cart-container">
				<div class="cart-content">
						<div class="cart-img" onclick="location.href='${pageContext.request.contextPath}/shop/productDetail.do?p_no=${list.p_no}'">
							<img src="${pageContext.request.contextPath}/product/photoView.do?p_no=${list.p_no}" width="100" height="100">
						</div>
						<div class="cart-name ml-3 pr-2 pt-3" onclick="location.href='${pageContext.request.contextPath}/shop/productDetail.do?p_no=${list.p_no}'">
							${list.p_name}
					</div>
					<c:if test="${list.p_amount != 0 && list.p_amount >= 10}">	
						<div class="cart-amount">
							<div class="cart-amount-div">
								<select id="select_amount_${list.p_no}" class="cart-amount-select">
					  				<c:forEach var="i" begin="1" end="10">
										<option value="${i}"
											<c:if test="${i==list.cart_amount}">
											selected="selected"</c:if>>
											${i}
			       						</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</c:if>
					<c:if test="${list.p_amount != 0 && list.p_amount < 10}">	
						<div class="cart-amount">
							<div class="cart-amount-div">
								<select id="select_amount_${list.p_no}" class="cart-amount-select">
					  				<c:forEach var="i" begin="1" end="${list.p_amount}">
										<option value="${i}"
											<c:if test="${i==list.cart_amount}">
											selected="selected"</c:if>>
											${i}
			       						</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</c:if>
					<div class="cart-pay">
						<div id="cart-pay-div" class="cart-pay-div">
							<c:if test="${list.p_discount != 0}">
								<c:set var="cart_pay" value="${cart_pay+((list.p_price-(list.p_price*list.p_discount/100))*list.cart_amount)}"/>
								<fmt:formatNumber value="${(list.p_price-(list.p_price*list.p_discount/100))*list.cart_amount}" pattern="#,###"/>원	
							</c:if>
							<c:if test="${list.p_discount == 0}">
								<c:set var="cart_pay" value="${cart_pay+(list.p_price*list.cart_amount)}"/>
								<fmt:formatNumber value="${list.p_price*list.cart_amount }" pattern="#,###"/>원
							</c:if>
						</div>
					</div>
					<div class="cart-btn-container pl-2">
						<button class="btn btn-light cart-delete-btn" id="delete_btn_${list.p_no}">삭제</button>
					</div>
				</div>
			</div>
		</c:forEach>
			<div class="middle-line pt-4 pb-1">
				<hr class="middle-line-hr">
			</div>
			<div class="cart-bottom-container">
				<div class="cart-bottom-pay">
					<div class="cart-bottom-pay1">
						총 상품금액
					</div>
					<div class="cart-bottom-pay2">	
						<fmt:formatNumber value="${cart_pay}" pattern="#,###"/>원
					</div>
				</div>
				<div class="cart-bottom-delivery">
					<div class="cart-bottom-delivery1">		
						배송비
					</div>
					<div class="cart-bottom-delivery2">
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
				<div class="cart-pay-result mt-2 ml-5">
					<span class="cart-pay-text mr-2">결제예상금액</span>
					<span class="cart-pay-money">
					<fmt:formatNumber value="${cart_pay+delivery_pay}" pattern="#,###"/>원
					</span>
					<span><button class="btn btn-primary cart-pay-btn ml-2" onclick="location.href='order.do'">구매하기</button></span>
				</div>
			</div>
		</div>
	</c:if>
</div>