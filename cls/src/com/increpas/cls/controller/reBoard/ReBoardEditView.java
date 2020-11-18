package com.increpas.cls.controller.reBoard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.increpas.cls.controller.ClsMain;

public class ReBoardEditView implements ClsMain {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		// 이 컨트롤러는 게시글 리스트에서 전달한 파라미터를 받아서
		// jsp에 표현하고 뷰만 보여주는 기능의 컨트롤러이다.
		String body = req.getParameter("body").replace("\r\n", "<br>");
		String view = "reBoard/ReBoardEdit";
		req.setAttribute("BODY", body);
		return view;
	}

}
