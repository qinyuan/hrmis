<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改用户信息</title>
<%@include file="/cHead.jsp"%>
</head>
<body>
	<%@include file="mdf-common.jsp"%>
	<h3>
		<a href="mdf-nickname.jsp">修改昵称</a>|修改密码
	</h3>
	<s:form action="mdf-password" method="post">
		<s:textfield label="新密码" cssClass="common" type="password"
			name="password" />
		<s:textfield label="密码确认" cssClass="common" type="password"
			name="password2" />
		<s:submit name="modifyPasswordSubmit" cssClass="common" value="提交修改" />
	</s:form>
	${result}
</body>
</html>