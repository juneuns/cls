package com.increpas.cls.sql;

public class ReBoardSQL {
	public final int SEL_RBD_RNO = 1001;
	public final int SEL_MEMBER_ID = 1002;
	public final int SEL_RBD_CNT = 1003;
	public final int SEL_ID_AVT = 1004;
	
	public final int EDIT_REBOARD = 2001;
	public final int DEL_REBOARD = 2002;
	
	public final int ADD_BOARD = 3001;
	public final int ADD_REBOARD = 3002;
	
	public String getSQL(int code) {
		StringBuffer buff = new StringBuffer();
		switch(code) {
		case SEL_RBD_RNO:
			buff.append("SELECT ");
			buff.append("    * ");
			buff.append("FROM ");
			buff.append("	( ");
			buff.append("    	SELECT ");
			buff.append("	        rownum rno, r.* ");
			buff.append("	    FROM ");
			buff.append("	        (SELECT ");
			buff.append("	            bno, b_mno mno, id, ano, afile avatar, body, wdate, upno, (level - 1) step ");
			buff.append("	        FROM ");
			buff.append("	            reboard re, member m, avatar a ");
			buff.append("	        WHERE ");
			buff.append("	            re.isshow = 'Y' ");
			buff.append("	            AND b_mno = mno ");
			buff.append("	            AND avt = ano ");
			buff.append("	        START WITH ");
			buff.append("	            upno IS NULL ");
			buff.append("	        CONNECT BY ");
			buff.append("	            PRIOR bno = upno ");
			buff.append("	        ORDER SIBLINGS BY ");
			buff.append("	            wdate DESC) r ");
			buff.append("	) ");
			buff.append("WHERE ");
			buff.append("    rno BETWEEN ? AND ? ");
			break;
		case SEL_MEMBER_ID:
			buff.append("SELECT ");
			buff.append("	id ");
			buff.append("FROM ");
			buff.append("	member ");
			buff.append("WHERE ");
			buff.append("	isshow = 'Y' ");
			break;
		case SEL_ID_AVT:
			buff.append("SELECT ");
			buff.append("	afile avatar ");
			buff.append("FROM ");
			buff.append("	member, avatar ");
			buff.append("WHERE ");
			buff.append("	isshow = 'Y' ");
			buff.append("	AND avt = ano ");
			buff.append("	AND id = ? ");
			break;
		case SEL_RBD_CNT:
			buff.append("SELECT ");
			buff.append("	COUNT(*) cnt ");
			buff.append("FROM ");
			buff.append("	reboard ");
			buff.append("WHERE ");
			buff.append("	isshow = 'Y' ");
			break;
		case DEL_REBOARD:
			buff.append("UPDATE ");
			buff.append("	reboard ");
			buff.append("SET ");
			buff.append("	isshow = 'N' ");
			buff.append("WHERE ");
			buff.append("	bno = ? ");
			break;
		case EDIT_REBOARD:
			buff.append("UPDATE ");
			buff.append("	reboard ");
			buff.append("SET ");
			buff.append("	body = ? ");
			buff.append("WHERE ");
			buff.append("	bno = ? ");
			break;
		case ADD_BOARD:
			buff.append("INSERT INTO ");
			buff.append("	reboard(bno, b_mno, body) ");
			buff.append("VALUES( ");
			buff.append("	(SELECT NVL(MAX(bno) + 1, 10001) FROM reboard), ");
			buff.append("	(SELECT mno FROM member WHERE id = ?), ");
			buff.append("	? ");
			buff.append(") ");
			break;
		case ADD_REBOARD:
			buff.append("INSERT INTO ");
			buff.append("	reboard(bno, b_mno, body, upno) ");
			buff.append("VALUES( ");
			buff.append("	(SELECT NVL(MAX(bno) + 1, 10001) FROM reboard), ");
			buff.append("	(SELECT mno FROM member WHERE id = ?), ");
			buff.append("	?, ? ");
			buff.append(") ");
			break;
		}
		
		return buff.toString();
	}
}
