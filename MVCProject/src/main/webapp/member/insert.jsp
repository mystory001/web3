<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/insert.jsp</title>
</head>
<body>
<!-- 
가상 주소 : http://localhost:8080/MVCProject/insert.me
실제페이지(화면) : http://localhost:8080/MVCProject/member/insert.jsp
-->
<h1>member/insert.jsp</h1>
<h1>회원가입</h1>
<form action="insertPro.me" method="post">
아이디 : <input type="text" name="id"><br>
비밀번호 : <input type="password" name="pw"><br>
이름 : <input type="text" name="name"><br>
<input type="submit" value="가입">
</form>
</body>
</html>