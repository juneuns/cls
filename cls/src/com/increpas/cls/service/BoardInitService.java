package com.increpas.cls.service;

import java.util.*;

import com.increpas.cls.dao.*;
import com.increpas.cls.vo.*;
public class BoardInitService {
	BoardDao bDao;
	public BoardInitService() {
		bDao = new BoardDao();
	}
	
	public int setBD() {
		int cnt = 0 ; 
		ArrayList<String> ids = bDao.getIdList();
		
		for(int i = 0 ; i < ids.size(); i++ ) {
			BoardVO bVO = new BoardVO();
			bVO.setId(ids.get(i));
			bVO.setTitle(ids.get(i) + " 회원 글");
			bVO.setBody(ids.get(i) +" 회원이 글을 씁니다.");
			cnt += bDao.addBoard(bVO);
		}
		return cnt;
	}

}
