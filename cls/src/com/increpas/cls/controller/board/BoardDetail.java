package com.increpas.cls.controller.board;

import javax.servlet.http.*;

import com.increpas.cls.controller.*;
import com.increpas.cls.dao.*;
import com.increpas.cls.util.*;
import com.increpas.cls.vo.*;

public class BoardDetail implements ClsMain {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		String view = "board/BoardDetail";
		String sno = req.getParameter("bno");
		int bno = 0;
		try {
			bno = Integer.parseInt(sno);
		} catch(Exception e) {
			req.setAttribute("isRedirect", true);
			view = "/cls/board/boardList.cls";
		}
		return view;
	}
	
	public BoardVO getDetail(int bno) {
		BoardVO bVO = new BoardVO();
		
		return bVO;
	}

}
