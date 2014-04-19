<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学历列表</title>
<%@include file="/cHead.jsp"%>
<q:css href="/lib/cForm" />
<q:css href="post-degree-list" />
<q:js src="degree-list" />
<q:js src="/lib/cTable" />
<q:js src="/lib/autoScroll" />
<q:js src="/lib/showResult" />
</head>
<body>
	<%@include file="common.jsp"%>
	${recruitor.degreeTable}
	<input type="hidden" id="result" value="${result}" />
	<form action="degree-list.action" method="post">
		<fieldset>
			<legend>添加学历</legend>
			<q:text id="degreeName" />
			<br />
			<q:submit value="添加" id="addDegreeSubmit" />
		</fieldset>
	</form>
</body>
</html>