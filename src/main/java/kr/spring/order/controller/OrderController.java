package kr.spring.order.controller;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.cart.service.CartService;
import kr.spring.cart.vo.CartVO;
import kr.spring.cart.vo.ProductCartVO;
import kr.spring.delivery.service.DeliveryService;
import kr.spring.delivery.vo.DeliveryVO;
import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import kr.spring.order.service.OrderService;
import kr.spring.order.vo.OrderAllVO;
import kr.spring.order.vo.OrderListVO;
import kr.spring.order.vo.OrderVO;
import kr.spring.orderDetail.service.OrderDetailService;
import kr.spring.orderDetail.vo.OrderDetailVO;
import kr.spring.product.service.ProductService;
import kr.spring.product.vo.ProductVO;
import kr.spring.util.PagingUtil;

@Controller
public class OrderController {
	private int rowCount = 5;
	private int pageCount = 10;
	
		private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
		@Autowired
		private CartService cartService;		
		@Autowired(required = true)
		private OrderService orderService;		
		@Autowired
		private OrderDetailService orderDetailService;		
		@Autowired
		private MemberService memberService;
		@Autowired
		private DeliveryService deliveryService;
		@Autowired
		private ProductService productService;
		//자바빈(VO) 초기화
		@ModelAttribute("orderVO")
		public OrderVO initCommand() {
			return new OrderVO();
		}

	  //장바구니에서 주문페이지 호출 
	  @GetMapping("/shop/order") 
	  public ModelAndView insertOrderForm(HttpSession session) { 
		  
		  Integer mem_num = (Integer)session.getAttribute("mem_num");
		  List<ProductCartVO> list = cartService.selectCart(mem_num);
		  
		  if(list.isEmpty()) {
			  return new ModelAndView("/common/notice");
		  }
		  
		  MemberVO member = memberService.selectMember(mem_num);
		  ModelAndView mav = new ModelAndView();
		  mav.setViewName("shopOrder");
		  mav.addObject("cartList",list);
		  mav.addObject("member",member);
		  return mav;
	  }
	  
	  //장바구니에서 주문 insert
	  @PostMapping("/shop/order")
	  public String insertOrder(HttpSession session, OrderVO orderVO, OrderDetailVO orderDetailVO,DeliveryVO deliveryVO) {
			
		  Integer mem_num = (Integer)session.getAttribute("mem_num");
		  
		  //주문번호 세팅 (yyyymmdd_랜덤6자리)
		  Calendar cal = Calendar.getInstance();
		  int year = cal.get(Calendar.YEAR);
		  String ym = year + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
		  String ymd = ym +  new DecimalFormat("00").format(cal.get(Calendar.DATE));
		  String subNum = "";
			 
		  for(int i = 1; i <= 6; i ++) {
			  subNum += (int)(Math.random() * 10);
		  }
			 
		  String orderNo = ymd + "_" + subNum;

		  orderVO.setOrder_no(orderNo); //주문번호
		  orderVO.setMem_num(mem_num); //회원번호
		  
		  orderDetailVO.setOrder_no(orderNo);
		  orderDetailVO.setMem_num(mem_num);
		  
		  deliveryVO.setOrder_no(orderNo);	  
	
		  orderService.insertOrder(orderVO); 					//주문테이블 insert		  
		  orderDetailService.insertOrderDetail(orderDetailVO); 	//주문상세테이블 insert
		  deliveryService.insertOrderDelivery(orderNo);			//배송테이블 insert
		  
		  //주문 완료 후, 카트에 담긴 상품을 삭제하기위해 orderDetail목록 가져옴
		  List<OrderDetailVO> orderList = orderDetailService.selectOrderDetail(orderNo);
		  
		  //상품 수량 변경
		  for(OrderDetailVO vo : orderList) {
			  productService.productAmountUpdate(vo.getOrder_d_amount(), vo.getP_no());
		  }
		  
		  //장바구니 목록 삭제
		  cartService.deleteAllCart(mem_num);		
		  
		  return "redirect:/shop/orderResult.do";
	  }
	  
	  //상품상세페이지에서 주문페이지 호출
	  @RequestMapping("/shop/orderNow.do")
	  public ModelAndView insertDirectOrderForm(HttpSession session, @RequestParam int p_no, int cart_amount) { 
		  Integer mem_num = (Integer)session.getAttribute("mem_num");
		  MemberVO member = memberService.selectMember(mem_num);
		  
		  ProductVO product = productService.ProductSelect(p_no);
		  int count = productService.countProduct(p_no);
		 
		  
		  if(count == 0) {
			  return new ModelAndView("/common/notice");
		  }
		  if(product.getP_amount() < cart_amount) { 
			  return new ModelAndView("/common/notice");
		  }
		  
		  CartVO cartVO = new CartVO();
		  cartVO.setMem_num(mem_num);
		  cartVO.setP_no(p_no); 
		  cartVO.setCart_amount(cart_amount);
		  ModelAndView mav = new ModelAndView();
			  
		  mav.setViewName("orderNow");
		  mav.addObject("cartVO", cartVO);
		  mav.addObject("member",member);
		  mav.addObject("product",product);
		  return mav;
		  
	  }
	  
	  //상품상세페이지 -> 주문페이지 -> 주문
	  @PostMapping("/shop/orderNow.do")
	  public String insertDirectOrder(HttpSession session,OrderVO orderVO, OrderDetailVO orderDetailVO,DeliveryVO deliveryVO,int p_no, int cart_amount) {
		  Integer mem_num = (Integer)session.getAttribute("mem_num");
		  
		  //주문번호 세팅 (yyyymmdd_랜덤6자리)
		  Calendar cal = Calendar.getInstance();
		  int year = cal.get(Calendar.YEAR);
		  String ym = year + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
		  String ymd = ym +  new DecimalFormat("00").format(cal.get(Calendar.DATE));
		  String subNum = "";
			 
		  for(int i = 1; i <= 6; i ++) {
			  subNum += (int)(Math.random() * 10);
		  }
			 
		  String orderNo = ymd + "_" + subNum;

		  orderVO.setOrder_no(orderNo); //주문번호
		  orderVO.setMem_num(mem_num); //회원번호
		  
		  //orderDetailVO.setOrder_no(orderNo);
		  //orderDetailVO.setMem_num(mem_num);
		  
		  deliveryVO.setOrder_no(orderNo);
		  
		  orderService.insertOrder(orderVO);  //주문테이블 insert
		  orderDetailService.insertDirectOrderDetail(orderNo, p_no, cart_amount); //주문상세테이블 insert
		  deliveryService.insertOrderDelivery(orderNo); //delivery테이블 insert
		  
		  productService.productAmountUpdate(cart_amount, p_no);
		  
		  return "redirect:/shop/orderResult.do";
	  }
	  
	  //주문완료페이지
	  @RequestMapping("/shop/orderResult")
	  public String resultOrder() {
		  return "orderResult";
	  }
	  
	  //주문내역페이지
	  @RequestMapping("/shop/orderList") 
	  public ModelAndView listOrder(HttpSession session,
			  						@RequestParam(value="pageNum",defaultValue="1") int currentPage){
		  Integer mem_num = (Integer)session.getAttribute("mem_num");
		  Map<String,Object> map = new HashMap<String,Object>();
		  map.put("mem_num", mem_num);
		  
		 int count = orderService.selectAllOrderCount(mem_num);
		  
		  PagingUtil page = new PagingUtil(currentPage, count, rowCount, pageCount, "orderList.do");
			
		  map.put("start", page.getStartCount());
		  map.put("end", page.getEndCount());
		  
		  List<OrderListVO> list = orderService.selectAllOrder(map);
		  
		  ModelAndView mav = new ModelAndView();
		  mav.addObject("list",list);
		 mav.addObject("count",count);
		  mav.addObject("pagingHtml",page.getPagingHtml());
		  mav.setViewName("orderList");
		  return mav; 
	  }
	  
	  //주문상세내역페이지
	  @RequestMapping("/shop/orderDetail")
	  public ModelAndView detailOrder(HttpSession session, @RequestParam String order_no) {
		 
		 List<OrderAllVO> listProduct = orderService.selectOrderDetailProduct(order_no);
		 OrderAllVO order = orderService.selectOrderDetailInfo(order_no);
		 
		 ModelAndView  mav = new ModelAndView();
		 
		 mav.setViewName("orderDetail");
		 mav.addObject("listProduct", listProduct);
		 mav.addObject("order",order);
		 return mav;
	  }
		 
	  
	  //주문취소페이지
	  @RequestMapping("/shop/orderCancel")
	  public ModelAndView cancelOrder(HttpSession session,
			  						@RequestParam(value="pageNum",defaultValue="1") int currentPage) {
		  Integer mem_num = (Integer)session.getAttribute("mem_num");
		  
		  Map<String,Object> map = new HashMap<String,Object>();
		  map.put("mem_num", mem_num);
		  
		  //주문 총 갯수
		  int count = orderService.selectCancelOrderCount(mem_num);
			
		  PagingUtil page = new PagingUtil(currentPage, count, rowCount, pageCount, "orderCancel.do");
		
		  map.put("start", page.getStartCount());
		  map.put("end", page.getEndCount());
		  
		  
		  List<OrderListVO> list = orderService.selectCancelOrder(map);
		 
		  ModelAndView mav = new ModelAndView();
		  mav.setViewName("orderCancel");
		  mav.addObject("list",list);
		  mav.addObject("count",count);
		  mav.addObject("pagingHtml",page.getPagingHtml());
		  return mav;
	  }
	  
	  //주문교환페이지
	  @RequestMapping("/shop/orderExchange")
	  public ModelAndView exchangeOrder(HttpSession session,
			  							@RequestParam(value="pageNum",defaultValue="1") int currentPage) {
		  
		  Integer mem_num = (Integer)session.getAttribute("mem_num");
		  
		  Map<String,Object> map = new HashMap<String,Object>();
		  map.put("mem_num", mem_num);
		  
		  int count = orderService.selectExchageOrderCount(mem_num);
		  PagingUtil page = new PagingUtil(currentPage, count, rowCount, pageCount, "orderCancel.do");
		  map.put("start", page.getStartCount());
		  map.put("end", page.getEndCount());
		  
		  List<OrderListVO> list = orderService.selectExchageOrder(map);
		 
		  ModelAndView mav = new ModelAndView();
		  mav.setViewName("orderExchange");
		  mav.addObject("list",list);
		  mav.addObject("count",count);
		  mav.addObject("pagingHtml",page.getPagingHtml());
		  
		  return mav;
	  }
	  
	  //주문반품페이지
	  @RequestMapping("/shop/orderRefund")
	  public ModelAndView refundOrder(HttpSession session,@RequestParam(value="pageNum",defaultValue="1") int currentPage) {
		  Integer mem_num = (Integer)session.getAttribute("mem_num");
		  Map<String,Object> map = new HashMap<String,Object>();
		  map.put("mem_num", mem_num);
		  int count = orderService.selectRefundOrderCount(mem_num);
		  
		  PagingUtil page = new PagingUtil(currentPage, count, rowCount, pageCount, "orderCancel.do");
		  map.put("start", page.getStartCount());
		  map.put("end", page.getEndCount());
				  
		  List<OrderListVO> list = orderService.selectRefundOrder(map);
		 
		  ModelAndView mav = new ModelAndView();
		  mav.setViewName("refundExchange");
		  mav.addObject("list",list);
		  mav.addObject("count",count);
		  mav.addObject("pagingHtml",page.getPagingHtml());
		  return mav;
	  }
	  
	  
	  
	  //주문구매확정페이지
	  @RequestMapping("/shop/orderConfirm")
	  public ModelAndView confirmOrder(HttpSession session,@RequestParam(value="pageNum",defaultValue="1") int currentPage) {
		  Integer mem_num = (Integer)session.getAttribute("mem_num");
		  Map<String,Object> map = new HashMap<String,Object>();
		  map.put("mem_num", mem_num);
		  
		  int count = orderService.selectConfirmOrderCount(mem_num);
		  PagingUtil page = new PagingUtil(currentPage, count, rowCount, pageCount, "orderCancel.do");
		  map.put("start", page.getStartCount());
				  map.put("end", page.getEndCount());
		  List<OrderListVO> list = orderService.selectConfirmOrder(map);
		  
		 
		  ModelAndView mav = new ModelAndView();
		  mav.setViewName("orderConfirm");
		  mav.addObject("list",list);
		  mav.addObject("count",count);
		  mav.addObject("pagingHtml",page.getPagingHtml());
		  return mav;
	  } 
	  
	  //주문상태변경(결제완료->취소)
	  @ResponseBody
	  @RequestMapping("/shop/updateOrderCancel.do")
	  public Map<String,String> updateOrderCancel(HttpSession session, DeliveryVO delivery){
		 Map<String,String> map=new HashMap<String,String>();
		 Integer mem_num = (Integer)session.getAttribute("mem_num");
		
		 if(mem_num == null) {
			 map.put("result", "logout");
		 }else {
			 deliveryService.updateOrderDeilveryCancel(delivery.getOrder_no());
			  //주문 상태 변경 후, 상품의 수량변경 위해 orderDetail목록 가져옴
			  List<OrderDetailVO> orderList = orderDetailService.selectOrderDetail(delivery.getOrder_no());
			  
			  //상품 수량 변경
			  for(OrderDetailVO vo : orderList) {
				  productService.productAmountPlusUpdate(vo.getOrder_d_amount(), vo.getP_no());
			  }
			 map.put("result", "success");
		 }
		 return map;
	  }
	  
	  //주문상태변경(배송완료->구매확정)
	  @ResponseBody
	  @RequestMapping("/shop/updateOrderConfirm.do")
	  public Map<String,String> updateOrderConfirm(HttpSession session, DeliveryVO delivery){
		 Map<String,String> map=new HashMap<String,String>();
		 Integer mem_num = (Integer)session.getAttribute("mem_num");
		
		 if(mem_num == null) {
			 map.put("result", "logout");
		 }else {
			 deliveryService.updateOrderDeilveryConfirm(delivery.getOrder_no());
			 map.put("result", "success");
		 }
		 return map;
	  }
	  
	  //주문상태변경(배송완료->반품)
	  @ResponseBody
	  @RequestMapping("/shop/updateOrderRefund.do")
	  public Map<String,String> updateOrderRefund(HttpSession session, DeliveryVO delivery){
		 Map<String,String> map=new HashMap<String,String>();
		 Integer mem_num = (Integer)session.getAttribute("mem_num");
		
		 if(mem_num == null) {
			 map.put("result", "logout");
		 }else {
			 deliveryService.updateOrderDeilveryRefund(delivery.getOrder_no());
			  //주문 상태 변경 후, 상품의 수량변경 위해 orderDetail목록 가져옴
			  List<OrderDetailVO> orderList = orderDetailService.selectOrderDetail(delivery.getOrder_no());
			  
			  //상품 수량 변경
			  for(OrderDetailVO vo : orderList) {
				  productService.productAmountPlusUpdate(vo.getOrder_d_amount(), vo.getP_no());
			  }
			 map.put("result", "success");
		 }
		 return map;
	  }
	  
	  //주문상태변경(배송완료->교환)
	  @ResponseBody
	  @RequestMapping("/shop/updateOrderExchange.do")
	  public Map<String,String> updateOrderExchange(HttpSession session, DeliveryVO delivery){
		 Map<String,String> map=new HashMap<String,String>();
		 Integer mem_num = (Integer)session.getAttribute("mem_num");
		
		 if(mem_num == null) {
			 map.put("result", "logout");
		 }else {
			 deliveryService.updateOrderDeilveryExchange(delivery.getOrder_no());
			 map.put("result", "success");
		 }
		 return map;
	  }
}
