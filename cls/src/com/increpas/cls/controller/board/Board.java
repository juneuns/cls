package com.increpas.cls.controller.board;

import java.util.*;

import javax.servlet.http.*;

import com.increpas.cls.controller.*;
import com.increpas.cls.dao.*;
import com.increpas.cls.util.*;
import com.increpas.cls.vo.*;

public class Board implements ClsMain {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		// 할일
		// 파라미터 받고
		String strPage = req.getParameter("nowPage");
		int nowPage = 1;
		int total = 0;
		BoardDao bDao = new BoardDao();
		total = bDao.getBoardTotal();
		
		try {
			nowPage = Integer.parseInt(strPage);
		} catch(Exception e) {
		}
		
		PageUtil page = new PageUtil(nowPage, total);
		
		// 필요한 데이터가 준비되었으니 데이베이스에 질의명령을 보내서 결과를 받으면 된다.
		ArrayList<BoardVO> list = bDao.getBoardList(page);
		
		// 뷰에 데이터 심고
		req.setAttribute("LIST", list);
		req.setAttribute("PAGE", page);
		
		// 뷰 부르고
		String view = "board/BoardList"; // forward 방식으로 뷰를 부른다.
		return view;
	}

}
