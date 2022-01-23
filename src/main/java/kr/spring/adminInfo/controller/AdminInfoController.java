package kr.spring.adminInfo.controller;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import kr.spring.adminInfo.service.AdminInfoService;
import kr.spring.login.vo.LoginVO;

@Controller
public class AdminInfoController {
	
	private static final Logger logger = LoggerFactory.getLogger(AdminInfoController.class);

	@Autowired
	private AdminInfoService adminInfoService;
	
	//자바빈 초기화
	@ModelAttribute
	public LoginVO initCommand() {
		
		return new LoginVO();
	}
	
	
	//관리자 마이페이지 - 폼
	@RequestMapping("/admin/adminInfoForm.do")
	public String adminInfoForm(HttpSession session, Model model) {
		
		String mem_id = (String)session.getAttribute("mem_id");
		
		LoginVO vo = adminInfoService.adminInfo(mem_id);
		
		if(mem_id!=null && vo!=null) {
			logger.debug("====관리자 마이페이지 ");
			model.addAttribute("vo",vo);
			
			return "adminInfoForm"; //타일즈 식별자
			
		}else {
			
			return "loginForm"; //타일즈 식별자
		}
	}
	
	
	//관리자 마이페이지 - 수정 폼
	@RequestMapping("/admin/adminInfoUpdateForm.do")
	public String adminInfoUpdateForm(HttpSession session, Model model) {
		
		String mem_id = (String)session.getAttribute("mem_id");
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		
		LoginVO vo = adminInfoService.adminInfo(mem_id);
		
		if(mem_id!=null && vo!=null) { // 로그인 됐으면
			logger.debug("====관리자 마이페이지 ");
			model.addAttribute("vo",vo);
			
			return "adminInfoUpdateForm"; //타일즈 식별자
			
		}else { //로그인 안됐으면
			System.out.println("그럼 여기로 와....?");
			return "loginForm"; //타일즈 식별자
		}
		
		
		
	}
	
	
	//관리자 마이페이지 - 수정 처리
	@RequestMapping("/admin/adminInfoUpdateAction.do")
	public String adminInfoUpdateAction(LoginVO loginVO, HttpSession session) {
		
		System.out.println(loginVO.toString());

		Integer mem_num = (Integer)session.getAttribute("mem_num");
		
		if(mem_num!=null) { // 로그인중이면
			adminInfoService.updateInfoAction(loginVO);
			
			return "redirect:/admin/adminInfoForm.do"; //수정 성공 - 
		}
			
		return "adminInfoUpdateForm"; //수정실패
	}
	
	
	
	
	//비밀번호 수정 폼
	@RequestMapping("/admin/adminInfoPasswdUpdate.do")
	public String adminInfoPasswdUpdate(HttpSession session, Model model) {
		
		String mem_id = (String)session.getAttribute("mem_id");
		
		LoginVO vo = adminInfoService.adminInfo(mem_id);
		
		if(vo!=null) {
			
			model.addAttribute("vo",vo);
			
		}
		
		return "adminInfoPasswdUpdateForm"; //타일즈 식별자
	}
	
	//관리자 마이페이지 - 비밀번호 수정처리
	@PostMapping("/admin/adminInfoPwUpdateAction.do")
	public String adminInfoUpdateAction(HttpSession session, LoginVO loginVO) {
		
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		
		if(mem_num!=null) { //로그인 됐을때
			adminInfoService.updateInfoPasswdAction(loginVO);
			
			return "/adminInfo/Updatesuccess"; //수정 성공
		}
		return "adminInfoUpdateForm"; //수정실패
	}
}







