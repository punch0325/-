<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>Main.jsp</h2>
	<button onclick="location.href='memberjoinform'">회원가입</button>
	<button onclick="location.href='memberloginform'">로그인</button>
	<button onclick="location.href='memberlogout'">로그아웃</button> <br><br>
	
	<a href="kakaojoin"><img src="${pageContext.request.contextPath}/resources/img/kakaolink_btn_small.png"></a>
	<a href="naverjoin"><img src="${pageContext.request.contextPath}/resources/img/네아로아이콘형.PNG" width="35px"></a>
	<br><br>
	
	<c:if test="${sessionScope.loginId eq 'admin'}">
		<a href="memberlist">관리자 회원 목록</a> <br><br>
	</c:if>
	
	<button onclick="location.href='boardlist'">글 목록</button>
</body>
</html>