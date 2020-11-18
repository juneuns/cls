package com.increpas.cls.util;

import javax.servlet.http.*;

public class SessionUtil {
	public static String procSession(HttpServletRequest req, HttpServletResponse resp) {
		String sid = (String) req.getSession().getAttribute("SID");
		if(sid == null) {
			try {
				resp.sendRedirect("/cls/member/login.cls");
			} catch(Exception e) {}
		}
		return sid;
	}
}
