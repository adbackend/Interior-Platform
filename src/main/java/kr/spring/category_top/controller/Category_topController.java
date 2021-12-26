package kr.spring.category_top.controller;

import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;

import kr.spring.category_top.service.Category_topService;
import kr.spring.category_top.vo.Category_topVO;

@Controller
public class Category_topController {

	private static final Logger logger = LoggerFactory.getLogger(Category_topController.class);

	@Autowired
	private Category_topService category_topService;

	// 자바빈(VO)초기화
	@ModelAttribute
	public Category_topVO initCommand() {
		return new Category_topVO();
	}
	
	// 상위 카테고리 등록
	@GetMapping("/category_top/category_topRegister")
	public String category_topRegisterForm(Category_topVO category_topVO){
		logger.info("<<category_top_registerForm>>");

		return "category_top_register"; //타일스식별자		
	}
	
	
	// 상위 카테고리 등록
	@PostMapping("/category_top/category_topRegister")
	public String category_topRegister(Category_topVO category_topVO){
		logger.info("<<category_top_register:>>"+category_topVO.getC_top_name());

		category_topService.insertCategory_top(category_topVO);

		return "redirect:/category_top/list.do";
		
	}

	// 상위카테고리 목록
	@RequestMapping("/category_top/main.do")
	public ModelAndView category_topList(@RequestParam(value = "c_top_no", defaultValue = "") int c_top_no,
			@RequestParam(value = "c_top_name", defaultValue = "") String c_top_name) {
		logger.debug(c_top_name);

		ModelAndView mav = new ModelAndView();
		mav.setViewName("category_topList");

		return mav;
	}

	// 상위카테고리 상세정보
	@RequestMapping("/category_top/list.do")
	public String selectCategory_top(HttpSession session, Model model) {
		List<Category_topVO> list = category_topService.selectCategory_top();
				
		logger.debug("상위카테고리 정보 : " + list);
		
		model.addAttribute("list", list);
		
		return "category_top_list"; // 타일스 식별자
	}

	// 상위카테고리 정보 수정 - 수정 폼
	@GetMapping("/category_top/category_topUpdate.do")
	public String formUpdate(@RequestParam int c_top_no, Model model) {
		logger.debug("c_top_no : " + c_top_no);
		
		Category_topVO category_topVO = category_topService.selectCategory_top(c_top_no);
		
		logger.debug("<<Category_topVO>> : " + category_topVO);
		
		model.addAttribute("category_topVO", category_topVO);

		return "category_top_update"; // 타일스 설정
	}

	// 상위카테고리 정보 수정 - 수정 데이터 처리
	@PostMapping("/category_top/category_topUpdate.do")
	public String submitUpdate(@Valid Category_topVO category_topVO, BindingResult result, HttpSession session) {

		logger.debug("대분류 카테고리 정보수정 : " + category_topVO);

		// 유효성 체크 결과 오류가 있으면 폼 호출
		if (result.hasErrors()) {
			return "category_top_update";
		}

		// 대분류 카테고리 정보수정
		category_topService.updateCategory_top(category_topVO);

		return "redirect:/category_top/list.do";
	}
	
	//상위카테고리 삭제 폼
	@GetMapping("/category_top/category_topDelete.do")
	public String category_topDeleteForm(@RequestParam int c_top_no, HttpServletRequest request, Model model) {
		logger.debug("category_top deleteForm 호출 - c_top_no : " + c_top_no);
		
		//삭제할 카테고리 번호
		model.addAttribute("c_top_no", c_top_no);
		
		return "category_top_delete";
	}
	
	//상위카테고리 삭제 처리
	@PostMapping("/category_top/category_topDelete.do")
	public String category_topDelete(int c_top_no) {
		logger.debug("category_top delete 호출 - c_top_no : " + c_top_no);
		
		//카테고리 삭제
		category_topService.deleteCategory_top(c_top_no);
		
		return "category_top/delete";
	}
	
}
