<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>岗位列表</title>
<%@include file="/cHead.jsp"%>
<q:css href="/lib/cForm" />
<q:css href="post-list"/>
<q:js src="post-list" />
<q:js src="/lib/cTable" />
<q:js src="/lib/autoScroll" />
<q:js src="/lib/showResult" />
</head>
<body>
	<%@include file="common.jsp"%>
	${recruitor.postTable}
	<form action="post-degree-list.action" method="post">
		<fieldset>
			<legend>添加岗位</legend>
			<q:text id="postName" /><br />
			<q:submit value="添加" id="addPostSubmit" />
		</fieldset>
	</form>
	<input type="hidden" id="result" value="${result}" />
</body>
</html>