<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board/content.jsp</title>
</head>
<body>
<%
//요청주소 content.jsp?num=1
// 서버에 request에 "num" 요청 파라미터값을 저장 => 가져와서 변수 int num 저장
// String snum = request.getParameter("num");
// int num = Integer.parseInt(request.getParameter("num"));
// BoardDAO 객체생성
// BoardDAO boardDAO = new BoardDAO();
//조회수 증가
// 리턴할형없음 updateReadcount(int num)메서드 정의
//  updateReadcount(num) 메서드 호출
// boardDAO.updateReadcount(num);

// BoardDTO 리턴할형 getBoard(int num) 메서드 정의
// BoardDTO boardDTO = getBoard(num) 메서드 호출
// BoardDTO boardDTO = boardDAO.getBoard(num);

//로그인 표시값 세션에서 가져오기
// String id = (String)session.getAttribute("id");
%>
<c:set var="boardDTO" value="${requestScope.boardDTO }"/>

<h1>board/content.jsp</h1>
<h1>글내용 보기[로그인 : ${sessionScope.id}]</h1>
<table border="1">
<tr><td>글번호</td><td>${boardDTO.num}</td></tr>
<tr><td>글쓴이</td><td>${boardDTO.name}</td></tr>
<tr><td>조회수</td><td>${boardDTO.readcount}</td></tr>
<tr><td>글쓴날짜</td><td>${boardDTO.date}</td></tr>
<tr><td>제목</td><td>${boardDTO.subject}</td></tr>
<tr><td>내용</td><td>${boardDTO.content}</td></tr>
<tr><td colspan="2">
<%
// 로그인 표시값 있으면
// if(id != null){
	// 로그인 표시값, 글쓴이 => 일치하면 글수정,글삭제 보이기
// 	if(id.equals(boardDTO.getName())){
	%>
<%-- 	<a href="update.jsp?num=<%=boardDTO.getNum()%>">글수정</a>  --%>
<%-- 	<a href="delete.jsp?num=<%=boardDTO.getNum()%>">글삭제</a> --%>
	<%
// 	}
// }
%>
<c:if test="${ ! empty sessionScope.id}">
	<c:if test="${sessionScope.id eq boardDTO.name}">
		<a href="update.bo?num=${boardDTO.num}">글수정</a> 
		<a href="delete.bo?num=${boardDTO.num}">글삭제</a>
	</c:if>
</c:if>

<c:if test="${sessionScope.id eq 'admin' }">
		<a href="update.bo?num=${boardDTO.num}">글수정</a> 
		<a href="delete.bo?num=${boardDTO.num}">글삭제</a>
</c:if>
<a href="list.bo">글목록</a></td></tr>
</table>
</body>
</html>
