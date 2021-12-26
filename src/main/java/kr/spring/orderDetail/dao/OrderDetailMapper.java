package kr.spring.orderDetail.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import kr.spring.orderDetail.vo.OrderDetailVO;

public interface OrderDetailMapper {
	 @Insert("insert into sorder_Detail(order_d_no, order_no, p_no, order_d_amount) "
		  		+ "select sorder_detail_seq.nextval, #{order_no},p_no, cart_amount "
		  		+ "from cart where mem_num =#{mem_num}")
	public void insertOrderDetail(OrderDetailVO orderDetailVO);
	 
	@Select("select * from sorder_detail where order_no = #{order_no}")
	public List<OrderDetailVO> selectOrderDetail(String order_no);
	
	@Insert("insert into sorder_Detail(order_d_no, order_no, p_no, order_d_amount) "
			+ "VALUES(sorder_detail_seq.nextval, #{param1}, #{param2}, #{param3})")
	public void insertDirectOrderDetail(String param1, int param2, int param3);
	
	
}
