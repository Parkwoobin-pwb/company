<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
 <!-- 타일즈를 사용하기 위해 반드시 추가 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
	<div id="container">
      <div id="header">
         <tiles:insertAttribute name="header"/>
       <!-- tiles_member.xml의 <definition>의 하위 태그인 <put-attribute> 태그의 name이 header인 JSP표시 
           아래의 나머지도 같은 맥락-->
      </div>
      <div id="sidebar-left">
          <tiles:insertAttribute name="side"/> 
      </div>
      <div id="content">
          <tiles:insertAttribute name="body"/>
      </div>
      <div id="footer">
          <tiles:insertAttribute name="footer"/>
      </div>
    </div>
</body>
</html>