<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>bUpdate</title>
</head>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
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
			<h1>Board Info</h1>
		</div>
		<form action="boardUpdate" method="post">
				<table class="table" border="1" style=" width : 700px;">
					<colgroup>
						<col width=20%>
						<col width=80%>
					</colgroup>
					<tr align="center">
						<td>NUMBER</td>
						<td>${bdto.num }</td>
					</tr>
					<tr align="center">
						<td>작성자</td>
						<td>${bdto.writer }</td>
					</tr>
					<tr align="center">
						<td>제목</td>
						<td><input type="text" name="subject" class="form-control" value="${bdto.subject }"></td>
					</tr>
					<tr align="center">
						<td>조회수</td>
						<td>${bdto.readCount }</td>
					</tr>
					<tr align="center">
						<td>password</td>
						<td><input type="password" class="form-control" name="password" /></td>
					</tr>
					<tr class="table-default">
						<td>내용</td>
						<td><textarea rows="10" cols="60" name="content" class="form-control">${bdto.content}</textarea></td>
					</tr>
					<tr align="center">
						<td colspan="2">
							<input type="hidden" name="num" value="${bdto.num }"/>
							<input type="submit" value="수정하기">
							<input type="button" value="목록보기" onclick="location.href='boardList'">
						</td>
					</tr>
				</table>
			</form>
	</div>
</body>
</html>