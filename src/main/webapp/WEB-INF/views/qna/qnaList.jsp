<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>   
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/qnaList.css"> 
<script type="text/javascript">
	$(function(){
		//검색 유효성 체크
		$('#search_form2').submit(function(){
			if($('#keyword2').val().trim() == ''){
				alert('검색어를 입력하세요!');
				$('#keyword2').val('').focus();
				return false;
			}
		});
	});
</script>
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
				<c:if test="${board_kind == 0}">
					<li class="order-current"><a href="qnaList.do?board_kind=0">상품 문의</a></li>
					<li class="order-a"><a href="qnaList.do?board_kind=1">배송 문의</a></li>
					<li class="order-a"><a href="qnaList.do?board_kind=2">교환/환불 문의</a></li>
					<li class="order-a"><a href="qnaList.do?board_kind=3">기타 문의</a></li>
				</c:if>
				<c:if test="${board_kind == 1}">
					<li class="order-a"><a href="qnaList.do?board_kind=0">상품 문의</a></li>
					<li class="order-current"><a href="qnaList.do?board_kind=1">배송 문의</a></li>
					<li class="order-a"><a href="qnaList.do?board_kind=2">교환/환불 문의</a></li>
					<li class="order-a"><a href="qnaList.do?board_kind=3">기타 문의</a></li>
				</c:if>
				<c:if test="${board_kind == 2}">
					<li class="order-a"><a href="qnaList.do?board_kind=0">상품 문의</a></li>
					<li class="order-a"><a href="qnaList.do?board_kind=1">배송 문의</a></li>
					<li class="order-current"><a href="qnaList.do?board_kind=2">교환/환불 문의</a></li>
					<li class="order-a"><a href="qnaList.do?board_kind=3">기타 문의</a></li>
				</c:if>
				<c:if test="${board_kind == 3}">
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
			<c:if test="${board_kind == 0}">
				상품 문의게시판
			</c:if>
			<c:if test="${board_kind == 1}">
				배송 문의게시판
			</c:if>
			<c:if test="${board_kind == 2}">
				교환/환불 문의게시판
			</c:if>
			<c:if test="${board_kind == 3}">
				기타 문의게시판
			</c:if>
		</div>
		<div class="mb-2 mt-3">
			<form action="qnaList.do" method="get" id="search_form2" style="width: auto;">
			<input type="hidden" id="board_kind" name="board_kind" value="${board_kind}">
				<div class="form-inline">
					<select name="keyfield" id="keyfield" class="form-control form-control-sm">
						<option value="mem_id">아이디</option>
						<option value="board_content">내용</option>
					</select>
					<input type="search" name="keyword2" id="keyword2" placeholder="검색어 입력" class="form-control form-control-sm ml-1">
					<input type="submit" value="검색" class="btn btn-dark btn-sm">
				</div>
			</form>
			<button class="btn btn-info write-btn btn-sm" style="float:right;" onclick="location.href='qnaWrite.do?board_kind=${board_kind}'">글작성</button>
		</div>
		<c:if test="${board_kind == 0}">
			<table class="order-table" border="1">
			<colgroup>
				<col width="100px">
				<col width="280px">
				<col width="250px">
				<col width="130px">
				<col width="170px">
			</colgroup>
			<thead>
				<tr>
					<th>게시물 번호</th>
					<th>상품정보</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일</th>
				</tr>
			</thead>
			<c:forEach var="list" items="${list}">
				<tbody>
					<tr>	
						<td class="table-text">${list.board_no}</td>
						<td> 
						<c:if test="${list.p_no!=0}">
							<div style="height:90px; line-height: 90px;">
							<div class="ml-2" style="float: left;">
										<img src="${pageContext.request.contextPath}/product/photoView.do?p_no=${list.p_no}" width="60" height="70">
								</div>
								<div class="ml-2"style="float:left; font-size: 13px;word-break:break-all;">
										<div>
											${list.p_name}
										</div>
								</div>
							</div>
							</c:if>	
						</td>
						<td class="table-product">
							<div class="table-product-name">
								<c:if test="${list.level > 1}">
									<c:forEach begin="1" end="${list.level}">
										&nbsp;
									</c:forEach>
									<a href="qnaDetail.do?board_no=${list.board_no}">RE : </a> 
								</c:if>
								<a href="qnaDetail.do?board_no=${list.board_no}">${list.board_title}</a>
							</div>
						</td>
						<td class="table-text">
							${list.mem_id}
						</td>
						<td class="table-text">
							<fmt:formatDate value="${list.board_date}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>	
				</tbody>
			</c:forEach>
		</table>
		</c:if>
		<c:if test="${board_kind != 0}">
			<table class="order-table" border="1">
			<colgroup>
				<col width="100px">
				<col width="600px">
				<col width="130px">
				<col width="170px">
			</colgroup>
			<thead>
				<tr>
					<th>게시물 번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일</th>
				</tr>
			</thead>
			<c:forEach var="list" items="${list}">
				<tbody>
					<tr>	
						<td class="table-text">${list.board_no}</td>
						<td class="table-product">
							<div class="table-product-name">
								<c:if test="${list.level > 1}">
									<c:forEach begin="1" end="${list.level}">
										&nbsp;
									</c:forEach>
									<a href="qnaDetail.do?board_no=${list.board_no}">RE : </a> 
								</c:if>
								<a href="qnaDetail.do?board_no=${list.board_no}">${list.board_title}</a>
							</div>
						</td>
						<td class="table-text">
							${list.mem_id}
						</td>
						<td class="table-text">
							<fmt:formatDate value="${list.board_date}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>	
				</tbody>
			</c:forEach>
		</table>
		</c:if>
		<div align="center" style="margin-bottom: 100px;">${pagingHtml}</div>
	</div>
	
</div>