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
<script type="text/javascript">
	$(function(){
		$('#frm').submit();
	});
</script>
</head>
<body>
	<form method="POST" action="/cls/board/${VIEW}" id="frm">
		<input type="hidden" name="bno" value="${BNO}">
		<input type="hidden" name="nowPage" value="${NOWPAGE}">
	</form>
</body>
</html>