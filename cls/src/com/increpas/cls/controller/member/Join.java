package com.increpas.cls.controller.member;

import java.util.*;
import javax.servlet.http.*;

import com.increpas.cls.controller.*;
import com.increpas.cls.dao.*;
import com.increpas.cls.vo.*;

public class Join implements ClsMain {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
		// 일단 뷰를 부르자.
		String view = "member/Join";
		
		// 데이터를 준비한다.
		MemberDAO mDao = new MemberDAO();
		
		ArrayList<AvatarVO> list = mDao.getAvtAll();
		
		req.setAttribute("LIST", list);
		
		return view;
	}

}
