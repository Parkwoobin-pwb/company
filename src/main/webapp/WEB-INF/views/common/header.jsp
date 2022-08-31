<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
 <!-- 타일즈를 사용하기 위해 반드시 추가 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<script type="text/javascript" src="/resource/js/bootstrap.js"></script>
</head>
<body>
	<nav class="navbar navbar-expand-md bg-dark navbar-dark">
	        <a class="navbar-brand" href="${pageContext.request.contextPath}/boardList">Woobin Board</a>
	        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
	          <span class="navbar-toggler-icon"></span>
	        </button>
	        <div class="collapse navbar-collapse" id="collapsibleNavbar">
	          <ul class="navbar-nav">
		     	<c:choose> 
		     	 	<c:when test = "${isLogin == true and userDTO.userType == 'A' }">
		               <li class="nav-item">
		               		<span class="user"><b>${userDTO.memberId }</b>님 로그인 중</span>&nbsp;
		               </li>
		               <li><a href="${pageContext.request.contextPath}/logout.do">로그아웃</a></li>&nbsp;&nbsp;
		               <li><a href="${pageContext.request.contextPath}/admin/userHistory">유저조회</a></li>&nbsp;&nbsp;
		               <li><a href="${pageContext.request.contextPath}/admin/goodsInsert">상품등록</a></li>&nbsp;&nbsp;
		           </c:when>
		           <c:when test="${isLogin==true and not empty userDTO }">
		           		<span class="user"><b>${userDTO.memberId }</b>님 로그인 중</span>&nbsp;
			   			<li><a href="logout.do">로그아웃</a></li>&nbsp;&nbsp;
			   			<!--  <li><a href="${contextPath}/myPageMain.do?${userDTO.memberId}">마이페이지</a></li>-->
			   			<li><a href="myPageMain.do">마이페이지</a></li>
			 	   </c:when>
		           <c:otherwise>
					    <li class="nav-item">
	                 		<a class="nav-link" href="boardWrite">게시글쓰기</a>
	               		</li>
	               		<li class="nav-item">
	                 		<a class="nav-link" href="join">회원가입</a>
	               		</li>  
	               		<li class="nav-item">
	                 		<a class="nav-link" href="login">로그인</a>
	               		</li>    
					 </c:otherwise>
				</c:choose> 
		           
		          </ul>
	        </div>  
	  </nav>
</body>
</html>