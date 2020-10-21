<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script>
	function idOverlap(){
		var id = document.getElementById("mid").value;
		
		$.ajax({
			type : "post",
			url : "idoverlap",
			data : {"mid" : id},
			dataType : "text",
			success : function(result){
				if(result=="OK"){
					alert("사용 가능한 아이디입니다.");
				}else {
					alert("이미 사용중인 아이디입니다.");
				}
			},
			error : function(){
				alert("ajax실패");
			}
		}); 
	}
	
	
	function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                var addr = ''; 
                var extraAddr = '';
                if (data.userSelectedType === 'R') {
                    addr = data.roadAddress;
                } else { 
                    addr = data.jibunAddress;
                }
                if(data.userSelectedType === 'R'){
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }                
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    document.getElementById("sample6_extraAddress").value = extraAddr;
                
                } else {
                    document.getElementById("sample6_extraAddress").value = '';
                }
                document.getElementById('sample6_postcode').value = data.zonecode;
                document.getElementById("sample6_address").value = addr;
                document.getElementById("sample6_detailAddress").focus();
            }
        }).open();
    }
	
	function passwordCh(){
		var exp = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$/;
	    var pwd = document.getElementById("mpw").value;
	    var pwdch = document.getElementById("pwCh");
	    if(pwd.match(exp)){
	       pwdch.style.color = "green";
	       pwdch.style.fontSize = "12px";
	       pwdch.innerHTML = "비밀번호 형식이 맞습니다.";
	    }else {
	       pwdch.style.color = "red";
	       pwdch.style.fontSize = "12px";
	       pwdch.innerHTML = "비밀번호 형식이 맞지 않습니다.";
	    }
	}
	
	function pwdCh(){
		var mpw = document.getElementById("mpw").value;
		var pwd = document.getElementById("pwd").value;
		var pwdch = document.getElementById("pwdCh");
		
		if(mpw==pwd){
			pwdch.style.color = "green";
		    pwdch.style.fontSize = "12px";
		    pwdch.innerHTML = "비밀번호가 일치합니다.";
		}else {
		    pwdch.style.color = "red";
		    pwdch.style.fontSize = "12px";
	        pwdch.innerHTML = "비밀번호가 일치하지 않습니다.";			
		}
	}
	
	function phoneCh(){
		var exp = /^\d{3}-\d{4}-\d{4}$/;
	      var phone = document.getElementById("mphone").value;
	      if(phone.match(exp)){
	        document.getElementById("phoneCh").style.color = "green";
	        document.getElementById("phoneCh").style.fontSize = "12px";
	        document.getElementById("phoneCh").innerHTML = "입력 되었습니다.";
	      }else {
	        document.getElementById("phoneCh").style.color = "red";
	        document.getElementById("phoneCh").style.fontSize = "12px";
	        document.getElementById("phoneCh").innerHTML = "형식이 틀립니다.";
	      } 
	}
	
/* 	function disappear1(){
		document.getElementById("phoneCh").innerHTML="";
	}
	
	function disappear2(){
		document.getElementById("pwCh").innerHTML="";
	}
	
	function disappear3(){
		document.getElementById("pwdCh").innerHTML="";
	} */
	
	function memberJoin(){
		joinform.submit();
	}
</script>
</head>
<body>
	<h2>MemberJoin.jsp</h2>
	<form action="memberjoin" method="post" name="joinform" enctype="multipart/form-data">
		<table>
			<tr>
				<c:choose>
	    			<c:when test="${kakaoId ne null}">
	    				<th>아이디</th>
						<td><input type="text" name ="mid" id="mid" placeholder="아이디를 입력하세요">
				    		<button type="button" onclick="idOverlap()">아이디 중복확인</button>
	    				 	<input type ="hidden" name="kakaoId" value="${kakaoId}"></td>
	    			</c:when>
	    			<c:when test="${naverId ne null}">
	    				<th>아이디</th>
						<td><input type="text" name ="mid" id="mid" placeholder="아이디를 입력하세요">
				    		<button type="button" onclick="idOverlap()">아이디 중복확인</button>
	    				 	<input type ="hidden" name="naverId" value="${naverId}"></td>
	    			</c:when>
	    			<c:otherwise>
						<th>아이디</th>
						<td><input type="text" name ="mid" id="mid" placeholder="아이디를 입력하세요">
				    		<button type="button" onclick="idOverlap()">아이디 중복확인</button></td>
	    			</c:otherwise>
	    		</c:choose>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td><input type="text" name ="mpw" id="mpw" placeholder="비밀번호를 입력하세요" 
				     onkeyup="passwordCh()" onblur="disappear2()">
				    <span id ="pwCh"></span></td>
			</tr>
			<tr>
				<th>비밀번호 확인</th>
				<td><input type="text" id="pwd" placeholder="비밀번호를 확인하세요" 
				    onkeyup="pwdCh()" onblur="disappear3()">
				    <span id ="pwdCh"></span></td>
			</tr>
			<tr>
				<th>이름</th>
				<td><input type="text" name ="mname" id="mname" placeholder="이름을 입력하세요"></td>
			</tr>
			<tr>
				<th>생년월일</th>
				<td><input type="date" name ="mbirth" id="mbirth"></td>
			</tr>
			<tr>
				<th>이메일</th>
				<td><input type="text" name ="memail" id="memail" placeholder="이메일을 입력하세요"></td>
			</tr>
			<tr>
				<th>주소</th>
				<td><input type="text" id="sample6_postcode" placeholder="우편번호">
                    <input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br>
                    <input type="text" name ="maddress" id="sample6_address" placeholder="주소"><br>
                    <input type="text" id="sample6_detailAddress" placeholder="상세주소">
                    <input type="text" id="sample6_extraAddress" placeholder="참고항목"></td>
			</tr>
			<tr>
				<th>전화번호</th>
				<td><input type="text" name ="mphone" id="mphone" placeholder="'-'를 포함하여 입력하여주세요" 
				     onkeyup="phoneCh()" onblur="disappear1()">
				    <span id ="phoneCh"></span></td>
			</tr>
			<tr>
				<th>프로필사진</th>
				<td><input type="file" name ="mfile" id="mfile"></td>
			</tr>
		</table>
	</form>
	<button onclick="memberJoin()">회원가입</button>
</body>
</html>