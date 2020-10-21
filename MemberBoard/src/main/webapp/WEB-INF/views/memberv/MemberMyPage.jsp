<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
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

	function memberUpdate(myId){
		console.log(myId);
		
		$.ajax({
			type : "post",
			url : "memberupdate",
			data : {"mid" : myId},
			dataType : "json", //최종적으로 받아오는 데이터의 타입을 써줌. 여기서는 값을 여러개 받아올 것이기 때문에 그럴 경우엔 json을 써주면 됨
			success : function(result){
				console.log(result);
				console.log(result.mid);
				
				var output = "<form action='memberupdateprocess' method='post' enctype='multipart/form-data'>";
				output += "<table>";
				output += "<tr><th>아이디</th><td><input type='text' name ='mid' value='"+result.mid+"' readonly></td></tr>";
				output += "<tr><th>비밀번호</th><td><input type='password' name ='mpw' value='"+result.mpw+"'></td></tr>";
				output += "<tr><th>이름</th><td><input type='text' name ='mname' value='"+result.mname+"'></td></tr>";
				output += "<tr><th>생년월일</th><td><input type='date' name ='mbirth' value='"+result.mbirth+"'></td></tr>";
				output += "<tr><th>이메일</th><td><input type='text' name ='memail' value='"+result.memail+"'></td></tr>";
				output += "<tr><th>주소</th><td><input type='text' id='sample6_postcode' placeholder='우편번호'>";
				output += "<input type='button' onclick='sample6_execDaumPostcode()' value='우편번호 찾기'><br>";
				output += "<input type='text' name ='maddress' id='sample6_address' placeholder='주소'><br>";
				output += "<input type='text' id='sample6_detailAddress' placeholder='상세주소'>";
				output += "<input type='text' id='sample6_extraAddress' placeholder='참고항목'></td></tr>";
				output += "<tr><th>전화번호</th><td><input type='text' name ='mphone' value='"+result.mphone+"'></td></tr>";
				output += "<tr><th>이메일</th><td><input type='file' name ='mfile'></td></tr>";
				output += "</table><br>";
				output += "<input type='submit' value='수정완료'>";
				output += "</form>";
				
				$("#update").html(output);
			},
			error : function(){
				console.log("통신실패");
			}
		});
	}
</script>
</head>
<body>
	<h2>${sessionScope.loginId}님의 마이페이지입니다.</h2>
	
	<h3>내가 쓴 글 총 ${bCount}개 ▼ </h3>

	
	<table border="1">
		<tr>
			<td>글번호</td>
			<td>작성자</td>
			<td>글제목</td>
			<td>작성일자</td>
			<td>조회수</td>
		</tr>
		<c:forEach var="board" items="${bList}" >
			<tr>
				<td>${board.bnumber}</td>
				<td>${board.bwriter}</td>
				<td><a
					href="boardview?bnumber=${board.bnumber}&page=${paging.page}">${board.btitle}</a></td>
				<td>${board.bdate}</td>
				<td>${board.bhits}</td>
			</tr>
		</c:forEach>
	</table><br><br>
	
	<a href="#" onclick="memberUpdate('${sessionScope.loginId}')">나의 정보 수정</a><br><br>
	<div id="update"></div>
</body>
</html>