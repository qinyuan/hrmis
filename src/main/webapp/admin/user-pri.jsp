<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设置用户权限</title>
<%@include file="/cHead.jsp"%>
<q:css href="user-pri" />
<q:js src="user-pri" />
</head>
<body>
	<%@include file="common.jsp"%>
	<h3>设置用户权限</h3>
	<form action="user-pri.action" method="post">
		<%=admin.getPriCheckBoxes()%>
		<div class="submit">
			<input type="submit" class="common" name="setPriSubmit" value="提交修改" />
		</div>
	</form>
</body>
</html>