package kr.spring.review.service;

import java.util.List;
import java.util.Map;

import kr.spring.order.vo.OrderListVO;
import kr.spring.review.vo.ReviewVO;

public interface ReviewService {
	public List<OrderListVO> selectReviewAvaliavle(int mem_num);
	public List<OrderListVO> selectReviewWritten(int mem_num);
	
	public int selectRowCount(Map<String,Object> map);
	public void insertReview(ReviewVO review);
	public ReviewVO selectReview(int review_no);
}
