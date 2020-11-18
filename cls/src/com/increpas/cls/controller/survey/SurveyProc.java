package com.increpas.cls.controller.survey;

import java.util.*;
import javax.servlet.http.*;

import com.increpas.cls.controller.*;
import com.increpas.cls.dao.*;
import com.increpas.cls.vo.*;
import com.increpas.cls.util.*;

public class SurveyProc implements ClsMain {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		// 세션 검사하고 아이디 꺼내고
		String sid = SessionUtil.procSession(req, resp);
		
		// 카운트 변수
		int cnt = 0;
		
		// 파라미터 받고
		Enumeration en = req.getParameterNames();
		ArrayList<SurveyVO> list = new ArrayList<SurveyVO>();
		try {
			while(en.hasMoreElements()) {
				String key = (String)en.nextElement();
				if(key.equals("sno")) {
					continue;
				};
				String sno = req.getParameter(key);
				int qno = Integer.parseInt(sno);
				
				SurveyVO sVO = new SurveyVO();
				sVO.setId(sid);
				sVO.setQno(qno);
				
				list.add(sVO);
			}
		} catch(Exception e) {}
		
		String view = "/cls/survey/surveyResult.cls";
		SurveyDao sDao = new SurveyDao();
		cnt = sDao.addAnswer(list);
		req.setAttribute("VIEW", view);
		req.setAttribute("SNO", req.getParameter("sno"));
		
		if(cnt != list.size()) {
			view = "/cls/survey/surveyInfo.cls";
			req.setAttribute("VIEW", view);
		}
		
		//System.out.println("####### controller keys size : " + list.size());
		
		return "survey/SurveyRedirect";
	}

}
