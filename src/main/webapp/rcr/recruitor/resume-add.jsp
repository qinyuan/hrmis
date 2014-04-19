<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加简历</title>
<%@include file="/cHead.jsp"%>
<q:css href="/lib/cForm" />
<q:css href="resume-tool" />
<q:js src="resume-tool" />
<q:js src="resume-add" />
</head>
<body>
	<%@include file="common.jsp"%>
	<form action="resume-add.action" method="post">
		<%=recruitor.getResumeTable()%>
		<q:submit value="添加" id="resumeAddSubmit" />
	</form>
	${result}
	<jsp:include page="resume-tool.jsp" />
</body>
</html>