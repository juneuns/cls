package com.increpas.cls.controller.reBoard;

import java.util.*;
import javax.servlet.http.*;

import com.increpas.cls.controller.*;
import com.increpas.cls.dao.*;
import com.increpas.cls.vo.*;
import com.increpas.cls.service.*;

public class AddRBD implements ClsMain {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		String view = "/cls/reBoard/reBoardList.cls";
		req.setAttribute("isRedirect", true);
		
		AddRBDService rSrvc = new AddRBDService();
		int cnt = rSrvc.writeRBD();
		
		System.out.println("**** 입력된 원글 수  : " + cnt);
		
		return view;
	}
	
}
