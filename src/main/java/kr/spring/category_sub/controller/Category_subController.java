package kr.spring.category_sub.controller;

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

import kr.spring.category_sub.service.Category_subService;
import kr.spring.category_top.service.Category_topService;
import kr.spring.category_sub.vo.Category_subVO;
import kr.spring.category_top.vo.Category_topVO;

@Controller
public class Category_subController {
	
	private static final Logger logger = LoggerFactory.getLogger(Category_subController.class);
	
	@Autowired
	private Category_subService category_subService;
	
	@Autowired
	private Category_topService category_topService;
	
	//자바빈(VO)초기화
	@ModelAttribute
	public Category_subVO initCommand() {
		return new Category_subVO();
	}
	
	// 하위 카테고리 등록
	@GetMapping("/category_top/category_sub/category_subRegister")
	public String category_subRegisterForm(Category_subVO category_subVO, Model model){
		logger.info("<<category_sub_registerForm>>");
		
		List<Category_topVO> list = category_topService.selectCategory_top();

		model.addAttribute("topList", list);

		return "category_sub_register"; //타일스 식별자		
	}
	
	// 하위 카테고리 등록
	@PostMapping("/category_top/category_sub/category_subRegister")
	public String category_topRegister(Category_subVO category_subVO){
		logger.info("<<category_sub_register:>>"+category_subVO.getC_sub_name());

		category_subService.insertCategory_sub(category_subVO);

		return "redirect:/category_top/category_sub/list.do?c_top_no="+category_subVO.getC_top_no();
		
	}
	
	//대분류 밑에 나타내는 카테고리 정보
	@RequestMapping("/category_top/category_sub/list.do")
	public String wantedCategory_sub(int c_top_no, Model model) {
		
		List<Category_subVO> list = category_subService.category_subWanted(c_top_no);
		
		logger.debug("대분류 밑 카테고리 상세정보 : " + list);
		
		model.addAttribute("list", list);
		
		return "category_sub_list"; // 타일스 식별자
	}
	
	
	//하위카테고리 정보 수정 - 수정 폼
	@GetMapping("/category_top/category_sub/category_subUpdate.do")
	public String formUpdate(@RequestParam int c_sub_no, Model model) {
		logger.debug("c_sub_no : " + c_sub_no);
		
		Category_subVO category_subVO = category_subService.category_subWant(c_sub_no);
		List<Category_topVO> list = category_topService.selectCategory_top();
		
		logger.info("Category_subVO : " + category_subVO);
		
		model.addAttribute("category_subVO", category_subVO);
		model.addAttribute("topList", list);
		
		return "category_sub_update"; //타일스 설정
	}
	
	//하위카테고리 정보 수정 - 수정 데이터 처리
	@PostMapping("/category_top/category_sub/category_subUpdate.do")
	public String submitUpdate(@Valid Category_subVO category_subVO, BindingResult result, 
			                   HttpSession session) {
		
		logger.debug("소분류 카테고리 정보수정 : " + category_subVO);
		
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return "category_sub_update";
		}
		
		//소분류 카테고리 정보수정
		category_subService.updateCategory_sub(category_subVO);
		
		return "redirect:/category_top/category_sub/list.do?c_top_no="+category_subVO.getC_top_no();
	}
	
	//하위카테고리 삭제 폼
	@GetMapping("/category_top/category_sub/category_subDelete.do")
	public String category_subDeleteForm(@RequestParam int c_sub_no, HttpServletRequest request, Model model) {
		logger.debug("category_sub deleteForm 호출 - c_sub_no : " + c_sub_no);
		
		//삭제할 카테고리 번호
		model.addAttribute("c_sub_no", c_sub_no);
		
		
		
		return "category_sub_delete";
	}
	
	//상위카테고리 삭제 처리
	@PostMapping("/category_top/category_sub/category_subDelete.do")
	public String category_subDelete(int c_sub_no) {
		logger.debug("category_sub delete 호출 - c_sub_no : " + c_sub_no);
		
		//카테고리 삭제
		category_subService.deleteCategory_sub(c_sub_no);
		
		return "redirect:/category_top/category_sub/list.do?c_top_no="+((c_sub_no/100)*100);
	}
	
}












