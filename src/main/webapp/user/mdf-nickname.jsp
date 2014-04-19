<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改昵称</title>
<%@include file="/cHead.jsp"%>
</head>
<body>
	<%@include file="mdf-common.jsp"%>
	<h3>
		修改昵称|<a href="mdf-password.jsp">修改密码</a>
	</h3>
	<s:form action="mdf-nickname" method="post">
		<h3>新用户昵称：</h3>
		<input type="text" class="common" value="<%=visitor.getNickname()%>"
			name="nickName" />
		<br />
		<input type="submit" class="common" name="mdfNickname" value="提交修改" />
	</s:form>
	${result}
</body>
</html>