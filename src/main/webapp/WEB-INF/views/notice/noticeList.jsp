<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function fn_searchList(idx){
		$("#pageIndex").val(idx);
		document.parameterDTO.submit();
	}
</script>
</head>
<body>
	<div>
	  			<c:import url="/header.do"/>
	</div>  
	    

	<div class="article">
					<div class="tabList">
						 <form:form name="parameterDTO" id="parameterDTO" method="post"
						    action="${pageContext.request.contextPath}/noticeList">
							<input type="hidden" name="pageIndex" id="pageIndex"
								value="${parameterDTO.pageIndex}" />
							<input type="hidden" id="admin_group_id"
								value="${parameterDTO.adminGroupId}" />
							<input type="hidden" name="nt_id" id="nt_id" />
							<fieldset>
								<legend>검색어 입력</legend>
								<div class="searchPanel">
									<span class="field"><input type="text"
										class="comText txtHelp" id="txt_search" name="search_text"
										value="${parameterDTO.search_text}" /><label for="txt_search">검색어를
											입력해주세요.</label></span>
									<button type="button" class="nbtn"
										onclick="javascript:fn_searchList('1');return false;">
										<em class="cbtn btGray"><em>조회</em></em>
									</button>
								</div>
								<!-- searchPanel -->
							</fieldset>
						</form:form>  
					</div>
					<!-- tabList -->
					<div class="listPanel">
						<table class="tbList" summary="공지사항 목록">
							<caption>공지사항 목록</caption>
							<colgroup>
								<col width="5%" />
								<col width="5%" />
								<col width="50%" />
								<col width="10%" />
								<col width="10%" />
								<col width="10%" />
								<col width="10%" />
							</colgroup>
							<thead>
								<tr>
									<th>선택</th>
									<th>번호</th>
									<th>제목</th>
									<th>작성자</th>
									<th>미리보기</th>
									<th>상세보기</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${resultList}" var="resultList"
									varStatus="status">
									<tr>
										<td><input type="checkbox" name="checkDelete"
											value="${resultList.ntId}" class="chkDelete" /></td>
										<td>${totalCnt-((parameterDTO.pageIndex-1)*10+status.count)+1}</td>
										<td>${resultList.ntTitle}</td>
										<td>${resultList.adminId}</td>
										<td><a
											href="${pageContext.request.contextPath}/noticeDetail.view?id=${resultList.ntId}"
											class="cbtnB btGray btPreview" target="_blank"><em>미리보기</em></a></td>
										<td><a href="#" class="cbtnB btGray" target="_blank"
											onclick="javascript:fn_detail('${resultList.ntId}');return false;"><em>상세보기</em></a></td>
									</tr>
								</c:forEach>
								<c:if test="${fn:length(resultList) == 0}">
									<tr>
										<td colspan="7">조회된 결과가 없습니다.</td>
									</tr>
								</c:if>
							</tbody>
						</table>

						<div class="pageIndex">
							<div class="pages">${pagingList}</div>
							<div class="moreBtns">
								<a href="#" onclick="javascript:fn_delete();return false;"><img
									src="${pageContext.request.contextPath}/resources/images/button/select_delete.png"
									alt="선택삭제" /></a> <a href="#"
									onclick="javascript:fn_resi();return false;"><img
									src="${pageContext.request.contextPath}/resources/images/button/menu_okbt.png"
									alt="등록" /></a>
							</div>
						</div>
						<!-- pageIndex -->
					</div>
					<!-- listPanel -->
				</div>
				<!-- article -->
			</div>
			<!-- section -->

</body>
</html>