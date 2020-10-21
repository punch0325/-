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
 	$(document).ready(function(){
		$("#button").click(function(){
			var cwriter =$("#cwriter").val();
			var ccontents = $("#ccontents").val();
			var cbnumber = "${board.bnumber}";
		
			$.ajax({
				type :"post",
				url : "comment/commentwrite",
				data : {
					"cwriter" : cwriter,
					"ccontents" : ccontents,
					"cbnumber" : cbnumber},
				dataType : "json",
				success : function(result){
				
					var output = "<table border='1'>";
					output += "<tr><th>작성자</th>";
					output += "<th>내용</th></tr>";

					for(var i in result){
						output += "<tr>";
						output += "<td>"+result[i].cwriter+"</td>";
						output += "<td>"+result[i].ccontents+"</td>";
						output += "</tr>";
					}
					output +="</table>";
					$("#commentArea").html(output);
				
					$("#ccontents").val("");
			    
				},
				error : function(){
					console.log("댓글 등록 실패")
				}
			});
		});
	});
 	
 	
</script>
</head>
<body>
	<h2>BoardView.jsp</h2>
	
	<strong>작성자 </strong>
	: ${board.bwriter} <br>
	<strong>제목 </strong>
	: ${board.btitle} <br>
	<strong>내용 </strong>
	: ${board.bcontents} <br><br>
	
	<c:choose>
		<c:when test="${empty board.bfilename}"><br><br></c:when>
		<c:otherwise>
			<img src="${pageContext.request.contextPath}/resources/img/${board.bfilename}">
			<!-- <button onclick="location.href='file/filedownload'">다운로드</button> --><br><br>
		</c:otherwise>
	</c:choose>
	
	<button onclick="location.href='boardlist?page=${page}'">글 목록</button>
	<c:if test="${sessionScope.loginId eq board.bwriter}">
		<button onclick="location.href='boardupdate?bnumber=${board.bnumber}'">수정</button>
		<button onclick="location.href='boarddelete?bnumber=${board.bnumber}'">삭제</button>	
	</c:if>
	<c:if test="${sessionScope.loginId eq 'admin'}">
		<button onclick="location.href='boarddelete?bnumber=${board.bnumber}'">삭제</button>	
	</c:if>
	<br><br>
	
	<div id = "commentWrite">
		<input type="text" id="cwriter" value="${sessionScope.loginId}" readonly style="width:400px; height:20px;"> <br>
		<input type="text" id="ccontents" placeholder="댓글 내용을 입력해주세요" style="width:400px; height:20px;">
		<button id="button">입력</button>
	</div>
	<br>
	<div id="commentArea">
		<table border="1">
			<tr><th>작성자</th>
			 	<th>내용</th></tr>
			<c:forEach var="c" items="${cList}">
				<tr><td>${c.cwriter}</td>
					<td>${c.ccontents}</td></tr>
			</c:forEach>
		</table>
	</div>
	
</body>
</html>