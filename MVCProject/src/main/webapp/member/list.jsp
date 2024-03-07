<!-- 231221 -->
<%@page import="com.itwill.dao.MemberDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.itwillbs.domain.MemberDTO"%>
<%@page import="com.itwillbs.controller.MemberController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/list.jsp</title>
</head>
<body>
<h1>member/list.jsp</h1>
<h1>회원 목록</h1>
<c:if test="${empty sessionScope.id }">
	<c:redirect url="login.me"/>
</c:if>
<c:if test="${! empty sessionScope.id }">
	<c:if test="${! sessionScope.id == 'admin' }">
	<c:redirect url="main.me"/>
	</c:if>
</c:if>
<%
//로그인 표시 → 세션에서 "id"값을 가져와서 → String id 저장
// String id = (String)session.getAttribute("id");
%>
<%
//로그인 표시값이 있으면 login.jsp이동, 있으면 로그인표시값이 "admin"이 아니면 main.jsp이동
// if(id==null){
// 	response.sendRedirect("login.me");
// }else{
// 	if(!id.equals("admin")){
// 		response.sendRedirect("main.me");
// 	}
	
// }

//MemberDAO 객체생성
// MemberDAO memberDAO = new MemberDAO();

//ArrayList 자바 내장객체 
//→ 배열(같은 기억장소를 여러 개 사용)에 단점(초기기억장소가 고정)울 보완한 자바 내장객체 배열 
//→ 기억장소 10개 할당 → 데이터가 10개가 넘어가면 추가로 10개가 추가되어짐
//→ java.util.ArrayList
// ArrayList 데이터 개수 : .size()
// ArrayList 배열에 데이터 저장(순서대로 값이 저장) : .add(memberDTO 주소값)
// ArrayList 배열에 데이터 가져오기 : .get(순서)
//ArrayList<MemberDTO> 리턴할 형 getMemberList() 메소드 정의

//ArrayList<MemberDTO> memberList = getMemberList() 메소드 호출 
// ArrayList<MemberDTO> memberList = memberDAO.getMemberList();

//request에서 값을 가지고 와서
// ArrayList<MemberDTO> memberList = (ArrayList<MemberDTO>)request.getAttribute("memberList");
%>


<c:set var="memberList" value="${requestScope.memberList }"/>
<table border="1">
<tr><td>아이디</td><td>비밀번호</td><td>이름</td><td>가입날짜</td></tr>
<%
// for(int i = 0; i < memberList.size(); i++){
// MemberDTO memberDTO = memberList.get(i);
%>

<%-- 	<tr><td><%=memberList.get(i).getId() %></td> --%>
<%-- 	<td><%=memberList.get(i).getpw() %></td> --%>
<%-- 	<td><%=memberList.get(i).getName() %></td> --%>
<%-- 	<td><%=memberList.get(i).getDate() %></td></tr> --%>

<%
// }
%>
<!--           변수                 배열 -->
<c:forEach var="memberDTO" items="${memberList }">
	<tr><td>${memberDTO.id}</td>
	<td>${memberDTO.pw}</td>
	<td>${memberDTO.name}</td>
	<td>${memberDTO.date}</td></tr>

</c:forEach>


</table>
</body>
</html>