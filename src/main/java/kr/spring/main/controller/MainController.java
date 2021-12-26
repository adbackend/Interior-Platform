package kr.spring.main.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.spring.product.service.ProductService;
import kr.spring.product.vo.ProductVO;

@Controller
public class MainController {
	@Autowired
	private ProductService productService;
	
	@RequestMapping("/shop/main.do")
	public String main(Model model) {
		
		List<ProductVO> list = productService.ProductSelectAll();
		int count = productService.selectMainProduct();
		
		model.addAttribute("list",list);
		model.addAttribute("count",count);
		return "shopMain";//타일스 식별자
	}
	
	@RequestMapping("/admin/main.do")
	public String adminMain() {
		return "adminMain";
	}
}

