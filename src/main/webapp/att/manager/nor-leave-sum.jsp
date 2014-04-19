<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>请假统计表</title>
<%@include file="/cHead.jsp"%>
<q:css href="nor-leave-sum" />
<q:css href="/lib/cForm" />
<q:js src="lib/date-emp-select-form" />
<q:js src="lib/dept-select-form" />
<q:js src="nor-leave-sum" />
</head>
<body>
	<div class="head">
		<%@include file="common.jsp"%>
		<%
			if (!attManager.isDeptSelected()) {
		%>
		<jsp:forward page="dept-select.jsp">
			<jsp:param value="nor-leave-sum.action" name="linkPage" />
		</jsp:forward>
		<%
			}
		%>
		<p>
			部门：${attManager.deptSelect} 开始时间：
			<q:text id="startDate" value="<%=attManager.getStartDate()%>" />
			结束时间：
			<q:text id="endDate" value="<%=attManager.getEndDate()%>" />
			&nbsp;&nbsp;&nbsp; <a href="download-excel.action" target="_blank">导出Excel</a>
		</p>
		${attManager.norLeaveHeadTable}
	</div>
	<div class="body">${attManager.norLeaveBodyTable}</div>
</body>
</html>