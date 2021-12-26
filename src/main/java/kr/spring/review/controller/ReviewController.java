package kr.spring.review.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.adminOrder.vo.AdminOrderVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.review.dao.ReviewMapper;
import kr.spring.review.service.ReviewService;
import kr.spring.review.vo.ReviewVO;
import kr.spring.util.PagingUtil;
import kr.spring.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.order.vo.OrderListVO;
import kr.spring.review.service.ReviewService;

@Controller
public class ReviewController {
	
	@Autowired
	ReviewService reviewService;
	
	private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
	private int rowCount = 20;
	private int pageCount = 10;
	
	//자바빈(VO) 초기화
	@ModelAttribute
	public ReviewVO initCommand() {
		return new ReviewVO();
	}
	
	
	  //글쓰기 - 폼 호출
	  @GetMapping("/review/write.do") 
	  public String form() { 
		  return "reviewWrite";//타일스 식별자 
	  }
	 
	
	//글쓰기 - 전송된 데이터 처리
	@PostMapping("/review/write.do")
	public String submit(@Valid ReviewVO reviewVO, BindingResult result,
			             HttpSession session, HttpServletRequest request) {
		
		logger.debug("<<상품 후기 작성>> : " + reviewVO);
		
		//유효성 체크 결과 오류가 있으면 폼을 호출
		if(result.hasErrors()) {
			return form();
		}
		
		//회원번호를 세팅
		reviewVO.setMem_num((Integer)session.getAttribute("mem_num"));
		//글쓰기
		reviewService.insertReview(reviewVO);
		
		return "shopMain";
	}
	
	//이미지 출력
		@RequestMapping("/review/imageView.do")
		public ModelAndView viewImage(@RequestParam int review_no) {
			
			ReviewVO review = reviewService.selectReview(review_no);
			
			ModelAndView mav = new ModelAndView();
			mav.setViewName("imageView");
			                //속성명         속성값(byte[]의 데이터)     
			mav.addObject("imageFile", review.getReview_image());
			mav.addObject("filename", review.getReview_image_name());
			
			return mav;
		}
	
	@GetMapping("/review/reviewAvaliable.do")
	public ModelAndView reviewAvaliavleForm(HttpSession session) {
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		List<OrderListVO> list = reviewService.selectReviewAvaliavle(mem_num);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("reviewAvaliable");
		mav.addObject("list",list);
		return mav;
	}
	
	@GetMapping("/review/reviewWritten.do")
	public ModelAndView reviewWrittenForm(HttpSession session) {
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		List<OrderListVO> list = reviewService.selectReviewWritten(mem_num);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("reviewWritten");
		mav.addObject("list",list);
		return mav;
	}
}
