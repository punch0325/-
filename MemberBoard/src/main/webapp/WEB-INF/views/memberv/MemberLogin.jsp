<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>MemberLogin.jsp</h2>
	<form action="memberlogin" method="post">
		<input type="text" name="mid" id="mid"><br><br>
		<input type="text" name="mpw" id="mpw"><br><br>
		<input type="submit" value="로그인">
	</form>
	<br>
	<a href="kakaologin"><img src="${pageContext.request.contextPath}/resources/img/kakao_login_small.png"></a>
	<a href="naverlogin"><img src="${pageContext.request.contextPath}/resources/img/네아로축약형.PNG" width="85px"></a>
</body>
</html>