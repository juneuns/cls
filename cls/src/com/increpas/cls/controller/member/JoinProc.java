package com.increpas.cls.controller.member;

import javax.servlet.http.*;

import com.increpas.cls.controller.*;
import com.increpas.cls.dao.*;
import com.increpas.cls.vo.*;

/**
 * 	회원가입 처리 요청처리 전담 컨트롤러
 * @author	전은석
 * @since	2020.11.03
 * @version	v.1.0
 *
 */
public class JoinProc implements ClsMain {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		String view = "/cls/guestBoard/guestBoard.cls";
		// 페이지 요청방식 설정
		req.setAttribute("isRedirect", true);
		
		// 할일
		// 1. 파라미터 받고
		String sname = req.getParameter("name");
		String sid = req.getParameter("id");
		String spw = req.getParameter("pw");
		String smail = req.getParameter("mail");
		String stel = req.getParameter("tel");
		String sgen = req.getParameter("gen");
		String savt = req.getParameter("avt");
		
		// VO에 데이터 담고
		MemberVO mVO = new MemberVO();
		
		mVO.setName(sname);
		mVO.setId(sid);
		mVO.setPw(spw);
		mVO.setMail(smail);
		mVO.setGen(sgen);
		mVO.setAvt(Integer.parseInt(savt));
		
		// 데이터베이스 작업하고
		MemberDAO mDAO = new MemberDAO();
		int cnt = mDAO.addMemb(mVO);
		if(cnt == 0) {
			// 회원가입 실패한 경우
			view = "/cls/member/join.cls";
		} else {
			// 이 경우는 데이터베이스에 정보입력이 성공한 경우이므로 로그인 처리해준다.
			req.getSession().setAttribute("SID", sid);
		}
		
		return view;
	}

}
