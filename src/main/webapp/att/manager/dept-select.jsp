<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门选择</title>
<%@include file="/cHead.jsp"%>
</head>
<body>
	<%@include file="common.jsp"%>
	<h3>部门选择</h3>
	<%
		String linkPage = request.getParameter("linkPage");
		if (linkPage == null) {
			linkPage = "att-detail.jsp";
		}
	%>
	<%=attManager.getDeptList(linkPage)%>
</body>
</html>