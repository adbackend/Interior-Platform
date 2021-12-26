<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>   
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/reviewList.css"> 
<div class="top_menu_info">
	<div>
	홈 > 이미 작성한 후기
	</div>
</div>
<div id="main-width">
	<div id="categorySide">
		<div id="showmenu">나의 쇼핑</div>
		<div class="menu mt-2">
			<ul>
				<li class="order-a"><a href="${pageContext.request.contextPath}/shop/orderList.do">주문 &#183; 배송</a></li>
				<li class="order-a"><a href="${pageContext.request.contextPath}/shop/orderCancel.do">주문취소</a></li>
				<li class="order-a"><a href="${pageContext.request.contextPath}/shop/orderExchange.do">교환하기</a></li>
				<li class="order-a"><a href="${pageContext.request.contextPath}/shop/orderRefund.do">반품하기</a></li>
				<li class="order-a"><a href="${pageContext.request.contextPath}/shop/orderConfirm.do">구매확정</a></li>
			</ul>
		</div>
		<div id="showmenu">구매후기</div>
		<div class="menu mt-2">
			<ul>
				<li class="order-a"><a href="${pageContext.request.contextPath}/review/reviewAvaliable.do">작성 가능한 후기</a></li>
				<li class="order-current"><a href="${pageContext.request.contextPath}/review/reviewWritten.do">이미 작성한 후기</a></li>
			</ul>
		</div>
	</div>
	<div class="top-small-menu">
		<div class="top-small-menu-title mt-3">
			이미 작성한 후기
		</div>
		<table class="order-table" border="1">
			<colgroup>
				<col width="170px">
				<col width="170px">
				<col width="470px">
				<col width="200px">
			</colgroup>
			<thead>
				<tr>
					<th>주문 번호</th>
					<th>주문 일자</th>
					<th>상품 정보</th>
					<th>리뷰 작성</th>
				</tr>
			</thead>
			<c:forEach var="list" items="${list}">
				<tbody>
					<tr>	
						<td class="table-text">${list.order_no}</td>
						<td class="table-text">
							<fmt:formatDate value="${list.order_date}" pattern="yyyy.MM.dd."/>
						</td>
						<td class="table-product">
							<div class="table-product-name-img" onclick="location.href='${pageContext.request.contextPath}/shop/productDetail.do?p_no=${list.p_no}'">
								<img src="${pageContext.request.contextPath}/product/photoView.do?p_no=${list.p_no}" width="100" height="100">
							</div>
							<div class="table-product-name">
								${list.p_name}
							</div>
						</td>
						<td>
							<div class="order-state-text">
								<button class="btn btn-primary">리뷰 보기</button>
							</div>
						</td>
					</tr>	
				</tbody>
			</c:forEach>
		</table>
	</div>
</div>