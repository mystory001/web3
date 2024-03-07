<%@page import="board.BoardDAO"%>
<%@page import="board.BoardDTO"%>
<%@page import="java.sql.Timestamp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board/writePro.jsp</title>
</head>
<body>
<h1>board/writePro.jsp</h1>
<%
// 사용자가 입력한 글내용을 서버에 전달하면 request 내장객체에 저장
// request 한글설정
request.setCharacterEncoding("utf-8");
// request에서 파라미터(태그) name, subject, content값을 가져와서 변수에 저장
String name = request.getParameter("name");
String subject = request.getParameter("subject");
String content = request.getParameter("content");

//BoardDAO 객체생성(기억장소 할당)
BoardDAO boardDAO = new BoardDAO();
// int 리턴할형  getMaxNum()  메서드 정의 

// num, readcount, date 구하기
// max(num) + 1
int num = boardDAO.getMaxNum() + 1;
// 조회수 초기값 0 설정
int readcount = 0;
// date 현시스템 날짜 설정
Timestamp date = new Timestamp(System.currentTimeMillis());

// 게시판내용을 저장하는 자바파일 준비 
// 폴더(패키지) board  파일이름 BoardDTO
// 멤버변수 정의 => 데이터은닉 => 접근할수 있는 메서드 setter() getter() 정의
// BoardDTO 객체생성(기억장소 할당)
BoardDTO boardDTO = new BoardDTO();
// set메서드 호출 (멤버변수의 기억장소에 파라미터(태그값을 저장)
boardDTO.setNum(num);
boardDTO.setName(name);
boardDTO.setSubject(subject);
boardDTO.setContent(content);
boardDTO.setReadcount(readcount);
boardDTO.setDate(date);
		
// 게시판 데이터베이스 작업을 하는 자바파일 준비		
// 폴더(패키지) board  파일이름 BoardDAO
// 리턴값없음 insertBoard(BoardDTO 변수)메서드 정의() 

// insertBoard(BoardDTO 주소) 메서드 호출
boardDAO.insertBoard(boardDTO);

//글목록 이동
response.sendRedirect("list.jsp");

%>
</body>
</html>