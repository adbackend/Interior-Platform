package kr.spring.delivery.service;

import java.util.List;
import java.util.Map;

import kr.spring.delivery.vo.DeliveryVO;

public interface DeliveryService {
	public void insertOrderDelivery(String order_no);
	public void updateOrderDeilveryCancel(String order_no);
	public void updateOrderDeilveryConfirm(String order_no);
	public void updateOrderDeilveryRefund(String order_no);
	public void updateOrderDeilveryExchange(String order_no);
	public DeliveryVO selectOrderDelivery(String order_no);
	
	//============== 배송관리 ==============
	public int getDeliveryCount(Map<String,Object> map);
	public List<DeliveryVO> getDeliveryList(Map<String,Object> map);
	public DeliveryVO selectDelivery(int delivery_no);
	public void insertTracking(DeliveryVO deliveryVO);
	public void updateStatus(DeliveryVO deliveryVO);
	
	//============== 교환반품관리 ==============
	public int getReturnCount(Map<String,Object> map);
	public List<DeliveryVO> getReturnList(Map<String,Object> map);
}
