<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<title>Join our</title>
<link href="${contextPath }/resources/css/myStyle.css" rel="stylesheet" />
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.23/jquery-ui.min.js"></script>
  <script type="text/javascript" src="<c:url value="/resources/js/cal.js" />"></script>
  <script type="text/javascript" src="http://code.jquery.com/jquery-3.5.1.min.js"></script>
  
  <script src="resources/jQuery/jquery-3.4.1.min.js"></script>
  <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<style>
	td:first-child {
		text-align: center;
		font-weight: bold;
	}
</style>
</head>
<script>
	window.onload = function(){
	    document.getElementById("address").addEventListener("click", function(){ //주소입력칸을 클릭하면
	        //카카오 지도 발생
	        new daum.Postcode({
	            oncomplete: function(data) { //선택시 입력값 세팅
	                document.getElementById("roadAddress").value = data.address; // 주소 넣기
	                document.querySelector("input[name=namujiAddress]").focus(); //상세입력 포커싱
	            }
	        }).open();
	    });
	}
</script>

<script>
	$(document).ready(function(e) {
		
		var idx= false;
		
		$('#checkUp').click(function() {
			
			var memberId = document.getElementById("memberId");
			if(memberId.value==""){
				alert("아이디를 입력하세요");
				memberId.focus();
				return ;
			}
			
			
			var memberName = document.getElementById("memberName");
			if(memberName.value==""){
				alert("이름을 입력하세요");
				memberName.focus();
				return ;
			}
			
			
		
				
			var memberPw = document.getElementById("memberPw");
			if(memberPw.value == ""){
				alert("비밀번호를 입력하세요");
				memberPw.focus();
				return ;
			}
			
			var memberPwCheck = document.getElementById("password");
			if(memberPw.value==""){
				alert("비밀번호를 확인하세요");
				memberPw.focus();
				return ;
			}
			
			if(memberPw.value !== memberPwCheck.value){
				alert("비밀번호가 일치 하지 않습니다.");
				memberPwCheck.focus();
				return ;
			}
		
			
			if(idx==false){
				alert("작성자 중복체크를 해주세요.");
				return;
			}else{
				$('#userDTO').submit();
			}
		});
		
		
		
		$('#check').click(function() {
			var id = document.getElementById("memberId");
			console.log(id.value+"밸류값 확인");
			
			//아이디가 영문 소문자만 입력받도록 검사하는곳
			for(var i =0; i < id.value.length; i++){
				var ch = id.value.charAt(i);
				if((ch < 'a' || ch > 'z')){
						alert("아이디는 영문 소문자로만 입력 가능합니다.");
						id.focus();
						return;
				
					}
			}
			
			if(id.value== ""){
				alert("작성자를 입력하세요");
				id.focus();
				return;
				
			}
			else if(id.value.length  < 4 || id.value.length > 12 ){
				alert("아이디는 4자이상 12이내로 입력해주세요");
				id.focus();
				return;
			}
			else{
				$.ajax({
					url : "${contextPath}/checkId",
					type: "POST",
		 			dateType : "JSON",
					data : {"id": $('#memberId').val()},
					success : function (data) {
						if(data == 0 ){
							idx = true;
							alert("사용가능한 작성자입니다. 사용하시겠습니까?");
							console.log(id);
						}else{
							alert("중복된 ID가 존재합니다. 다른ID로 입력주세요.");
							idx = false;
							id.focus();
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
<body>
	<form action="join" method="post" name="userDTO" id="userDTO" >
	<h2>회원가입</h2>
	<br>
	<table class="table table-bordered table-hover">
		<colgroup>
			<col width="20%" >
			<col width="80%">
		</colgroup>
		<tr>
			<td>
				<label>아이디</label>
			</td>
			<td>
	            <input type="text" class="form-control" style="display:inline; width:300px;" 
	            	name="memberId"  id="memberId"  placeholder="아이디를 입력하세요." />
	        	&emsp;<input type="button" id="check" class="btn btn-primary btn-sm" value="중복확인" />
	        </td>
	    </tr>
        <tr>
	        <td>
	        	 <label class="small mb-1" for="memberPw">비밀번호</label>
	        </td>
	        <td>
	        	<input class="form-control" id="memberPw" name="memberPw" style="display:inline; width:300px;" type="password" placeholder="비밀번호를 입력하세요." />
	        </td>
        </tr>
        <tr>
	        <td>
	        	 <label class="small mb-1">비밀번호 확인</label>
	        </td>
	        <td>
	        	<input class="form-control" type="password" id="password" style="display:inline; width:300px;" placeholder="비밀번호를 입력하세요." />
	        </td>
        </tr>         
        <tr>
	        <td>
	        	<label class="small mb-1" for="memberName">이름</label>
	        </td>
	        <td>
	        	<input type="text" class="form-control" name="memberName"  id="memberName" maxlength="15" style="display:inline; width:300px;" placeholder="이름을 입력하세요." />
	        </td>
        </tr>                
	    <tr>
	        <td>
	        	<label for="g1">성별</label>
	        </td>
	        <td>
	        	<input class="custom-control-input" type="radio" id="g1" name="memberGender" value="101" checked />
				<label class="custom-control-label" for="g1">남성</label>&emsp;&emsp;&emsp;
				<input class="custom-control-input" type="radio" id="g2" name="memberGender" value="102" />
				<label class="custom-control-label" for="g2">여성</label>
	        </td>
        </tr>                              
        <tr>
	        <td>
	        	<label class="small mb-1" for="memberBirthY">생년월일</label>
	        </td>
	        <td>
                <select class="form-control" id="memberBirthY" name="memberBirthY" style="display:inline; width:70px; padding:0" >
				<c:forEach var="year" begin="1" end="100">
					<c:choose>
						<c:when test="${year==80}">
							<option value="${1921+year}" selected>${ 1921+year}
							</option>
						</c:when>
						<c:otherwise>
							<option value="${1921+year}">${ 1921+year}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				</select> 년 
				<select class="form-control" name="memberBirthM" style="display:inline; width:50px; padding:0">
				  <c:forEach var="month" begin="1" end="12">
				      <c:choose>
				        <c:when test="${month==5}">
					   <option value="${month}" selected>${month }</option>
					</c:when>
					<c:otherwise>
					  <option value="${month}">${month}</option>
					</c:otherwise>
					</c:choose>
				  	</c:forEach>
				</select> 월  
				<select class="form-control" name="memberBirthD" style="display:inline; width:50px; padding:0">
				<c:forEach var="day" begin="1" end="31">
				      <c:choose>
				        <c:when test="${day==10}">
					   <option value="${day}" selected>${day}</option>
					</c:when>
					<c:otherwise>
					  <option value="${day}">${day}</option>
					</c:otherwise>
					</c:choose>
				  	</c:forEach>
				</select> 일 &nbsp;
	        </td>
        </tr>                        
        <tr>
	        <td>
	        	<label class="small mb-1" for="tel1">집 전화번호</label>
	        </td>
	        <td>
	        	<select class="form-control" id="tel1" name="tel1" style="display:inline; width:70px; padding:0">
					<option>없음</option>
					<option value="02" selected>02</option>
					<option value="031">031</option>
					<option value="032">032</option>
					<option value="033">033</option>
					<option value="041">041</option>
					<option value="042">042</option>
					<option value="043">043</option>
					<option value="044">044</option>
					<option value="051">051</option>
					<option value="052">052</option>
					<option value="053">053</option>
					<option value="054">054</option>
					<option value="055">055</option>
					<option value="061">061</option>
					<option value="062">062</option>
					<option value="063">063</option>
					<option value="064">064</option>													
				 </select> - 
		 		<input class="form-control" size="10px" type="text" name="tel2" style="display:inline; width:100px; padding:0" > - 
		 		<input class="form-control" size="10px" type="text" name="tel3" style="display:inline; width:100px; padding:0">
	        </td>
        </tr>                         
        <tr>
	        <td>
	        	<label class="small mb-1" for="hp1">핸드폰 번호</label>
	        </td>
	        <td>
	        	<select  class="form-control" id="hp1" name="hp1" style="display:inline; width:70px; padding:0">
					<option>없음</option>
					<option selected value="010">010</option>
					<option value="011">011</option>
					<option value="016">016</option>
					<option value="017">017</option>
					<option value="018">018</option>
					<option value="019">019</option>
				</select> - 
				<input class="form-control"  size="10px"  type="text" name="hp2" style="display:inline; width:100px; padding:0"> - 
				<input class="form-control"  size="10px"  type="text"name="hp3" style="display:inline; width:100px; padding:0"><br><br>
				<input class="custom-control-input" id="smsstsYn" type="checkbox" name="smsstsYn"  value="Y" checked/>
                <label for="smsstsYn" >BMS에서 발송하는 SMS 소식을 수신합니다.</label>
	        </td>
        </tr>                         
        <tr>
	        <td>
	        	<label class="small mb-1" for="email1">이메일</label>
	        </td>
	        <td>
	        	<input class="form-control"  size="10px"  type="text" id="email1" name="email1" style="display:inline; width:100px; padding:0"> @ 
					<input class="form-control"  size="10px"  type="text" id="email2" name="email2" style="display:inline; width:100px; padding:0">
					<select class="form-control" id="select_email" name="email
					3" style="display:inline; width:100px; padding:0">
						 <option value="none" selected>직접입력</option>
						 <option value="gmail.com">gmail.com</option>
						 <option value="naver.com">naver.com</option>
						 <option value="daum.net">daum.net</option>
						 <option value="nate.com">nate.com</option>
					</select><br><br>
                    <input class="custom-control-input" id="emailstsYn" type="checkbox" name="emailstsYn"  value="Y" checked/>
                    <label for="emailstsYn">BMS에서 발송하는 E-mail을 수신합니다.</label>
	        </td>
        </tr>                              
        <tr>
	        <td>
	        	<label class="small mb-1" for="zipcode">주소</label>
	        </td>
	        <td>
	        	<input class="form-control"  size="70px"  type="text" placeholder="우편번호 입력" id="zipcode" name="zipcode" style="display:inline; width:150px; padding:0">
                <input type="button" class="btn btn-outline-primary btn-sm" id="address" value="검색">
                <div></div><br>
                도로명 주소 : <input type="text" class="form-control" id="roadAddress"  name="roadAddress" style=" width:300px;" > <br>
				지번 주소 : <input type="text" class="form-control" id="jibunAddress" name="jibunAddress" style=" width:300px;"> <br>
				나머지 주소: <input type="text" class="form-control" name="namujiAddress" style=" width:300px;"/>
	        </td>
        </tr>                              
        <tr>
	        <td colspan="2">
	        	<input type="button" id="checkUp" value="회원가입하기" class="btn btn-primary btn-block" >
	        </td>
	    </tr>
	    <tr>
	        <td colspan="2" align="center">
	        	이미 회원가입이 되어있다면 ? <a href="${contextPath}/login"><strong>로그인하기</strong></a>
	        </td>
        </tr>                            
     </table>
     </form>
</body>
</html>