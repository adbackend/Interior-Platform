package kr.spring.order.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.order.dao.OrderMapper;
import kr.spring.order.vo.OrderAllVO;
import kr.spring.order.vo.OrderListVO;
import kr.spring.order.vo.OrderVO;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	OrderMapper orderMapper;

	@Override
	public void insertOrder(OrderVO order) {
		orderMapper.insertOrder(order);
	}

	@Override
	public List<OrderAllVO> countOrderProduct(int mem_num) {
		return orderMapper.countOrderProduct(mem_num);
	}

	@Override
	public List<OrderListVO> selectRefundOrder(Map<String,Object> map) {
		return orderMapper.selectRefundOrder(map);
	}

	@Override
	public List<OrderListVO> selectConfirmOrder(Map<String,Object> map) {
		return orderMapper.selectConfirmOrder(map);
	}

	@Override
	public List<OrderListVO> selectExchageOrder(Map<String,Object> map) {
		return orderMapper.selectExchageOrder(map);
	}

	@Override
	public List<OrderAllVO> selectOrderDetailProduct(String order_no) {
		return orderMapper.selectOrderDetailProduct(order_no);
	}

	@Override
	public OrderAllVO selectOrderDetailInfo(String order_no) {
		return orderMapper.selectOrderDetailInfo(order_no);
	}

	@Override
	public List<OrderListVO> selectCancelOrder(Map<String,Object> map) {
		return orderMapper.selectCancelOrder(map);
	}

	@Override
	public int selectCancelOrderCount(int mem_num) {
		return orderMapper.selectCancelOrderCount(mem_num);
	}

	@Override
	public List<OrderListVO> selectAllOrder(Map<String, Object> map) {
		return orderMapper.selectAllOrder(map);
	}

	@Override
	public int selectAllOrderCount(int mem_num) {
		return orderMapper.selectAllOrderCount(mem_num);
	}

	@Override
	public int selectRefundOrderCount(int mem_num) {
		return orderMapper.selectRefundOrderCount(mem_num);
	}

	@Override
	public int selectExchageOrderCount(int mem_num) {
		return orderMapper.selectExchageOrderCount(mem_num);
	}

	@Override
	public int selectConfirmOrderCount(int mem_num) {
		return orderMapper.selectConfirmOrderCount(mem_num);
	}

}
