package kr.spring.interceptor;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.spring.category_sub.service.Category_subService;
import kr.spring.category_sub.vo.Category_subVO;
import kr.spring.category_top.service.Category_topService;
import kr.spring.category_top.vo.Category_topVO;

public class MenuCheckInterceptor extends HandlerInterceptorAdapter{
	private static final Logger logger = LoggerFactory.getLogger(MenuCheckInterceptor.class);
	@Autowired
	private Category_topService category_topService;
	@Autowired
	private Category_subService category_subService;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
											Object handler) throws Exception {
		logger.debug("<<MenuCheckInterceptor 진입>>");
		if(logger.isDebugEnabled()) {
			logger.debug("====공통 정보 처리 진입====");
		}
		
		List<Category_topVO> list_top = category_topService.selectCategory_top();
		List<Category_subVO> list_sub = category_subService.category_subSelectAll();

	
		request.setAttribute("category_top", list_top);
		request.setAttribute("category_sub", list_sub);
		
		return true;
	}
}
