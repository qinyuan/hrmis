<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>简历列表</title>
<%@include file="/cHead.jsp"%>
<q:css href="../resume-list" />
<q:js src="../resume-list" />
</head>
<body>
	<%@include file="common.jsp"%>
	<div>${rcrManager.resumesTable}</div>
</body>
</html>