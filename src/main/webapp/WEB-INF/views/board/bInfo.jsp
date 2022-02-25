<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<title>Info</title>
<meta charset="UTF-8">
  <meta http-equiv="Content-Script-Type" content="text/javascript" />
<link rel="stylesheet" href="/resource/css/bootstrap.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script>
	function emailSend() {
		
		var result = confirm("메일을 보내시겠습니까?");
		if(result == true){
			alert("개발중에 있습니다.");
			
			$.ajax({
				url : "${contextPath}/sendEmail",
				type : "POST",
				data : {"email" : $("email").val()},
				processData : false,
				contentType:false,
				success : function(data){
					if(data == true){
						alert("메일을 성공적으로 보냈습니다.");
						console.log(data);
					}else{
						alert("메일주소가 없습니다.");
					}
				}
				
				
			})
			
		}else{
			alert("메일송부를 취소했습니다.");
		}
		
	}
	
</script>
<title>bInfo</title>
</head>
<script type="text/javascript" src="/resource/js/bootstrap.js"></script>
  
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<body>
	<!-- <nav class="navbar navbar-expand-md bg-dark navbar-dark">
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
	   </nav> -->
	<div>
	  	<c:import url="/header.do"/>
	</div>
	<div align="center" style="padding-top:200px">
		<div align="center">
			<h1>Board Info</h1>
		</div>
		<br><br>
		<table class="table" border="1" style=" width : 800px;">
			<tr align="center">
				<td>작성자</td>
				<td>${bdto.writer }</td>
			</tr>
			<tr align="center" >
				<td>제목</td>
				<td>${bdto.subject }</td>
			</tr>
			<tr align="center" >
				<td>조회수</td>
				<td>${bdto.readCount }</td>
			</tr>
			<tr align="center" >
				<td>등록일</td>
				<td><fmt:formatDate value="${bdto.regDate}" pattern="yyyy-MM-dd"/></td>
			</tr>
			<tr align="center">
				<td>내용</td>
				<td>${bdto.content }</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" class="btn btn-primary btn-sm" value="Reply" onclick="location.href='boardReplyWrite?num=${bdto.num}'">
					<input type="button" value="modify" onclick="location.href='boardUpdate?num=${bdto.num}'">
					<input type="button" value="delete" onclick="location.href='boardDelete?num=${bdto.num}'">
					<input type="button" value="메일보내기" onclick="javascrpit:emailSend(); return false;">
					<input type="button" class="btn btn-primary btn-sm" value="작성하기" onClick="onClickModal()">
					<input type="button" value="move Board" onclick="location.href='boardList'">
				</td>
			</tr>
		<form:form id="BoardDTO" name="BoardDTO" method="post">
			<input type="hidden" name="num" id="num" value="${ bdto.num}"/>
		</form:form>
		</table>
	</div>
	<script>
		const onClickModal  => {
			
			var num = '${bdto.num}';
			
			$.ajax({
				url : "boardReplyWrite",
				data : "num"+num,
				type : "POST",
				success : function(data) {
					
					var option = "width=1000, height  = 500, top = 100, left = 200, location=no"
					const openWin = window.open("boardReplyWrite","",option);
					console.log(num);
					
				},
				error : function() {
					alert("에러");
				}
			})
			
			
			
			
		}
	</script>
</body>
</html>