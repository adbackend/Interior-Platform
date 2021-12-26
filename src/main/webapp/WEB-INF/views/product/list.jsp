<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- CSS file -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/adminPage.css">
<!-- BootStrap icon -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.6.0/font/bootstrap-icons.css">
<!-- 중앙 내용 시작 -->
<div id="admin-main-width">
<div id="wide-width">
	<h2>상품 목록</h2><br>
	<div class="align-center">
		<input type="button" class="btn btn-secondary" value="상품 등록" onclick="location.href='productRegister.do'"><br><br>
	</div>
	<c:if test="${count == 0}">
	<div class="result-display">
		등록된 상품이 없습니다.
	</div>	
	</c:if>
	<c:if test="${count > 0}">
	<table class="table table-hover table-bordered table-sm text-center">
		<tr>
			<th width="100">상품 번호</th>
			<th width="100">상품명</th>
			<th width="150">재고</th>
			<th width="200">상품 이미지</th>
			<th width="200">상품 가격</th>
			<th width="140">할인율(%)</th>
			<th width="140">카테고리 번호</th>
			<th width="200">수정/삭제</th>
		</tr>
		<c:forEach var="product" items="${list}">
		<tr>
			<td>${product.p_no}</td>
			<td>${product.p_name}</td>
			<td>${product.p_amount}</td>
			<td><img src="photoView.do?p_no=${product.p_no}" style="max-width:100px;"></td>
			<td>${product.p_price}</td>
			<td>${product.p_discount}</td>
			<td>${product.c_sub_no}</td>
			<td>
				<input type="button" class="btn btn-info" value="수정" onclick="location.href='productUpdate.do?p_no=${product.p_no}'">
				<input type="button" class="btn btn-danger deleteBtn" value="삭제" onclick="location.href='productDelete.do?p_no=${product.p_no}'" id="delete_${product.p_no}">
			</td>
		</tr>	
		</c:forEach>
	</table>
	<div class="align-center">${pagingHtml}</div>
	</c:if>
</div>
</div>