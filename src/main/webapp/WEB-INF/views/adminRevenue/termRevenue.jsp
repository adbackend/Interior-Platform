<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- CSS file -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/revenue_style.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">

	window.onload = function(){
		var form = document.getElementById('search_form');
		
		app.initDate();
		google.charts.load('current', {'packages':['corechart']});
		google.charts.setOnLoadCallback(app.drawSheetAndChart);
	};
	
	var app = {
		drawSheetAndChart: function() {
			let param = {};
			let startDate = document.getElementById('startDate');
			let endDate = document.getElementById('endDate');
			param['startDate'] = startDate.value;
			param['endDate'] = endDate.value;
			
			$.ajax({
				url:'${pageContext.request.contextPath}/admin/getGoodsRevenue.do',
				data: param,
				async: true,
				success: function(data) {
					drawGoodsRevenueChart(data);
					drawGoodsRevenueSheet(data);
				},
				error: function(err, err1, err2) {
					console.log(err);
					console.log(err1);
					console.log(err2);
				}
			});
		}, dateSubtractAndSearch: function(num, type) {
			let startDate = document.getElementById('startDate').valueAsDate;
			let endDate = document.getElementById('endDate').valueAsDate;
			
			if (type === "DATE") {
				startDate.setDate(endDate.getDate() - num);
			} else {
				startDate.setMonth(endDate.getMonth() - num);	
			}
			
			document.getElementById('startDate').valueAsDate = startDate;

			this.drawSheetAndChart();
		}, initDate: function() {
			// 시작일, 종료일 오늘로 초기값 설정
			document.getElementById('startDate').valueAsDate = new Date();
			document.getElementById('endDate').valueAsDate = new Date();
		}, initDateAndSearch: function() {
			this.initDate();
			this.drawSheetAndChart();
		}
	};
	
	var chart = null;
	
	function drawGoodsRevenueChart(goodsRevenues) {
		if (chart == null)
			chart = new google.visualization.PieChart(document.getElementById('chart_div'));	
		
		// Create the data table.
		var data = new google.visualization.DataTable();
		data.addColumn('string', '상품명');
		data.addColumn('number', '판매수량');
		   
		let goodsRevenueChartDatas = [];
		   
		for (let goodsRevenue of goodsRevenues) {
			let goodsRevenueChartData = [goodsRevenue['PRODUCT_NAME'], goodsRevenue['TOTAL_ORDER_COUNT']];
			goodsRevenueChartDatas.push(goodsRevenueChartData);
		}
   
		data.addRows(goodsRevenueChartDatas);

		// Set chart options
		var options = {'title':'상위 TOP 10 상품(판매수량)', 'pieHole': 0.4, 'height': 400};

		chart.draw(data, options);
	}
		
	function drawGoodsRevenueSheet(goodsRevenues) {

		// sheet clear
		document.getElementById('GoodsRevenueSheetBody').innerHTML = "";
		
		if (goodsRevenues.length == 0) {
			document.getElementById('GoodsRevenueSheetBody').innerHTML += "<tr><td colspan=7>매출 정보가 없습니다.</td></tr>";
		} else {
			for (let goodsRevenue of goodsRevenues) {
				let rowHtml = "<tr>" +
								"<td>" + goodsRevenue['RANK'] + "</td>" + 
								"<td>" + goodsRevenue['CATEGORY_NAME'] + "</td>" + 
								"<td>" + goodsRevenue['PRODUCT_CODE'] + "</td>" + 
								"<td>" + goodsRevenue['PRODUCT_NAME'] + "</td>" + 
								"<td>" + goodsRevenue['PRODUCT_PRICE'] + "</td>" + 
								"<td>" + goodsRevenue['TOTAL_ORDER_COUNT'] + "</td>" + 
								"<td>" + goodsRevenue['TOTAL_ORDER_PRICE'] + "</td>" + 
							"</tr>";
				document.getElementById('GoodsRevenueSheetBody').innerHTML += rowHtml;
			}
		}
	}
</script>
<!-- 중앙 내용 시작 -->
<div id="admin-main-width">
	<h3 id="header-3">매출관리</h3>
	<!--검색 조건 -->
	<div id="normal-width">
		<form  method="get" id="search_form">
			<table class="table table-borderless table-sm">
				<tr>
					<th>기간</th>
					<td>
						<input type="button" value="오늘" onclick="javascript:app.initDateAndSearch()">
						<input type="button" value="3일" onclick="javascript:app.dateSubtractAndSearch(3, 'DATE')">
						<input type="button" value="7일" onclick="javascript:app.dateSubtractAndSearch(7, 'DATE')">
						<input type="button" value="1개월" onclick="javascript:app.dateSubtractAndSearch(1, 'MONTH')">
						<input type="button" value="3개월" onclick="javascript:app.dateSubtractAndSearch(3, 'MONTH')">
						<input type="button" value="6개월" onclick="javascript:app.dateSubtractAndSearch(6, 'MONTH')">
						<input type="date" id="startDate" name="start_date" size="15" style="width:145px"> ~ 
						<input type="date" id="endDate" name="end_date" size="15" style="width:145px">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div>
		<div style="margin: 0 auto;">
			<div id="chart_div" style="width:40%; margin: 0 auto"></div>
		</div>
		<div>
		<table class="table table-hover table-bordered" id="GoodsRevenueSheet">
			<thead>
				<tr>
					<th>순위</th>
					<th>카테고리</th>
					<th>상품코드</th>
					<th>상품명</th>
					<th>판매가</th>
					<th>판매수량</th>
					<th>총 판매 합계</th>
				</tr>
			<thead>
			<tbody id="GoodsRevenueSheetBody">

			</tbody>
		</table>
		</div>
	</div>
</div>
<!-- 중앙 내용 끝 -->