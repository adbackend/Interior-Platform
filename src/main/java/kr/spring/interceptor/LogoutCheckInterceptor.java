package kr.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LogoutCheckInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(LogoutCheckInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.debug("====로그아웃 인터셉터 진입====");
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("mem_num")!=null || session.getAttribute("mem_id")!=null || session.getAttribute("mem_auth")!=null  ) {
			
			session.invalidate();
//			response.sendRedirect(request.getContextPath()+"/login/logout.do");
			response.sendRedirect(request.getContextPath()+"/login/loginForm.do");
			return false;
		}
		return true;
	}

}
