package com.increpas.cls.controller.member;

import javax.servlet.http.*;

import com.increpas.cls.controller.*;
import com.increpas.cls.dao.*;

public class AjaxDel implements ClsMain {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		// 할일
		// 0. 접속된 상태인지 점검
		String ssid = (String) req.getSession().getAttribute("SID");
		if(ssid == null) {
			req.setAttribute("isRedirect", true);
			return "/cls/member/login.cls";
		}
		// 1. 파라미터 받고
		String sno = req.getParameter("no");
		int mno = Integer.parseInt(sno);
		String spw = req.getParameter("pw");
		// 2. 데이터베이스 작업하고
		MemberDAO mDAO = new MemberDAO();
		int cnt = mDAO.delMemb(mno, spw);
		// 3. 결과 반환해주고
		String result = "NO";
		if(cnt == 1) {
			req.getSession().removeAttribute("SID");
			result = "OK";
		}
		
		StringBuffer buff = new StringBuffer();
		buff.append("{");
		buff.append("	\"result\": \"" + result + "\"");
		buff.append("}");
		
		req.setAttribute("isRedirect", null);
		return buff.toString();
	}

}
