package kr.spring.order.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import kr.spring.order.vo.OrderAllVO;
import kr.spring.order.vo.OrderListVO;
import kr.spring.order.vo.OrderVO;

public interface OrderMapper {
	
	  @Insert("Insert into sorder(order_no,mem_num,order_zipcode,order_address1,order_address2,order_name, order_phone,order_method, order_pay, delivery_pay) "
	  		+ "VALUES(#{order_no},#{mem_num}, #{order_zipcode}, #{order_address1}, #{order_address2},#{order_name},#{order_phone}, #{order_method},#{order_pay},#{delivery_pay})")
	  public void insertOrder(OrderVO order);
	  
	  @Select("select count(*) FROM sorder s, sorder_detail d "
		  		+ "WHERE s.order_no=d.order_no and s.mem_num=#{mem_num} "
		  		+ "GROUP BY s.order_no,s.order_date ORDER BY s.order_date desc")
	  public List<OrderAllVO> countOrderProduct(int mem_num);
		  
	  @Select("select * from sorder s, sorder_detail sd, product p where s.order_no=sd.order_no and sd.p_no = p.p_no and s.order_no=#{order_no}")
	  public List<OrderAllVO> selectOrderDetailProduct(String order_no);
		  
	  @Select("select * from sorder s, delivery d, delivery_status ds "
		  		+ "where s.order_no = d.order_no and d.d_status_num=ds.d_status_num "
		  		+ "and s.order_no=#{order_no}")
	  public OrderAllVO selectOrderDetailInfo(String order_no);
	 
	  public List<OrderListVO> selectAllOrder(Map<String,Object> map);  
	  public List<OrderListVO> selectCancelOrder(Map<String,Object> map);	  
	  public List<OrderListVO> selectRefundOrder(Map<String,Object> map);
	  public List<OrderListVO> selectExchageOrder(Map<String,Object> map);	  
	  public List<OrderListVO> selectConfirmOrder(Map<String,Object> map);
	  
	  public int selectCancelOrderCount(int mem_num);
	  public int selectAllOrderCount(int mem_num);
	  public int selectRefundOrderCount(int mem_num);
	  public int selectExchageOrderCount(int mem_num);
	  public int selectConfirmOrderCount(int mem_num);
	  
	
	  
}
