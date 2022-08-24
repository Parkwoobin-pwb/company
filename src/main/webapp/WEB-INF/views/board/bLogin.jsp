<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset= UTF-8">
<title>Insert title here</title>
<link href="${contextPath }/resources/css/myStyle.css" rel="stylesheet" />
<script src="${contextPath}/resources/jQuery/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="/resource/js/bootstrap.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
</head>
<c:if test="${not empty message }">
<script>
		$().ready(function(){
			alert("아이디와 비밀번호를 확인하세요");
		});
</script>
</c:if>
<body>
	<div>
	  	<c:import url="/header.do"/>
	</div>
	
	<br>
	<form action="login.do" method="post" name="userdto">
		<table class="table table-bordered table-hover">
			<colgroup>
				<col width="20%">
				<col width="80%">
			</colgroup>
			<tr>
				<td><label for="memberId">아이디</label></td>
				<td><input class="form-control" id="memberId" name="memberId" style="display:inline; width:300px;"type="text" placeholder="아이디를 입력하세요." /></td>
			</tr>
			<tr>
				<td><label for="memberPw">비밀번호</label></td>
				<td><input class="form-control" id="memberPw" name="memberPw" style="display:inline; width:300px;" type="password" placeholder="비밀번호를 입력하세요." /></td>
			</tr>
			<tr >
				<td colspan="2" align="left">
					<input type="submit" class="btn btn-primary btn-sm" value="로그인">
					<input type="button" class="btn btn-primary btn-sm" value="move Board" onclick="location.href='boardList'">
				</td>
			</tr>
		</table>
	</form>

</body>
</html>