<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2>BoardUpdate.jsp</h2>
	
	<form action="boardupdateprocess" method="post" enctype="multipart/form-data">
		<input type="hidden" name="bnumber" value="${board.bnumber}">
		<table>
			<tr>
				<td>작성자</td>
				<td><input type="text" name="bwriter" id="bwriter" value="${sessionScope.loginId}" readonly></td>
			</tr>
			<tr>
				<td>제목</td>
				<td><input type="text" name="btitle" id="btitle" value="${board.btitle}"></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea  cols="50" rows="20" name="bcontents" id="bcontents">${board.bcontents}</textarea></td>
			</tr>
			<tr>
				<td>첨부파일</td>
				<td><input type="file" name="bfile" id="bfile"></td>
			</tr>
		</table> <br>
		<input type="submit" value="작성완료">
	</form>
</body>
</html>