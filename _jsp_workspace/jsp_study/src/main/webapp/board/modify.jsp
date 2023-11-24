<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Detail</h1>
	<!-- 		this.bno = bno;
		this.title = title;
		this.writer = writer;
		this.content = content;
		this.regdate = regdate;
		this.moddate = moddate;
		this.readCount = readCount; -->
	<form action="/brd/edit" method="post">
		<input type="hidden" name="bno" value="${bvo.bno }">
	<table border="1">
		<tr>
			<th>번호</th>
			<!--<td><input type="text" name="title" value="${bvo.bno }" readonly="readonly"></td>-->
			<td>${bvo.bno }</td>
		</tr>
		<tr>
			<th>제목</th>
			<td><input type="text" name="title" value="${bvo.title }"></td>
		</tr>
		<tr>
			<th>작성자</th>
			<td><input type="text" name="title" value="${bvo.writer }" readonly="readonly"></td>
		</tr>
		<tr>
			<th>내용</th>
			<td><textarea rows="10" cols="30" name="content" value="">${bvo.content }</textarea></td>
		</tr>
		<tr>
			<th>작성일</th>
			<td><input type="text" name="title" value="${bvo.regdate }" readonly="readonly"></td>
		</tr>
		<tr>
			<th>수정일</th>
			<td><input type="text" name="title" value="${bvo.moddate }" readonly="readonly"></td>
		</tr>
		<tr>
			<th>조회수</th>
			<td><input type="text" name="title" value="${bvo.readCount }" readonly="readonly"></td>
		</tr>
		
	</table>
		<button type="submit">modify</button>
	</form>

	
	<a href="/brd/delete?bno=${bvo.bno }"><button>remove</button></a>
	<a href="/brd/list"><button>list</button></a>
</body>
</html>