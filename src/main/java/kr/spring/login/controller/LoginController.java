package kr.spring.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.spring.login.service.LoginService;
import kr.spring.login.vo.LoginVO;

@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	
	//로그인 폼
	@RequestMapping("/login/loginForm.do")
	public String loginForm() {
		return "loginForm"; //타일즈 식별자
	}
	
	//로그인 처리
	@PostMapping("/login/loginAction.do")
	public String loginAction(@Valid LoginVO loginVO, HttpSession session, HttpServletResponse response ) {
		logger.debug("============로그인처리 Controller===========" +loginVO);
		
//		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
//		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
//		response.setHeader("Expires", "0"); // Proxies.
		
		LoginVO vo = loginService.loginAction(loginVO.getMem_id());
		
		boolean passwdCheck = false;
		
		if(vo!=null) {
			passwdCheck = vo.isCheckPassword(loginVO.getMem_passwd()); //비밀번호 일치여부 체크
		}
		
		
		if(passwdCheck) { //아이디가 존재,비밀번호 일치-> 로그인 성공 처리
			System.out.println("로그인 성공!");
			session.setAttribute("mem_num", vo.getMem_num()); //시퀀스
			session.setAttribute("mem_id", vo.getMem_id()); //아이디
			session.setAttribute("mem_auth", vo.getMem_auth()); //0:탈퇴 1:정지 2:일반 3:일반관리자 4:최고관리자
			
			return "redirect:/shop/main.do";
		}
		
		return "login/loginFail";
		
	}
	
	
	//로그아웃 처리
	@RequestMapping("/login/logout.do")
	public String logout(HttpSession session, HttpServletRequest request) {
		
		session.invalidate();
		request.getSession(true);
		
		return "logout"; //타일스 식별자
		
	}
	

	

}
