<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/orderDetail.css"> 
<div class="top_menu_info">
	<div>
	홈 > 주문상세
	</div>
</div>
<div id="main-width">
	<div id="categorySide">
		<div id="showmenu">나의 쇼핑</div>
		<div class="menu mt-2">
			<ul>
				<li class="order-current"><a href="orderList.do">주문 &#183; 배송</a></li>
				<li class="order-a"><a href="orderCancel.do">주문취소</a></li>
				<li class="order-a"><a href="orderExchange.do">교환하기</a></li>
				<li class="order-a"><a href="orderRefund.do">반품하기</a></li>
				<li class="order-a"><a href="orderConfirm.do">구매확정</a></li>
			</ul>
		</div>
		<div id="showmenu">구매후기</div>
		<div class="menu mt-2">
			<ul>
				<li class="order-a"><a href="${pageContext.request.contextPath}/review/reviewAvaliable.do">작성 가능한 후기</a></li>
				<li class="order-a"><a href="${pageContext.request.contextPath}/review/reviewWritten.do">이미 작성한 후기</a></li>
			</ul>
		</div>
	</div>
	<div class="top-small-menu">
		<div class="top-small-menu-title mt-3">
			주문 상세 정보
		</div>
		<div class="order-main">
			<div class="order-main-container mt-3">
				<div class="order-info">
					<div class="order-info-title">
						주문번호
					</div>
					<div class="order-info-value">
						${order.order_no}
					</div>
				</div>
				<div class="order-info">
					<div class="order-info-title">
						주문일자
					</div>
					<div class="order-info-value">
						<fmt:formatDate value="${order.order_date}" pattern="yyyy.MM.dd."/>
					</div>
				</div>
				<div class="order-info">
					<div class="order-info-title">
						받는 사람
					</div>
					<div class="order-info-value">
						${order.order_name}
					</div>
				</div>
				<div class="order-info">
					<div class="order-info-title">
						연락처
					</div>
					<div class="order-info-value">
						${order.order_phone}
					</div>
				</div>
				<div class="order-info">
					<div class="order-info-title">
						배송주소
					</div>
					<div class="order-info-value">
						${order.order_address1} ${order.order_address2}
					</div>
				</div>	
			</div>	
			<hr class="hr-line"/>
			<div class="order-main-container mt-4 mb-4">
				<div class="order-price-content">
					<div class="order-price-text pl-3">
						총 상품 금액
					</div>
					<div class="order-price-money ml-3">
					<fmt:formatNumber value="${order.order_pay}" pattern="#,###"/>원
					</div>
				</div>
				<div class="order-price-content">
					<div class="order-price-text pl-3">
						배송비
					</div>
					<div class="order-price-money ml-3">
						<c:if test="${order.delivery_pay !=0}">
							<fmt:formatNumber value="${order.delivery_pay}" pattern="#,###"/>원
						</c:if>
						<c:if test="${order.delivery_pay ==0}">
							무료
						</c:if>
					</div>
				</div>
				<div class="order-price-content">
					<div class="order-price-text pl-3">
						결제 방법
					</div>
					<div class="order-price-money ml-3">
						<c:if test="${order.order_method == 0}">
							카드결제
						</c:if>
						<c:if test="${order.order_method == 1}">
							현금결제
						</c:if>
					</div>
				</div>
				<div class="order-price-content">
					<div class="order-price-text font-weight-bold pl-3">
						총 결제금액
					</div>
					<div class="order-price-money font-weight-bold ml-3">
						<fmt:formatNumber value="${order.order_pay+order.delivery_pay}" pattern="#,###"/>원
					</div>
				</div>
			</div>
			<div class="div-line">
				주문내역
			</div>
			<div class="order-main-container mt-3">
				<div class="order-main-product">
					<c:forEach var="product" items="${listProduct}">
						<div class="order-container-product mt-3">
							<div class="order-content-product">
								<div class="order-img" onclick="location.href='${pageContext.request.contextPath}/shop/productDetail.do?p_no=${product.p_no}'">
									<img src="${pageContext.request.contextPath}/product/photoView.do?p_no=${product.p_no}" width="100" height="100">
								</div>
								<div class="order-name-cart ml-2 pr-2 pt-1">
									<div class="order-name-text">
										${product.p_name}
									</div>
									<div class="order-amount-text">
										수량 : ${product.order_d_amount}개
									</div>
									<div class="order-amount-text">
										${order.d_status_name}
									</div>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<div class="btn-div">
			<button class="btn btn-light history-btn mt-3 mb-4" onclick="history.go(-1)">목록으로</button>
			<button class="btn btn-primary orderlist-btn mt-3 mb-4" onclick="location.href='${pageContext.request.contextPath}/shop/main.do'">홈으로 이동</button>
		</div>
	</div>
	
</div>