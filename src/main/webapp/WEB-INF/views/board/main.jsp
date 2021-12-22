<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main</title>
</head>
<script src="/newspaper/mobile/js/jquery-3.4.1.min.js"></script>
<script src="<c:url value="/js/egovframework/mbl/cmm/jquery-1.9.1.min.js" />"></script>
<script>
	$(document).ready(function() {
		
	var sentence = document.getElementById("set");
	sentence.style.color ="red";
	
	var weather = document.getElementById("weather");
	sentence.style.color ="blue";
		
	});
</script>
<body>
	<div align="center">
		<div align="center">
			<h1 id="set">welcome to our WebSite!!</h1>
			<p>controller time : ${time }</p>
			<p id="yesterDay">${yesterDay }</p>
			<p id="weather">오늘의 날씨는 ${today }입니다.</p>
			<p id="micro">${controller } 농도는 ${controller2} 입니다. </p>
			<input type="button" value="read List" onclick="location.href='boardList'">
		</div>
	</div>

</body>
</html>