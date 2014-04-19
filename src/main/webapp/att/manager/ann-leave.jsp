<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>年休假管理</title>
<%@include file="/cHead.jsp"%>
<q:css href="ann-leave" />
<q:css href="/lib/cForm" />
<q:js src="ann-leave" />
<q:js src="lib/dept-select-form" />
<q:js src="/lib/cTable" />
</head>
<body>
	<%@include file="common.jsp"%>
	<%@include file="lib/ann-leave-year.jsp"%>
	<p>
		部门选择：${attManager.deptSelect} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 当前统计年份：<b><u><%=year%></u></b>
	</p>
	<%
		if (year > 0) {
			out.print(attManager.getAnnLeaveTable(year));
		}
	%>
</body>
</html>