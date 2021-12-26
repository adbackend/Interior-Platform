package kr.spring.qna.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.product.service.ProductService;
import kr.spring.product.vo.ProductVO;
import kr.spring.qna.service.QnaService;
import kr.spring.qna.vo.QnaVO;
import kr.spring.util.PagingUtil;
import kr.spring.util.StringUtil;


@Controller
public class QnaController {
	private static final Logger logger = LoggerFactory.getLogger(QnaController.class);
	private int rowCount = 10;
	private int pageCount = 10;
	
	@Autowired
	private QnaService qnaService;
	@Autowired
	private ProductService productService;
	
	//자바빈(VO)초기화
	@ModelAttribute
	public QnaVO initCommand() {
		return new QnaVO();
	}
	
	//문의게시판목록
	@RequestMapping("/qna/qnaList.do")
	public ModelAndView qnaList(@RequestParam(value="board_kind") int board_kind,
								@RequestParam(value="pageNum",defaultValue="1") int currentPage,
								@RequestParam(value="keyfield",defaultValue="") String keyfield,
								@RequestParam(value="keyword2",defaultValue="") String keyword2){
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyfield", keyfield);
		map.put("keyword2", keyword2);
		map.put("board_kind",board_kind);
		
		//글의 총 갯수
		int count = qnaService.selectRowQnaCount(map);
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield,keyword2,currentPage, count, rowCount, pageCount, "qnaList.do","&board_kind="+board_kind);
		
		map.put("start", page.getStartCount());
		map.put("end", page.getEndCount());
			
		List<QnaVO> list = qnaService.selectAllQna(map);
		ModelAndView mav = new ModelAndView();
		mav.addObject("list",list);
		mav.addObject("count", count);
		mav.addObject("board_kind",board_kind);
		mav.addObject("pagingHtml", page.getPagingHtml());
		mav.setViewName("qnaList");
		return mav;
	} 
	
	//문의게시판상세
	@RequestMapping("/qna/qnaDetail.do")
	public ModelAndView qnaDetail(@RequestParam int board_no) {
		QnaVO qna = qnaService.selectDetailQna(board_no);
		ModelAndView mav = new ModelAndView();
		qna.setBoard_title(StringUtil.useNoHtml(qna.getBoard_title()));
		mav.addObject("qna",qna);
		mav.setViewName("qnaDetail");
		return mav;
	} 
	
	//문의게시판 글 작성폼
	@RequestMapping("/qna/qnaWrite.do")
	public ModelAndView qnaWriteForm(@RequestParam int board_kind) {
		return new ModelAndView("qnaWrite","board_kind",board_kind);
	}
	
	//문의게시판 상품 글 작성폼
	@RequestMapping("/qna/qnaProductWrite.do")
	public ModelAndView qnaProductWriteForm(@RequestParam int p_no) {
		ProductVO product = productService.ProductSelect(p_no);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("qnaProductWrite");
		mav.addObject("product",product);
		mav.addObject("board_kind",0);
		return mav;
	}
	
	//문의게시판 상품 글 작성폼
	@PostMapping("/qna/qnaProductWrite.do")
	public String qnaProdyctWrite(QnaVO qnaVO,HttpSession session) {
		qnaVO.setMem_num((Integer)session.getAttribute("mem_num"));
		qnaService.insertProductQna(qnaVO);
		return "redirect:/qna/qnaList.do?board_kind="+qnaVO.getBoard_kind();
	}
	
	//문의게시판 글 작성
	@PostMapping("/qna/qnaWrite.do")
	public String qnaWrite(QnaVO qnaVO, HttpSession session ) {
		logger.debug("<<글작성>> : "+qnaVO);
		
		qnaVO.setMem_num((Integer)session.getAttribute("mem_num"));
		
		if(qnaVO.getP_no()==0) {
			qnaService.insertQna(qnaVO);
		}else {
			qnaService.insertProductQna(qnaVO);
		}
		
		
		return "redirect:/qna/qnaList.do?board_kind="+qnaVO.getBoard_kind();
	}	
		
	//문의게시판 글 답변폼
	@GetMapping("/qna/qnaReply.do")
	public ModelAndView qnaReplyForm(@RequestParam int board_no) {
		QnaVO qna = qnaService.selectDetailQna(board_no);
		ProductVO product = null;
		if(qna.getP_no() != 0) {
			product = productService.ProductSelect(qna.getP_no());
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("qnaReply");
		mav.addObject("qna",qna);
		mav.addObject("product",product);
		return mav;
	}
		
	//문의게시판 답변 작성
	@PostMapping("/qna/qnaReply.do")
	public String qnaReply(QnaVO qnaVO, HttpSession session) {
		logger.debug("<<답변작성>> : " + qnaVO);
		qnaVO.setMem_num((Integer)session.getAttribute("mem_num"));
		if(qnaVO.getP_no()==0) {
			qnaService.insertQna(qnaVO);
		}else {
			qnaService.insertProductQna(qnaVO);
		}
		
		return "redirect:/qna/qnaList.do?board_kind="+qnaVO.getBoard_kind();
	}
	
	//문의게시판 글 삭제
	@RequestMapping("/qna/qnaDelete.do")
	public String qnaDelete(@RequestParam int board_no) {
		//board_kind알기위해
		QnaVO qna = qnaService.selectDetailQna(board_no);
		
		logger.debug("<<글 삭제 >> : "+board_no);
		qnaService.deleteQna(board_no);
		return "redirect:/qna/qnaList.do?board_kind="+qna.getBoard_kind();
	}
	
	//문의게시판 글 수정 폼
	@RequestMapping("/qna/qnaUpdate.do")
	public String qnaUpdateForm(@RequestParam int board_no, Model model) {
		QnaVO qnaVO = qnaService.selectDetailQna(board_no);
		model.addAttribute("qnaVO",qnaVO);
		
		return "qnaUpdate";
	}
	
	//문의게시판 글 수정
	@PostMapping("/qna/qnaUpdate.do")
	public String qnaUpdate(QnaVO qnaVO, Model model,HttpServletRequest request) {
		//board_kind알기위해
		qnaService.updateQna(qnaVO);
		
		model.addAttribute("message","글 수정이 완료되었습니다.");
		model.addAttribute("url", request.getContextPath()+"/qna/qnaList.do?board_kind="+qnaVO.getBoard_kind());
		
		return "common/resultView";
	}
}
