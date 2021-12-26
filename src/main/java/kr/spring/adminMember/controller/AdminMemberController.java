package kr.spring.adminMember.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.adminMember.service.AdminMemberService;
import kr.spring.adminMember.vo.AdminMemberVO;
import kr.spring.util.PagingUtil;

@Controller
public class AdminMemberController {
	private static final Logger logger = LoggerFactory.getLogger(AdminMemberController.class);
	private int rowCount = 10;	//표시할 데이터 수
	private int pageCount = 10;	//표시할 페이지 수
	
	@Autowired
	private AdminMemberService adminMemberService;
	
	//자바빈(VO) 초기화
	@ModelAttribute
	public AdminMemberVO initCommand() {
		return new AdminMemberVO();
	}
	
	//회원 목록
	@RequestMapping("/admin/memberList.do")
	public ModelAndView adminMemberList(@RequestParam(value="pageNum", defaultValue="1") int currentPage,
										@RequestParam(value="auth_num", defaultValue="") String auth_num,
										@RequestParam(value="keyfield", defaultValue="") String keyfield,
										@RequestParam(value="keyword", defaultValue="") String keyword) {
		
		logger.debug("<<adminMemberList 호출>> currentPage : " + currentPage + ", auth_num : " + auth_num  + ", keyfield : " + keyfield + ", keyword : " + keyword);
		
		//검색 조건
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("auth_num", auth_num);
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		
		//결과데이터 수
		int count = adminMemberService.getMemberCount(map);
		//부가적인 key
		String addKey = "&auth_num=" + auth_num; 
		
		PagingUtil page = new PagingUtil(keyfield, keyword, currentPage, count, rowCount, pageCount, "memberList.do", addKey);
		
		List<AdminMemberVO> list = null;
		if(count > 0) {
			map.put("start", page.getStartCount());
			map.put("end", page.getEndCount());
			
			list = adminMemberService.getMemberList(map);
		}

		ModelAndView mav = new ModelAndView();
		mav.setViewName("adminMemberList");
		mav.addObject("count", count);
		mav.addObject("list", list);
		mav.addObject("pagingHtml", page.getPagingHtml());
		
		//검색조건 유지를 위한 데이터
		mav.addObject("auth_num", auth_num);
		mav.addObject("keyfield", keyfield);
		mav.addObject("keyword", keyword);
		
		return mav;
	}
	
	//회원 상세
	@RequestMapping("/admin/memberDetail.do")
	public String adminMemberDetail(@RequestParam int mem_num, HttpServletRequest request, Model model) {
		logger.debug("<<adminMemberDetail 호출>> mem_num : " + mem_num);
		
		//회원 존재여부 체크
		AdminMemberVO member = adminMemberService.selectMember(mem_num);
		if(member == null) {
			//alert창에 표시할 내용
			model.addAttribute("message", "존재하지 않는 회원입니다.");
			model.addAttribute("url", request.getContextPath() + "/admin/memberList.do");
			
			return "common/resultView";
		}
		
		AdminMemberVO adminMemberVO = adminMemberService.selectMember(mem_num);
		model.addAttribute("adminMemberVO", adminMemberVO);
		model.addAttribute("orderCnt", adminMemberService.getOrderCount(mem_num));
		model.addAttribute("cancelCnt", adminMemberService.getCancelCount(mem_num));
//		model.addAttribute("reviewCnt", adminMemberService.getReviewCount(mem_num));
		model.addAttribute("qnaCnt", adminMemberService.getQnaCount(mem_num));
		
		return "adminMemberDetail";
	}
	
	//회원 수정 폼
	@GetMapping("/admin/memberUpdate.do")
	public String adminMemberUpdateForm(@RequestParam int mem_num, HttpServletRequest request, Model model) {
		logger.debug("<<adminMemberUpdateForm 호출>> mem_num : " + mem_num);
		
		//회원 존재여부 체크
		AdminMemberVO member = adminMemberService.selectMember(mem_num);
		if(member == null || member.getMem_auth() == 0) {
			//alert창에 표시할 내용
			model.addAttribute("message", "존재하지 않거나 이미 탈퇴한 회원입니다.");
			model.addAttribute("url", request.getContextPath() + "/admin/memberList.do");
			
			return "common/resultView";
		}
		
		AdminMemberVO adminMemberVO = adminMemberService.selectMember(mem_num);
		model.addAttribute("adminMemberVO", adminMemberVO);
		
		return "adminMemberUpdate";
	}
	
	//회원 수정 처리
	@PostMapping("/admin/memberUpdate.do")
	public String adminMemberUpdate(@Valid AdminMemberVO adminMemberVO, BindingResult result, HttpServletRequest request, Model model) {
		logger.debug("<<adminMemberUpdate 호출>> adminMemberVO : " + adminMemberVO);
		
		//입력데이터 유효성 체크
		if(result.hasErrors()) { 
			return "adminMemberUpdate";
		}
		
		//회원정보 수정
		adminMemberService.updateMember(adminMemberVO);
		
		//alert창에 표시할 내용
		model.addAttribute("message", "회원정보 수정이 완료되었습니다.");
		model.addAttribute("url", request.getContextPath() + "/admin/memberDetail.do?mem_num=" + adminMemberVO.getMem_num());
		
		return "common/resultView";
	}

	//회원 삭제 폼 - 최고관리자 인증
	@GetMapping("/admin/memberDelete.do")
	public String adminMemberDeleteForm(@RequestParam int mem_num, HttpServletRequest request, Model model) {
		logger.debug("<<adminMemberDeleteForm 호출>> mem_num : " + mem_num);
		
		//회원 존재여부 체크
		AdminMemberVO member = adminMemberService.selectMember(mem_num);
		if(member == null || member.getMem_auth() == 0) {
			//alert창에 표시할 내용
			model.addAttribute("message", "존재하지 않거나 이미 탈퇴한 회원입니다.");
			model.addAttribute("url", request.getContextPath() + "/admin/memberList.do");
			
			return "common/resultView";
		}
		
		//삭제할 회원번호
		model.addAttribute("mem_num", mem_num);
		
		return "adminMemberDelete";
	}
	
	//회원 삭제 처리
	@PostMapping("/admin/memberDelete.do")
	public String adminMemberDelete(AdminMemberVO adminMemberVO, HttpSession session, HttpServletRequest request, Model model) {
		logger.debug("<<adminMemberDelete 호출>> adminMemberVO : " + adminMemberVO);
		
		String mem_id = (String)session.getAttribute("mem_id"); //로그인한 아이디
		AdminMemberVO dbMember = adminMemberService.selectCheckMember(adminMemberVO.getMem_id()); //입력한 아이디,비밀번호,삭제할 회원번호
		boolean check = false;
		
		//로그인한 아이디와 입력한 아이디가 일치하는 경우
		if(dbMember != null && dbMember.getMem_id().equals(mem_id)) {
			//비밀번호 일치 여부 체크
			check = dbMember.isCheckedPassword(adminMemberVO.getMem_passwd());
		}
		
		if(check) {
			//회원정보 삭제
			adminMemberService.deleteMember(Integer.parseInt(adminMemberVO.getManage_num()));
			
			//완료시 alert창에 표시할 내용
			model.addAttribute("message", "회원정보 삭제가 완료되었습니다.");
			model.addAttribute("url", request.getContextPath() + "/admin/memberList.do");
		
		}else {
			//실패시 alert창에 표시할 내용
			model.addAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
			model.addAttribute("url", request.getContextPath() + "/admin/memberDelete.do?mem_num=" + adminMemberVO.getManage_num());
		}
		
		return "common/resultView";
	}
	
	//============== 관리자관리 ==============
	//관리자 목록
	@RequestMapping("/admin/adminList.do")
	public ModelAndView adminList(@RequestParam(value="pageNum", defaultValue="1") int currentPage) {
		
		logger.debug("<<adminList 호출>> currentPage : " + currentPage);
		
		//검색 조건
		Map<String,Object> map = new HashMap<String,Object>();
		
		//결과데이터 수
		int count = adminMemberService.getAdminCount();
		
		PagingUtil page = new PagingUtil(currentPage, count, rowCount, pageCount, "adminList.do");
		
		List<AdminMemberVO> list = null;
		if(count > 0) {
			map.put("start", page.getStartCount());
			map.put("end", page.getEndCount());
			
			list = adminMemberService.getAdminList(map);
		}

		ModelAndView mav = new ModelAndView();
		mav.setViewName("adminList");
		mav.addObject("count", count);
		mav.addObject("list", list);
		mav.addObject("pagingHtml", page.getPagingHtml());
		
		return mav;
	}
	
	//관리자 등록 - 아이디 중복 체크
	@RequestMapping("/admin/confirmId.do")
	@ResponseBody
	public Map<String,String> process(@RequestParam String mem_id){
		logger.debug("<<process 호출>> mem_id : " + mem_id);
		
		Map<String,String> map = new HashMap<String, String>();
		
		AdminMemberVO adminMemberVO = adminMemberService.selectCheckMember(mem_id);
		if(adminMemberVO != null) {
			//아이디 중복
			map.put("result", "idDuplicated");
		}else {
			if(!Pattern.matches("^[A-Za-z0-9]{4,12}$", mem_id)) {
				//패턴 불일치
				map.put("result", "notMatchPattern");
			}else {
				//아이디 미중복
				map.put("result", "idNotFound");
			}
		}
		return map;
	}
	
	//관리자 등록 폼
	@RequestMapping("/admin/adminRegister.do")
	public String adminRegisterForm(Model model) {
		logger.debug("<<adminRegisterForm 호출>>");
		return "adminRegister";
	}
	
	//관리자 등록 처리
	@PostMapping("/admin/adminRegister.do")
	public String adminRegister(@Valid AdminMemberVO adminMemberVO, BindingResult result, HttpSession session, HttpServletRequest request, Model model) {
		logger.debug("<<adminRegister 호출>> adminMemberVO : " + adminMemberVO);
		
		//입력데이터 유효성 체크
		if(result.hasErrors()) { 
			return "adminRegister";
		}
		
		//관리자 등록
		adminMemberService.insertAdmin(adminMemberVO);
		
		//alert창에 표시할 내용
		model.addAttribute("message", "관리자 등록이 완료되었습니다.");
		model.addAttribute("url", request.getContextPath() + "/admin/adminList.do");
		
		return "common/resultView";
	}
	
	//관리자 수정 폼
	@GetMapping("/admin/adminUpdate.do")
	public String adminUpdateForm(@RequestParam int mem_num, HttpServletRequest request, Model model) {
		logger.debug("<<adminUpdateForm 호출>> mem_num : " + mem_num);
		
		//관리자 존재여부 체크
		AdminMemberVO member = adminMemberService.selectAdmin(mem_num);
		if(member == null || member.getMem_auth() == 0) {
			//alert창에 표시할 내용
			model.addAttribute("message", "존재하지 않는 관리자입니다.");
			model.addAttribute("url", request.getContextPath() + "/admin/adminUpdate.do");
			
			return "common/resultView";
		}
		
		AdminMemberVO adminMemberVO = adminMemberService.selectAdmin(mem_num);
		model.addAttribute("adminMemberVO", adminMemberVO);
		
		return "adminUpdate";
	}
	
	//관리자 수정 처리
	@PostMapping("/admin/adminUpdate.do")
	public String adminUpdate(@Valid AdminMemberVO adminMemberVO, BindingResult result, HttpServletRequest request, Model model) {
		logger.debug("<<adminUpdate 호출>> adminMemberVO : " + adminMemberVO);
		
		//입력데이터 유효성 체크
		if(result.hasErrors()) { 
			return "adminUpdate";
		}
		
		//관리자 수정
		adminMemberService.updateMember(adminMemberVO);
		
		//완료시 alert창에 표시할 내용
		model.addAttribute("message", "관리자 수정이 완료되었습니다.");
		model.addAttribute("url", request.getContextPath() + "/admin/adminList.do");
		
		return "common/resultView";
	}
	
	//관리자 삭제 폼 - 최고관리자 인증
	@GetMapping("/admin/adminDelete.do")
	public String adminDeleteForm(@RequestParam int mem_num, HttpServletRequest request, Model model) {
		logger.debug("<<adminDeleteForm 호출>> mem_num : " + mem_num);
		
		//관리자 존재여부 체크
		AdminMemberVO member = adminMemberService.selectAdmin(mem_num);
		if(member == null) {
			//alert창에 표시할 내용
			model.addAttribute("message", "존재하지 않는 관리자입니다.");
			model.addAttribute("url", request.getContextPath() + "/admin/adminList.do");
			
			return "common/resultView";
		}
		
		//삭제할 회원번호
		model.addAttribute("mem_num", mem_num);
		
		return "adminDelete";
	}
	
	//관리자 삭제 처리
	@PostMapping("/admin/adminDelete.do")
	public String adminDelete(AdminMemberVO adminMemberVO, HttpSession session, HttpServletRequest request, Model model) {
		logger.debug("<<adminDelete 호출>> adminMemberVO : " + adminMemberVO);
		
		String mem_id = (String)session.getAttribute("mem_id"); //로그인한 아이디
		AdminMemberVO dbMember = adminMemberService.selectCheckMember(adminMemberVO.getMem_id()); //입력한 아이디,비밀번호,삭제할 회원번호
		boolean check = false;
		
		//로그인한 아이디와 입력한 아이디가 일치하는 경우
		if(dbMember != null && dbMember.getMem_id().equals(mem_id)) {
			//비밀번호 일치 여부 체크
			check = dbMember.isCheckedPassword(adminMemberVO.getMem_passwd());
		}
		
		if(check) {
			//관리자정보 삭제
			adminMemberService.deleteMember(Integer.parseInt(adminMemberVO.getManage_num()));
			
			//완료시 alert창에 표시할 내용
			model.addAttribute("message", "관리자 삭제가 완료되었습니다.");
			model.addAttribute("url", request.getContextPath() + "/admin/adminList.do");
		
		}else {
			//실패시 alert창에 표시할 내용
			model.addAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
			model.addAttribute("url", request.getContextPath() + "/admin/adminDelete.do?mem_num=" + adminMemberVO.getManage_num());
		}
		
		return "common/resultView";
	}
}
