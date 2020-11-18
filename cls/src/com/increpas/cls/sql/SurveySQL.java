package com.increpas.cls.sql;

public class SurveySQL {
	public final int SEL_CURR_LIST		=	1001;
	public final int SEL_QUEST_LIST		=	1002;
	public final int SEL_ANSWER_RESULT	=	1003;
	
	public final int ADD_ANSWER			=	3001;
	
	public String getSQL(int code) {
		StringBuffer buff = new StringBuffer();
		switch(code) {
		case SEL_CURR_LIST:
			buff.append("SELECT ");
			buff.append("	sno, sbody, ");
			buff.append("   ( ");
			buff.append("        SELECT ");
			buff.append("            count(*) ");
			buff.append("        FROM ");
			buff.append("            surveyquest, surveyanswer, member ");
			buff.append("        WHERE ");
			buff.append("            sino = sno ");
			buff.append("            and sqno = sa_qno ");
			buff.append("            and mno = sa_mno ");
			buff.append("            and id = ? ");
			buff.append("    ) cnt ");
			buff.append("FROM ");
			buff.append("	surveyInfo ");
			buff.append("WHERE ");
			buff.append("	sysdate BETWEEN sstart AND send ");
			break;
		case SEL_QUEST_LIST:
			buff.append("SELECT ");
			buff.append("	sbody, sqno qno, sqbody qbody, sino sno, upno ");
			buff.append("FROM ");
			buff.append("	surveyinfo, surveyquest ");
			buff.append("WHERE ");
			buff.append("	sno = sino ");
			buff.append("	AND sno = ? ");
			buff.append("START WITH ");
			buff.append("	upno IS NULL ");
			buff.append("CONNECT BY ");
			buff.append("	PRIOR sqno = upno ");
			buff.append("ORDER SIBLINGS BY ");
			buff.append("	sqno ");
			break;
		case SEL_ANSWER_RESULT:
			buff.append("SELECT ");
			buff.append("    sbody, sqno qno, sqbody qbody, sno, upno, NVL(cnt, 0) cnt, TRUNC(NVL(per, 0), 2) per ");
			buff.append("FROM ");
			buff.append("    surveyinfo, ");
			buff.append("    surveyquest, ");
			buff.append("    ( ");
			buff.append("        SELECT ");
			buff.append("            sa_qno, count(*) cnt, ");
			buff.append("            (COUNT(*) / ( ");
			buff.append("                            SELECT ");
			buff.append("                                COUNT(DISTINCT sa_mno) ");
			buff.append("                            FROM ");
			buff.append("                                surveyanswer ");
			buff.append("                        ) * 100) PER ");
			buff.append("        FROM ");
			buff.append("            surveyanswer ");
			buff.append("        GROUP BY ");
			buff.append("            sa_qno ");
			buff.append("    ) ");
			buff.append("WHERE ");
			buff.append("    sno = sino ");
			buff.append("    AND sqno = sa_qno(+) ");
			buff.append("    AND sno = ? ");
			buff.append("START WITH ");
			buff.append("	upno IS NULL ");
			buff.append("CONNECT BY ");
			buff.append("	PRIOR sqno = upno ");
			buff.append("ORDER SIBLINGS BY ");
			buff.append("	sqno ");
			break;
		case ADD_ANSWER:
			buff.append("INSERT INTO ");
			buff.append("    surveyanswer ");
			buff.append("VALUES( ");
			buff.append("    (SELECT NVL(MAX(sano) + 1, 10001) FROM surveyanswer), ");
			buff.append("    (SELECT mno FROM member WHERE id = ? ), ");
			buff.append("    ?, sysdate ");
			buff.append(") ");
			break;
		}
		return buff.toString();
	}
}
