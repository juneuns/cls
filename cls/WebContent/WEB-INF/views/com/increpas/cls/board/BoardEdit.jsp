<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/cls/css/w3.css">
<link rel="stylesheet" type="text/css" href="/cls/css/cls.css">
<script type="text/javascript" src="/cls/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="/cls/js/board.js"></script>
<script type="text/javascript" src="/cls/js/boardEdit.js"></script>
</head>
<body>
	<div class="w3-content mw750">
		<h1 class="w3-indigo w3-center w3-round-large w3-card-4 w3-padding">파일게시판 글수정</h1>
		<form class="w3-col w3-margin-top w3-round-large w3-card-4 w3-padding"
			method="POST" action="/cls/board/boardEditProc.cls" encType="multipart/form-data"
			id="efrm" name="efrm">
			<input type="hidden" name="bno" value="${DATA.bno}">
			<div class="w3-col w3-margin-top">
				<label class="w3-col m2 w3-right-align w3-label">작성자</label>
				<div class="w3-col m9 pdl20 w3-label">${DATA.id}</div>
			</div>
			<div class="w3-col w3-margin-top">
				<label class="w3-col m2 w3-right-align w3-label">Title</label>
				<div class="w3-col m9 pdl20">
					<input type="text" name="title" id="title" 
							class="w3-col w3-input w3-border pdl20" placeholder="제목을 입력하세요!" value="${DATA.title}">
				</div>
			</div>
			<div class="w3-col w3-margin-top">
					<label class="w3-col m2 w3-right-align w3-label">File</label>
					<div class="w3-col m9 pdl20">
						<div class="w3-col w3-center">
	<c:forEach var="fdata" items="${DATA.list}">
							<div class="imgboxfr w3-margin-top w3-border w3-card-2" id="fr${fdata.fno}" >
								<div class="w3-col">
									<a id="${fdata.fno}" class="imgLink">
										<img src="/cls/img/upload/${fdata.savename}" class="imgsrc2" onLoad="resize(this, 'addImgW2', 'addImgH2')">
									</a>
									<span class="w3-col w3-text-grey">
										<small>${fdata.oriname}</small>
									</span>
									<span style="display: none;">${fdata.len}</span>
									<span style="display: none;">${fdata.fdate}</span>
								</div>
							</div>
	</c:forEach>
						</div>
						<div class="w3-col">
							<div class="w3-col w3-margin-top" id="filefr"><!-- 
								자바스크립트에서 태그를 추가...
						 --></div>
						</div>
						<div class="w3-col w3-center" id="fileimg" style="display: none;"><!-- 이미지 파일이 추가되면 이 부분에 이미지를 보여주기로 한다. --></div>
					</div>
			</div>
			<div class="w3-col w3-margin-top w3-margin-bottom">
				<label class="w3-col m2 w3-right-align w3-label">Message</label>
				<div class="w3-col m9 pdl20">
					<textarea name="body" id="body" 
							class="w3-col w3-input w3-border" placeholder="제목을 입력하세요!" rows="10" style="resize: none;">${DATA.body}</textarea>
				</div>
			</div>
		</form>
		<div class="w3-col w3-margin-top w3-margin-bottom w3-card-4">
			<div class="w3-third w3-button w3-red w3-hover-deep-orange" id="cbtn">취 소</div>
			<div class="w3-third w3-button w3-green w3-hover-lime" id="hbtn">Home</div>
			<div class="w3-third w3-button w3-blue w3-hover-aqua" id="edit">edit</div>
		</div>
		
		<div id="fileEditModal" class="w3-modal">
			<div class="w3-modal-content mw600 w3-card-4">
				<header class="w3-container w3-deep-orange"> 
					<div class="w3-col w3-center w3-button w3-right w3-hover-orange w3-display-topright closeBtn" id="closeBtn">&times;</div>
					<h2 class="w3-rest w3-left">■ File 삭제 !</h2>
				</header>
				<div class="w3-container">
					<div class="w3-col pd20">
						<div class="w3-display-container inblock delImgBox w3-border w3-card-2 pd10">
								<img src="/cls/img/upload/B6367089342_l.jpg" class="w3-display-middle w3-border delImg" id="mImg">
						</div>
						<div class="inblock imgDescBox pdl20 w3-text-grey w3-display-container txt11">
							<div class="w3-col w3-display-middle">
								<div class="w3-col w3-margin-bottom">
									<span class="w3-col m4 w3-right-align">파일번호 : </span><span class="w3-col m8 pdl10" id="mfno"></span>
								</div>
								<div class="w3-col w3-margin-bottom">
									<span class="w3-col m4 w3-right-align">파일이름 : </span><span class="w3-col m8 pdl10" id="moname"></span>
								</div>
								<div class="w3-col w3-margin-bottom">
									<span class="w3-col m4 w3-right-align">파일크기 : </span><span class="w3-col m8 pdl10" id="mlen"></span>
								</div>
								<div class="w3-col">
									<span class="w3-col m4 w3-right-align">저장시간 : </span><span class="w3-col m8 pdl10" id="mdate"></span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<footer class="w3-container w3-padding">
					<div class="w3-half pd5">
			 			<div class="w3-col w3-button w3-border w3-round-medium w3-border-red w3-hover-red closeBtn">close</div>
					</div>
					<div class="w3-half pd5">
			 			<div class="w3-col w3-button w3-border w3-round-medium w3-border-blue w3-hover-blue" id="delBtn">삭 제</div>
					</div>
				</footer>
			</div>
		</div>
	</div>

</body>
</html>