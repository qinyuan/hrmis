<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门列表</title>
<%@include file="/cHead.jsp"%>
<q:css href="/lib/cForm" />
<q:css href="dept-list" />
<q:js src="/lib/cTable" />
<q:js src="/lib/cSelect" />
<q:js src="dept-list" />
<q:js src="/lib/showResult" />
</head>
<body>
	<%@include file="common.jsp"%>
	<input type="hidden" id="result" value="${result}" />
	<form action="dept-list.action" method="post">
		${assAdmin.deptTable}
		<div id="control">
			<fieldset>
				<legend>添加新部门</legend>
				<q:text id="deptname" />
				<q:submit value="添加" id="addSubmit" />
			</fieldset>
			<p>
				<q:submit value="删除选中的部门" id="delSubmit" />
			</p>
		</div>
	</form>
</body>
</html>