<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- 중앙 내용 시작 -->
<div id="admin-main-width">
	<h3 id="header-3">카테고리 삭제</h3>
	<div id="narrow-width" class="element-center text-center">
	<h4>정말 삭제하시겠습니까?</h4>
		<hr class="sub-line">
		<form action="category_subDelete.do" method="post" id="delete_form" class="form">
			<input type="hidden" name="c_sub_no" value="${c_sub_no}"/>
			<table class="table table-borderless table-sm" style="width: 80%; margin: 0 auto;">
				
			</table>
			<div class="element-center">
				<input type="submit" value="삭제" class="btn btn-danger">
				<input type="button" value="취소" class="btn btn-secondary" onclick="history.back(-1)">
			</div>
		</form>
	</div>
</div>
<!-- 중앙 내용 끝 -->