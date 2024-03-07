<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board/write.jsp</title>
</head>
<body>
<%
// 로그인 표시값을 session 가져와서 변수에 저장
// String id = (String)session.getAttribute("id");
%>
<h1>board/write.jsp</h1>
<h1>글쓰기</h1>
<form action="writePro.bo" method="post">
<table border="1">
<tr><td>글쓴이</td>
    <td><input type="text" name="name" value="${sessionScope.id }" readonly></td></tr>
<tr><td>글제목</td>
    <td><input type="text" name="subject"></td></tr>
<tr><td>글내용</td>
    <td><textarea name="content" rows="10" cols="20"></textarea></td></tr>
<tr><td colspan="2"><input type="submit" value="글쓰기"></td></tr>
</table>
</form>
</body>
</html>