<%@page import="qinyuan.hrmis.domain.ass.AssMon"%>
<%@page import="qinyuan.lib.lang.MyMath"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>考核结果汇总</title>
<%@include file="/cHead.jsp"%>
<q:js src="/lib/cTable" />
</head>
<body>
	<div class="hidden">
		<%@include file="common.jsp"%>
	</div>
	<%
		String monIdStr = request.getParameter("monId");
		if (!MyMath.isNumeric(monIdStr)) {
			return;
		}

		int monId = Integer.parseInt(monIdStr);
		AssMon mon = AssMon.getInstance(monId);
		if (mon == null) {
			return;
		}
	%>
	<h3><%=mon.getYear()%>年<%=mon.getMon()%>月 考核结果汇总
	</h3>
	<%=assAdmin.getAssSum(monId)%>
</body>
</html>