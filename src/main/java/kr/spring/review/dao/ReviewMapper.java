package kr.spring.review.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import kr.spring.order.vo.OrderListVO;
import kr.spring.review.vo.ReviewVO;

public interface ReviewMapper {
	
	@Select("select o.ORDER_NO, o.ORDER_DATE ,o.MEM_NUM, p.P_NAME, p.P_NO, p.P_PRICE "
			+ "from SORDER o join SORDER_DETAIL d on o.ORDER_NO = d.ORDER_NO "
			+ "AND o.mem_num=#{mem_num} join PRODUCT p on d.P_NO = p.P_NO "
			+ "left outer join REVIEW r on o.ORDER_NO = r.ORDER_NO "
			+ "and d.P_NO = r.P_NO join DELIVERY l on o.ORDER_NO = l.ORDER_NO where l.D_STATUS_NUM = 4 and r.REVIEW_NO is null ORDER BY o.order_date desc")
	public List<OrderListVO> selectReviewAvaliavle(int mem_num);
	
	@Select("select o.ORDER_NO, o.ORDER_DATE , p.P_NAME, p.P_NO, p.P_PRICE "
			+ "from SORDER o join SORDER_DETAIL d on o.ORDER_NO = d.ORDER_NO "
			+ "AND o.mem_num=#{mem_num} join PRODUCT p on d.P_NO = p.P_NO "
			+ "left outer join REVIEW r on o.ORDER_NO = r.ORDER_NO "
			+ "and d.P_NO = r.P_NO join DELIVERY l on o.ORDER_NO = l.ORDER_NO where l.D_STATUS_NUM = 4 and r.REVIEW_NO is not null  ORDER BY o.order_date desc")
	public List<OrderListVO> selectReviewWritten(int mem_num);
	
	@Insert("INSERT INTO review (review_no,p_no,review_content,review_image,review_image_name,mem_num) VALUES (review_seq.nextval,#{p_no},#{review_content},#{review_image},#{review_image_name},#{mem_num})")
	public void insertReview(ReviewVO review);
	public int selectRowCount(Map<String,Object> map);

	public ReviewVO selectReview(int review_no);
}
