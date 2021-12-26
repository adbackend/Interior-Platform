package kr.spring.adminOrder.service;

import java.util.List;
import java.util.Map;

import kr.spring.adminOrder.vo.AdminOrderVO;

public interface AdminOrderService {
	
	//============== 주문관리 ==============
	public int getOrderCount(Map<String,Object> map);
	public List<AdminOrderVO> getOrderList(Map<String,Object> map);
	public AdminOrderVO selectOrder(String order_no);
}
