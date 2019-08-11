<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
	
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.4.0.js"></script>
<style>
#boardList td{
	max-width:500px;
	text-overflow: ellipsis;
	border: solid 1px;
	border-collapse: collapse;
	overflow:hidden;
	text-align: center;
	padding:5px;
}
#boardList th{
	max-width:500px;
	border: solid 1px;
	border-collapse: collapse;
	text-align: center;
	padding:5px;
}
#boardList{
	margin:0 auto;
	margin-top:50px;
	border-collapse: collapse;
	table-layout:fixed;
}
#write button{
	margin:0 auto;
}
#top{
	text-align: center;
}
</style>
</head>
<body>

	<div id="top"><button id="write" onclick="writeBoard();">글쓰기</button>
	<button style="margin:0 auto;" onclick="location.href='${pageContext.request.contextPath}/'" >전체 보기</button>
	</div>
	<div id="boardList">

	<c:if test="${not empty list }">
		<table id="boardList">
		<tr>
		<th style="width:80px;">작성자</th>
		<th style="width:100px;">제목</th>
		<th style="width:200px">내용</th>
		<th>작성일</th>
		</tr>
			<c:forEach items="${list }" var="b">
				<tr onclick="location.href='${pageContext.request.contextPath}/boardView.do?boardNo=${b.boardNo }'">
					
					<td ${b.important eq true?'style="background:lightblue;"':'' } style="width:80px;">${b.nickName }</td>
					<td ${b.important eq true?'style="background:lightblue;"':'' } style="width:100px;"><nobr> ${b.boardTitle }</nobr></td>
					<td ${b.important eq true?'style="background:lightblue;"':'' } style="width:200px"><nobr> ${b.content }</nobr></td>
					<td ${b.important eq true?'style="background:lightblue;"':'' }><nobr> ${b.writeDate }</nobr></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<c:if test="${empty list }">
		게시글이 없습니다.
	</c:if>
	</div>
	
	
	<div id="searchBar" style="margin-top:30px;text-align: center;">
	<form action="${pageContext.request.contextPath }/searchKeyword.do" method="post" >
		<select name="searchType" id="searchType">
			<option selected disabled>--검색 유형 선택--</option>
			<option value="nickName">닉네임으로 검색</option>
			<option value="title">제목으로 검색</option>
		</select>
		<input type="text" name="searchKeyword" id="" placeholder="검색어 입력" />  <input type="submit" value="검색하기" />

	</form>
	</div>
	


<script> 

function writeBoard(){
	location.href="${pageContext.request.contextPath}/writeBoard.do";
	
}
</script>
</body>
</html>