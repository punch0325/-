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

	function boardArray(){
		$.ajax({
			type : "get",
			url : "boardarray",
			dataType : "json",
			success : function(result){
				var output = "<table border='1'>";
				output += "<tr><th>글번호</th>";
				output += "<th>작성자</th>";
				output += "<th>글제목</th>";
				output += "<th>작성일자</th>";
				output += "<th>조회수</th></tr>";
				for(var i in result){
					output += "<tr>";
					output += "<td>"+result[i].bnumber+"</td>";
					output += "<td><a href='#' onclick=\"memberView('"+result[i].bwriter+"')\">"+result[i].bwriter+"</a></td>";
					output += "<td><a href='boardview?bnumber="+result[i].bnumber+"'>"+result[i].btitle+"</a></td>";
					output += "<td>"+result[i].bdate+"</td>";
					output += "<td>"+result[i].bhits+"</td>";
					output += "</tr>";
				}
				output +="</table>";
				$("#array").html(output);
			},
			error : function(){
				alert("ajax실패");
			}
		});
	}
	
	function memberView(mid){
		 $.ajax({
			type : "post",
			url : "memberview",
			data : {"mid" : mid},
			dataType : "json",
			success : function(result){
				
			
				var output = "<img src='${pageContext.request.contextPath}/resources/img/" + result.mfilename + "' width='200px';><br><br>";
				output += "<strong>ID</strong> : " + result.mid + "<br>";
				output += "<strong>PW</strong> : " + result.mpw + "<br>";
				output += "<strong>NAME</strong> : " + result.mname + "<br>";
				output += "<strong>BIRTH</strong> : " + result.mbirth + "<br>";
				output += "<strong>EMAIL</strong> : " + result.memail + "<br>";
				output += "<strong>ADDRESS</strong> : " + result.maddress + "<br>";
				output += "<strong>PHONE</strong> : " + result.mphone + "<br><br><br>";
				
				$("#memberviewdiv").html(output);
				
			},
			error : function(){
				console.log("통신실패");
			}
		}); 
	}
	
	function searchFn(){
		searchform.submit();
	}
</script>
</head>
<body>
	<h2>BoardList.jsp</h2>
	
	<c:if test="${!empty sessionScope.loginId}">
		<a href="membermypage">마이페이지</a><br><br>
	</c:if>
	
	<form action="boardsearch" method="get" name="searchform">
		<select id="search" name="search" style="height:25px;">
			<option value="">선택안함</option>
			<option value="swriter">작성자</option>
			<option value="stitle">제목</option>	
		</select>
		<input type="text" id="keyword" name="keyword" 
		       style="height:20px; margin-right:-5px;">
		<input type="button" onclick="searchFn()" value="검색">
	</form>
	
	<div id="array">
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
				<td><a href="#" onclick="memberView('${board.bwriter}')">
					${board.bwriter}</a></td>
				<td><a href="boardview?bnumber=${board.bnumber}&page=${paging.page}">
					${board.btitle}</a></td>
				<td>${board.bdate}</td>
				<td>${board.bhits}</td>
			</tr>
		</c:forEach>
	</table>
    
	<!-- 페이징 처리 -->
	<c:if test="${paging.page<=1}">
	[이전]&nbsp;
	</c:if>
	<c:if test="${paging.page>1}">
		<a href="boardlist?page=${paging.page-1}">[이전]</a>&nbsp;
	</c:if>
	<c:forEach begin="${paging.startPage}" end="${paging.endPage}" var="i" step="1">
		<c:choose>
			<c:when test="${i eq paging.page}">
			${i}
			</c:when>
			<c:otherwise>
				<a href="boardlist?page=${i}">${i}</a>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<c:if test="${paging.page>=paging.maxPage}">
		[다음]
	</c:if>
	<c:if test="${paging.page<paging.maxPage}">
		<a href="boardlist?page=${paging.page+1}">[다음]</a>
	</c:if>
	<a href="#" onclick="boardArray()" style="padding-left:100px;">인기순 보기</a>
	</div>
	
	<br><br>
	<button onclick="location.href='boardwriteform'">글 쓰기</button>
	<button onclick="location.href='gomain'">돌아가기</button>
	
	<br><br>
	<div id="memberviewdiv"></div>

</body>
</html>