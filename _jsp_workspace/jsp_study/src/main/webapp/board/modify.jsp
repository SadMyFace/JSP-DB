<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Modify Page</h1>
	<img alt="" src="/_fileUpload/${bvo.imageFile }">
	<form action="/brd/edit" method="post" enctype="multipart/form-data">
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
			<td><input type="text" name="writer" value="${bvo.writer }" readonly="readonly"></td>
		</tr>
		<tr>
			<th>내용</th>
			<td><textarea rows="10" cols="30" name="content" value="">${bvo.content }</textarea></td>
		</tr>
		<tr>
			<th>작성일</th>
			<td><input type="text" name="regdate" value="${bvo.regdate }" readonly="readonly"></td>
		</tr>
		<tr>
			<th>수정일</th>
			<td><input type="text" name="moddate" value="${bvo.moddate }" readonly="readonly"></td>
		</tr>
		<tr>
			<th>조회수</th>
			<td><input type="text" name="readCount" value="${bvo.readCount }" readonly="readonly"></td>
		</tr>
		<tr>
			<th>Image_File</th>
			<td>
				<input type="hidden" name="imgae_file" value="${bvo.imageFile }">
				<input type="file" name="new_file">
			</td>
		</tr>
		
	</table>
		<button type="submit">modify</button>
	</form>

	
	<a href="/brd/delete?bno=${bvo.bno }"><button>remove</button></a>
	<a href="/brd/list"><button>list</button></a>
	
</body>
</html>