package com.increpas.cls.dao;

import java.util.*;
import java.sql.*;

import db.*;
import com.increpas.cls.sql.*;
import com.increpas.cls.util.*;
import com.increpas.cls.vo.*;

public class BoardDao {
	ClsDBCP db;
	Connection con;
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	BoardSQL bSQL;
	
	public BoardDao() {
		db = new ClsDBCP();
		bSQL = new BoardSQL();
	}

	// 게시판 리스트 가져오기 전담 처리함수
	public ArrayList<BoardVO> getBoardList(PageUtil page){
		ArrayList<BoardVO> list = new ArrayList<BoardVO>();
		
		// con
		con = db.getCon();
		// sql
		String sql = bSQL.getSQL(bSQL.SEL_BOARD_LIST);
		// pstmt
		pstmt = db.getPSTMT(con, sql);
		try {
			// 질의명령완성하고
			pstmt.setInt(1, page.getStartCont());
			pstmt.setInt(2, page.getEndCont());
			
			// 질의보내고 결과받고
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				// 하나씩 꺼내서 VO에 담고 
				BoardVO bVO = new BoardVO();
				bVO.setBno(rs.getInt("bno"));
				bVO.setId(rs.getString("id"));
				bVO.setTitle(rs.getString("title"));
				bVO.setWdate(rs.getDate("bdate"));
				bVO.setWtime(rs.getTime("bdate"));
				bVO.setClick(rs.getInt("bclick"));
				bVO.setCnt(rs.getInt("cnt"));
				// VO를리스트에 담고
				list.add(bVO);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(rs);
			db.close(pstmt);
			db.close(con);
		}
		// 리스트 반환해주고
		return list;
	}
	
	// 게시판 글 작성 데이터베이스 작업 전담 처리함수
	public int addBoard(BoardVO bVO) {
		int cnt = 0 ;
		// 커넥션
		con = db.getCon();
		// sql
		String sql = bSQL.getSQL(bSQL.ADD_BOARD);
		// pstmt
		pstmt = db.getPSTMT(con, sql);
		try {
			pstmt.setString(1, bVO.getId());
			pstmt.setString(2, bVO.getTitle());
			pstmt.setString(3, bVO.getBody());
			
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
	
	// 파일정보 입력 전담 처리함수
	public int addFile(FileVO fVO) {
		int cnt = 0;
		con = db.getCon();
		String sql = bSQL.getSQL(bSQL.ADD_FILE);
		pstmt = db.getPSTMT(con, sql);
		try {
			// 질의명령 완성
			pstmt.setString(1, fVO.getId());
			pstmt.setString(2, fVO.getOriname());
			pstmt.setString(3, fVO.getSavename());
			pstmt.setLong(4, fVO.getLen());
			
			// 질의보내고 결과받고
			cnt = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(pstmt);
			db.close(con);
		}
		
		return cnt;
	}
	// 파일정보 입력 전담 처리함수2
	public int addFile(ArrayList<FileVO> list) {
		int cnt = 0;
		// 위에서 만든 단일파일 업로드 함수를 재사용하기로 한다.
		for(FileVO fVO : list) {
			cnt += addFile(fVO);
		}
		
		return cnt;
	}
	
	// 회원 아이디 리스트 가져오기 전담 처리함수
	public ArrayList<String> getIdList(){
		ArrayList<String> list = new ArrayList<String>();
		con = db.getCon();
		String sql = bSQL.getSQL(bSQL.SEL_MEMB_LIST);
		stmt = db.getSTMT(con);
		try {
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				list.add(rs.getString("id"));
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
	
	// 총 게시물 갯수 꺼내기 전담 처리함수
	public int getBoardTotal() {
		int total = 0;
		con = db.getCon();
		String sql = bSQL.getSQL(bSQL.SEL_BOARD_TOTAL);
		pstmt = db.getPSTMT(con, sql);
		try {
			// 질의명령은 이미 완성이 되었으므로 그냥 보내기만 하면 된다.
			rs = pstmt.executeQuery();
			rs.next();
			total = rs.getInt("cnt");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(rs);
			db.close(pstmt);
			db.close(con);
		}
		
		return total;
	}
	
	// 게시글 상세 정보 가져오기 전담 처리함수
	public BoardVO getDetail(int bno) {
		BoardVO bVO = new BoardVO();
		con = db.getCon();
		String sql = bSQL.getSQL(bSQL.SEL_BOARD_DETAIL);
		pstmt = db.getPSTMT(con, sql);
		try {
			pstmt.setInt(1, bno);
			rs = pstmt.executeQuery();
			
			ArrayList<FileVO> list = new ArrayList<FileVO>();
			FileVO fVO ;
			while(rs.next()) {
				fVO = new FileVO();
				if(!(rs.isFirst())) {
					fVO.setFno(rs.getInt("fno"));
					fVO.setOriname(rs.getString("oriname"));
					fVO.setSavename(rs.getString("savename"));
				} else {
					bVO.setBno(rs.getInt("bno"));
					bVO.setTitle(rs.getString("title"));
					bVO.setBody(rs.getString("body"));
					bVO.setId(rs.getString("id"));
					bVO.setClick(rs.getInt("click"));
					bVO.setWdate(rs.getDate("bdate"));
					bVO.setWtime(rs.getTime("bdate"));
					
					String oriname = rs.getString("oriname");
					if(oriname == null) {
						continue;
					}
					fVO.setFno(rs.getInt("fno"));
					fVO.setOriname(rs.getString("oriname"));
					fVO.setSavename(rs.getString("savename"));
				}
				list.add(fVO);
			}
			
			bVO.setList(list);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return bVO;
	}
	
	
}
