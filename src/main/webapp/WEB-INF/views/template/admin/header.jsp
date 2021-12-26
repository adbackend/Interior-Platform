<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!-- 상단 시작 -->
	<div class="admin-top-title">
		<div class="title1">
			내일의 집 관리자 페이지
		</div>
		<div class="title2">
			<ul>
			<c:if test="${!empty mem_num && (mem_auth==3 || mem_auth==4) }">
				<li>${mem_id}님</li>
				<li><a href="${pageContext.request.contextPath}/shop/main.do">쇼핑몰로 이동</a></li>
				<li><a href="${pageContext.request.contextPath}/login/logout.do">로그아웃</a></li>
			</c:if>
			</ul>
		</div>
	</div>
	
	<div id="top-menu">
		<ul>
			<li><a href="${pageContext.request.contextPath}/admin/main.do">메인</a></li>
			<li><a href="${pageContext.request.contextPath}/category_top/list.do">카테고리관리</a></li>
			<li><a href="${pageContext.request.contextPath}/product/list.do">상품관리</a></li>
			<li><a href="${pageContext.request.contextPath}/admin/orderList.do">주문관리</a></li>
			<li><a href="${pageContext.request.contextPath}/admin/memberList.do">회원관리</a></li>
			<li><a href="${pageContext.request.contextPath}/admin/termRevenue.do">매출관리</a></li>
			<c:if test="${!empty mem_num && mem_auth==4}">
				<li><a href="${pageContext.request.contextPath}/admin/adminList.do">관리자관리</a></li>
			</c:if>
		</ul>
	</div>
<!-- 상단 끝 -->