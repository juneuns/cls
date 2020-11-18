package com.increpas.cls.controller.reBoard;

import javax.servlet.http.*;

import com.increpas.cls.controller.*;
import com.increpas.cls.dao.*;

public class ReBoardEditProc implements ClsMain {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		// 세션 검사
		String sid = "";
		try {
			sid = (String) req.getSession().getAttribute("SID");
		} catch(Exception e) {
			// 이 경우는 세션이 끊긴경우이므로 로그인 페이지로 보내준다.
			req.setAttribute("isRedirect", true);
			return "/cls/member/login.cls";
		}
		
		// 파라미터 받고
		String tno = req.getParameter("bno");
		String body = req.getParameter("body");
		String nowPage = req.getParameter("nowPage");
		int bno = Integer.parseInt(tno);
		// 데이터베이스 작업
		ReBoardDao rDao = new ReBoardDao();
		int cnt = rDao.editReBoard(bno, body);
		
		/*
		String view = "/cls/reBoard/reBoardList.cls?nowPage=" + nowPage;
		req.setAttribute("isRedirect", true);
		*/
		
		String view = "reBoard/RedirectView";
		return view;
	}

}
