<%@page import="qinyuan.hrmis.domain.user.AttManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>考勤管理(宽屏版)</title>
<%@include file="att-detail-cHead.jsp"%>
<q:css href="att-detail-width" />
<%
	localAttMng.setNarrow(false);
%>
</head>
<body>
	<%@include file="att-detail-cBody.jsp"%>
</body>
</html>