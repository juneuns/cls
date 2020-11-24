package com.increpas.cls.controller.board;

import javax.servlet.http.*;

import com.increpas.cls.controller.*;
import com.increpas.cls.dao.*;

public class BoardDelImg implements ClsMain {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		req.setAttribute("isRedirect", null);
		
		String sno = req.getParameter("fno");
		int fno = 0 ;
		int cnt = 0 ;
		System.out.println("########## sno : " + sno);
		BoardDao bDao = new BoardDao();
		try {
			fno = Integer.parseInt(sno);
			cnt = bDao.delImg(fno);
		} catch(Exception e) {}
		System.out.println("########## cnt : " + cnt);
		String count = cnt + "";
		StringBuffer buff = new StringBuffer();
		buff.append("{");
		buff.append("	\"cnt\": \"" + count + "\"" );
		buff.append("}");
		
		return buff.toString();
	}

}
