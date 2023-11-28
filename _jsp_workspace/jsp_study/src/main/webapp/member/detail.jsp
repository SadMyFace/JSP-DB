<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="memb/modify" method="post">
		ID : <input type="text" name="id" value="${ses.id }" readonly> <br>
		PW : <input type="text" name="pw" placeholder="${ses.pwd }"> <br>
		E-mail: <input type="text" name="email" placeholder="${ses.email }"> <br>
		Age :  <input type="text" name="age" placeholder="${ses.age }"> <br>
		<button type="submit">수정</button>
		<a href="/memb/remove?id=${ses.id }"><button type="button">회원탈퇴</button></a>
	</form>
</body>
</html>
