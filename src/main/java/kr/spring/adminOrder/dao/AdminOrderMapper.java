package kr.spring.adminOrder.dao;

import java.util.List;
import java.util.Map;

import kr.spring.adminOrder.vo.AdminOrderVO;

public interface AdminOrderMapper {

	//============== 주문관리 ==============
	public int getOrderCount(Map<String,Object> map);
	
	public List<AdminOrderVO> getOrderList(Map<String,Object> map);
	
	public AdminOrderVO selectOrder(String order_no);
}