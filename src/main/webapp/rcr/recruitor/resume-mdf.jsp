<%@page import="qinyuan.lib.lang.MyMath"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改简历</title>
<%@include file="/cHead.jsp"%>
<q:css href="/lib/cForm" />
<q:css href="resume-tool" />
<q:js src="resume-tool" />
</head>
<body>
	<%@include file="common.jsp"%>
	<%
		String resumeId = request.getParameter("resumeId");
		if (!MyMath.isNumeric(resumeId)) {
			return;
		}
	%>

	<form action="resume-mdf.action" method="post">
		<%=recruitor.getResumeTable(Integer.parseInt(resumeId))%>
		<q:hidden value="<%=resumeId%>" id="resumeId" />
		<q:submit value="提交修改" id="resumeMdfSubmit" />
	</form>
	${result}

	<jsp:include page="resume-tool.jsp" />
</body>
</html>