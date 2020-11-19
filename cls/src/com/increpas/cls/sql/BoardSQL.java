package com.increpas.cls.sql;

public class BoardSQL {
	public final int SEL_BOARD_LIST			= 1001;
	public final int SEL_MEMB_LIST			= 1002;
	public final int SEL_BOARD_TOTAL		= 1003;
	public final int SEL_BOARD_DETAIL		= 1004;
	
	public final int EDIT_BOARD				= 2001;
	
	public final int ADD_BOARD				= 3001;
	public final int ADD_FILE				= 3002;
	
	public String getSQL(int code) {
		StringBuffer buff = new StringBuffer();
		
		switch(code) {
		case SEL_BOARD_TOTAL:
			buff.append("SELECT ");
			buff.append("	COUNT(*) cnt ");
			buff.append("FROM ");
			buff.append("	board ");
			buff.append("WHERE ");
			buff.append("	bisshow = 'Y' ");
			break;
		case SEL_BOARD_LIST:
			buff.append("SELECT ");
			buff.append("    rno, bno, id, title, bdate, bclick, NVL(cnt, 0) cnt ");
			buff.append("FROM ");
			buff.append("    ( ");
			buff.append("    	SELECT ");
			buff.append("        	rownum rno, b.* ");
			buff.append("    	FROM ");
			buff.append("        	( ");
			buff.append("            	SELECT ");
			buff.append("                	bno, id, title, bdate, bclick ");
			buff.append("            	FROM ");
			buff.append("                	board, member ");
			buff.append("            	WHERE ");
			buff.append("                	mno = bmno ");
			buff.append("                	AND bisshow = 'Y' ");
			buff.append("            	order by ");
			buff.append("                	bdate DESC ");
			buff.append("        	 ) b ");
			buff.append("        ), ");
			buff.append("    (SELECT ");
			buff.append("        fbno, 	COUNT(*) cnt ");
			buff.append("    FROM ");
			buff.append("        fileinfo ");
			buff.append("    GROUP BY ");
			buff.append("        fbno) ");
			buff.append("WHERE ");
			buff.append("    bno = fbno(+) ");
			buff.append("    AND rno BETWEEN ? AND ? ");
			buff.append("ORDER BY ");
			buff.append("    rno ");
			break;
		case SEL_MEMB_LIST:
			buff.append("SELECT ");
			buff.append("	id ");
			buff.append("FROM ");
			buff.append("	member ");
			buff.append("WHERE ");
			buff.append("	isshow = 'Y' ");
			break;
		case SEL_BOARD_DETAIL:
			buff.append("SELECT ");
			buff.append("	bno, id, title, body, bclick click, bdate, ");
			buff.append("	fno, oriname, savename ");
			buff.append("FROM ");
			buff.append("	board, member, fileinfo ");
			buff.append("WHERE ");
			buff.append("	bisshow = 'Y' ");
			buff.append("	AND bmno = mno ");
			buff.append("	AND bno = fbno(+) ");
			buff.append("	AND bno = ? ");
			break;
		case EDIT_BOARD:
			buff.append("UPDATE ");
			buff.append("	board ");
			buff.append("SET ");
			buff.append("	### ");
			buff.append("WHERE ");
			buff.append("	bno = ? ");
			break;
		case ADD_BOARD:
			buff.append("INSERT INTO ");
			buff.append("    board(bno, bmno, title, body) ");
			buff.append("VALUES( ");
			buff.append("    (SELECT NVL(MAX(bno) + 1, 10001) FROM board ), ");
			buff.append("    (SELECT mno FROM member WHERE id = ? ), ");
			buff.append("    ?, ? ");
			buff.append(") ");
			break;
		case ADD_FILE:
			buff.append("INSERT INTO ");
			buff.append("    fileinfo(fno, fbno, dir, oriname, savename, len) ");
			buff.append("VALUES( ");
			buff.append("    (SELECT NVL(MAX(fno) + 1, 1000001) FROM fileinfo), ");
			buff.append("    ( ");
			buff.append("        SELECT ");
			buff.append("            MAX(bno) ");
			buff.append("        FROM ");
			buff.append("            board , member ");
			buff.append("        WHERE ");
			buff.append("            bmno = mno ");
			buff.append("            AND id = ? ");
			buff.append("    ), '/img/upload/' , ? , ? , ? ");
			buff.append(") ");
			break;
		};
		
		return buff.toString();
	}
}
