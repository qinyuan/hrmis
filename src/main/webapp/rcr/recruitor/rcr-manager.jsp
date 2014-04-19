<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>岗位列表</title>
<%@include file="/cHead.jsp"%>
<q:css href="rcr-manager" />
<q:js src="rcr-manager" />
<q:css href="/lib/cForm" />
</head>
<body>
	<%@include file="common.jsp"%>
	<form action="rcr-manager.action" method="post">
		${recruitor.rcrManagerCheckBoxes }
		<q:submit value="提交修改" id="mdfSubmit" />
	</form>
</body>
</html>