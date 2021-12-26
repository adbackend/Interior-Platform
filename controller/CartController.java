package kr.spring.cart.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.cart.service.CartService;
import kr.spring.cart.vo.CartVO;
import kr.spring.member.controller.MemberController;
import kr.spring.cart.vo.ProductCartVO;

@Controller
public class CartController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private CartService cartService;
	
	@RequestMapping("/shop/cart.do")
	public ModelAndView selectCart(HttpSession session) {
		
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		List<ProductCartVO> list= cartService.selectCart(mem_num);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("cart");
		mav.addObject("list",list);
		
		return mav;
	}
	
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
	
	@ResponseBody
	@RequestMapping("/shop/insertCart.do")
	public Map<String,String> insertCart(HttpSession session, CartVO cart) {
		Map<String,String> map = new HashMap<String,String>();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		int num = 0;
		int CartProductNum = 0;
		int product_num = 0;
		
		if(cartService.selectDuplicationCart(cart.getP_no()) != 0) {//장바구니에 중복되는 상품이 있으면
			num = cart.getCart_amount(); //상품상세페이지에서 선택한 상품 갯수
			CartProductNum = cartService.selectCartProduct(cart.getP_no()); //DB에 저장되어 있는 상품 갯수
			product_num = num+CartProductNum; //선택한 상품 갯수 + DB에 저장되어 있는 상품 갯수
		}
		
		
		if(mem_num == null) { //로그인 안된 상태
			map.put("result","logout");
		}else if(product_num > 10) {	//장바구니에 중복되는 상품이 있고, 선택한 상품 갯수 + DB에 저장되어 있는 상품 갯수가 10개를 넘어가면
			map.put("result","numExcess");
		}
		else if(cartService.selectDuplicationCart(cart.getP_no()) != 0) { //장바구니에 중복되는 상품이 있으면
			cartService.UpdateDuplicationProduct(product_num,cart.getP_no(),mem_num);
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
