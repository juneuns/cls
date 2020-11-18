package com.increpas.cls.controller.survey;

import java.util.*;
import javax.servlet.http.*;

import com.increpas.cls.controller.*;
import com.increpas.cls.dao.*;
import com.increpas.cls.vo.*;

public class SurveyInfo implements ClsMain {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		// 할일
		// 세션 처리하고
		String sid = "";
		try {
			sid = (String) req.getSession().getAttribute("SID");
			if(sid == null) {
				throw new Exception();
			}
		} catch(Exception e) {
			req.setAttribute("isRedirect", true);
			return "/cls/member/login.cls";
		}
		
		SurveyDao sDao = new SurveyDao();
		ArrayList<SurveyVO> list = sDao.getSIList(sid);
		
		// 뷰에 데이터 심고
		req.setAttribute("LIST", list);
		
		String view = "survey/SurveyInfo";
		return view;
	}

}
