package kr.spring.adminRevenue.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.spring.adminRevenue.dao.AdminRevenueMapper;
import kr.spring.adminRevenue.vo.RevenueVO;

@Service
public class AdminRevenueServiceImpl implements AdminRevenueService{

	@Autowired
	private AdminRevenueMapper adminRevenueMapper;
	
	@Override
	public List<RevenueVO> selectList(Map<String, String> map) {
		return adminRevenueMapper.selectList(map);
	}

	@Override
	public List<HashMap<String, Object>> getGoodsRevenue(String startDate, String endDate) {
		return adminRevenueMapper.getGoodsRevenue(startDate, endDate);
	}

}
