package kr.spring.interceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.spring.qna.service.QnaService;
import kr.spring.qna.vo.QnaVO;

public class QnaCheckInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(QnaCheckInterceptor.class);
	
	@Autowired
	private QnaService qnaService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		
		logger.debug("<<QnaCheckInterceptor 진입>>");
		
		int board_no = Integer.parseInt(request.getParameter("board_no"));
		QnaVO qna = qnaService.selectDetailQna(board_no);
		
		//회원번호 가져오기
		HttpSession session = request.getSession();
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		Integer mem_auth = (Integer)session.getAttribute("mem_auth");
		
		
		if(mem_num != qna.getMem_num() && (mem_auth!=3 || mem_auth!=4)){
			RequestDispatcher dispatcher = 
					request.getRequestDispatcher(
							       "/WEB-INF/views/common/notice.jsp");
			dispatcher.forward(request, response);
			
			return false;
		}
		
		
		//로그인이 된 상태
		return true;
	}
}
