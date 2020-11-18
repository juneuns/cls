package com.increpas.cls.dao;

import java.sql.*;
import java.util.*;

import db.*;
import com.increpas.cls.sql.*;
import com.increpas.cls.util.*;
import com.increpas.cls.vo.*;

public class ReBoardDao {
	ClsDBCP db;
	Connection con;
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	ReBoardSQL rSQL;
	
	public ReBoardDao() {
		db = new ClsDBCP();
		rSQL = new ReBoardSQL();
	}
	
	// 댓글게시판 게시글 가져오기 전담 처리함수
	public ArrayList<ReBoardVO> getBoardList(PageUtil page){
		ArrayList<ReBoardVO> list = new ArrayList<ReBoardVO>();
		// 할일
		// 1. connection 가져오고
		con = db.getCon();
		// 2. 질의명령 가져오고
		String sql = rSQL.getSQL(rSQL.SEL_RBD_RNO);
		// 3. 스테이트먼트 가져오고
		pstmt = db.getPSTMT(con, sql);
		try {
			// 4. 질의명령 완성하고
			pstmt.setInt(1, page.getStartCont());
			pstmt.setInt(2, page.getEndCont());
			// 5. 질의명령 보내고 결과받고
			rs = pstmt.executeQuery();
			// 6. 결과에서 데이터꺼내서 리스트에 담고
			while(rs.next()) {
				// VO 만들고
				ReBoardVO rVO = new ReBoardVO();
				
				// 데이터 채우고
				rVO.setBno(rs.getInt("bno"));
				rVO.setAno(rs.getInt("ano"));
				rVO.setMno(rs.getInt("mno"));
				rVO.setId(rs.getString("id"));
				rVO.setAvatar(rs.getString("avatar"));
				rVO.setBody(rs.getString("body").replace("\r\n", "<br>"));
				rVO.setUpno(rs.getInt("upno"));
				rVO.setStep(rs.getInt("step"));
				rVO.setWdate(rs.getDate("wdate"));
				rVO.setWtime(rs.getTime("wdate"));
				
				// 리스트에 담고
				list.add(rVO);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(rs);
			db.close(pstmt);
			db.close(con);
		}
		// 7. 리스트 반환해주고...
		return list;
	}
	
	// 댓글 원글 입력 전담 처리함수
	public int addBoard(ReBoardVO rVO) {
		int cnt = 0 ; 
		con = db.getCon();
		String sql = rSQL.getSQL(rSQL.ADD_BOARD);
		pstmt = db.getPSTMT(con, sql);
		try {
			// 질의명령 완성하고
			pstmt.setString(1, rVO.getId());
			pstmt.setString(2, rVO.getBody());
			// 질의명령 보내고 결과받고
			cnt  = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(pstmt);
			db.close(con);
		}
		return cnt;
	}
	
	// 회원 아이디리스트 조회 전담 처리함수
	public ArrayList<ReBoardVO> getIdList(){
		ArrayList<ReBoardVO> list = new ArrayList<ReBoardVO>();
		con = db.getCon();
		String sql = rSQL.getSQL(rSQL.SEL_MEMBER_ID);
		stmt = db.getSTMT(con);
		try {
			// 질의명령 보내고 결과 받고
			rs = stmt.executeQuery(sql);
			// 데이터꺼내서 리스트에 담고
			while(rs.next()) {
				ReBoardVO rVO = new ReBoardVO();
				rVO.setId(rs.getString("id"));
				
				list.add(rVO);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(rs);
			db.close(stmt);
			db.close(con);
		}
		return list;
	}
	
	// 글등록 데이터베이스 작업 전담 처리함수
	public int addContent(String id, String body) {
		int cnt = 0 ;
		// 할일
		// 1. 커넥션 받아오고
		con = db.getCon();
		// 2. 질의명령 꺼내오고
		String sql = rSQL.getSQL(rSQL.ADD_BOARD);
		// 3. pstmt 가져오고
		pstmt = db.getPSTMT(con, sql);
		try {
			// 4. 질의명령 완성
			pstmt.setString(1, id);
			pstmt.setString(2, body);
			// 5. 질의명령 보내고 결과 받고
			cnt = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(pstmt); // 조회질의명령이 아니므로 ResultSet이 만들어지지 않는다.
			db.close(con);
		}
		// 6. 결과 내보내고
		return cnt;
	}
	
	// 글삭제 데이터베이스 작업 전담 처리함수
	public int delReboard(int bno) {
		int cnt = 0 ;
		
		con = db.getCon();
		String sql = rSQL.getSQL(rSQL.DEL_REBOARD);
		pstmt = db.getPSTMT(con, sql);
		try {
			pstmt.setInt(1, bno);
			
			cnt = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(pstmt);
			db.close(con);
		}
		
		return cnt;
	}
	
	// 글 수정 데이터베이스 작업 전담 처리함수
	public int editReBoard(int bno, String body) {
		int cnt = 0;
		// 할일
		// 커넥션 얻어오고
		con = db.getCon();
		// 질의명령 가져오고
		String sql = rSQL.getSQL(rSQL.EDIT_REBOARD);
		// pstmt 가져오고
		pstmt = db.getPSTMT(con, sql);
		try {
			// 질의명령 완성하고
			pstmt.setString(1, body);
			pstmt.setInt(2, bno);
			// 질의명령 보내고 반환값 받고
			cnt = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(pstmt);
			db.close(con);
		}
		// 결과 내보내고
		return cnt;
	}
	
	// 댓글 추가 베이터베이스 작업 전담 처리함수
	public int addComment(String id, String body, int upno) {
		int cnt = 0 ;
		// con
		con = db.getCon();
		// sql
		String sql = rSQL.getSQL(rSQL.ADD_REBOARD);
		// pstmt
		pstmt = db.getPSTMT(con, sql);
		try {
			// 질의명령 완성
			pstmt.setString(1, id);
			pstmt.setString(2, body);
			pstmt.setInt(3, upno);
			
			// 질의명령 보내고 결과 받고
			cnt = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(pstmt);
			db.close(con);
		}
		return cnt;
	}
	
	// 아바타 저장이름 조회 전담 처리함수
	public String getAvtFile(String id) {
		String file = null;
		// 할일
		// 커넥션 얻어오고
		con = db.getCon();
		// 질의명령 가져오고
		String sql = rSQL.getSQL(rSQL.SEL_ID_AVT);
		// pstmt 가져오고
		pstmt = db.getPSTMT(con, sql);
		try {
			// 질의명령 완성하고
			pstmt.setString(1, id);
			// 질의명령 보내고 결과받고
			rs = pstmt.executeQuery();
			// 결과꺼내고
			while(rs.next()) {
				file = rs.getString("avatar");
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(rs);
			db.close(pstmt);
			db.close(con);
		}
		// 데이터 넘겨주고
		return file;
	}
	
	// 게시글 수 조회 전담 처리함수
	public int getCnt() {
		int cnt = 0 ; 
		con = db.getCon();
		String sql = rSQL.getSQL(rSQL.SEL_RBD_CNT);
		stmt = db.getSTMT(con);
		try {
			// 질의명령 보내고 결과받고
			rs = stmt.executeQuery(sql);
			// 결과값이 한행이므로
			rs.next();
			cnt = rs.getInt("cnt");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(rs);
			db.close(stmt);
			db.close(con);
		}
		
		return cnt;
	}
	
}
