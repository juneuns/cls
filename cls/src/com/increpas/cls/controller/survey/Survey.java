package com.increpas.cls.controller.survey;

import java.util.*;

import javax.servlet.http.*;

import com.increpas.cls.controller.*;
import com.increpas.cls.dao.*;
import com.increpas.cls.vo.*;
import com.increpas.cls.util.*;

public class Survey implements ClsMain {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		// 세션 검사하고
		new SessionUtil().procSession(req, resp);
		
		String view = "survey/Survey";
		
		// 파라미터 꺼내고
		String strno = req.getParameter("sno");
		int sno = 0 ;
		try {
			sno = Integer.parseInt(strno);
			SurveyDao sDao = new SurveyDao();
			ArrayList<SurveyVO> list = sDao.getQuestList(sno);
			
			req.setAttribute("LIST", list);
			req.setAttribute("SNO", sno);
		} catch(Exception e) {
			req.setAttribute("isRedirect", true);
			view = "/cls/survey/surveyInfo.cls";
		}
		
		return view;
	}

}
