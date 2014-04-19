<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>特殊日期</title>
<%@include file="/cHead.jsp"%>
<q:css href="att-spec-date" />
<q:css href="/lib/cTable" />
<q:css href="/lib/cForm" />
<q:js src="att-spec-date" />
</head>
<body>
	<%@include file="common.jsp"%>
	<p>
		<a href="att-spec-date.action?weekToAdd=-1">上一周</a> <a
			href="att-spec-date.action?weekToAdd=1">下一周</a>
	</p>
	<form action="att-spec-date.action" id="mainForm" method="post">
		${attAdmin.specDateTable}</form>
</body>
</html>