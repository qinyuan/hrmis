<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>免刷卡与未刷卡申请单</title>
<%@include file="/cHead.jsp"%>
<q:css href="spec-report" />
<q:js src="lib/dept-select-form" />
<q:js src="/lib/cTable" />
</head>
<body>
	<div class="hidden">
		<%@include file="common.jsp"%>
	</div>
	<h3>未刷卡与免刷卡补签申请单</h3>
	${attManager.specTable}
</body>
</html>