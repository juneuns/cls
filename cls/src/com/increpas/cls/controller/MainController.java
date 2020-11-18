package com.increpas.cls.controller;

import javax.servlet.http.*;

import com.increpas.cls.dao.*;

public class MainController implements ClsMain {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		req.setAttribute("isRedirect", false);
		String view = "main";
//		String view = "/WEB-INF/views/main.jsp";
		
		// 댓글게시판 입력글수 알아내기
		int cnt = new ReBoardDao().getCnt();
		
		// 데이터 뷰에 심고
		req.setAttribute("RCNT", cnt);
		return view;
	}
	
}
