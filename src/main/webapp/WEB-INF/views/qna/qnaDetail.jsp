<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>   
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/qnaDetail.css"> 
<div class="top_menu_info">
	<div>
	홈 > 문의게시판
	</div>
</div>
<div id="main-width">
	<div id="categorySide">
		<div id="showmenu">문의게시판</div>
		<div class="menu mt-2">
			<ul>
			<c:if test="${qna.board_kind == 0}">
				<li class="order-current"><a href="qnaList.do?board_kind=0">상품 문의</a></li>
				<li class="order-a"><a href="qnaList.do?board_kind=1">배송 문의</a></li>
				<li class="order-a"><a href="qnaList.do?board_kind=2">교환/환불 문의</a></li>
				<li class="order-a"><a href="qnaList.do?board_kind=3">기타 문의</a></li>
			</c:if>
			<c:if test="${qna.board_kind == 1}">
					<li class="order-a"><a href="qnaList.do?board_kind=0">상품 문의</a></li>
					<li class="order-current"><a href="qnaList.do?board_kind=1">배송 문의</a></li>
					<li class="order-a"><a href="qnaList.do?board_kind=2">교환/환불 문의</a></li>
					<li class="order-a"><a href="qnaList.do?board_kind=3">기타 문의</a></li>
				</c:if>
				<c:if test="${qna.board_kind == 2}">
					<li class="order-a"><a href="qnaList.do?board_kind=0">상품 문의</a></li>
					<li class="order-a"><a href="qnaList.do?board_kind=1">배송 문의</a></li>
					<li class="order-current"><a href="qnaList.do?board_kind=2">교환/환불 문의</a></li>
					<li class="order-a"><a href="qnaList.do?board_kind=3">기타 문의</a></li>
				</c:if>
				<c:if test="${qna.board_kind == 3}">
					<li class="order-a"><a href="qnaList.do?board_kind=0">상품 문의</a></li>
					<li class="order-a"><a href="qnaList.do?board_kind=1">배송 문의</a></li>
					<li class="order-a"><a href="qnaList.do?board_kind=2">교환/환불 문의</a></li>
					<li class="order-current"><a href="qnaList.do?board_kind=3">기타 문의</a></li>
				</c:if>
			</ul>
		</div>
	</div>
	<div class="top-small-menu">
		<div class="top-small-menu-title mt-3">
			<c:if test="${qna.board_kind == 0}">
				상품 문의게시판
			</c:if>
			<c:if test="${qna.board_kind == 1}">
				배송 문의게시판
			</c:if>
			<c:if test="${qna.board_kind == 2}">
				교환/환불 문의게시판
			</c:if>
			<c:if test="${qna.board_kind == 3}">
				기타 문의게시판
			</c:if>
		</div>
		<c:if test="${qna.p_no!=0}">
		<div class="product-qna-div1" onclick="location.href='${pageContext.request.contextPath}/shop/productDetail.do?p_no=${qna.p_no}'">
				<div class="product-qna-div2">
					<img src="${pageContext.request.contextPath}/product/photoView.do?p_no=${qna.p_no}" width="90" height="90" style="margin-top:5px;">
				</div>
				<div class="product-qna-div3 ml-2">
					<div class="product-qna-div4">
					${qna.p_name}
					</div>
					<div class="product-qna-div5">
					<fmt:formatNumber value="${qna.p_price }" pattern="#,###"/>원
				</div >
				</div>
			</div>
		</c:if>
		
		<table class="order-table" border="1">
			<colgroup>
				<col width="100px">
				<col width="900px">
			</colgroup>
				<tr>
					<td class="text-back table-padding">제목</td>
					<td class="table-padding">${qna.board_title}</td>
				</tr>
				<tr>
					<td class="text-back table-padding">작성자</td>
					<td class="table-padding">${qna.mem_id}</td>
				</tr>
				<tr>
					<td class="text-back table-padding">작성일</td>
					<td class="table-padding"><fmt:formatDate value="${qna.board_date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
					<tr>	
						<td colspan="2" class="table-text table-body-padding" >
							${qna.board_content}
						</td>
					</tr>	
		</table>
		<div class="mt-3">
			<button class="btn btn-light ml-1" style="float: right;" onclick="location.href='qnaList.do?board_kind=${qna.board_kind}'" >목록으로</button>
			<c:if test="${!empty mem_num && (mem_auth==3 || mem_auth==4)}">
				<button class="btn btn-info ml-1" style="float: right;" onclick="location.href='qnaReply.do?board_no=${qna.board_no}'" >답글쓰기</button>
			</c:if>
			<c:if test="${!empty mem_num && ((mem_auth==3 || mem_auth==4) || mem_num == qna.mem_num)}">

				<button class="btn btn-danger ml-1" style="float:right;" id="delete_btn">삭제</button>
				<button class="btn btn-warning ml-1" style="float:right;" onclick="location.href='qnaUpdate.do?board_no=${qna.board_no}'">수정</button>
				
			</c:if>
			<script type="text/javascript">
				var delete_btn = document.getElementById('delete_btn');
				delete_btn.onclick=function(){
					var choice = confirm('글을 삭제하시겠습니까?');
					if(choice){
						location.replace('qnaDelete.do?board_no=${qna.board_no}');
					}
				};
		</script>	
		</div>
	</div>
</div>
<div style="height: 100px;"></div>