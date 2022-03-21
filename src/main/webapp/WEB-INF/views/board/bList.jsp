<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>BoardList</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/js/all.min.js" crossorigin="anonymous"></script>
<link href="${contextPath}/resources/css/styles.css" rel="stylesheet" />
<script src="${contextPath}/resources/js/jquery-3.5.1.min.js"></script>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
  
<script>
	$().ready(function(){
		
		$("#onePageViewCount").change(function(){
			
			var onePageViewCount = $("#onePageViewCount").val();
			var searchKeyword = $("#searchKeyword").val();
			var searchWord = $("#searchWord").val();
			var url = "${contextPath}/boardList?";
				url	+= "onePageViewCount="+onePageViewCount;
				url	+= "&searchKeyword="+searchKeyword;
				url	+= "&searchWord="+searchWord;
			location.href = url;
			
		}) ;
		
		$("#getSearchBoard").click(function(){
			var onePageViewCount = $("#onePageViewCount").val();
			var searchKeyword = $("#searchKeyword").val();
			var searchWord = $("#searchWord").val();
			var url = "${contextPath}/boardList?";
				url	+= "onePageViewCount="+onePageViewCount;
				url	+= "&searchKeyword="+searchKeyword;
				url	+= "&searchWord="+searchWord;
			location.href = url;
		});
			
		
	});
	
</script>
</head>
	<body>
	   <nav class="navbar navbar-expand-md bg-dark navbar-dark">
	        <a class="navbar-brand" href="boardList">Woobin Board</a>
	        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
	          <span class="navbar-toggler-icon"></span>
	        </button>
	        <div class="collapse navbar-collapse" id="collapsibleNavbar">
	          <ul class="navbar-nav">
	               <li class="nav-item">
	                 <a class="nav-link" href="boardWrite">게시글쓰기</a>
	               </li>
	               <li class="nav-item">
	                 <a class="nav-link" href="join">회원가입</a>
	               </li>  
	               <li class="nav-item">
	                 <a class="nav-link" href="login">로그인</a>
	               </li>  
	               <li class="nav-item">
	                 <a class="nav-link" href="userHistory">유저조회</a>
	               </li>  
	          </ul>
	        </div>  
	   </nav> 
	<br>
        <div id="layoutSidenav_content">
                <div class="container-fluid">
                    <h1 class="mt-4">MVC2_Model</h1>
                    <ol class="breadcrumb mb-4">
                        <li class="breadcrumb-item"><a href="${contextPath }/boardList">Dashboard</a></li>
                        <li class="breadcrumb-item active">advance</li>
                    </ol>
                    <div class="card mb-4">
                        <div class="card-body">
                            1) 부트스트랩 적용<br>
                            2) 검색기능 적용<br>
                            3) 페이징기능 적용<br>
                            4) 데이터더미 만들기 > <a href="${contextPath}/makeDummyData">테스트용 더미 만들기</a> <br>
                            5) 계층형 댓글 적용 <br>
                            6) 유저조회 기능 적용 <br> 
                            7) 회원가입 기능 적용 <br>
                        </div>
                    </div>
                            <i class="fas fa-table mr-1"></i>
                            Total <span style="color: red; font-weight: bold">${totalBoardCount}</span> entries       
                                	<div class="row">
                                		<div class="col-sm-12 col-md-6">
                                			<div class="dataTables_length" id="dataTable_length">
                                				<label>Show 
                                 				<select id="onePageViewCount" name="dataTable_length" aria-controls="dataTable" class="custom-select custom-select-sm form-control form-control-sm">
                                 					<option <c:if test="${onePageViewCount eq 5}"> selected</c:if> value="5">5</option>
													<option <c:if test="${onePageViewCount eq 7}"> selected</c:if> value="7">7</option>
													<option <c:if test="${onePageViewCount eq 10}"> selected</c:if> value="10">10</option>
                                 				</select> entries
                                 				</label>
			                            		<p><input class="btn btn-outline-info btn-sm" type="button" value="excelPort" onclick="location.href='${contextPath}/excelPort'"></p> 
                                 			</div>		                               
                              			</div>
                              		</div>
                              		<table class="table" style="" border="1">
                              			<thead class='thead-dark' class="form-controll">
											<tr align ="center">
												<th>ORDER</th>
												<th>SUBJECT</th>
												<th>WRITER</th>
												<th>REG_DATE</th>
												<th>READ_COUNT</th>
												
											</tr>
										</thead>
                              		    <c:set var="order" value="${totalBoardCount - (currentPageNumber-1) * onePageViewCount}"/>
										<c:forEach var="bdto" items="${boardList }">
											<tr>
												<td><fmt:parseNumber integerOnly="true" value="${order}"/></td>	<c:set var="order" value="${order - 1}"/>
												<td>
													 <c:if test="${bdto.reStep > 1}">
														<c:forEach var="j" begin="0" end="${(bdto.reLevel-1 ) * 5 }">
															&nbsp;
														</c:forEach>
													 	»
													 </c:if>
													<a href="${contextPath }/boardInfo?num=${bdto.num}"> ${bdto.subject}</a>
												</td>
												<td><a href="boardInfo?num=${bdto.num}">${bdto.writer }</a></td>
												<td><fmt:formatDate value="${bdto.regDate }" pattern="yyyy-MM-dd"/></td>
												<td>${bdto.readCount }</td>
												
											</tr>
										</c:forEach>
										<tr>
												<td colspan="5" align="center">	
													<select id="searchKeyword" class="form-control" style="width: 150px; display: inline;">
														<option <c:if test="${searchKeyword eq 'total'}"> selected</c:if> value="total">total</option>
														<option <c:if test="${searchKeyword eq 'writer'}"> selected</c:if> value="writer">writer</option>
														<option <c:if test="${searchKeyword eq 'subject'}"> selected</c:if> value="subject">subject</option>
													</select>
			                                 		<input type="text" style="width: 300px; display: inline;" class="form-control" id="searchWord" name="searchWord" value="${searchWord}" >
													<input type="button" class="btn btn-outline-info btn-sm" value="Search" id="getSearchBoard">
												</td>
											</tr>
	                                    </tbody>										
	                                </table>
                                	<div style="display: table; margin-left: auto; margin-right: auto">
                                 	<div class="dataTables_paginate paging_simple_numbers" id="dataTable_paginate">
                                 		<c:if test="${totalBoardCount gt 0 }">
                                  		<ul class="pagination">
                                  			<c:if test="${startPage gt 10 }">
                                   			<li class="paginate_button page-item previous" id="dataTable_previous">
                                   				<a href="${contextPath }/boardList?currentPageNumber=${startPage - 10}&onePageViewCount=${onePageViewCount}&searchKeyword=${searchKeyword}&searchWord=${searchWord}" aria-controls="dataTable" data-dt-idx="0" tabindex="0" class="page-link">Previous</a>
                                   			</li>
                                  			</c:if>
                                  			<c:forEach var="i" begin="${startPage}" end="${endPage }" >
                                   			<li class="paginate_button page-item">
                                   				<a href="${contextPath }/boardList?currentPageNumber=${i}&onePageViewCount=${onePageViewCount}&searchKeyword=${searchKeyword}&searchWord=${searchWord}" aria-controls="dataTable" data-dt-idx="1" tabindex="0" class="page-link">${i}</a>
                                   			</li>
                                   		</c:forEach>
                                  			<c:if test="${endPage le totalBoardCount && endPage ge 10}"> 
                                   			<li class="paginate_button page-item next" id="dataTable_next">
                                   				<a href="${contextPath }/boardList?currentPageNumber=${startPage + 10}&onePageViewCount=${onePageViewCount}&searchKeyword=${searchKeyword}&searchWord=${searchWord}" aria-controls="dataTable" data-dt-idx="7" tabindex="0" class="page-link">Next</a>
                                   			</li>
                                  			</c:if>
                                  		</ul>
                                  	</c:if>
                                 	</div>
                                 </div>
                            </div>
                        </div>
			            <footer class="py-4 bg-light mt-auto">
			                <div class="container-fluid">
			                    <div class="d-flex align-items-center justify-content-between small">
			                        <div class="text-muted">Copyright &copy; Your Website 2021</div>
			                        <div>
			                            <a href="#">Privacy Policy</a>
			                            &middot;
			                            <a href="#">Terms &amp; Conditions</a>
			                        </div>
			                    </div>
			                </div>
			            </footer>
</body>
</html>