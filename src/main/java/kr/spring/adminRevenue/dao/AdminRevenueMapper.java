package kr.spring.adminRevenue.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import kr.spring.adminRevenue.vo.RevenueVO;

public interface AdminRevenueMapper {
	
	public List<RevenueVO> selectList(Map<String, String> map);

	public List<HashMap<String, Object>> getGoodsRevenue(@Param("startDate") String startDate, @Param("endDate") String endDate);

}
