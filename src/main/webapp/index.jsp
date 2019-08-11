<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%
	String stop=(String)request.getAttribute("msg");
System.out.println(stop);
%>
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
}

</style>
</head>
<body>




<script>
<% if(stop==null){%>
$(document).ready(function() {
    // 로딩되기 시작할때 게시판 읽어오기
    console.log('콘트롤러 갔다옴');
location.href="<%=request.getContextPath()%>/index";
});
<%}%> 

</script>
</body>
</html>