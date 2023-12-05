<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri ="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<table border="1">
		<tr>
			<th>번호</th>
			<td>${bvo.bno }</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${bvo.title }</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${bvo.writer }</td>
		</tr>
		<tr>
			<th>내용</th>
			<td>${bvo.content }</td>
		</tr>
		<tr>
			<th>작성일</th>
			<td>${bvo.regdate }</td>
		</tr>
		<tr>
			<th>수정일</th>
			<td>${bvo.moddate }</td>
		</tr>
		<tr>
			<th>조회수</th>
			<td>${bvo.readCount }</td>
		</tr>
	</table>
	
		<c:if test="${ses.id eq bvo.writer}">
			<a href="/brd/modify?bno=${bvo.bno }"><button>modify</button></a>
			<a href="/brd/remove?bno=${bvo.bno }"><button>remove</button></a>
		</c:if>
		<a href="/brd/list"><button>list</button></a>
		
		<!-- comment line -->
		<hr>
		<div>
			comment line <br>
			<input type="text" id="cmtWriter" value="${ses.id }" readonly="readonly"> <br>
			<input type="text" id="cmtText" placeholder="Add Comment...">
			<button type="button" id="cmtAddBtn">댓글 등록</button>
		</div>
		
		<!-- 댓글 표시 라인 -->
		<div id="commentLine">
			<div>cno, bno, writer, regdate</div>
			<div>
				<button>수정</button>  <button>삭제</button> <br>
				<input value="content">
			</div>
		</div>
		
		<script type="text/javascript">
			 const bnoVal = `<c:out value="${bvo.bno}" />`;
			 console.log(bnoVal);
		</script>
		<script src="/resources/board_detail.js"></script>
		<script type="text/javascript">
			printCommentList(bnoVal);
		</script>
</body>
</html>