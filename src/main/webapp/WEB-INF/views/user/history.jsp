<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script type="text/javascript" src="/resource/js/bootstrap.js"></script>
<title>User History</title>
</head>
<body>
	<div>
	  	<c:import url="/header.do"/>
	</div>
	&nbsp;&nbsp;
	<h2>회원조회</h2>
	<form method="post">
		<table>
			<tbody>
				<tr>
					<td>
						<input type="radio" name="simple"  checked/> 간단조회 &nbsp;&nbsp;&nbsp;
						<input type="radio" name="simple" /> 일간  &nbsp;&nbsp;&nbsp;
						<input type="radio" name="simple" /> 월간
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
										<option value="${i}" ${i}></option>
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
						<input type="text" size="5" value="${beginYear}">년
						<input type="text" size="5" value="${beginMonth}">월
						<input type="text" size="5" value="${beginDay}">일
						&nbsp; ~
						<input type="text" size="5" value="${endYear}">년
						<input type="text" size="5" value="${endMonth}">월
						<input type="text" size="5" value="${endnDay}">일
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	<div class="userList">
		<table class="table">
			<thead class='thead-dark' class="form-controll">
				<tr align ="center">
					<th>ID</th>
					<th>NAME</th>
					<th>WRITER</th>
					<th>REG_DATE</th>
					<th>READ_COUNT</th>
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
							<c:choose>
								<c:when test=""></c:when>
							</c:choose>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
</body>
</html>