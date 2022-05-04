package com.mis.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mis.domain.BoardVO;
import com.mis.domain.Criteria;
import com.mis.domain.PageMaker;
import com.mis.domain.SearchCriteria;
import com.mis.service.BoardService;

@Controller
@RequestMapping("/sboard/*")
public class SboardController {
	
	@Inject
	private BoardService service;
	
	@RequestMapping(value = "/register", method=RequestMethod.GET)
	public void registerGET() throws Exception {
		
		
		
	}
	
	@RequestMapping(value = "/register", method=RequestMethod.POST)
	public String registerPOST(BoardVO vo, RedirectAttributes rttr) throws Exception {
		
		service.register(vo);
		rttr.addFlashAttribute("msg", "SUCCESS");
		
				
		return "redirect:/sboard/list";
	}
	  
	
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	public void list(@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {
		
		model.addAttribute("list", service.listSearch(cri));
		
		//페이징 네비게이션이 추가.
				PageMaker pageMaker = new PageMaker();
				pageMaker.setCri(cri);
				pageMaker.setTotalCount(service.listSearchCountCriteria(cri));
				
				//페이지 정보 화면 전달
				model.addAttribute("pageMaker", pageMaker);
	}
	
	
	@RequestMapping(value = "/readPage", method = RequestMethod.GET)
	public void read(@RequestParam("bno") int bno, Model model, @ModelAttribute("cri") SearchCriteria cri) throws Exception {
	model.addAttribute(service.read(bno));
	} 
	
	@RequestMapping(value = "/removePage", method = RequestMethod.POST)
	public String remove(@RequestParam("bno") int bno, SearchCriteria cri, RedirectAttributes rttr) throws Exception {

		service.remove(bno);
		

		rttr.addAttribute("searchType", cri.getSearchType());		
		rttr.addAttribute("keyword", cri.getKeyword());
		
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		
		return "redirect:/sboard/list";
		
		
	} 
	
	@RequestMapping(value = "/modifyPage", method = RequestMethod.GET)
	public void modifyGET(int bno, Model model, @ModelAttribute("cri") SearchCriteria cri) throws Exception {
	model.addAttribute(service.read(bno));
	}
	
	@RequestMapping(value = "/modifyPage", method = RequestMethod.POST)
	public String modifyPOST(BoardVO vo,  @ModelAttribute("cri") SearchCriteria cri, RedirectAttributes rttr) throws Exception {

		service.modify(vo);
		
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());		
		rttr.addAttribute("keyword", cri.getKeyword());
		
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		return "redirect:/sboard/list";
	}
	
	@RequestMapping(value = "/listCri", method = RequestMethod.GET)
	public void listCri(Criteria cri, Model model) throws Exception {
		// 페이징 네비게이션이 없음.
		model.addAttribute("list", service.listCriteria(cri));
		
	}
}
