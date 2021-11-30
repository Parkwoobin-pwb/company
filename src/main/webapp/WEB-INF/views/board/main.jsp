<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main</title>
</head>
<script src="/newspaper/mobile/js/jquery-3.2.1.min.js"></script>
<script src="<c:url value="/js/egovframework/mbl/cmm/jquery-1.11.2.min.js" />"></script>
<script>
	$(document).ready(function() {
		
	var sentence = document.getElementById("set");
	sentence.style.color ="red";
		
	});
</script>
<body>
	<div align="center">
		<div align="center">
			<h1 id="set">welcome to our WebSite!!</h1>
			<input type="button" value="read List" onclick="location.href='boardList'">
		</div>
	</div>

</body>
</html>