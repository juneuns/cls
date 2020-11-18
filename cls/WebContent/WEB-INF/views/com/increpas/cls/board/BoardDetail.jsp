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
</head>
<body>
	<div class="w3-content mw750">
		<h1 class="w3-indigo w3-center w3-round-large w3-card-4 w3-padding">파일게시판 글쓰기</h1>
		<div class="w3-col w3-margin-top w3-round-large w3-card-4 w3-padding">
			<div class="w3-col w3-margin-top">
				<label class="w3-col m2 w3-right-align w3-label">작성자</label>
				<div class="w3-col m9 pdl20 w3-label">${DATA.id}</div>
			</div>
			<div class="w3-col w3-margin-top">
				<label class="w3-col m2 w3-right-align w3-label">Title</label>
				<div class="w3-col m9 pdl20">
					<div class="w3-col w3-input w3-border pdl20">${DATA.title}</div>
				</div>
			</div>
			<div class="w3-col w3-margin-top w3-margin-bottom">
					<label class="w3-col m2 w3-right-align w3-label">File</label>
					<div class="w3-col m9 w3-center w3-margin-bottom pdl20 filefr">
	<c:forEach var="fdata" items="${DATA.list}">
						<div class="imgboxfr w3-border">
							<div class="w3-margin-bottom imgbox3">
								<a href="/cls/img/upload/${fdata.savename}">
									<img src="/cls/img/upload/${fdata.savename}" class="imgsrc2">
								</a>
								<span class="w3-col w3-margin-bottom w3-text-grey"><small>${fdata.oriname}</small></span>
							</div>
						</div>
	</c:forEach>
					</div>
			</div>
			<div class="w3-col w3-margin-top w3-margin-bottom">
				<label class="w3-col m2 w3-right-align w3-label">Message</label>
				<div class="w3-col m9 pdl20">
					<div class="w3-col h70 w3-border">${DATA.body}</div>
				</div>
			</div>
		</div>
		<div class="w3-col w3-margin-top w3-margin-bottom w3-card-4">
			<div class="w3-third w3-button w3-red w3-hover-deep-orange" id="cbtn">취 소</div>
			<div class="w3-third w3-button w3-green w3-hover-lime" id="hbtn">Home</div>
			<div class="w3-third w3-button w3-blue w3-hover-aqua" id="ebtn">수정</div>
		</div>
	</div>
</body>
</html>