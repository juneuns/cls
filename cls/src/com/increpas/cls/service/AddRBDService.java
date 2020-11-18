package com.increpas.cls.service;

import java.util.*;

import com.increpas.cls.dao.*;
import com.increpas.cls.vo.*;

public class AddRBDService {
	ReBoardDao rDao;
	public AddRBDService() {
		rDao = new ReBoardDao();
	}
	
	// 댓글게시판 원글입력 전담 처리함수
	public int writeRBD() {
		int cnt = 0 ; 
		ArrayList<ReBoardVO> list = getList();
		
		for(int i = 0 ; i < list.size(); i++ ) {
			cnt += rDao.addBoard(list.get(i));
		}
		
		return cnt;
	}
	
	// 댓글게시판 더미데이터 생성 함수
	public ArrayList<ReBoardVO> getList(){
		// 반환값 변수
		ArrayList<ReBoardVO> list = getIdList();
		// 본문글만 채워준다.
		for(int i = 0 ; i < list.size() ; i++ ) {
			list.get(i).setBody(list.get(i).getId() + " 님이 글을 씁니다."); // jiwoo 씨가 글을 씁니다.
		}
		
		return list;
	}
	
	// 회원 아이디리스트 반환해주는 함수
	public ArrayList<ReBoardVO> getIdList(){
		
		ArrayList<ReBoardVO> list = rDao.getIdList();
		
		/*
		list.add("euns");
		list.add("joo");
		list.add("euisan");
		list.add("sun");
		list.add("mygusdnd");
		list.add("smkim");
		list.add("jjang");
		list.add("joseph");
		list.add("kys");
		list.add("chan");
		list.add("wook");
		list.add("jieun");
		list.add("jeong");
		list.add("jiwoo");
		list.add("juhyun");
		list.add("park");
		list.add("jang");
		list.add("jinwoo");
		list.add("hh");
		list.add("hong");
		list.add("dooly");
		*/
		
		
		return list;
	}

}
