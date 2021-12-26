package kr.spring.delivery.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.delivery.vo.DeliveryVO;

public interface DeliveryMapper {
	@Insert("insert into delivery(delivery_no,order_no) "
			+ "VALUES(delivery_no_seq.nextval,#{order_no})")
	public void insertOrderDelivery(String order_no);
	
	@Update("UPDATE delivery SET d_status_num = 7 WHERE order_no = #{order_no}")
	public void updateOrderDeilveryCancel(String order_no);
	
	@Update("UPDATE delivery SET d_status_num = 4 WHERE order_no = #{order_no}")
	public void updateOrderDeilveryConfirm(String order_no);
	
	@Update("UPDATE delivery SET d_status_num = 5 WHERE order_no = #{order_no}")
	public void updateOrderDeilveryRefund(String order_no);
	
	@Update("UPDATE delivery SET d_status_num = 6 WHERE order_no = #{order_no}")
	public void updateOrderDeilveryExchange(String order_no);
	
	@Select("SELECT * from delivery where order_no = ${order_no}")
	public DeliveryVO selectOrderDelivery(String order_no);
	
	//============== 배송관리 ==============
	public int getDeliveryCount(Map<String,Object> map);
	
	public List<DeliveryVO> getDeliveryList(Map<String,Object> map);
	
	public DeliveryVO selectDelivery(int delivery_no);
	
	@Update("UPDATE delivery SET d_status_num=2, tracking_num=#{tracking_num} WHERE delivery_no=#{delivery_no}")
	public void insertTracking(DeliveryVO deliveryVO);
	
	@Update("UPDATE delivery SET d_status_num=#{d_status_num} WHERE delivery_no=#{delivery_no}")
	public void updateStatus(DeliveryVO deliveryVO);
	
	//============== 교환반품관리 ==============
	public int getReturnCount(Map<String,Object> map);
	
	public List<DeliveryVO> getReturnList(Map<String,Object> map);
}
