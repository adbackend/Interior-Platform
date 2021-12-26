<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/videoAdapter.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
	
</script>
<!-- 중앙 내용 시작 -->
<div class="page-main">
	<h2>${review.p_no}</h2>
	<ul>
		<li>후기 번호 : ${review.review_no}</li>
		<%-- <li>작성자 : ${review.id}</li> --%>
	</ul>
	<hr size="1" width="100%">
	<c:if test="${!empty review.review_image_name}">
	<div class="align-center">
		<img src="imageView.do?review_no=${review.review_no}" style="max-width:500px">
	</div>
	</c:if>
	<p>${review.review_content}</p>
	<hr size="1" width="100%">
	<div class="align-right">
		<c:if test="${!empty user_num && user_num == review.mem_num}">
		<input type="button" value="상품 후기 수정하기" onclick="location.href='update.do?review_no=${review.review_no}'">
		<input type="button" value="상품 후기 삭제하기" id="delete_btn">
		<script type="text/javascript">
			var delete_btn = document.getElementById('delete_btn');
			delete_btn.onclick=function(){
				var choice = confirm('삭제하시겠습니까?');
				if(choice){
					location.replace('delete.do?review_no=${review.review_no}');
				}
			};
		</script>
		</c:if>
		<input type="button" value="상품 후기 목록" onclick="location.href='list.do'">
	</div>
	<div id="loading" style="display:none;">
		<img src="${pageContext.request.contextPath}/resources/images/ajax-loader.gif">
	</div>
</div>