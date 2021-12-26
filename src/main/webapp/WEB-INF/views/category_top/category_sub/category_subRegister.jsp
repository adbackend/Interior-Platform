<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- CSS file -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/admin1_style.css">
<!-- 중앙 내용 시작 -->
	<h3>하위 카테고리 등록<br></h3><br>
	<!-- 하위 카테고리 정보 -->
<div class="card mt-3">
	 <div class="card-body object-center text-center">
		<table class="table table-hover text-center line-bottom">
		<form:form action="category_subRegister.do" modelAttribute="category_subVO">
			<table>
				<tr>
					<th>넣을 상위 카테고리 번호&emsp;&emsp;</th>
					<td>
						<form:select path="c_top_no" class="form-control form-control-sm">
							<c:forEach var="top" items="${topList}">
								<option value="${top.c_top_no}">${top.c_top_name}</option>
							</c:forEach>
						</form:select>
					</td>
				</tr><br>
				<tr>
					<th>하위 카테고리 명&emsp;&emsp;</th>
					<td>
						<form:input path="c_sub_name" class="form-control form-control-sm"/>
					</td>
				</tr>
				<tr>
					
				</tr>
			</table>
			<div class="align-center">
				<form:button class="btn btn-primary">등록</form:button>
				<input type="button" class="btn btn-secondary" value="취소" onclick="history.back(-1)">
			</div>
		</form:form>
		</table>
	</div>
</div>