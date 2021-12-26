<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- CSS file -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/adminPage.css">
<!-- 중앙 내용 시작 -->
<div id="admin-main-width">
	<div id="wide-width" class="wide-table">
		<h4 id="header-main">회원 상세정보</h4>
		<!-- 회원 정보 -->
		<table class="table table-bordered">
			<tr>
				<th>회원번호</th>
				<td>${adminMemberVO.mem_num}</td>				
			</tr>
			<tr>
				<th>회원유형</th>
				<td>
					<c:if test="${adminMemberVO.mem_auth == 0}"><span class="text-gray">탈퇴회원</span></c:if>
					<c:if test="${adminMemberVO.mem_auth == 1}"><span class="text-red">정지회원</span></c:if>
					<c:if test="${adminMemberVO.mem_auth == 2}">일반회원</c:if>
				</td>
			</tr>
			<tr>
				<th>아이디</th>
				<td>${adminMemberVO.mem_id}</td>
			</tr>
			<c:if test="${adminMemberVO.mem_auth != 0}">
			<tr>
				<th>이름</th>
				<td>${adminMemberVO.mem_name}</td>
				
			</tr>
			<tr>
				<th>연락처</th>
				<td>${adminMemberVO.mem_phone}</td>
			</tr>
			<tr>
				<th>이메일</th>
				<td>${adminMemberVO.mem_email}</td>
			</tr>
			<tr>
				<th>주소</th>
				<td>(${adminMemberVO.mem_zipcode}) ${adminMemberVO.mem_address1} ${adminMemberVO.mem_address2}</td>
			</tr>
			<tr>
				<th>가입일</th>
				<td>${adminMemberVO.mem_date}</td>
			</tr>
			</c:if>
		</table>
		<div class="element-center text-right div-button">
			<c:if test="${adminMemberVO.mem_auth != 0}">
				<input type="button" value="회원수정" class="btn btn-primary btn-sm" onclick="location.href='memberUpdate.do?mem_num=${adminMemberVO.mem_num}'">
				<c:if test="${mem_auth == 4}">
				<input type="button" value="회원삭제" class="btn btn-danger btn-sm" onclick="location.href='memberDelete.do?mem_num=${adminMemberVO.mem_num}'">
				</c:if>
			</c:if>
		</div>
		<!-- 활동 정보 -->
		<h5 id="header-sub">활동정보</h5>
		<table class="table table-bordered" id="activity_table">
			<thead>
				<tr>
					<th>주문건수</th>
					<th>취소건수</th>
					<!-- <th>후기건수</th> -->
					<th>문의건수</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><b>${orderCnt}</b> 건</td>
					<td><b>${cancelCnt}</b> 건</td>
					<%-- <td><b>${reviewCnt}</b> 건</td> --%>
					<td><b>${qnaCnt}</b> 건</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<!-- 중앙 내용 끝 -->