package kr.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AdminCheckInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(AdminCheckInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		
		logger.debug("<<AdminCheckIntercepter 진입>>");
		
		//관리자 여부 검사
		HttpSession session = request.getSession();
		int mem_auth = (Integer)session.getAttribute("mem_auth");
		logger.debug("***** mem_auth : " + mem_auth);
		
		//관리자인 경우
		if(mem_auth == 3 || mem_auth == 4) {
			return true;
		}
		
		//관리자가 아닌 경우
		response.sendRedirect(request.getContextPath() + "/shop/main.do");
		
		//*** 페이지로 표시하는 방법
		//RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/common/notice.jsp");
		
		return false;
	}
}
