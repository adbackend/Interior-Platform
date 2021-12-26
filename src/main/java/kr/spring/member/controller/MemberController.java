package kr.spring.member.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

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

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;



@Controller
public class MemberController {
	
	 private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	 
	 @Autowired 
	 private MemberService memberService;
	 
	  //자바빈(VO) 초기화
	  @ModelAttribute 
	  public MemberVO initCommand() { 
		  return new MemberVO(); 
	  }
	 
	
	  //회원가입 - 아이디 중복 체크
	  @RequestMapping("/member/confirmId.do")
	  @ResponseBody 
	  public Map<String,String> process(@RequestParam String mem_id){
	  
	  //logger.debug("<<id>> : " + mem_id);
	  
	  Map<String,String> map = new HashMap<String,String>();
	  
	  MemberVO member = memberService.selectCheckMember(mem_id); 
	  if(member!=null) {
		  //아이디 중복 
		  map.put("result", "idDuplicated"); 
	  }else {
		  if(!Pattern.matches("^[A-Za-z0-9]{4,12}$", mem_id)) { 
		  //패턴 불일치
		  map.put("result", "notMatchPattern"); 
	  }else { 
		  //아이디 미중복 
		  map.put("result","idNotFound"); 
	  } 
	}
	  return map; 
	}
	 
	  //회원가입 - 회원가입 폼 호출
	  @GetMapping("/member/registerUser.do") 
	  public String form() {
		  return "memberRegister"; 
	  }
	 
	 //회원가입 - 회원가입 처리
	  @PostMapping("/member/registerUser.do") 
	  public String submit(@Valid MemberVO memberVO, BindingResult result) {
	  
	  logger.debug("<<회원 정보>> : " + memberVO);
	  
	  //유효성 검사 결과 오류가 있으면 폼 호출 
	  if(result.hasErrors()) { 
		  return form(); 
	  }
	  
	  //회원가입 
	  memberService.insertMember(memberVO);
	  
	  	return "redirect:/shop/main.do"; 
	  } 
	  
	  //회원 상세 정보(마이페이지)
	  @RequestMapping("/member/myPage.do")
	  public String process(HttpSession session, Model model) {
		Integer mem_num = (Integer)session.getAttribute("mem_num");
			
		MemberVO member = memberService.selectMember(mem_num);
			
		logger.debug("<<회원 상세 정보>> : " + member);
			
		model.addAttribute("member", member);
			
			return "memberView";//타일스 식별자
		}
	  	
	  //회원정보수정 - 수정 폼
	  @GetMapping("/member/update.do")
	  public String formUpdate(HttpSession session, Model model) {
		Integer mem_num = (Integer)session.getAttribute("mem_num");
			
		MemberVO memberVO = memberService.selectMember(mem_num);
			
		model.addAttribute("memberVO", memberVO);
			
			return "memberModify"; //타일스 설정
		}
	  
	  //회원정보수정 - 수정 데이터 처리
	  @PostMapping("/member/update.do")
	  public String submitUpdate(@Valid MemberVO memberVO,BindingResult result, HttpSession session) {
			
	  logger.debug("<<회원정보수정>> : " + memberVO);
			
      //유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return "memberModify";
		}
			
		Integer mem_num = (Integer)session.getAttribute("mem_num");
		memberVO.setMem_num(mem_num);
			
		//회원정보수정
		memberService.updateMember(memberVO);
			
		return "redirect:/member/myPage.do";
		}
	  
	  	//회원 탈퇴 - 폼 호출
		@GetMapping("/member/delete.do")
		public String formDelete() {
			return "memberDelete";//타일스 식별자
		}
		
		//회원 탈퇴 - 데이터 처리
		@PostMapping("/member/delete.do")
		public String submitDelete(@Valid MemberVO memberVO, BindingResult result, 
				                   HttpSession session) {
			
			logger.debug("<<회원탈퇴>> : " + memberVO);
			
			//id,passwd 필드의 에러만 체크
			if(result.hasFieldErrors("mem_id") || result.hasFieldErrors("mem_passwd")) {
				return formDelete();
			}
			
			String mem_id = (String)session.getAttribute("mem_id");
			
			MemberVO dbMember = memberService.selectCheckMember(memberVO.getMem_id());
			boolean check = false;
			
			//사용자가 입력한 아이디가 db에 저장되어 있고 세션에 저장된 id와 일치함
			if(dbMember!=null && dbMember.getMem_id().equals(mem_id)) {
				//비밀번호 일치 여부 체크
				check = dbMember.isCheckedPassword(memberVO.getMem_passwd());
			}
			if(check) {
				//인증 성공, 회원정보 삭제
				memberVO.setMem_num((Integer)session.getAttribute("mem_num"));
				memberService.deleteMember(memberVO.getMem_num());
				//로그아웃
				session.invalidate();
				return "shopMain";
			}else {
				//인증 실패
				result.reject("invalidIdOrPassword");
				return formDelete();
			}		
		}
}
