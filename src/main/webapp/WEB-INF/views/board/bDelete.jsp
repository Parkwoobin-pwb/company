<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<meta charset="UTF-8">
<title>bDelete</title>
</head>
<body>
<nav class="navbar navbar-expand-md bg-dark navbar-dark">
	        <a class="navbar-brand" href="boardList">Woobin Board</a>
	        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
	          <span class="navbar-toggler-icon"></span>
	        </button>
	        <div class="collapse navbar-collapse" id="collapsibleNavbar">
	          <ul class="navbar-nav">
	               <li class="nav-item">
	                 <a class="nav-link" href="boardList">목록보기</a>
	               </li>
	          </ul>
	        </div>  
	   </nav>
	<div align="center" style="padding-top:200px">
		<div align="center">
			<h1>boardDelete</h1>
		</div>
		<form action="boardDelete" method="post">
			<table class="table" border="1" style="width:600px">
				<colgroup>
					<col width=30%>
					<col width=70%>
				</colgroup>
				<tr align="center">
					<td>NUM</td>
					<td>${bdto.num }</td>
				</tr>
				<tr align="center">
					<td> WRITER</td>
					<td>${bdto.writer }</td>
				</tr>
				<tr align="center">
					<td>SUBJECT</td>
					<td>${bdto.subject }</td>
				</tr>
				<tr align="center">
					<td>REG_DATE</td>
					<td><fmt:formatDate value="${bdto.regDate }" pattern="yyyy-MM-dd"/>
				</tr>
				<tr align="center">
					<td>PASSWORD</td>
					<td><input type="password" name="password" size="70"></td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<input type="hidden" name="num" value="${bdto.num }">
						<input type="submit" value="삭제하기">
						<input type="button" value="목록보기" onclick="location.href='boardList'">
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>