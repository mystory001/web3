<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board/update.jsp</title>
</head>
<body>
<h1>board/update.jsp</h1>
<h1>게시판 글수정</h1>
<%
// // update.jsp?num=1
// // 서버에 request에 "num" 요청 파라미터값을 저장 => 가져와서 변수 int num 저장
// int num = Integer.parseInt(request.getParameter("num"));
// // BoardDAO 객체생성
// BoardDAO boardDAO = new BoardDAO();
// // BoardDTO boardDTO = getBoard(num) 메서드 호출
// BoardDTO boardDTO = boardDAO.getBoard(num);
%>
<c:set var="boardDTO" value="${requestScope.boardDTO }"/>
<form action="updatePro.bo" method="post">
<input type="hidden" name="num" value="${boardDTO.num}">
<table border="1">
<tr><td>글쓴이</td>
    <td><input type="text" name="name" value="${boardDTO.name }" readonly></td></tr>
<tr><td>글제목</td>
    <td><input type="text" name="subject" value="${boardDTO.subject}"></td></tr>
<tr><td>글내용</td>
    <td><textarea name="content" rows="10" cols="20">${boardDTO.content}</textarea></td></tr>
<tr><td colspan="2"><input type="submit" value="글수정"></td></tr>
</table>
</form>
</body>
</html>



