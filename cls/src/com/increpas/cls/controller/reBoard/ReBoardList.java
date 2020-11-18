package com.increpas.cls.controller.reBoard;

import java.util.*;

import javax.servlet.http.*;

import com.increpas.cls.controller.*;
import com.increpas.cls.dao.*;
import com.increpas.cls.util.*;
import com.increpas.cls.vo.*;

public class ReBoardList implements ClsMain {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		String view = "reBoard/ReBoard";
		ReBoardDao rDao = new ReBoardDao();
		
		// 아바타 꺼내오기
		String sid = "";
		String avatar = "noimage.jpg";
		try {
			sid = (String) req.getSession().getAttribute("SID");
			avatar = rDao.getAvtFile(sid);
		} catch(Exception e) {}
		
		// 파라미터 받고
		int nowPage = 1;
		try {
			nowPage = Integer.parseInt(req.getParameter("nowPage"));
		} catch(Exception e) {}
		
		// PageUtil 만들고
		int total = rDao.getCnt();
		
		PageUtil page = new PageUtil(nowPage, total);
		
		ArrayList<ReBoardVO> list = rDao.getBoardList(page);
		
		// 데이터 뷰에 심고
		req.setAttribute("LIST", list);
		req.setAttribute("PAGE", page);
		req.setAttribute("AVTIMG", avatar);
		
		return view;
	}

}
