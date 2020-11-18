package com.increpas.cls.controller.guestBoard;

import java.util.*;
import javax.servlet.http.*;

import com.increpas.cls.controller.*;
import com.increpas.cls.dao.*;
import com.increpas.cls.vo.*;

public class GuestBoard implements ClsMain {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		String view = "guestBoard/GuestBoard";
		
		// 데이터베이스에서 데이터 가져오고
		GBoardDao gDao = new GBoardDao();
		ArrayList<GuestBoardVO> list = gDao.getGBoardList();
		
		int cnt = 0;
		try{
			String sid = (String) req.getSession().getAttribute("SID");
			/*
			// list 활용하는 방법
			for(int i = 0 ; i < list.size(); i++ ) {
				GuestBoardVO gVO = list.get(i);
				String tid = gVO.getId();
				if(sid.equals(tid)) {
					cnt = 1;
				}
			}
			*/
			
			// 질의명령을 한번더 보내서 처리하는 방법
			cnt = gDao.getIdCnt(sid);
		} catch(Exception e) {}
		
		// 뷰에 데이터 심고
		req.setAttribute("LIST", list);
		req.setAttribute("CNT", cnt);
		
		return view;
	}

}
