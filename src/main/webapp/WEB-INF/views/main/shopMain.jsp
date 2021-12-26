<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>   
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/template/productList.css"> 
<link rel="stylesheet" href="https://unpkg.com/swiper@7/swiper-bundle.min.css"/>
<script src="https://unpkg.com/swiper@7/swiper-bundle.min.js"></script>
 <script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
   <style>
      .swiper {
        width: 100%;
        height: 300px;
      }

      .swiper-slide {
        text-align: center;
        font-size: 18px;
        background: #fff;

        /* Center slide text vertically */
        display: -webkit-box;
        display: -ms-flexbox;
        display: -webkit-flex;
        display: flex;
        -webkit-box-pack: center;
        -ms-flex-pack: center;
        -webkit-justify-content: center;
        justify-content: center;
        -webkit-box-align: center;
        -ms-flex-align: center;
        -webkit-align-items: center;
        align-items: center;
      }

      .swiper-slide img {
        display: block;
        width: 100%;
        height: 100%;
        object-fit: cover;
      }
      
      .slide1 {
      	background: url("${pageContext.request.contextPath}/resources/images/1.png");
      }
      .slide2 {
      	background: url("${pageContext.request.contextPath}/resources/images/2.png");
      }
      .slide3 {
      	background: url("${pageContext.request.contextPath}/resources/images/3.png");
      }
      .slide4 {
      	background: url("${pageContext.request.contextPath}/resources/images/4.png");
      }
      
      
    </style>  
    
<!-- 메인 시작 -->

<div id="main-width">
		    <div class="swiper mySwiper">
      <div class="swiper-wrapper">
        <div class="slide1 swiper-slide"></div>
        <div class="slide2 swiper-slide"></div>
        <div class="slide3 swiper-slide"></div>
        <div class="slide4 swiper-slide"></div>
      </div>
      <div class="swiper-button-next"></div>
      <div class="swiper-button-prev"></div>
      <div class="swiper-pagination"></div>
    </div>

    <!-- Initialize Swiper -->
    <script>
      var swiper = new Swiper(".mySwiper", {
        slidesPerView: 1,
        spaceBetween: 30,
        loop: true,
        pagination: {
          el: ".swiper-pagination",
          clickable: true,
          
        },
        autoplay: {
        	delay: 3500, 
        	disableOnInteraction: false,
        },

        navigation: {
          nextEl: ".swiper-button-next",
          prevEl: ".swiper-button-prev",
        },
      });
      </script>
 		<div class="list-container mt-3">
			<c:forEach var="product" items="${list}">
				<div class="list-content" onclick="location.href='productDetail.do?p_no=${product.p_no}'">
					<div class="image-ani">
					<img src="${pageContext.request.contextPath}/product/photoView.do?p_no=${product.p_no}" width="230" height="230">
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
			<c:if test="${count%5==2}">
				<div class="list-container-empty" style="width: 230px; height: 350px; cursor:auto;"> </div>
				<div class="list-container-empty" style="width: 230px; height: 350px; cursor:auto;"> </div>
				<div class="list-container-empty" style="width: 230px; height: 350px; cursor:auto;"> </div>
			</c:if>			
			<c:if test="${count%5==3}">
				<div class="list-container-empty" style="width: 230px; height: 350px; cursor:auto;"> </div>
				<div class="list-container-empty" style="width: 230px; height: 350px; cursor:auto;"> </div>
			</c:if>
			<c:if test="${count%5==4}">
				<div class="list-container-empty" style="width: 230px; height: 350px; cursor:auto;"> </div>
			</c:if>
		</div>
</div>

<!-- 메인 끝 -->