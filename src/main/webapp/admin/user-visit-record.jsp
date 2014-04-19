<%@page import="qinyuan.hrmis.lib.filter.RequestMonitor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>访问记录</title>
<%@include file="/cHead.jsp"%>
<q:js src="user-visit-record"/>
</head>
<body>
	<%@include file="common.jsp"%>
	<%=RequestMonitor.getRecordTable()%>
</body>
</html>