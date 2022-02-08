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
		<div align="center">
			<div>
				<div class="searchPanel">	
					<select id="interview_group_id" name="interview_group_id">
						<option value="">게시글선택</option>
						<c:forEach var="bdto" items="${resultList}" varStatus="status">
							<option >${bdto.subject }</option>
						</c:forEach>
					</select>				
					<span class="field"><input  type="text" class="comText txtHelp" id="txt_search" name="search_text" value="${BoardDTO.subject}"/><label for="txt_search">검색어를 입력해주세요.</label></span>
					<button type="button" class="nbtn" onclick="javascript:fn_searchList('1');return false;"><em class="cbtn btGray"><em>조회</em></em></button>
				</div><!-- searchPanel -->
			
			</div>
		</div>
</body>
</html>