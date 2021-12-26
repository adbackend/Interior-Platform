package kr.spring.delivery.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import kr.spring.adminOrder.controller.AdminOrderController;
import kr.spring.adminOrder.service.AdminOrderService;
import kr.spring.adminOrder.vo.AdminOrderVO;
import kr.spring.delivery.service.DeliveryService;
import kr.spring.delivery.vo.DeliveryVO;
import kr.spring.util.PagingUtil;

@Controller
public class DeliveryController {
	private static final Logger logger = LoggerFactory.getLogger(AdminOrderController.class);
	private int rowCount = 10;	//표시할 데이터 수
	private int pageCount = 10;	//표시할 페이지 수
	
	@Autowired
	private DeliveryService deliveryService;
	@Autowired
	private AdminOrderService adminOrderService;
	
	//자바빈(VO) 초기화
	@ModelAttribute
	public DeliveryVO initCommand() {
		return new DeliveryVO();
	}
	
	//배송 목록
	@RequestMapping("/admin/deliveryList.do")
	public ModelAndView adminDeliveryList(@RequestParam(value="pageNum", defaultValue="1") int currentPage,
										  @RequestParam(value="d_status_num", defaultValue="") String d_status_num,
										  @RequestParam(value="keyfield", defaultValue="") String keyfield,
										  @RequestParam(value="keyword", defaultValue="") String keyword) {
		
		logger.debug("<<adminDeliveryList 호출>> currentPage : " + currentPage + ", d_status_num : " + d_status_num + ", keyfield : " + keyfield +", keyword : " + keyword);
		
		//검색 조건
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("d_status_num", d_status_num);
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		
		//결과데이터 수
		int count = deliveryService.getDeliveryCount(map);
		//부가적인 key
		String addKey = "&d_status_num=" + d_status_num; 
		
		PagingUtil page = new PagingUtil(keyfield, keyword, currentPage, count, rowCount, pageCount, "deliveryList.do", addKey);
		
		List<DeliveryVO> list = null;
		if(count > 0) {
			map.put("start", page.getStartCount());
			map.put("end", page.getEndCount());
			
			list = deliveryService.getDeliveryList(map);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("adminDeliveryList");
		mav.addObject("count", count);
		mav.addObject("list", list);
		mav.addObject("pagingHtml", page.getPagingHtml());
		
		//검색조건 유지를 위한 데이터
		mav.addObject("d_status_num", d_status_num);
		mav.addObject("keyfield", keyfield);
		mav.addObject("keyword", keyword);
		
		return mav;
	}
	
	//송장번호 등록 폼
	@GetMapping("/admin/deliveryTrack.do")
	public String DeliveryTrackingForm(@RequestParam int delivery_no, HttpServletRequest request, Model model) {
		logger.debug("<<DeliveryTrackingForm 호출>> delivery_no : " + delivery_no);
		
		//주문 존재여부 체크
		DeliveryVO deliveryVO = deliveryService.selectDelivery(delivery_no);
		
		if(deliveryVO == null) {
			model.addAttribute("message", "존재하지 않는 주문번호입니다.");
			model.addAttribute("url", request.getContextPath() + "/admin/deliveryList.do");
			
			return "common/resultView";
		}
		
		//송장번호 등록 여부 체크
		if(deliveryVO.getTracking_num() != null) {
			model.addAttribute("message", "이미 송장번호가 등록되어 있습니다.");
			model.addAttribute("url", request.getContextPath() + "/admin/deliveryList.do");
			
			return "common/resultView";
		}
		
		AdminOrderVO adminOrderVO = adminOrderService.selectOrder(deliveryVO.getOrder_no());
		model.addAttribute("deliveryVO", deliveryVO);
		model.addAttribute("adminOrderVO", adminOrderVO);
		
		return "adminDeliveryTrack";
	}
	
	//송장번호 등록 처리
	@PostMapping("/admin/deliveryTrack.do")
	public String DeliveryTracking(DeliveryVO deliveryVO) {
		logger.debug("<<DeliveryTracking 호출>> deliveryVO : " + deliveryVO);
		
		//송장번호 등록
		deliveryService.insertTracking(deliveryVO);
		
		return "redirect:/admin/deliveryList.do";
	}
	
	
	//배송상태 변경
	@RequestMapping("/admin/dStatusUpdate.do")
	public String adminDeliveryStatusUpdate(@RequestParam int delivery_no, @RequestParam int d_status_num, HttpServletRequest request, Model model) {
		logger.debug("<<adminDeliveryStatusUpdate 호출>> delivery_no : " + delivery_no + ", d_status_num : " + d_status_num);
		
		DeliveryVO deliveryVO = new DeliveryVO();
		deliveryVO.setDelivery_no(delivery_no);
		deliveryVO.setD_status_num(d_status_num);
		
		DeliveryVO dbDelivery = deliveryService.selectDelivery(delivery_no);
		int before = dbDelivery.getD_status_num(); //이전 상태번호
		int after = deliveryVO.getD_status_num();  //변경할 상태번호
		
		//배송상태가 1단계 전후인지 체크
		if((after-before) == 1 || before >= 0 && after <= 3 ) {
			//배송상태 변경
			deliveryService.updateStatus(deliveryVO);
			
			return "redirect:/admin/deliveryList.do";
		}
		
		//반품교환 여부 체크
		if((before == 5 && after == 8) || (before == 6 && after == 9)) {
			//반품교환상태 변경
			deliveryService.updateStatus(deliveryVO);
			
			return "redirect:/admin/returnList.do";
		}
		
		model.addAttribute("message", "잘못된 접근입니다.");
		model.addAttribute("url", request.getContextPath() + "/shop/main.do");
		
		return "common/resultView";
	}
	
	//반품교환취소 목록
	@RequestMapping("/admin/returnList.do")
	public ModelAndView adminReturnList(@RequestParam(value="pageNum", defaultValue="1") int currentPage,
										  @RequestParam(value="d_status_num", defaultValue="") String d_status_num,
										  @RequestParam(value="keyfield", defaultValue="") String keyfield,
										  @RequestParam(value="keyword", defaultValue="") String keyword) {
		
		logger.debug("<<adminReturnList 호출>> currentPage : " + currentPage + ", d_status_num : " + d_status_num + ", keyfield : " + keyfield +", keyword : " + keyword);
		
		//검색 조건
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("d_status_num", d_status_num);
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		
		//결과데이터 수
		int count = deliveryService.getReturnCount(map);
		//부가적인 key
		String addKey = "&d_status_num=" + d_status_num; 
		
		PagingUtil page = new PagingUtil(keyfield, keyword, currentPage, count, rowCount, pageCount, "returnList.do", addKey);
		
		List<DeliveryVO> list = null;
		if(count > 0) {
			map.put("start", page.getStartCount());
			map.put("end", page.getEndCount());
			
			list = deliveryService.getReturnList(map);
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("adminReturnList");
		mav.addObject("count", count);
		mav.addObject("list", list);
		mav.addObject("pagingHtml", page.getPagingHtml());
		
		//검색조건 유지를 위한 데이터
		mav.addObject("d_status_num", d_status_num);
		mav.addObject("keyfield", keyfield);
		mav.addObject("keyword", keyword);
		
		return mav;
	}
	
}
