<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board/list.jsp</title>
</head>
<body>
<%
// // BoardDAO 객체생성
// BoardDAO boardDAO = new BoardDAO();
// // ArrayList<BoardDTO>  리턴할형  getBoardList() 메서드 정의
// // ArrayList<BoardDTO> boardList = getBoardList() 메서드 호출
// ArrayList<BoardDTO> boardList = boardDAO.getBoardList();
%>
<c:set var="pageDTO" value="${requestScope.pageDTO}"/>
<h1><a href="write.bo">글쓰기</a></h1>
<h1>board/list.jsp</h1>
<h1>게시판 글 목록 [전체 글의 개수 : ${pageDTO.count}]</h1>
<table border="1">
<tr><td>글번호</td><td>글쓴이</td><td>제목</td>
    <td>조회수</td><td>작성일</td></tr>
<%
// for(int i=0; i<boardList.size(); i++){
// 	BoardDTO boardDTO = boardList.get(i);
%>
<%-- <tr><td><%=boardDTO.getNum() %></td> --%>
<%--     <td><%=boardDTO.getName() %></td> --%>
<%--     <td><a href="content.jsp?num=<%=boardDTO.getNum() %>"><%=boardDTO.getSubject() %></a></td> --%>
<%--     <td><%=boardDTO.getReadcount() %></td> --%>
<%--     <td><%=boardDTO.getDate() %></td> --%>
<!-- </tr> -->
<%
// }
%>

<c:set var="boardList" value="${requestScope.boardList}"/>

<c:forEach var="boardDTO" items="${boardList }">
	<tr><td>${boardDTO.num}</td>
    	<td>${boardDTO.name}</td>
    	<td><a href="content.bo?num=${boardDTO.num}">
    	    ${boardDTO.subject}</a></td>
    	<td>${boardDTO.readcount}</td>
    	<td>${boardDTO.date}</td>
	</tr>
</c:forEach>
<a href="main.me">메인으로 이동</a><br>
</table>
<!-- 11,21에서만 이전버튼이 보여야함. pageBlock과 비교 즉. 시작페이지번호와 페이지블록을 비교 -->
<c:if test="${pageDTO.startPage > pageDTO.pageBlock }"> 
 	<a href="list.bo?pageNum=${pageDTO.startPage - pageDTO.pageBlock }">[이전]</a>
</c:if>
<c:forEach var="i" begin="${pageDTO.startPage }" end="${pageDTO.endPage }" step="1">
<a href="list.bo?pageNum=${i}">${i }</a>
</c:forEach>
<!-- 끝페이지에서는 다음 버튼이 보여지지 않아야함. 끝 페이지 전체 < 페이지 개수 -->
<c:if test="${pageDTO.endPage < pageDTO.pageCount }">
<a href="list.bo?pageNum=${pageDTO.startPage+pageDTO.pageBlock}">[다음]</a>
</c:if>
</body>
</html>