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

import com.mis.domain.PageMaker;
import com.mis.domain.UserVO;
import com.mis.domain.SearchCriteria;
import com.mis.dto.LoginDTO;
import com.mis.service.UserService;

@Controller
@RequestMapping("/user/*")
public class UserController {

	@Inject
	private UserService service;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void loginGET(@ModelAttribute("dto") LoginDTO dto) throws Exception {

	}

	@RequestMapping(value = "/loginPost", method = RequestMethod.POST)
	public void loginPOST(LoginDTO dto, HttpSession sesstion, Model model) throws Exception {

		UserVO vo = service.login(dto);

		if (vo == null) {
			return;

		}
		model.addAttribute("userVO", vo);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) throws Exception {

		Object userVO = session.getAttribute("login");

		if (userVO != null) {

			session.removeAttribute("login");
			session.invalidate();

		}

		return "redirect:/";

	}

	@RequestMapping(value = "/memberRegister", method = RequestMethod.GET)
	public String memberRegisterGET(UserVO vo) throws Exception {

		return "user/user_register";

	}

	@RequestMapping(value = "/memberRegister", method = RequestMethod.POST)
	public String memberRegisterPOST(UserVO vo, RedirectAttributes rttr) throws Exception {

		service.register(vo);
		rttr.addFlashAttribute("msg", "SUCCESS");

		return "redirect:/user/login";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {

		model.addAttribute("list", service.listSearch(cri));

		// 페이징 네비게이션이 추가.
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);

		// 페이지 정보 화면 전달
		model.addAttribute("pageMaker", pageMaker);
	}

	@RequestMapping(value = "/readPage", method = RequestMethod.GET)
	public void read(@RequestParam("usid") String usid, Model model, @ModelAttribute("cri") SearchCriteria cri)
			throws Exception {
		model.addAttribute(service.read(usid));
	}

	@RequestMapping(value = "/removePage", method = RequestMethod.POST)
	public String remove(String usid, @ModelAttribute("cri") SearchCriteria cri, Model model, RedirectAttributes rttr)
			throws Exception {

		service.remove(usid);
		rttr.addFlashAttribute("msg", "SUCCESS");
		return "redirect:/user/list";

	}

	@RequestMapping(value = "/modifyPage", method = RequestMethod.GET)
	public String modifyPageGET(String usid, @ModelAttribute("cri") SearchCriteria cri, Model model,
			RedirectAttributes rttr) throws Exception {

		model.addAttribute(service.read(usid));

		return "/user/modifyPage";
	}

	@RequestMapping(value = "/modifyPage", method = RequestMethod.POST)
	public String modifyPOST(UserVO vo, @ModelAttribute("cri") SearchCriteria cri, RedirectAttributes rttr)
			throws Exception {

		service.modify(vo);

		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());

		rttr.addFlashAttribute("msg", "SUCCESS");

		return "redirect:/user/list";
	}

	// 회원가입

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registerGET(UserVO vo) throws Exception {

	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerPOST(UserVO vo, RedirectAttributes rttr) throws Exception {

		service.register(vo);
		rttr.addFlashAttribute("msg", "SUCCESS");

		return "redirect:/user/list";
	}
}