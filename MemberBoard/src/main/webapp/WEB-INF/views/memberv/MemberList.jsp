<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script>

	function memberDelete(deleteId){
		console.log(deleteId);
 		location.href="memberdelete?mid="+deleteId;
	}
	
	function memberView(mid){
		console.log(mid);
		 $.ajax({
			type : "post",
			url : "memberview",
			data : {"mid" : mid},
			dataType : "json",
			success : function(result){
				
/* 				<c:choose>
				<c:when test="${empty board.bfilename}"><br><br></c:when>
				<c:otherwise><Koala.jpg"><br><br></c:otherwise>
			</c:choose> */
			
				var output = "<img src='${pageContext.request.contextPath}/resources/img/" + result.mfilename + "' width='200px';><br><br>";
				output += "<strong>ID</strong> : " + result.mid + "<br>";
				output += "<strong>PW</strong> : " + result.mpw + "<br>";
				output += "<strong>NAME</strong> : " + result.mname + "<br>";
				output += "<strong>BIRTH</strong> : " + result.mbirth + "<br>";
				output += "<strong>EMAIL</strong> : " + result.memail + "<br>";
				output += "<strong>ADDRESS</strong> : " + result.maddress + "<br>";
				output += "<strong>PHONE</strong> : " + result.mphone + "<br><br><br>";
				output += "<button onclick=\"memberDelete('"+result.mid+"')\">회원삭제</button>";
				
				console.log(mid+" : 2번째");
				$("#memberviewdiv").html(output);
				
			},
			error : function(){
				console.log("통신실패");
			}
		}); 
	}
</script>
</head>
<body>
	<h2>MemberList.jsp</h2>
	
	<table border="1">
		<c:forEach var="m" items="${mList}">
			<tr>
				<td><a href="#" onclick="memberView('${m.mid}')">${m.mid}</a></td>
			</tr>
		</c:forEach>
	</table>
	<br>
	
	<button onclick="location.href='gomain'">돌아가기</button>
	
	<br>
	<br>
	<div id="memberviewdiv"></div>
</body>
</html>