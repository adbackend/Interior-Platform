package kr.spring.product.controller;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.category_sub.service.Category_subService;
import kr.spring.category_sub.vo.Category_subVO;
import kr.spring.category_top.service.Category_topService;
import kr.spring.category_top.vo.Category_topVO;
import kr.spring.member.controller.MemberController;
import kr.spring.product.service.ProductService;
import kr.spring.product.vo.ProductVO;
import kr.spring.qna.service.QnaService;
import kr.spring.qna.vo.QnaVO;
import kr.spring.util.PagingUtil;

@Controller
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	private int rowCount = 10;
	private int pageCount = 10;
	private int rowCount2 = 10;
	private int pageCount2 = 10;
	
	@Autowired
	private QnaService qnaService;

	@Autowired
	private ProductService productService;

	@Autowired
	private Category_topService category_topService;

	@Autowired
	private Category_subService category_subService;

	// 상품 카테고리별 목록
	@RequestMapping("/shop/productList.do")
	public ModelAndView ShopProductList(@RequestParam(value="c_top_no") int c_top_no, 
										@RequestParam(value="c_sub_no") int c_sub_no,
										@RequestParam(value="orderby",defaultValue="") String orderby) {

		List<ProductVO> list = null;
		int count = productService.ProductCategorySelectCount(c_top_no, c_sub_no);
		if(count > 0) {
			if(orderby.equals("default") || orderby.equals("")) {
				list = productService.ProductCategorySelectAll(c_top_no, c_sub_no);
			}else if(orderby.equals("best")){
				list = productService.selectPriceBest(c_top_no,c_sub_no);
			}else if(orderby.equals("high")) {
				list = productService.selectPriceHigh(c_top_no, c_sub_no);
			}else if(orderby.equals("row")) {
				list=productService.selectPriceRow(c_top_no, c_sub_no);
			}
			
		}

		Category_topVO category_top = category_topService.selectCategoryOne(c_top_no);
		Category_subVO category_sub = category_subService.selectCategoryOne(c_sub_no);
		List<Category_topVO> list_top = category_topService.selectCategory_top();
		List<Category_subVO> list_sub = category_subService.category_subSelectAll();

		ModelAndView mav = new ModelAndView();
		mav.setViewName("shopProductList");
		mav.addObject("list",list);
		mav.addObject("count",count);
		mav.addObject("category_top",list_top);
		mav.addObject("category_sub",list_sub);
		mav.addObject("category_top_name",category_top);
		mav.addObject("category_sub_name",category_sub);
		
		mav.addObject("c_sub_no",c_sub_no);
		mav.addObject("c_top_no",c_top_no);
		mav.addObject("orderby",orderby);

		return mav;
	}
	
	//상품 검색 목록
	@RequestMapping("/shop/productSearch.do")
	public ModelAndView productSearch(@RequestParam(value="keyfield",defaultValue="p_name") String keyfield,
									  @RequestParam(value="keyword",defaultValue="") String keyword) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		
		int count = productService.selectCountSearchProduct(map);
		List<ProductVO> list = productService.selectSearchProduct(map);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("shopProductSearchList");
		mav.addObject("list",list);
		mav.addObject("keyword",keyword);
		mav.addObject("count",count);
		return mav;
	}

	//상품 상세 목록
	@RequestMapping("/shop/productDetail.do")
	public ModelAndView productDetail(@RequestParam int p_no,	
									@RequestParam(value="pageNum",defaultValue="1") int currentPage){
		
		int count = productService.countProduct(p_no);
		
		if(count == 0) {
			 return new ModelAndView("/common/notice");
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("p_no",p_no);
		
		//상품 문의 총 갯수
		int count2 = qnaService.selectAllPrpductQnaCount(map);
		
		//페이징처리
		PagingUtil page = new PagingUtil(currentPage, count2, rowCount2, pageCount2, "productDetail.do","&p_no="+p_no);
		
		//페이징
		map.put("start", page.getStartCount());
		map.put("end", page.getEndCount());
		
		
		List<QnaVO> list = qnaService.selectAllProductQna(map);
		
		ProductVO product = productService.ProductSelect(p_no);
		Category_topVO category_top = category_topService.selectCategoryOne(product.getC_top_no());
		Category_subVO category_sub = category_subService.selectCategoryOne(product.getC_sub_no());
		System.out.println(category_top);
		System.out.println(category_sub);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("shopProductDetail");
		mav.addObject("product",product);
		 mav.addObject("category_top_name",category_top);
		 mav.addObject("category_sub_name",category_sub);
		 //문의글
		 mav.addObject("list",list);
		 //문의글갯수
		 mav.addObject("count",count2);
		 mav.addObject("pagingHtml",page.getPagingHtml());
		 
		return mav;
	}

	//상품 이미지 출력
	@RequestMapping("/product/photoView.do")
	public ModelAndView viewImage(@RequestParam int p_no) {

		logger.debug("<<상품 상세 이미지 출력>> : " + p_no);
		ProductVO product = productService.ProductSelect(p_no);

		ModelAndView mav = new ModelAndView();
		mav.setViewName("imageView");
		mav.addObject("imageFile",product.getP_image());
		mav.addObject("filename",product.getP_image_name());
		return mav;
	}

	//자바빈(VO) 초기화
	@ModelAttribute
	public ProductVO initCommand() {
		return new ProductVO();
	}
	
	//ckeditor를 이용한 이미지 업로드
		@RequestMapping("/product/imageUploader.do")
		@ResponseBody
		public Map<String,Object> uploadImage(MultipartFile upload,
				                              HttpSession session,
				                              HttpServletRequest request)
		                                         throws Exception{
			
			//업로드할 절대 경로 구하기
			String realFolder = 
				session.getServletContext().getRealPath("/resources/image_upload");
			
			//업로드한 파일 이름
			String org_filename = upload.getOriginalFilename();
			String str_filename = System.currentTimeMillis() + org_filename;
			
			logger.debug("<<원본 파일명>> : " + org_filename);
			logger.debug("<<저장할 파일명>> : " + str_filename);
			
			Integer mem_num = (Integer)session.getAttribute("mem_num");
			
			String filepath = realFolder + "\\" + mem_num + "\\" + str_filename;
			logger.debug("<<파일 경로>> : " + filepath);
			
			File f = new File(filepath);
			if(!f.exists()) {
				f.mkdirs();
			}
			
			upload.transferTo(f);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("uploaded", true);
			map.put("url", request.getContextPath()+"/resources/image_upload/"+mem_num+"/"+str_filename);
			
			return map;
		}

	//상품 리스트
	@RequestMapping("/product/list.do")
	public ModelAndView process(@RequestParam(value="pageNum",defaultValue="1") int currentPage){

		Map<String,Object> map = new HashMap<String,Object>();



		//글의 총갯수 또는 검색된 글의 갯수
		int count = productService.selectRowCount(map);

		logger.debug("<<count>> : " + count);

		//페이지 처리
		PagingUtil page = 
				new PagingUtil(currentPage,count,rowCount,pageCount,"list.do");

		map.put("start", page.getStartCount());
		map.put("end", page.getEndCount());

		List<ProductVO> list = null;
		if(count > 0) {
			list = productService.selectList(map);
		}

		ModelAndView mav = new ModelAndView();
		mav.setViewName("productList"); //타일스 식별자
		mav.addObject("count", count);
		mav.addObject("list",list);
		mav.addObject("pagingHtml", page.getPagingHtml());

		return mav;
	}

	//상품 등록 페이지


	//상품 삭제 폼
	@GetMapping("/product/productDelete.do")
	public String category_topDeleteForm(@RequestParam int p_no, HttpServletRequest request, Model model) {
		logger.debug("product deleteForm 호출 - p_no : " + p_no);

		//삭제할 카테고리 번호
		model.addAttribute("p_no", p_no);

		return "productDelete";
	}

	//상품 삭제 처리
	@PostMapping("/product/productDelete.do")
	public String category_topDelete(int p_no) {
		logger.debug("product delete 호출 - p_no : " + p_no);

		//카테고리 삭제
		productService.deleteProduct(p_no);

		return "redirect:/product/list.do";
	}

	// 상품 등록
	@GetMapping("/product/productRegister")
	public String productRegisterForm(Model model){
		logger.info("<<product_registerForm>>");

		List<Category_topVO> list = category_topService.selectCategory_top();

		model.addAttribute("topList", list);

		logger.info("<<대분류>> : " + list);

		return "productRegister"; //타일스 식별자		
	}

	// 상품 등록
	@PostMapping("/product/productRegister")
	public String productRegister(ProductVO productVO){
		logger.info("<<product_register:>>"+productVO.getP_name());

		productService.insertProduct(productVO);

		return "redirect:/product/list.do";

	}
	//댓글 목록(ajax)
	@RequestMapping("/product/getSubCate.do")
	@ResponseBody
	public Map<String,Object> getList(@RequestParam int c_top_no){

		List<Category_subVO> list = category_subService.category_subWanted(c_top_no);

		Map<String,Object> mapJson = new HashMap<String,Object>();
		mapJson.put("list", list);
		
		logger.info("<<하위카테고리>>" + list);

		return mapJson;
	}
	
	//상품 정보 수정 - 수정 폼
	@GetMapping("/product/productUpdate.do")
	public String formUpdate(@RequestParam int p_no, Model model) {
		logger.info("p_no : " + p_no);
		
		ProductVO productVO = productService.ProductSelect(p_no);
		List<Category_topVO> list = category_topService.selectCategory_top();

		
		logger.debug("ProductVO : " + productVO);
		
		model.addAttribute("topList", list);
		model.addAttribute("productVO", productVO);
		
		return "productUpdate"; //타일스 설정
	}
	
	//상품 정보 수정 - 수정 데이터 처리
	@PostMapping("/product/productUpdate.do")
	public String submitUpdate(@Valid ProductVO productVO, BindingResult result, 
			                   HttpSession session) {
		
		logger.info("상품 정보수정 : " + productVO);
		
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return "productUpdate";
		}
		
		//소분류 카테고리 정보수정
		productService.updateProduct(productVO);
		
		return "redirect:/product/list.do";
	}
	

}
