<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/order.css">
<div class="top_menu_info">
	<div>
	홈 > 결제완료
	</div>
</div>
<div id="main-width">
	<div class="top-order-heading mt-3">
		주문결제
	</div>
	<div class="top-order-step mb-2">
		<span>01 장바구니</span> > <span>02 주문결제</span> > <span style="color:#35c5f0;">03 결제완료</span>
	</div>
	<div class="order-main pt-4">
		<div class="order-container mt-5 mb-5" style="text-align: center; height:auto;">
			<div class="pb-4"style="font-weight: bold; font-size: 17px">
				주문접수가 완료되었습니다.
			</div>
			<div class="pb-5">
				<button class="btn btn-outline-primary shop-btn"  onclick="location.href='${pageContext.request.contextPath}/shop/main.do'">쇼핑 계속하기</button>
				<button class="btn btn-primary orderlist-btn" onclick="location.href='orderList.do'">주문 내역보기</button>
			</div>
		</div>
	</div>
</div>