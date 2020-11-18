package com.increpas.cls.dao;

import java.util.*;
import java.sql.*;
import javax.sql.*;

import db.*;
import com.increpas.cls.sql.*;
import com.increpas.cls.vo.*;
import com.increpas.cls.util.*;

public class GBoardDao {
	private ClsDBCP db;
	private Connection con;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private GBoardSQL gSQL;
	
	public GBoardDao() {
		db = new ClsDBCP();
		gSQL = new GBoardSQL();
	}
	
	// 방명록 등록 데이터베이스작업 전담 처리함수
	public int addGBoard(ArrayList<HashMap<String, String>> list) {
		// 반환값 변수
		int cnt = 0 ;
		// con
		con = db.getCon();
		// sql
		String sql = gSQL.getSQL(gSQL.ADD_GBD);
		try {
			for(int i = 0 ; i < list.size() ; i++ ) {
				// pstmt
				pstmt = db.getPSTMT(con, sql);
				// 질의명령 완성
				pstmt.setString(1, list.get(i).get("id"));
				pstmt.setString(2, list.get(i).get("body"));
				// 보내고 결과받고
				cnt += pstmt.executeUpdate();
				db.close(pstmt);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(con);
		}
		// 결과 반환하고
		return cnt;
	}
	// 방명록 등록 데이터베이스작업 전담 처리함수
	public int addGBoard(String id, String body) {
		// 반환값 변수
		int cnt = 0 ;
		// con
		con = db.getCon();
		// sql
		String sql = gSQL.getSQL(gSQL.ADD_GBD);
		// pstmt
		pstmt = db.getPSTMT(con, sql);
		try {
			// 질의명령 완성
			pstmt.setString(1, id);
			pstmt.setString(2, body);
			// 보내고 결과받고
			cnt = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(pstmt);
			db.close(con);
		}
		// 결과 반환하고
		return cnt;
	}
	
	// 방명록 리스트 꺼내오기 전담 처리함수
	public ArrayList<GuestBoardVO> getGBoardList(){
		ArrayList<GuestBoardVO> list = new ArrayList<GuestBoardVO>();
		// 할일
		// 커넥션 얻어오고
		con = db.getCon();
		// 질의명령 가져오고
		String sql = gSQL.getSQL(gSQL.SEL_ALL_GBD);
		// 스테이트먼트 꺼내오고
		stmt = db.getSTMT(con);
		try {
			// 질의명령 보내고 결과받고
			rs = stmt.executeQuery(sql);
			// 결과처리하고
			// 여러행이 만들어지므로 반복해서 처리한다.
			while(rs.next()) {
				GuestBoardVO gVO = new GuestBoardVO();
				
				gVO.setGno(rs.getInt("gno"));
				gVO.setId(rs.getString("id"));
				gVO.setBody(rs.getString("body"));
				gVO.setAvatar(rs.getString("avatar"));
				gVO.setWdate(rs.getDate("wdate"));
				gVO.setWtime(rs.getTime("wdate"));
				
				// 리스트에 추가하고
				list.add(gVO);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(rs);
			db.close(stmt);
			db.close(con);
		}
		// 데이터 넘겨주고
		return list;
	}
	
	// 방명록 게시글 리스트 꺼내오기 전담 처리함수
	public ArrayList<GuestBoardVO> getGBoardList(PageUtil page){
		ArrayList<GuestBoardVO> list = new ArrayList<GuestBoardVO>();
		// 할일
		// 커넥션 얻어오고
		con = db.getCon();
		// 질의명령 가져오고
		String sql = gSQL.getSQL(gSQL.SEL_GBD_ROW);
		// 스테이트먼트 꺼내오고
		pstmt = db.getPSTMT(con, sql);
		try {
			// 질의명령 완성하고
			pstmt.setInt(1, page.getStartCont());
			pstmt.setInt(2, page.getEndCont());
			
			// 질의명령 보내고 결과받고
			rs = pstmt.executeQuery();
			// 결과처리하고
			// 여러행이 만들어지므로 반복해서 처리한다.
			while(rs.next()) {
				GuestBoardVO gVO = new GuestBoardVO();
				
				gVO.setRno(rs.getInt("rno"));
				gVO.setGno(rs.getInt("gno"));
				gVO.setId(rs.getString("id"));
				gVO.setBody(rs.getString("body"));
				gVO.setAvatar(rs.getString("avatar"));
				gVO.setWdate(rs.getDate("wdate"));
				gVO.setWtime(rs.getTime("wdate"));
				
				// 리스트에 추가하고
				list.add(gVO);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(rs);
			db.close(pstmt);
			db.close(con);
		}
		// 데이터 넘겨주고
		return list;
	}
	
	// 글작성여부 카운트 데이터베이스작업 전담 처리함수
	public int getIdCnt(String id) {
		// 반환값 변수
		int cnt = 0 ;
		
		// connection
		con = db.getCon();
		// sql
		String sql = gSQL.getSQL(gSQL.SEL_ID_CNT);
		// pstmt
		pstmt = db.getPSTMT(con, sql);
		
		try {
			pstmt.setString(1, id);
			// rs
			rs = pstmt.executeQuery();
			// 결과는 한 행
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
	
	// 총 게시물 가져오기 전담 처리함수
	public int getTotal() {
		int total = 0;
		
		con = db.getCon();
		String sql = gSQL.getSQL(gSQL.SEL_GBD_TCNT);
		stmt = db.getSTMT(con);
		try {
			rs = stmt.executeQuery(sql);
			rs.next();
			total = rs.getInt("total");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(rs);
			db.close(stmt);
			db.close(con);
		}
		
		return total;
	}
}
