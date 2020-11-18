package com.increpas.cls.controller.survey;

import java.util.*;
import javax.servlet.http.*;

import com.increpas.cls.controller.*;
import com.increpas.cls.dao.*;
import com.increpas.cls.vo.*;

public class SurveyResult implements ClsMain {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		// 할일
		// 파라미터 받고
		String tno = req.getParameter("sno");
		String view = "survey/SurveyResult";
		int sno = 0 ; 
		try {
			sno = Integer.parseInt(tno);
		} catch(Exception e) {
			// 이경우는 설문주제 번호가 잘못된 경우이므로
			// 설문 정보페이지로 리다이렉트시킨다.
			view = "survey/SurveyRedirect";
			String rview = "/cls/survey/surveyInfo.cls";
			req.setAttribute("VIEW", rview);
		}
		
		SurveyDao sDao = new SurveyDao();
		ArrayList<SurveyVO> list = sDao.getResult(sno);
		
		// 데이터 뷰에 넘겨주고
		req.setAttribute("LIST", list);
		// 뷰를 부르고
		return view;
	}

}
