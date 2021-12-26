package kr.spring.adminRevenue.service;

import javax.inject.Inject;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GoogleChartServiceImpl implements GoogleChartService {

	@Inject
	AdminRevenueService adminRevenueService;
	
	// {"변수명": [{},{},{}], "변수명":"값"}
	@Override
	public JSONObject getChartData() {
		
//		List<RevenueVO> items = adminRevenueService.selectList();
		
		//리턴할 json 객체
		JSONObject data = new JSONObject(); // {}
		
		//json의 컬럼 객체
		JSONObject col1 = new JSONObject();
		JSONObject col2 = new JSONObject();
		
		//json배열 객체
		JSONArray title = new JSONArray();
		col1.put("", "상품");
		
		return null;
	}
}
