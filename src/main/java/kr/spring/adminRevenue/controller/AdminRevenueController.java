package kr.spring.adminRevenue.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.adminRevenue.service.AdminRevenueService;

/**
  * @FileName : AdminRevenueController.java
  * @Date : 2021. 10. 12. 
  * @Author : 최유정
  * @Description : goods상품별 매출관리 Controller
  */

@Controller
public class AdminRevenueController {

	@Autowired
	private AdminRevenueService adminRevenueService;

	@RequestMapping("/admin/goodsRevenue.do")
	public String goodsRevenue() {
		return "goodsRevenue";
	}
	
	@RequestMapping("/admin/termRevenue.do")
	public ModelAndView termChartRevenue() {
		ModelAndView model = new ModelAndView("termRevenue");
		return model;
	}
	
	@RequestMapping("/admin/getGoodsRevenue.do")
	@ResponseBody
	public List<HashMap<String, Object>> getGoodsRevenue(@RequestParam String startDate, @RequestParam String endDate) {
		return adminRevenueService.getGoodsRevenue(startDate, endDate);
	}

}
