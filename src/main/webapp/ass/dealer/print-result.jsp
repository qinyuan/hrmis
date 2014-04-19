<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>考核其他部门</title>
<%@include file="/cHead.jsp"%>
<q:css href="../ass-list" />
<q:css href="report" />
</head>
<body>
	<div class="hidden">
		<%@include file="common.jsp"%>
	</div>
	<div id="head">
		<h3><%=assDealer.getReportHead(false).replace(" ", "&nbsp;")%></h3>
	</div>
	${assDealer.scoresTableAsCheckee}
	${assDealer.valuesTableAsCheckee}
</body>
</html>