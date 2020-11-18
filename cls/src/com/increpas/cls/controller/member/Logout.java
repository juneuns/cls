package com.increpas.cls.controller.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.increpas.cls.controller.ClsMain;

public class Logout implements ClsMain {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		String view = "/cls/main.cls";
		// 뷰요청방식
		req.setAttribute("isRedirect", true);
		
		// 세션의 속성 지우고\
		try {
			req.getSession().removeAttribute("SID");
		} catch(Exception e) {}
		
		return view;
	}

}
