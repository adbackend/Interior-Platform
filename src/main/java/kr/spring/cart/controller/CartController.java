package kr.spring.cart.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.cart.service.CartService;
import kr.spring.cart.vo.CartVO;
import kr.spring.cart.vo.ProductCartVO;

@Controller
public class CartController {
	
	private static final Logger logger = LoggerFactory.getLogger(CartController.class);
	
	@Autowired
	private CartService cartService;
	
	//카트 페이지
	@RequestMapping("/shop/cart.do")
	public ModelAndView selectCart(HttpSession session) {
		
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		List<ProductCartVO> list= cartService.selectCart(mem_num);
		int count = cartService.countCartAll(mem_num);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("cart");
		mav.addObject("list",list);
		mav.addObject("count",count);
		return mav;
	}
	
	//카트 상품 수정
	@ResponseBody
	@RequestMapping("/shop/updateCart.do")
	public Map<String,String> updateCart(HttpSession session, CartVO cart){
		Map<String,String> map = new HashMap<String,String>();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		
		if(mem_num == null) { //로그인 안된 상태
			map.put("result","logout"); 
		}else {
			cart.setMem_num(mem_num);
			logger.debug("<<ajax진입 : >>" + cart);
			cartService.updateCart(cart);
			map.put("result", "success");
		}
		
		return map;
	} 
	
	//카트 상품 넣기
	@ResponseBody
	@RequestMapping("/shop/insertCart.do")
	public Map<String,String> insertCart(HttpSession session, CartVO cart) {
		Map<String,String> map = new HashMap<String,String>();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		int num = 0;
		int cartProductNum = 0;
		int productNum = 0;
		int duplicationNum = cartService.selectDuplicationCart(cart.getP_no(), mem_num); //장바구니에 중복된 상품이 있나 체크
		
		if(duplicationNum != 0) {//장바구니에 중복되는 상품이 있으면
			num = cart.getCart_amount(); //상품상세페이지에서 선택한 상품 갯수
			cartProductNum = cartService.selectCartProduct(cart.getP_no(),mem_num); //DB에 저장되어 있는 상품 갯수
			productNum = num+cartProductNum; //선택한 상품 갯수 + DB에 저장되어 있는 상품 갯수
		}
		
		
		if(mem_num == null) { //로그인 안된 상태
			map.put("result","logout");
		}else if(productNum > 10) {	//장바구니에 중복되는 상품이 있고, 선택한 상품 갯수 + DB에 저장되어 있는 상품 갯수가 10개를 넘어가면
			map.put("result","numExcess");
		}
		else if(duplicationNum != 0) { //장바구니에 중복되는 상품이 있으면
			cartService.UpdateDuplicationProduct(productNum,cart.getP_no(),mem_num);
			map.put("result", "successDuplication");
		}else {
			cart.setMem_num(mem_num);
			cartService.insertCart(cart);
			
			map.put("result", "success");
		}
		
		return map;
	}
	
	//카트 상품 삭제
	@RequestMapping("/shop/deleteCart.do")
	public String deleteCart(@RequestParam int p_no) {
		logger.debug("<<카트 상품 삭제>> : "+p_no);
		
		cartService.deleteCart(p_no);
		return "redirect:/shop/cart.do";
	}
	
}
