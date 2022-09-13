<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
 
    request.setCharacterEncoding("UTF-8");
 
%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script type="text/javascript" src="/resource/js/bootstrap.js"></script>
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<script>
	function search_order_history(fixedSearchPeriod) {
/* 		var formObj = document.createElement("form");
		var i_fixedSearch_period = document.createElement("input");
		i_fixedSearch_period.name = "fixedSearchPeriod";
		i_fixedSearch_period.value = fixedSearchPeriod;
		formObj.appendChild(i_fixedSearch_period);
		document.body.appendChild(formObj);
		formObj.method="get";
		formObj.action = "${contextPath}/userHistory";
		formObj.submit();
		 */
		 location.href="${contextPath}/userHistory?fixedSearchPeriod="+fixedSearchPeriod;
		

	}
	
	function fn_detail_search() {
		
		var parameter = document.searchParameter;
		
		beginYear = parameter.beginYear.value;
		beginMonth = parameter.beginMonth.value;
		beginDay = parameter.beginDay.value;
		endYear = parameter.endYear.value;
		endYear = parameter.endYear.value;
		endMonth = parameter.endMonth.value;
		endDay = parameter.endDay.value;
		search_type = parameter.search_type.value;
		search_word = parameter.search_word.value;
		
		if (beginYear < 10) 	beginYear = "0" + beginYear; 
		if (beginMonth < 10) 	beginMonth = "0" + beginMonth; 
		if (beginDay < 10) 		beginDay = "0" + beginDay; 
		if (endYear < 10) 		endYear = "0" + endYear; 
		if (endMonth < 10) 		endMonth = "0" + endMonth; 
		if (endDay < 10) 		endDay = "0" + endDay; 
		
		var url = "${contextPath}/userHistory?";
			url += "beginDate="+ beginYear+"-"+beginMonth+"-"+beginDay;
			url += "&endDate=" + endYear+"-"+endMonth+"-"+endDay;
			url += "&search_type=" + search_type;
			url += "&search_word=" +search_word;
		
		location.href=url;
		
		
		
	}
</script>
<title>User History</title>
</head>
<body>
	<div>
	  	<c:import url="/header.do"/>
	</div>
	&nbsp;&nbsp;
	<h2>회원조회</h2>
	<form method="post" name="searchParameter" >
		<table>
			<tbody>
				<tr>
					<td>
						
					</td>
				</tr>
				<tr>
					<td>
						<select name="curYear">
							<c:forEach var="i" begin="0" end="5">
								<c:choose>
									<c:when test="${endYear == endYear-i} ">
										<option value="${endYear}" selected>${endYear}</option>
									</c:when>
									<c:otherwise>
										<option value="${endYear-i}">${endYear-i}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>년 <select name="curMonth">
							<c:forEach var="i" begin="1" end="12">
								<c:choose>
									<c:when test="${endMonth == i }">
										<option value="${i}" selected>${i} </option>
									</c:when>
									<c:otherwise>
										<option value="${i}"> ${i}</option> 
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>월<select name="curDay">
							<c:forEach var="i" begin="1" end="31">
								<c:choose>
									<c:when test="${endDay == i }">
										<option value="${i}" selected>${i}</option>
									</c:when>
									<c:otherwise>
										<option value="${i}"> ${i}</option> 
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>일 &nbsp;이전&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="javascript:search_order_history('today')">
						   <img src="${contextPath}/resources/image/btn_search_one_day.jpg">
						</a>
						<a href="javascript:search_order_history('one_week')">
						   <img src="${contextPath}/resources/image/btn_search_1_week.jpg">
						</a>
						<a href="javascript:search_order_history('two_week')">
						   <img src="${contextPath}/resources/image/btn_search_2_week.jpg">
						</a>
						<a href="javascript:search_order_history('one_month')">
						   <img src="${contextPath}/resources/image/btn_search_1_month.jpg">
						</a>
						<a href="javascript:search_order_history('two_month')">
						   <img src="${contextPath}/resources/image/btn_search_2_month.jpg">
						</a>
						<a href="javascript:search_order_history('three_month')">
						   <img src="${contextPath}/resources/image/btn_search_3_month.jpg">
						</a>
						<a href="javascript:search_order_history('four_month')">
						   <img src="${contextPath}/resources/image/btn_search_4_month.jpg">
						</a>
						&nbsp;까지 조회 
					</td>
				</tr>
				<tr>
					<td>
						조회한기간 : 
						<select name="beginYear">
							<c:forEach var="i" begin="0" end="5">
								<c:choose>
									<c:when test="${beginYear == beginYear-i} ">
										<option value="${beginYear}" selected>${beginYear}</option>
									</c:when>
									<c:otherwise>
										<option value="${beginYear-i}">${beginYear-i}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>년
						<select name="beginMonth">
							<c:forEach var="i" begin="1" end="12">
								<c:choose>
									<c:when test="${beginMonth == i }">
										<option value="${i}" selected>${i} </option>
									</c:when>
									<c:otherwise>
										<option value="${i}"> ${i}</option> 
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>월
						<select name="beginDay">
							<c:forEach var="i" begin="1" end="31">
								<c:choose>
									<c:when test="${endDay == i }">
										<option value="${i}" selected>${i}</option>
									</c:when>
									<c:otherwise>
										<option value="${i}"> ${i}</option> 
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>일
						&nbsp; ~
						<select name="endYear">
							<c:forEach var="i" begin="0" end="5">
								<c:choose>
									<c:when test="${endYear == endYear-i} ">
										<option value="${endYear}" selected>${endYear}</option>
									</c:when>
									<c:otherwise>
										<option value="${endYear-i}">${endYear-i}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>년
						<select name="endMonth">
							<c:forEach var="i" begin="1" end="12">
								<c:choose>
									<c:when test="${endMonth == i }">
										<option value="${i}" selected>${i} </option>
									</c:when>
									<c:otherwise>
										<option value="${i}"> ${i}</option> 
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>월
						<select name="endDay">
							<c:forEach var="i" begin="1" end="31">
								<c:choose>
									<c:when test="${endDay == i }">
										<option value="${i}" selected>${i}</option>
									</c:when>
									<c:otherwise>
										<option value="${i}"> ${i}</option> 
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</select>일
					</td> 
			
				</tr>
				<tr>
					<td>
					<select name="search_type">
						<option value ="all" selected>전체 </option>
						<option value ="memberName" >회원이름 </option>
						<option value ="memberId" >회원아이디 </option>
					</select>
					<input type="text" size="30" id="search_word" value="${search_word }" placeholder="검색어를 입력하세요">
					<input type="button" value="조회" id="btn_search" onclick="fn_detail_search()">
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	
	
     <p><input class="btn btn-outline-info btn-sm" type="button" value="excelPort" onclick="location.href='${contextPath}/admin/memberExport'"></p>
    
    
	<div class="userList">
		<table class="table">
			<thead class='thead-dark' class="form-controll">
				<tr align ="center">
					<th>ID</th>
					<th>NAME</th>
					<th>PHONE</th>
					<th>REG_DATE</th>
					<th>EMAIL</th>
				</tr>
			</thead>
				<c:choose>
					<c:when test="${empty userList}">
						<tr>
							<td colspan="5" class="fixed">
							<strong>유저 목록이 없습니다.</strong>
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="item" items="${userList }" varStatus="i">
							<tr align ="center">
								<td>${item.memberId }</td>
								<td>${item.memberName }</td>
								<td>
									${item.tel1} - 
									${item.tel2}<c:if test="${item.tel2 == '' || item.tel2 == null}">번호미등록</c:if>-
									${item.tel3}<c:if test="${item.tel3 == '' || item.tel3 == null}">번호미등록</c:if>
								</td>
								<td><fmt:formatDate value="${item.regDate }" pattern="yyyy-MM-dd"/></td>
								<td>
									${item.email1}<c:if test="${item.email1 == '' || item.email1 == null}">이메일미기재</c:if>-  
									${item.email2}<c:if test="${item.email2 == '' || item.email2 == null}">이메일미기재</c:if>-  
									${item.email3}<c:if test="${item.email3 == '' || item.email3 == null}">이메일미기재</c:if> 
								
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
				<span class="material-icons">
					login
				</span>
	</div>
</body>
</html>