<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>   
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/productList.css"> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/template/fotter_template.css"> 
<script type="text/javascript">
$(document).ready(function() {
    $('[id=showmenu]').click(function() {
        $('[class=menu]').not($(this).next('[class=menu]').slideToggle("slow")).slideUp();
    });
});
</script>
<div class="top_menu_info">
	<div>
	홈 > 검색
	</div>
</div>
<div id="main-width">
	<div id="categorySide">
		<c:forEach var="top" items="${category_top}">
			<div id="showmenu">${top.c_top_name}</div>
			<div class="menu">
				<ul>
					<c:forEach var="sub" items="${category_sub}">
						<c:if test="${sub.c_top_no == top.c_top_no}">
							<li><a href="${pageContext.request.contextPath}/shop/productList.do?c_top_no=${sub.c_top_no}&c_sub_no=${sub.c_sub_no}">${sub.c_sub_name}</a></li>
						</c:if>
					</c:forEach>
		  		</ul>
		</div>
		</c:forEach>
	</div>
	<div class="top-small-menu">
		<div class="top-small-menu-title">
			${keyword} 검색 목록
		</div>
		<div class="menu-amount mb-3">
			${count}개의 상품
		</div>
		<div class="list-container">
			<c:forEach var="product" items="${list}">
				<div class="list-content" onclick="location.href='productDetail.do?p_no=${product.p_no}'">
					<div class="image-ani">
					<img src="${pageContext.request.contextPath}/product/photoView.do?p_no=${product.p_no}" width="240" height="240">
				</div>
				
					<div class="list-name mt-2 pl-2">
						${product.p_name}
					</div>
					<c:if test="${product.p_amount==0 }">
						<c:if test="${product.p_discount != 0}">
							<div class="list-sale mr-1 pl-2" style="opacity: 0.2">
								${product.p_discount}%
							</div>
						</c:if>
					</c:if>
					<c:if test="${product.p_amount!=0 }">
						<c:if test="${product.p_discount != 0}">
							<div class="list-sale mr-1 pl-2">
								${product.p_discount}%
							</div>
						</c:if>
					</c:if>
				<c:if test="${product.p_amount!=0 }">
				<div class="list-price pl-2">
					<c:if test="${product.p_discount != 0}">
						<fmt:formatNumber value="${product.p_price-(product.p_price*product.p_discount/100)}" pattern="#,###"/>원				
					</c:if>
					<c:if test="${product.p_discount == 0}">
						<fmt:formatNumber value="${product.p_price }" pattern="#,###"/>원
					</c:if>
					</div>
				</c:if>
				<c:if test="${product.p_amount==0 }">
				<div class="list-price pl-2" style="opacity: 0.2">
					<c:if test="${product.p_discount != 0}">
						<fmt:formatNumber value="${product.p_price-(product.p_price*product.p_discount/100)}" pattern="#,###"/>원				
					</c:if>
					<c:if test="${product.p_discount == 0}">
						<fmt:formatNumber value="${product.p_price }" pattern="#,###"/>원
					</c:if>
					</div>
				</c:if>
				<c:if test="${product.p_amount != 0}">	
					<c:if test="${product.p_discount != 0}">
						<div class="special-price-div ml-2">
							<div class="special-price-text">
								특가
							</div>
						</div>	
					</c:if>
				</c:if>
				<c:if test="${product.p_amount == 0}">	
					<div class="soldout-div ml-2">
						<div class="special-price-text">
							품절
						</div>
					</div>	
				</c:if>
				</div>
			</c:forEach>
			<c:if test="${count%4==3}">
				<div class="list-container-empty" style="width: 240px; height: 360px; cursor:auto;"> </div>
			</c:if>			
			<c:if test="${count%4==2}">
				<div class="list-container-empty" style="width: 240px; height: 360px; cursor:auto;"> </div>
				<div class="list-container-empty" style="width: 240px; height: 360px; cursor:auto;"> </div>
			</c:if>
		</div>
		
	</div>	
</div>
