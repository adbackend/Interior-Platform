package kr.spring.adminRevenue.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.spring.adminRevenue.vo.RevenueVO;

public interface AdminRevenueService {
	
	public List<RevenueVO> selectList(Map<String,String> map); //매출관리
	
	public List<HashMap<String, Object>> getGoodsRevenue(String startDate, String endDate);

}
