<%@page import="qinyuan.lib.lang.MyMath"%>
<%@page import="qinyuan.hrmis.domain.user.AttManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>年休假列表</title>
<%@include file="/cHead.jsp"%>
<q:css href="leave-detail" />
<q:js src="/lib/cTable" />
</head>
<body>
	<div id="head">
		<%@include file="common.jsp"%>
	</div>
	<%
		String userId = request.getParameter("userId");
		if (MyMath.isNumeric(userId)) {
			out.print(attManager.getNorLeaveDetail(Integer.parseInt(userId)));
		}
	%>
</body>
</html>