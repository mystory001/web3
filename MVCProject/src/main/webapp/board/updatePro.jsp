<%@page import="board.BoardDAO"%>
<%@page import="board.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board/updatePro.jsp</title>
</head>
<body>
<h1>board/updatePro.jsp</h1>
<h1>글수정 처리 작업</h1>
<%
// 사용자가 수정할 내용을 적고 => 서버에 전달 => request내장객체 저장
// request 한글처리
request.setCharacterEncoding("utf-8");
// request num,name,subject,content 파리미터 가져와서 => 변수에 저장
int num = Integer.parseInt(request.getParameter("num"));
String name = request.getParameter("name");
String subject = request.getParameter("subject");
String content = request.getParameter("content");

// BoardDTO 객체생성
BoardDTO boardDTO = new BoardDTO();
// set메서드 호출해서 변수내용을 저장
boardDTO.setNum(num);
boardDTO.setName(name);
boardDTO.setSubject(subject);
boardDTO.setContent(content);

// BoardDAO 객체생성
BoardDAO boardDAO = new BoardDAO();
// 리턴할형없음 updateBoard(BoardDTO 변수) 메서드 정의
// updateBoard(BoardDTO 주소) 메서드 호출
boardDAO.updateBoard(boardDTO);

// list.jsp 글목록으로 이동
response.sendRedirect("list.jsp");
%>
</body>
</html>




