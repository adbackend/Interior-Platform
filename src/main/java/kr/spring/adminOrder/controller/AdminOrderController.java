package kr.spring.adminOrder.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.adminMember.service.AdminMemberService;
import kr.spring.adminMember.vo.AdminMemberVO;
import kr.spring.adminOrder.service.AdminOrderService;
import kr.spring.adminOrder.vo.AdminOrderVO;
import kr.spring.delivery.service.DeliveryService;
import kr.spring.order.service.OrderService;
import kr.spring.util.PagingUtil;

@Controller
public class AdminOrderController {
	private static final Logger logger = LoggerFactory.getLogger(AdminOrderController.class);
	private int rowCount = 10;	//표시할 데이터 수
	private int pageCount = 10;	//표시할 페이지 수

	@Autowired
	private AdminOrderService adminOrderService;
	@Autowired
	private OrderService orderService;	
	@Autowired
	private AdminMemberService adminMemberService;
	@Autowired
	private DeliveryService deliveryService;
	
	//자바빈(VO) 초기화
	@ModelAttribute
	public AdminOrderVO initCommand() {
		return new AdminOrderVO();
	}
	
	//주문 목록
	@RequestMapping("/admin/orderList.do")
	public ModelAndView adminOrderList(@RequestParam(value="pageNum", defaultValue="1") int currentPage,
									   @RequestParam(value="start_date", defaultValue="") String start_date,
									   @RequestParam(value="end_date", defaultValue="") String end_date,
									   @RequestParam(value="d_status_num", defaultValue="") String d_status_num,
									   @RequestParam(value="keyfield", defaultValue="") String keyfield,
									   @RequestParam(value="keyword", defaultValue="") String keyword,
									   @RequestParam(value="orderby", defaultValue="") String orderby) {
		logger.debug("<<adminOrderList 호출>> currentPage : " + currentPage + ", d_status_num : " + d_status_num + 
				     ", keyfield : " + keyfield +", keyword : " + keyword + "start_date : " + start_date + "end_date : " + end_date);
		
		//검색 조건
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("d_status_num", d_status_num);
		map.put("start_date", start_date);
		map.put("end_date", end_date);
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		map.put("orderby", orderby);
		
		//결과데이터 수
		int count = adminOrderService.getOrderCount(map);
		//부가적인 key
		String addKey = "&start_date=" + start_date + "&end_date=" + end_date + "&d_status_num=" + d_status_num + "&orderby=" + orderby; 
		
		PagingUtil page = new PagingUtil(keyfield, keyword, currentPage, count, rowCount, pageCount, "orderList.do", addKey);
		
		List<AdminOrderVO> list = null;
		if(count > 0) {
			map.put("start", page.getStartCount());
			map.put("end", page.getEndCount());
			
			list = adminOrderService.getOrderList(map);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("adminOrderList");
		mav.addObject("count", count);
		mav.addObject("list", list);
		mav.addObject("pagingHtml", page.getPagingHtml());
		
		//검색조건 유지를 위한 데이터
		mav.addObject("start_date", start_date);
		mav.addObject("end_date", end_date);
		mav.addObject("d_status_num", d_status_num);
		mav.addObject("keyfield", keyfield);
		mav.addObject("keyword", keyword);
		mav.addObject("orderby", orderby);
		
		return mav;
	}
	
	//주문 상세
	@RequestMapping("/admin/orderDetail.do")
	public ModelAndView adminOrderDetail(@RequestParam String order_no, HttpServletRequest request) {
		logger.debug("<<adminOrderDetail 호출>> order_no : " + order_no);
		
		//주문 존재여부 체크
		AdminOrderVO adminOrder = adminOrderService.selectOrder(order_no);
		
		ModelAndView mav = new ModelAndView();
		if(adminOrder == null) {
			mav.setViewName("common/resultView");
			mav.addObject("message", "존재하지 않는 주문입니다.");
			mav.addObject("url", request.getContextPath() + "/admin/orderList.do");
		}
		
		mav.setViewName("adminOrderDetail");
		mav.addObject("adminOrder", adminOrder);
		mav.addObject("list", orderService.selectOrderDetailProduct(order_no));
		mav.addObject("adminMember", adminMemberService.selectMember(adminOrder.getMem_num()));
		
		return mav;
	}
	
	//주문 취소 폼 - 최고관리자 인증
	@GetMapping("/admin/orderCancel.do")
	public String adminOrderCancelForm(@RequestParam String order_no, HttpServletRequest request, Model model) {
		logger.debug("<<adminOrderCancelForm 호출>> order_no : " + order_no);
		
		//주문 존재여부 체크
		AdminOrderVO adminOrderVO = adminOrderService.selectOrder(order_no);
		if(adminOrderVO == null) {
			model.addAttribute("message", "존재하지 않는 주문입니다.");
			model.addAttribute("url", request.getContextPath() + "/admin/orderList.do");
			
			return "common/resultView";
		}
		
		//배송 여부 체크
		if(!(adminOrderVO.getD_status_name().equals("결제완료") || adminOrderVO.getD_status_name().equals("배송준비중"))) {
			model.addAttribute("message", "이미 배송이 시작된 주문은 취소할 수 없습니다.");
			model.addAttribute("url", request.getContextPath() + "/admin/orderList.do");
			
			return "common/resultView";
		}
		
		//취소할 주문번호
		model.addAttribute("order_no", order_no);
		
		return "adminOrderCancel";
	}
	
	//주문 취소 처리
	@PostMapping("/admin/orderCancel.do")
	public String adminOrderCancel(AdminMemberVO adminMemberVO, HttpSession session, HttpServletRequest request, Model model) {
		logger.debug("<<adminOrderCancelForm 호출>> adminMemberVO : " + adminMemberVO);
		
		String mem_id=(String)session.getAttribute("mem_id"); //로그인한 아이디
		AdminMemberVO dbMember = adminMemberService.selectCheckMember(mem_id); //입력한 아이디,비밀번호,취소할 주문번호
		boolean check = false;
		
		//로그인한 아이디와 입력한 아이디가 일치하는 경우
		if(dbMember != null && dbMember.getMem_id().equals(mem_id)) {
			//비밀번호 일치 여부 체크
			check = dbMember.isCheckedPassword(adminMemberVO.getMem_passwd());
		}
		
		if(check) {
			//주문정보 삭제
			deliveryService.updateOrderDeilveryCancel(adminMemberVO.getManage_num());

			model.addAttribute("message", "주문취소가 완료되었습니다.");
			model.addAttribute("url", request.getContextPath() + "/admin/orderList.do");
	
		}else {
			model.addAttribute("message", "아이디 또는 비밀번호 일치하지 않습니다.");
			model.addAttribute("url", request.getContextPath() + "/admin/orderCancel.do?order_no=" + adminMemberVO.getManage_num());
		}
		
		return "common/resultView";
	}
	
}
