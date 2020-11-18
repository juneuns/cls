package com.increpas.cls.controller.guestBoard;

import java.util.*;
import javax.servlet.http.*;

import com.increpas.cls.controller.*;
import com.increpas.cls.dao.*;
import com.increpas.cls.vo.*;
import com.increpas.cls.util.*;

public class GBoardList implements ClsMain {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		String view = "guestBoard/GBoardList";
		
		// 파라미터 받고
		int nowPage = 1 ;
		try {
			nowPage = Integer.parseInt(req.getParameter("nowPage"));
		}catch(Exception e) {}
		
		// PageUtil 객체 만들고
		GBoardDao gDao = new GBoardDao();
		int total = gDao.getTotal();
		PageUtil page = new PageUtil(nowPage, total);
		
		// 데이터베이스에서 데이터 가져오고
		ArrayList<GuestBoardVO> list = gDao.getGBoardList(page);
		System.out.println("### list size : " + list.size());
		int cnt = 0;
		try{
			String sid = (String) req.getSession().getAttribute("SID");
			
			// 질의명령을 한번더 보내서 처리하는 방법
			cnt = gDao.getIdCnt(sid);
		} catch(Exception e) {}
		
		// 뷰에 데이터 심고
		req.setAttribute("LIST", list);
		req.setAttribute("CNT", cnt);
		req.setAttribute("PAGE", page);
		
		return view;
	}

}
