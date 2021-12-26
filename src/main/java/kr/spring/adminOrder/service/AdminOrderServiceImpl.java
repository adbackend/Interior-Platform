package kr.spring.adminOrder.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.adminOrder.dao.AdminOrderMapper;
import kr.spring.adminOrder.vo.AdminOrderVO;

@Service
@Transactional
public class AdminOrderServiceImpl implements AdminOrderService{
	
	@Autowired
	private AdminOrderMapper adminOrderMapper;
	
	@Override
	public int getOrderCount(Map<String,Object> map) {
		return adminOrderMapper.getOrderCount(map);
	}
	
	@Override
	public List<AdminOrderVO> getOrderList(Map<String, Object> map) {
		return adminOrderMapper.getOrderList(map);
	}
	
	@Override
	public AdminOrderVO selectOrder(String order_no) {
		return adminOrderMapper.selectOrder(order_no);
	}
	
}
