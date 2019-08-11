<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
Date now = new Date();
request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글쓰기</title>
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.4.0.js"></script>
<style>
table#table td{
	text-align:center;
	padding: 3px;
}

</style>
</head>
<body>
	<form action="${pageContext.request.contextPath }/writeBoardEnd.do" method="post"  >
	<table id="table" style="margin:0 auto;">
		<thead>
		<tr>
			<td>글쓰기</td>
		</tr>
		</thead>
		<tbody>
			<tr>
				<td>
				<c:if test="${nickName eq null or nickName eq '' }">
					<label for="nickName">닉네임 </label> <input type="text" name="nickName" id="nickName" />
				</c:if>
				<c:if test="${nickName ne null }">
					<label for="nickName">닉네임 </label> <input type="text" name="nickName" value="${nickName }" readonly id="nickName" />
				</c:if>
				</td>
			</tr>
			<tr>
				<td><label for="boardTitle">제목 </label><input type="text" name="boardTitle" id="boardTitle"/></td>
			</tr>
			<tr>
				<td><textarea name="content" id="content" cols="100" rows="200" style="resize:none;width:400px;height: 500px" ></textarea></td>
			</tr>
			<tr>
				<td>
				<label for="chkbox">중요게시물</label><input type="checkbox" name="chkbox" id="chkbox" value="false" onchange="chageImportant(this);"/>
				</td>
			</tr>
			<tr>
				<td>
				<input type="submit" value="글쓰기" />
	<input type="button" value="뒤로가기" onclick="location.href='${pageContext.request.contextPath}/'"/>
				</td>
			</tr>
		</tbody>
	</table>
	<input type="hidden" name="date" value="<%=now %>" />
	<input type="hidden" name="important" id="important" value="false" />
	
	</form>


<script>
function chageImportant(obj){
	if(obj.value=='true'){
		obj.value='false';
		$('#important').val('false');
	}else{
	obj.value='true';
	$('#important').val('true');
	}
	console.log($('#important').val());
}

</script>
</body>
</html>