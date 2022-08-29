<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Main</title>
<link rel="stylesheet" href="/resource/css/bootstrap.css">
</head>
<script src="/newspaper/mobile/js/jquery-3.4.1.min.js"></script>
<script src="<c:url value="/js/egovframework/mbl/cmm/jquery-1.9.1.min.js" />"></script>
<script type="text/javascript"
		src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.23/jquery-ui.min.js"></script>
<script>

	
</script>
<body>
	<div align="center">
		<div align="center">
			<h1 id="set">welcome to our WebSite!!</h1>
			<p>${img }</p>
			<p id="weather">지금의 날씨는 ${today }입니다.</p>
			<p id="yesterDay">${yesterDay }</p>
			<p id="micro"> ${misemeonji } 입니다. </p>
			<div>
				<input type="button" class="comText width_200" id="interview_detail_title" onclick="onClick" value="새로운창 오픈"  autocomplete="off" />
				<a class = "openModalBtn" type="button" onclick="onClick">새로운창 오픈</a>
			</div>
			<input type="button" class="btn btn-primary btn-sm" value="read List" onclick="location.href='boardList'">
			</div>

		</div>
		<div class="login_body" align="center">
			
						<fieldset>
	                    	<legend>instargram</legend>
							<ul>
								<li>
									<input type="image" src="${pageContext.request.contextPath}/resources/image/instagram_icon.png" alt="Login" class="login_bt" onclick="javascript:fn_actionLogin();return false;"/>
								</li>
								<li>
									<div class="login_idcheck">
		                                <input type="checkbox" class="login_Idcheckbox" id="login_Idcheckbox"/><label for="login_Idcheckbox">아이디 저장</label>	
		                            </div>
								</li>
							</ul>
						</fieldset>
		</div>
	
		
</body>
</html>