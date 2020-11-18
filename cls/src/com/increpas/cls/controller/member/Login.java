package com.increpas.cls.controller.member;

import javax.servlet.http.*;

import com.increpas.cls.controller.ClsMain;

public class Login implements ClsMain {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		// 할일
		// 로그인 여부 검사하고
		HttpSession session = req.getSession();
		String sid = (String) session.getAttribute("SID");
		String view = "member/Login";
		if(sid == null) {
			// 포워드 또는 리다이렉트 설정하고
			req.setAttribute("isRedirect", false);
		} else {
			// 이미 로그인 한 경우이므로 메인페이지로 리다이렉트 시킨다.
			req.setAttribute("isRedirect", true);
			view = "/cls/main.cls";
		}
		// 뷰를 부른다.
		return view;
	}

}
