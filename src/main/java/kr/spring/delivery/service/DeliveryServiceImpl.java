package kr.spring.delivery.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.delivery.dao.DeliveryMapper;
import kr.spring.delivery.vo.DeliveryVO;

@Service
@Transactional
public class DeliveryServiceImpl implements DeliveryService {
	
	@Autowired
	DeliveryMapper deliveryMapper;

	@Override
	public void insertOrderDelivery(String order_no) {
		deliveryMapper.insertOrderDelivery(order_no);		
	}

	@Override
	public void updateOrderDeilveryCancel(String order_no) {
		deliveryMapper.updateOrderDeilveryCancel(order_no);	
	}

	@Override
	public void updateOrderDeilveryConfirm(String order_no) {
		deliveryMapper.updateOrderDeilveryConfirm(order_no);		
	}

	@Override
	public void updateOrderDeilveryRefund(String order_no) {
		deliveryMapper.updateOrderDeilveryRefund(order_no);		
	}

	@Override
	public void updateOrderDeilveryExchange(String order_no) {
		deliveryMapper.updateOrderDeilveryExchange(order_no);		
	}

	@Override
	public DeliveryVO selectOrderDelivery(String order_no) {
		return deliveryMapper.selectOrderDelivery(order_no);
	}
	
	//============== 배송관리 ==============
	@Override
	public int getDeliveryCount(Map<String, Object> map) {
		return deliveryMapper.getDeliveryCount(map);
	}

	@Override
	public List<DeliveryVO> getDeliveryList(Map<String, Object> map) {
		return deliveryMapper.getDeliveryList(map);
	}

	@Override
	public DeliveryVO selectDelivery(int delivery_no) {
		return deliveryMapper.selectDelivery(delivery_no);
	}

	@Override
	public void insertTracking(DeliveryVO deliveryVO) {
		deliveryMapper.insertTracking(deliveryVO);
	}

	@Override
	public void updateStatus(DeliveryVO deliveryVO) {
		deliveryMapper.updateStatus(deliveryVO);
	}

	@Override
	public int getReturnCount(Map<String, Object> map) {
		return deliveryMapper.getReturnCount(map);
	}

	@Override
	public List<DeliveryVO> getReturnList(Map<String, Object> map) {
		return deliveryMapper.getReturnList(map);
	}
}
