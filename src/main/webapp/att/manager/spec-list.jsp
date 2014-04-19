<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>特殊情况列表</title>
<%@include file="/cHead.jsp"%>
<q:css href="spec-list" />
<q:css href="/lib/cForm" />
<q:js src="lib/date-emp-select-form" />
<q:js src="lib/dept-select-form" />
<q:js src="/lib/cTable" />
<q:js src="spec-list.js" />
<q:js src="/lib/autoScroll" />
</head>
<body>
	<%@include file="common.jsp"%>
	<p>
		部门：${attManager.deptSelect} 人员：${attManager.empSelect} 时间：
		<q:text id="startDate" value="<%=attManager.getStartDate()%>" />
		——
		<q:text id="endDate" value="<%=attManager.getEndDate()%>" />
	</p>
	<div id="leaveHead">请假/公出/调休</div>
	<div id="accdHead">意外情况</div>
	<div id="leave">${attManager.leaveTable}</div>
	<div id="accd">${attManager.accdTable}</div>
</body>
</html>