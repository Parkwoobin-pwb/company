<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
  <script src="${contextPath}/resources/ckeditor/ckeditor.js"></script> 
  <script src="https://cdn.ckeditor.com/ckeditor5/29.1.0/classic/ckeditor.js"></script>
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.23/jquery-ui.min.js"></script>
  <script type="text/javascript" src="<c:url value="/resources/js/cal.js" />"></script>
  <script type="text/javascript" src="http://code.jquery.com/jquery-3.5.1.min.js"></script>
  <script src="resources/jQuery/jquery-3.4.1.min.js"></script>
<meta charset="UTF-8">
<title>bWrite</title>
<script>
	$(document).ready(function(e) {
		
		var idx= false;
		
		$('#checkUp').click(function() {
			
			var writer = document.getElementById("writer");
			if(writer.value==""){
				alert("작성자를 입력하세요");
				writer.focus();
				return false;
			}
			
			var subject = document.getElementById("subject");
			if(subject.value==""){
				alert("제목을 입력하세요");
				subject.focus();
				return false;
			}
			
			var email = document.getElementById("email");
			if( email.value==""){
				alert("이메일을 입력하세요");
				email.focus();
				return false;
			}else{
				a = email.value.indexOf("@");
				b = email.value.lastIndexOf(".");
				if(a < 1 || b-a < 2){
					alert("이메일형식으로 입력해주세요");
					return false;
				}
			}
				
			var password = document.getElementById("password");
			if(password.value == ""){
				alert("비밀번호를 입력하세요");
				password.focus();
				return false;
			}
			
			/* var content = CKEDITOR.instances.content.getData(); */
			var content = CKEDITOR.instances.content.getData();
			if(content == "" || content == null){
				alert("내용을 입력하세요");
				content.focus();
				return false;
			}
			
			if(idx==false){
				alert("작성자 중복체크를 해주세요.");
				return;
			}else{
				$('#BoardDTO').submit();
			}
		});
		
		
		$('#check').click(function() {
			var writer = document.getElementById("writer");
			if(writer.value== ""){
				alert("작성자를 입력하세요");
				writer.focus();
			}
			else{
				$.ajax({
					url : "${contextPath}/checkWriter",
					type: "POST",
		 			dateType : "JSON",
					data : {"writer": $('#writer').val()},
					success : function (data) {
						if(data == 0 ){
							idx = true;
							alert("사용가능한 작성자입니다. 사용하시겠습니까?");
							$('#writer').attr("readyonly", true);
							$("#idCheck").text("사용가능한 작성자.");
						}else{
							idx = false;
							alert("중복된작성자입니다.");
							$("#idCheck").text("사용불가능한 작성자.");
							
						}
						
					},
					error : function(data) {
						alert("서버에러");
					}
					
				});
			}
		});
	});
</script>
<script>
	window.onload = function(){
	    document.getElementById("address").addEventListener("click", function(){ //주소입력칸을 클릭하면
	        //카카오 지도 발생
	        new daum.Postcode({
	            oncomplete: function(data) { //선택시 입력값 세팅
	                document.getElementById("address").value = data.address; // 주소 넣기
	                document.querySelector("input[name=address_detail]").focus(); //상세입력 포커싱
	            }
	        }).open();
	    });
	}
</script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<!-- script>
	function validateCheck() {
		
				var writer = document.getElementById("writer");
				if(writer.value==""){
					alert("작성자를 입력하세요");
					writer.focus();
					return false;
				}
				
				var subject = document.getElementById("subject");
				if(subject.value==""){
					alert("제목을 입력하세요");
					subject.focus();
					return false;
				}
				
				var email = document.getElementById("email");
				if( email.value==""){
					alert("이메일을 입력하세요");
					email.focus();
					return false;
				}else{
					a = email.value.indexOf("@");
					b = email.value.lastIndexOf(".");
					if(a < 1 || b-a < 2){
						alert("이메일형식으로 입력해주세요");
						return false;
					}
				}
					
				var password = document.getElementById("password");
				if(password.value == ""){
					alert("비밀번호를 입력하세요");
					password.focus();
					return false;
				}
				
				var content = document.getElementById("content");
				if(content.value==""){
					alert("내용을 입력하세요");
					content.focus();
					return false;
				}
		
				
			
				
	} -->
	
<!-- </script>     -->
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
	                 <a class="nav-link" href="boardList">목록보기</a>
	               </li>
	          </ul>
	        </div>  
	   </nav>
	<div align="center" style="padding-top:110px">
		<form action="boardWrite" method="post"  name="BoardDTO" id="BoardDTO"><!-- onsubmit="return validateCheck()" -->
			<div align="center">
				 <h1 class="mt-4">게시글쓰기</h1>
				<br>
			</div>
			<table class="table" border="1" style="width:800px;">
				<colgroup>
					<col width="30%">
					<col width="70%">
				</colgroup>
				<tr>
					<td align="center">작성자</td>
					<td>
						<input type="text" name="writer" id="writer" size="80px">
						<input type="button" id="check" name="check"value="중복체크"><p id="idCheck"></p>
					</td>
				</tr>
				<tr>
					<td align="center">제목</td>
					<td><input type="text" name="subject" id="subject" size="80"></td>
				</tr>
				<tr>
					<td align="center">이메일</td>
					<td><input type="text" name="email" id="email" size="80"></td>
				</tr>
				<tr>
					<td align="center">패스워드</td>
					<td><input type="password" id="password" name="password" size="80"></td>
				</tr>
				<tr>
					<td align="center">주소</td>
					<td>
						<input type="text" id="address" name="address" size="80" placeholder="주소입력"><br>
						<input type="text" name="addressDetail" id="addressDetail" placeholder="상세주소"/>
					</td>

					
				</tr>
				<tr>
					<td align="center">글내용</td>
					<td>
						<textarea rows="50" cols="100" id="content" name="content" ></textarea>
						<script>
							var editor = CKEDITOR.replace('content',{
								language:'ko',
				              	height:'600px',
				               removePlugins: 'elementspath', 
				                  resize_enabled: false,
				                  cloudServices_tokenUrl:'',
				                  cloudServices_uploadUrl:''
							});
						</script>
					</td>
				</tr>
				 
				<tr>
					<td colspan="2" align="center">
						<input type="button" id="checkUp" value="등록하기">
						<input type="reset" value="초기화">
						<input type="button" value="목록보기" onclick="location.href='boardList'"> 
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>