package com.increpas.cls.controller.member;

import javax.servlet.http.*;

import com.increpas.cls.controller.*;
import com.increpas.cls.dao.*;

public class MemberEdit implements ClsMain {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		// 할일
		// 파라미터 받아오고
		String sid = (String) req.getSession().getAttribute("SID");
		/*
		// 회원번호로 전달한 경우
		String sno = req.getParameter("mno");
		int mno = Integer.parseInt(sno);
		*/
		String mail = req.getParameter("mail");
		String savt = req.getParameter("avt");
		int avt = Integer.parseInt(savt);
		// 뷰요청방식 설정하고( 비동기 통신 : null )
		req.setAttribute("isRedirect", null);
		// 데이터베이스 작업하고 결과 받아오고
		MemberDAO mDAO = new MemberDAO();
		int cnt = mDAO.editMember(sid, mail, avt);
		
		// 뷰를 부르고
		return cnt + "";
	}

}
