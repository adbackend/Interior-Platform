<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- <!DOCTYPE html> -->
<!-- <html> -->
<!-- <head> -->
<!-- <meta charset="UTF-8"> -->
<!-- <title>Insert title here</title> -->
<!-- </head> -->

<!-- <body> -->

<!-- </body> -->
<!-- </html> -->

<!-- 구글 차트 호출을 위한 js 파일 -->
<script type="text/javascritp" src="https://www.google.com/jsapi"></script>
<script>
	//구글 차트 라이브러리 로딩
	google.load('visualization','1',{
		'packages':['corechart']
	})
	
	//로딩이 완료되면 drawChart 함수 호출
	google.setOnLoadCallback(drawChart);
	
	function drawChart(){
		var jsonData = $.ajax({
			url:"",
			dataType:"json",
			async:false
		}).responseText;
		console.log(jsonData);
		
		//데이터를 테이블로 생성
		var data = new google.visualization.DataTable(jsonData);
		
// 		var chart = new google.visualization.PieChart(
// 				document.getElementById('chart_div'));  //파이차트
		
// 		var chart = new google.visualization.LineChart(
// 				document.getElementById('chart_div')); //라인차트
				
		var chart = new google.visualization.ColumnChart(
				document.getElementById('chart_div')); //막대 그래프
		
		chart.draw(data,{
			title:"차트예제",
			width:600,
			height:440
		});
	}
</script>