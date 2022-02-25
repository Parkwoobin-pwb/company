<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
 <!-- 타일즈를 사용하기 위해 반드시 추가 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<script type="text/javascript" src="/resource/js/bootstrap.js"></script>
</head>
<body>
	<nav class="navbar navbar-expand-md bg-dark navbar-dark">
	        <a class="navbar-brand" href="boardList">Woobin Board</a>
	        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
	          <span class="navbar-toggler-icon"></span>
	        </button>
	        <div class="collapse navbar-collapse" id="collapsibleNavbar">
	          <ul class="navbar-nav">
	     <%--   <c:if test="${user not empty}">
	               <li class="nav-item">
	               		<span class="user"><b>${user.memberId }</b>님 로그인 중</span>&nbsp;
	               </li>
	            </c:if>
	          	<c:if test="${user empty}">
	               <li class="nav-item">
	               		<span class="user"><b>${user.memberId }</b>님 로그인 중</span>&nbsp;
	               </li> 
	            </c:if>--%>
	               <li>
	                 <a class="nav-link" href="boardWrite">게시글쓰기</a>
	               </li>  
	               <li class="nav-item">
	                 <a class="nav-link" href="login">로그인</a>
	               </li>  
	          </ul>
	        </div>  
	  </nav>
</body>
</html>