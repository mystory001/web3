<!-- 240109 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/main.jsp</title>
</head>
<body>
<h1>member/main.jsp</h1>
<%
//p510~537
//p512 
//EL (Expression Language, 표현 언어,출력 언어)
//   <%="하나의 내용을 출력" % > => ${출력 내용} -> 좀 더 편하게 출력하기 위해 제공된 기능(프로그램 설치하지 않아도 동작함)
//	 p516 연산자 empty(값이 null이나 0이면 즉 비어있으면 true)
//	 		   / 또는 div, % 또는 mod, && 또는 and, || 또는 or, ! 또는 not
//			   == 또는 eq(문자든 숫자든), != 또는 ne, < 또는 lt, > 또는 gt, <= 또는 le, >= 또는 ge
//	 p513 내장객체 sessionScope : Session 영역에 사용하는 내장객체 
//	            requestScope
//p519 JSTL(JSP Standard Tag Library) => if, for % 대신에 태그처럼 사용 -> 프로그램 설치
//p510 JSTL 프로그램 설치 http://tomcat.apache.org
//Apache Tomcat - Taglibs - Download
// Impl: taglibs-standard-impl-1.2.5.jar (pgp, sha512)
// Spec: taglibs-standard-spec-1.2.5.jar (pgp, sha512)
// EL:taglibs-standard-jstlel-1.2.5.jar (pgp, sha512)
// Compat:
// taglibs-standard-compat-1.2.5.jar (pgp, sha512)
// 다운로드 후 Project-src-main-webapp-web-inf-lib 넣기
// => core(if, for문,..), fmt(날짜, 숫자 포맷), xml, sql 종류
// => p519 core 
//    설치된 것 가져오기 상단에 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" % >
//	  <c:out> : 출력, <c:set> : 변수 선언, <c:if>(else문이 없음), <c:choose><c:when><c:otherwise>(switch)
//	  <c:forEach> : for문, <c:forTokens> : 기준값을 기준으로 반복 ex)"사과;바나나;포도" ,;: 기준으로 반복
//	  <c:redirect> : 이동, <c:url> : 하이퍼링크 주소
// => p526 fmt
//    설치된 것 가져오기 상단<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" % >
// <fmt:requestEncoding> : 한글 처리, <fmt:formatData> : 날짜 포멧, <fmt:formatNumber> : 숫자 포멧 

String id = (String)session.getAttribute("id");
// if(id == null){
// 	response.sendRedirect("login.me");
// }
%>

<%-- <c:if test="조건"> --%>
<c:if test="${ empty sessionScope.id}">
<c:redirect url="login.me" />
</c:if>

<%=id %> == ${sessionScope.id } 님 환영합니다.


<a href="logout.me">로그아웃</a><br>
<a href="info.me">회원 정보 조회</a><br>
<a href="update.me">회원 정보 수정</a><br>
<a href="delete.me">회원 정보 삭제</a><br>

<%
//로그인 한 사용자가 null이 아닌 경우
// if(id!=null){
	// //로그인한 관리자가 "admin" → 일치하면 회원 목록이 보이게
// <%if(id.equals("admin")){
	
// <a href="list.me">회원 목록(관리자)</a><br>
%>
<%
// }
// }	
%>
<c:if test="${ ! empty sessionScope.id}">
	<c:if test="${ sessionScope.id == 'admin'}">
		<a href="list.me">회원 목록</a>
	</c:if>
</c:if><br>
<!-- 태그에 큰따옴표 열고 닫고되어 있으면 작은 따옴표로 감싸준다. -->
<a href="write.bo">글쓰기</a><br>
<a href="list.bo">글목록</a><br>

</body>
</html>