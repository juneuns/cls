package com.increpas.cls.controller.board;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oreilly.servlet.*;
import com.oreilly.servlet.multipart.*;

import com.increpas.cls.controller.*;
import com.increpas.cls.dao.*;
import com.increpas.cls.util.*;
import com.increpas.cls.vo.*;

public class BoardWriteProc implements ClsMain {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) {
/*
	String title = req.getParameter("title");
	
	이제까지 위의 코드처럼 파라미터에서 데이터를 꺼내와서 작업을 했지만
	이 컨트롤러는 지금까지의 컨트롤러와는 조금 다르다.
	지금까지는 parameter 방식으로 데이터가 전달되었지만...
	
	지금은 파라미터 방식이 아닌 스트림 방식으로 데이터가 전달 되기 때문에
		( <form encType="multipart/form-data">
	받는 방식도 스트림 방식으로 받아야 된다.
	
	스트림 방식으로 처리해주는 라이브러리
		cos.jar
		file-upload.jar
	를 주로 사용하는데...
	
	cos.jar 에서는 
		MultipartRequest라는 클래스가 그 역할을 담당한다.
		
		사용법 ]
			
			new MultipartRequest(req, 서버의 저장경로, 업로드할 파일의 최대 크기, 인코딩방식,
									파일이름이 중복되는 경우 리네임정책);
					
		이 클래스를 new 시키면
			1. byte[]로 전송된 데이터를 사용하기 편하도록 파라미터 방식으로 변환시켜준다.
			2. 파일 업로드가 완성이 된다.
				==> 서버의 저장 경로에 파일이 저장된다.
				
		1. 저장 경로 지정방법
			1. 다운로드만을 위해서 저장한다면
				==> 서버의 아무곳에나 가능
					윈도우 - d:\\upload
					리눅스 - /home/user1/upload
			2. 화면(jsp or html)에 사용하기 위해서 저장하는 경우
				반드시 리얼패스(실제 저장경로)를 찾아서 저장해야 한다.
				
				예 ]
					
					String path = req.getSession().getSevletContext().getRealPath("upload");
		
		2. 업로드파일 최대크기는 byte 단위로 지정한다.
			예 ]
				6MB		==>  1024 * 1024 * 6
				
		3. 인코딩 방식
			파일의 이름이 한글인 경우 파일의 이름이 깨질 수 있다.
			이런 경우를 대비해서 한글 파일의 이름을 복구할  인코딩 방식을 지정하는 것.
			
			예 ]
				encoding="utf-8"
				
		4. 파일 리네임정책
			<--- 파일이름이 중복되는 경우 해결하는 정책
			파일이름 뒤에 (1), (2), ... 등의 숫자를 이용해서 변경하는 방식을 기본적으로 제공해 주고 있다.
			DefaultFileRenamePolicy p = new DefaultRileRenamePolicy();
*/

		// 지금부터 스트림 방식으로 날아온 데이터를
		// 파라미터 방식으로 변환 시키자.
		// 이때 서버의 저장경로에 파일이 자동으로 저장될 것이기 때문에 
		// 실제 실행되는 실제 저장경로를 알아내 온다.
		
		String view = "/cls/board/boardList.cls";
		req.setAttribute("isRedirect", true);
		
		// 아이디 꺼내오고
		String sid = SessionUtil.procSession(req, resp);
		
		String path = req.getSession().getServletContext().getRealPath("WEB-INF/resources/img/upload");
		try {
			MultipartRequest multi = new MultipartRequest(req, path, 1024*1024*10, "UTF-8",
											new DefaultFileRenamePolicy());
			String title = multi.getParameter("title");
			String body = multi.getParameter("body");
			
			BoardVO bVO = new BoardVO();
			bVO.setId(sid);
			bVO.setTitle(title);
			bVO.setBody(body);
			
			// 데이터베이스 작업하고
			BoardDao bDao = new BoardDao();
			
			int cnt = bDao.addBoard(bVO);
			
			if(cnt != 1) {
				// 이 경우는 데이터베이스에 입력이 실해한 경우이므로 쓰기페이지로 강제 이동시킨다.
				view = "/cls/board/boardWrite.cls";
				return view;
			}
			
			// 이 라인을 실행하는 경우는 입력에 성공한 경우이므로
			// 파일이 있으면 파일 정보 테이블에 입력해주면 된다.
			// 파일 정보가 필요하므로 만들어 준다.
			
			// 파일 정보 꺼내오고
			//ArrayList<FileVO> list = null;
			
			try{
				ArrayList<FileVO> list = getFileInfo(sid, multi);
				cnt = bDao.addFile(list);
			} catch(Exception e) {
				// 보여줄 뷰 부르고....
			}
			
			// 파일정보 데이터베이스 작업하고
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*
			일반 컨트롤러 보다 한가지 작업이 더 추가되어야 하는데
			byte[]을 파라미터로 바꾸는 작업이다.
			그 작업이 위에서 만든 MultipartRequest 를 만드는 과정이다.
			
			*****
			따라서 파라미터를 받을 때는
			multi에서 꺼내야 한다.
			<===
				스트림 방식으로 전송된 데이터를 파라미터로 바꾸서 저장하고 있는 객체가 multi 이기 때문에....
		 */
		
		
		return view;
	}

	// 파일 처리 함수
	public ArrayList<FileVO> getFileInfo(String id, MultipartRequest multi) {
		ArrayList<FileVO> list = new ArrayList<FileVO>();
		/*
			MultipartRequest 의  파일 관련 함수
			
				1. String getOriginalFilename("키값")
					==> 업로드하는 파일 원 이름을 꺼내주는 함수
				2. String getFilesystemName("키값")
					==> 업로드하는 파일의 서버에 저장된 이름을 꺼내주는 함수
				3. Enumeration getFileNames()
					==> 업로드하는 파일들의 키값들을 Enumeration 으로 반환해준다.
				4 String[] getParameterValues("키값")
					==> 하나의 키값으로 전달되는 파라미터를 배열형태로 반환해준다.
				5. String[]  getParameterNames()
					==> 파라미터 키값들을 문자열배열로 반환해준다.
					
		 */
		
		Enumeration en = multi.getFileNames();
		
		int idx = 0;
		while(en.hasMoreElements()) {
			String name = (String) en.nextElement();
			
			String oriname = multi.getOriginalFileName(name);
			
			// 아무것도 선택 안된 파일태그는 건너뛴다.
			if(oriname == null) continue;
			
			String savename = multi.getFilesystemName(name);
			File file = multi.getFile(name);
			long len = file.length();
			
			FileVO fVO = new FileVO();
			fVO.setId(id);
			fVO.setOriname(oriname);
			fVO.setSavename(savename);
			fVO.setLen(len);
			
			// 리스트에 VO 하나씩 담고
			list.add(fVO);
		}
		
		return list;
	}
}
