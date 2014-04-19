<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>申请单时间设置</title>
<%@include file="/cHead.jsp"%>
<q:css href="spec-report-set" />
<q:js src="spec-report-set" />
<q:js src="lib/date-emp-select-form" />
<q:js src="lib/dept-select-form" />
</head>
<body>
	<%@include file="common.jsp"%>
	<div class="setForm">
		<p>部门：${attManager.deptSelect}</p>
		<p>人员：${attManager.empSelect}</p>
		<p>
			时间：
			<q:text id="startDate" value="<%=attManager.getStartDate()%>" />
			——
			<q:text id="endDate" value="<%=attManager.getEndDate()%>" />
		</p>
		<q:button value="确定" id="showReport" style="common" />
	</div>
</body>
</html>