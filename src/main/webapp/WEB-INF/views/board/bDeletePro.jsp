<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
	<c:choose>
		<c:when test="${success eq true }">
			<script>
				alert("삭제완료되었습니다.");
				location.href="boardList";
			</script>
		</c:when>
		<c:otherwise>
			<script>
				alert("비밀번호를 확인하세요");
				history.back(-1);
			</script>
		</c:otherwise>
	</c:choose>

</body>
</html>