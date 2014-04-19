<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>考勤负责人设置</title>
<%@include file="/cHead.jsp"%>
<q:css href="/lib/cForm" />
<q:js src="/lib/cTable" />
</head>
<body>
	<%@include file="common.jsp"%>
	<s:form action="att-director" method="post">
		${attAdmin.attManagerTable}
		<input type="submit" name="setDeptSubmit" value="提交修改" />
	</s:form>
</body>
</html>