<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- CSS file -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/adminPage.css">
<!-- BootStrap icon -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.6.0/font/bootstrap-icons.css">
<script type="text/javascript">
	$(function(){
		//배송조회 창 열기
		$('.btn-tracking').click(function(){
			var tnum = $(this).attr('data-tnum');
			window.open('https://service.epost.go.kr/trace.RetrieveDomRigiTraceList.comm?displayHeader=N&sid1=' + tnum,
				    	'_blank', 'left=30pixels, top=50pixels, width=750, height=700');
 		});
		
		//배송준비 버튼 확인창
		$('.btn-status1').click(function(){
			var check = confirm('배송상태가 [배송준비중]로 변경됩니다.');
			if(check){
				var dnum = $(this).attr('data-dnum');
				location.href='dStatusUpdate.do?delivery_no=' + dnum + '&d_status_num=1';
			}
		});
		
		//배송완료 버튼 확인창
		$('.btn-status3').click(function(){
			var check = confirm('배송상태가 [배송완료]로 변경됩니다.');
			if(check){
				var dnum = $(this).attr('data-dnum');
				location.href='dStatusUpdate.do?delivery_no=' + dnum + '&d_status_num=3';
			}
		});
	});
</script>
<!-- 중앙 내용 시작 -->
<div id="admin-main-width">
	<div id="wide-width" class="wide-table">
		<h4 id="header-main">배송 관리</h4>
		<!-- 배송 검색 조건 -->
		<form action="deliveryList.do" method="get" id="search_form">
			<table class="table table-bordered table-sm">
				<tr>
					<th>배송상태</th>
					<td>
						<div class="form-check-inline">
						<input type="radio" name="d_status_num" value="" class="form-check-input" <c:if test="${empty d_status_num}">checked='checked'</c:if>>전체
						<input type="radio" name="d_status_num" value="0" class="form-check-input ml-3" <c:if test="${d_status_num == '0'}">checked='checked'</c:if>>결제완료
						<input type="radio" name="d_status_num" value="1" class="form-check-input ml-3" <c:if test="${d_status_num == 1}">checked='checked'</c:if>>배송준비중
						<input type="radio" name="d_status_num" value="2" class="form-check-input ml-3" <c:if test="${d_status_num == 2}">checked='checked'</c:if>>배송중
						<input type="radio" name="d_status_num" value="3" class="form-check-input ml-3" <c:if test="${d_status_num == 3}">checked='checked'</c:if>>배송완료						</div>
					</td>
				</tr>
				<tr>
					<th>검색조건</th>
					<td>
						<div class="form-inline">
							<select name="keyfield" id="keyfield" class="form-control form-control-sm">
								<option value="order_no" <c:if test="${keyfield == 'order_no'}">selected='selected'</c:if>>주문번호</option>
								<option value="mem_id" <c:if test="${keyfield == 'mem_id'}">selected='selected'</c:if>>아이디</option>
							</select>
							<input type="search" name="keyword" id="keyword" value="${keyword}" placeholder="검색어 입력" class="form-control form-control-sm ml-1">
							<input type="submit" value="검색" class="btn btn-dark btn-sm">
						</div>
					</td>
				</tr>
			</table>
		</form>
		<!-- 배송 검색 결과 -->
		<c:if test="${count == 0}">
			<div class="card rounded-0">
				<div class="card-body my-5 text-center">배송 처리중인 주문 정보가 없습니다.</div>
			</div>
		</c:if>
		<c:if test="${count > 0}">
		<table class="table table-hover table-bordered table-sm text-center">
			<thead>
				<tr>
					<th class="c-15">주문일자</th>
					<th class="c-15">주문번호</th>
					<th class="c-15">주문자아이디</th>
					<th class="c-15">배송상태</th>
					<th class="c-20">송장번호</th>
					<th class="c-20">관리</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="delivery" items="${list}">
				<tr>
					<td>
						<fmt:formatDate value="${delivery.order_date}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>${delivery.order_no}</td>
					<td>${delivery.mem_id}</td>
					<td>
						<c:if test="${delivery.d_status_num == 3}"><span class="text-gray"></c:if>
						${delivery.d_status_name}
						<c:if test="${delivery.d_status_num == 3}"></span></c:if>
					</td>
					<td>
						${delivery.tracking_num}
						<c:if test="${!empty delivery.tracking_num}">
							<button class="btn-tracking btn-detail" data-tnum="${delivery.tracking_num}">
								<i class="bi bi-zoom-in "></i>조회
							</button>
						</c:if>
					</td>
					<td class="text-left">
						<c:if test="${delivery.d_status_num == 0}">
							<button class="btn btn-light btn-sm btn-status1" data-dnum="${delivery.delivery_no}">
								<i class="bi bi-box-seam mr-1"></i>배송준비
							</button>
						</c:if>
						<c:if test="${delivery.d_status_num == 1}">
							<button class="btn btn-light btn-sm" onclick="location.href='deliveryTrack.do?delivery_no=${delivery.delivery_no}'">
								<i class="bi bi-truck mr-1"></i>송장등록
							</button>
						</c:if>
						<c:if test="${delivery.d_status_num == 2}">
							<button class="btn btn-light btn-sm btn-status3" data-dnum="${delivery.delivery_no}">
								<i class="bi bi-check-circle mr-1"></i>배송완료
							</button>
						</c:if>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="result-count">[검색결과 : <b>${count}</b> 건]</div>
		<div class="text-center mt-4">${pagingHtml}</div>
		</c:if>
	</div>
</div>
<!-- 중앙 내용 끝 -->