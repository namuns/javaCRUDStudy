package com.mis.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mis.domain.Criteria;
import com.mis.domain.PageMaker;
import com.mis.domain.ProductVO;
import com.mis.domain.SearchCriteria;
import com.mis.domain.UserVO;
import com.mis.service.ProductService;

@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	@Inject
	private ProductService service;
	
	
	@RequestMapping(value = "/register", method=RequestMethod.GET)
	public void registerGET() throws Exception {
		
	}
	

	@RequestMapping(value = "/register", method=RequestMethod.POST)
	public String registerPOST(ProductVO vo, RedirectAttributes rttr) throws Exception {
		
		service.register(vo);
		rttr.addFlashAttribute("msg", "SUCCESS");
		
				
		return "redirect:/product/list";
	}
	  
	
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	public void list(@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {
		
		model.addAttribute("list", service.listSearch(cri));
		
		//페이징 네비게이션이 추가.
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		
		//페이지 정보 화면 전달
		model.addAttribute("pageMaker", pageMaker);
	}
	
	
	@RequestMapping(value = "/readPage", method = RequestMethod.GET)
	public void read(@RequestParam("pno") int pno, Model model, @ModelAttribute("cri") SearchCriteria cri) throws Exception {
	model.addAttribute(service.read(pno));
	} 
	
	@RequestMapping(value = "/removePage", method = RequestMethod.POST)
	public String remove(int pno, HttpSession session, @ModelAttribute("cri") SearchCriteria cri, Model model,
			RedirectAttributes rttr) throws Exception {

		UserVO user = (UserVO) session.getAttribute("login");
		
		 // 2) 게시글의 작성자 정보와 비교
		// 2-1) 게시글 정보를 가져오기
		
		ProductVO vo = service.read(pno);
		
		// 2-2) 게시글 정보와 작성자 정보 비교
		
		if(user.getUsid().equals(vo.getWriter())) {
			
			//정보 일치 -> 
			service.remove(pno);
			rttr.addFlashAttribute("msg", "SUCCESS");
			return "redirect:/product/list";
		} else {
			
			//정보 불일치 -> 상세페이지로 강제 이동
			rttr.addAttribute("pno", pno);
			rttr.addAttribute("page", cri.getPage());
			rttr.addAttribute("perPageNum", cri.getPerPageNum());

			rttr.addAttribute("searchType", cri.getSearchType());
			rttr.addAttribute("keyword", cri.getKeyword());
			rttr.addFlashAttribute("msg", "로그인 정보가 일치하지않아 삭제 불가능합니다.");
			
			return "redirect:/product/readPage";
		}
		
		
	} 
	


	@RequestMapping(value = "/modifyPage", method = RequestMethod.GET)
	public String modifyPageGET(int pno, HttpSession session, @ModelAttribute("cri") SearchCriteria cri, Model model,
		RedirectAttributes rttr) throws Exception {
	
		// 수정할 수 있으려면, 로그인한 정보와 글의 작성자 정보가 일치해야.
		// 1) 로그인 정보 가져오기
		
		
	UserVO user = (UserVO) session.getAttribute("login");
	
	 // 2) 게시글의 작성자 정보와 비교
	// 2-1) 게시글 정보를 가져오기
	
	ProductVO vo = service.read(pno);
	
	// 2-2) 게시글 정보와 작성자 정보 비교
	
	if(user.getUsid().equals(vo.getWriter())) {
		
		//정보 일치 -> 
		model.addAttribute(vo);
		return "/product/modifyPage";
	} else {
		
		//정보 불일치 -> 상세페이지로 강제 이동
		rttr.addAttribute("pno", pno);
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());

		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addFlashAttribute("msg", "로그인 정보가 일치하지않아 수정이 불가능합니다.");
		
		return "redirect:/product/readPage";
	}
	
	

	
}
	
	
	
	@RequestMapping(value = "/modifyPage", method = RequestMethod.POST)
	public String modifyPagePOST(ProductVO vo, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) throws Exception {

		service.modify(vo);
		

		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		return "redirect:/product/list";
	}
	
}
