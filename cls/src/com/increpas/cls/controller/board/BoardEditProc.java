package com.increpas.cls.controller.board;

import com.oreilly.servlet.*;
import com.oreilly.servlet.multipart.*;
import javax.servlet.http.*;

import com.increpas.cls.controller.*;
import com.increpas.cls.dao.*;
import com.increpas.cls.vo.*;
import com.increpas.cls.util.*;

public class BoardEditProc implements ClsMain {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		String view = "board/boardRedirect";
		//할일
		// 1. 파라미터 받고 지금은 스트림 방식이므로 MultipartRequest 에서 꺼내와야 한다.
		// 아이디 꺼내오고
		String sid = SessionUtil.procSession(req, resp);
		int cnt = 0 ;
		int bno = 0;
		String path = req.getSession().getServletContext().getRealPath("\\WEB-INF\\resources\\img\\upload");
		try {
			MultipartRequest multi = new MultipartRequest(req, path, 1024*1024*10, "UTF-8",
											new DefaultFileRenamePolicy());
			String title = multi.getParameter("title");
			String body = multi.getParameter("body");
		
			String sno = multi.getParameter("bno");
			bno = Integer.parseInt(sno);
			
			// 2. VO에 채우고 
			BoardVO bVO = new BoardVO();
			bVO.setBno(bno);
			bVO.setTitle(title);
			bVO.setBody(body);
			
			// 3. 데이터베이스 작업하고 결과 받고
			BoardDao bDao = new BoardDao();
			cnt = bDao.editBoard(bVO);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		String tmp = "";
		if(cnt == 1) {
			// 정보수정에 성공한 경우
			tmp = "boardDetail.cls";
		} else if(cnt == 0){
			// 정보수정에 실패한 경우
			tmp = "boardEdit.cls";
		}
		
		req.setAttribute("VIEW", tmp);
		req.setAttribute("BNO", bno);
		
		return view;
	}

}
