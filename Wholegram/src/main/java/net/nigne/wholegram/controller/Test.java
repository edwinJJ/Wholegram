package net.nigne.wholegram.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/test")
public class Test {

	/* 처음 게시물 리스트 보여줄 때*/ 
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView Board_List(Locale locale, Model model, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("notice_popup");
		return mav;
	}
}
