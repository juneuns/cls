package com.increpas.cls.controller.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.increpas.cls.controller.*;
import com.increpas.cls.dao.*;
import com.increpas.cls.vo.*;

public class BoardEdit implements ClsMain {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		String view = "board/BoardEdit";
		
		// 파라미터 받고
		String sno = req.getParameter("bno");
		int bno = 0 ;
		try {
			bno = Integer.parseInt(sno);
		} catch(Exception e) {
			System.out.println("### 숫자 변환 에러 ###");
			
			// 이 경우는 게시글 번호를 가져올 수 없는 상황이므로
			// 게시글 리스트로 다시 보낸다.
			req.setAttribute("isRedirect", true);
			return "/cls/board/boardList.cls";
		}
		
		// 글 번호에 해당하는 데이터를 데이터베이스에서 가져오고..
		BoardDao bDao = new BoardDao();
		BoardVO bVO = bDao.getDetail(bno);
		bVO.setBody(bVO.getBody().replaceAll("<br>", "\n"));
		// 뷰에 데이터 심고
		req.setAttribute("DATA", bVO);
		
		return view;
	}

}
