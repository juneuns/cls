package com.increpas.cls.dao;

import java.sql.*;
import java.util.*;

import db.*;

import db.*;
import com.increpas.cls.sql.*;
import com.increpas.cls.vo.*;
public class MemberDAO {
	ClsDBCP db;
	Connection con;
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	MemberSQL mSQL;
	
	public MemberDAO() {
		db = new ClsDBCP();
		mSQL = new MemberSQL();
	}
	
	// 로그인 데이터베이스작업 전담 처리함수
	public int getLoginCnt(String sid, String spw) {
		int cnt = 0 ;
		// 할일
		// 1. 커넥션 얻어오고
		con = db.getCon();
		// 2. 질의명령 가져오고
		String sql = mSQL.getSQL(mSQL.SEL_LOGIN_CNT);
		// 3. PreparedStatement 가져오고
		pstmt = db.getPSTMT(con, sql);
		try {
			// 4. 질의명령 완성하고
			pstmt.setString(1, sid);
			pstmt.setString(2, spw);
			// 5. 질의명령 보내고 결과 받고
			rs = pstmt.executeQuery();
			// 6. 결과 꺼내고
			rs.next();
			cnt = rs.getInt("cnt");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(rs);
			db.close(pstmt);
			db.close(con);
		}
		
		// 7. 데이터 넘겨주고
		return cnt;
	}
	
	// 아이디 체크 데이터 베이스 전담 처리함수
	public int getIdCnt(String id) {
		int cnt = 0 ;
		
		// 할일
		// 1. 커넥션 얻어오고
		con = db.getCon();
		// 2. 질의명령 가져오고
		String sql = mSQL.getSQL(mSQL.SEL_ID_CNT);
		// 3. 스테이트먼트 가져오고
		pstmt = db.getPSTMT(con, sql);
		try {
			// 4. 질의명령 완성하고
			pstmt.setString(1, id);
			// 5. 질의명령 보내고 결과 받고
			rs = pstmt.executeQuery();
			
			// 6. 결과 꺼내고
			rs.next();
			cnt = rs.getInt("cnt");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(rs);
			db.close(pstmt);
			db.close(con);
		}
		
		return cnt;
	}
	
	// 아바타 데이터 가져오기 전담 처리함수
	public ArrayList<AvatarVO> getAvtAll(){
		ArrayList<AvatarVO> list = new ArrayList<AvatarVO>();
		// 할일
		// 1. 커넥션 얻어오고
		con = db.getCon();
		// 2. 질의명령 가져오고
		String sql = mSQL.getSQL(mSQL.SEL_AVT_ALL);
		// 3. Statement 얻어오고
		stmt = db.getSTMT(con);
		try{
			// 4. 질의명령보내고 결과 받고
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				// 4.1 list에 VO를 하나씩 완성해서 채워준다.
				// 		질의명령의 결과가 여러개의 아바타 정보를 조회를 할것이기 때문에 여러개의 데이터가 만들어진다.
				AvatarVO aVO = new AvatarVO();
				aVO.setAno(rs.getInt("ano"));
				aVO.setSavename(rs.getNString("sname"));
				aVO.setGen(rs.getNString("gen"));
				
				// vo가 완성이 되면 list에 채워준다.
				list.add(aVO);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(rs);
			db.close(stmt);
			db.close(con);
		}
		
		// 5. 리스트 반환해주고
		return list;
	}
	
	public int addMemb(MemberVO mVO) {
		int cnt = 0 ;
		// 할일
		// 1. 커넥션 얻어오고
		con = db.getCon();
		// 2. 질의명령 가져오고
		String sql = mSQL.getSQL(mSQL.ADD_MEMB);
		// 3. 스테이트먼트 만들고
		pstmt = db.getPSTMT(con, sql);
		try {
			// 4. 질의명령 완성하고
			pstmt.setString(1, mVO.getId());
			pstmt.setString(2, mVO.getPw());
			pstmt.setString(3, mVO.getName());
			pstmt.setString(4, mVO.getMail());
			pstmt.setString(5, mVO.getGen());
			pstmt.setInt(6, mVO.getAvt());
			// 5. 질의명령 보내고 결과받고
			cnt = pstmt.executeUpdate();
			// 반환값 cnt의 의미는 변경된 데이터의 수를 의미한다.
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(pstmt);
			db.close(con);
		}
		// 6. 결과 반환해주고
		return cnt;
	}
	
	public MemberVO getMembInfo(String id) {
		MemberVO mVO = new MemberVO();
		
		// con
		con = db.getCon();
		// sql
		String sql = mSQL.getSQL(mSQL.SEL_ID_INFO);
		// pstmt
		pstmt = db.getPSTMT(con, sql);
		try {
			// data setting
			pstmt.setString(1, id);
			// send sql
			rs = pstmt.executeQuery();
			
			rs.next();
			mVO.setMno(rs.getInt("mno"));
			mVO.setName(rs.getString("name"));
			mVO.setId(rs.getString("id"));
			mVO.setMail(rs.getString("mail"));
			mVO.setAvatar(rs.getString("sname"));
			mVO.setGen(rs.getString("gen"));
			mVO.setJoinDate(rs.getDate("joindate"));
			mVO.setJoinTime(rs.getTime("joindate"));
			mVO.setAvt(rs.getInt("avt"));
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(rs);
			db.close(pstmt);
			db.close(con);
		}
		
		return mVO;
	}
	
	// 회원정보 수정 데이터베이스작업 전담 처리함수
	public int editMember(String id, String mail, int avt) {
		int cnt = 0 ;
		
		// 할일
		// 1. 커넥션 얻어오고
		con = db.getCon();
		// 2. 질의명령 가져오고
		String sql = mSQL.getSQL(mSQL.EDIT_MEMB);
		// 3. 준비된스테이트먼트 가져오고
		pstmt = db.getPSTMT(con, sql);
		try {
			// 4. 질의명령 완성하고
			pstmt.setString(1, mail);
			pstmt.setInt(2, avt);
			pstmt.setString(3, id);
			// 5. 질의명령 보내고 결과받고
			cnt = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(pstmt);
			db.close(con);
		}
		// 6. 결과 반환해주고
		return cnt;
	}
	
	// 회원탈퇴 데이터베이스 작업 전담 처리함수
	public int delMemb(int mno, String pw) {
		int cnt = 0 ;
		// con
		con = db.getCon();
		// sql
		String sql = mSQL.getSQL(mSQL.DEL_MEMB);
		// pstmt
		pstmt = db.getPSTMT(con, sql);
		try{
			// 질의명령 완성하고
			pstmt.setInt(1, mno);
			pstmt.setString(2, pw);
			// 질의명령 보내고 결과받고
			cnt = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(pstmt);
			db.close(con);
		}
		// 결과 반환해주고
		return cnt;
	}
}
