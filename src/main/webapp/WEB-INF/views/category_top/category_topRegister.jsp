<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- CSS file -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin1_style.css">
<!-- 중앙 내용 시작 -->
<h3>상위 카테고리 등록</h3>
<div id="admin-main-width">
	<div class="card mt-3" id="wide-width">
	<!-- 상위 카테고리 정보 -->
	<div class="card-body object-center text-center">
		<form:form action="category_topRegister.do" modelAttribute="category_topVO" class="table table-hover text-center line-bottom">
			<table>
				<tr>
					<th>상위 카테고리 명<br><br></th>
					<td>
						<form:input path="c_top_name" class="form-control form-control-sm"/>
					</td>
				</tr>
			</table>
			<div class="align-center">
			<br><br>
				<form:button class="btn btn-primary">등록</form:button>
				<input type="button" class="btn btn-secondary" value="취소" onclick="location.href='list.do'">
			</div>
		</form:form>
	</div>
</div>
</div>