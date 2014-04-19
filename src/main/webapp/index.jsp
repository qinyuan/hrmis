<%@page import="qinyuan.lib.web.IdentifyCode"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人力资源信息系统</title>
<link rel="stylesheet" href="index.css" type="text/css" />
<script src="index.js"></script>
<%
	if (session.getAttribute("visitor") != null) {
		response.sendRedirect("user/login.jsp");
	}
%>
</head>
<body>
	<div class="body">
		<h2>人力资源信息系统</h2>
		<h2>用户登录</h2>
		<div class="form">
			<form action="user/login.action" method="post">
				<table>
					<tr>
						<td>用户名：</td>
						<td><input class="text" type="text" name="username" /></td>
					</tr>
					<tr>
						<td>密码：</td>
						<td><input class="text" type="password" name="password" /></td>
					</tr>
					<%
						if (!IdentifyCode.check(request.getRemoteAddr())) {
					%>
					<tr>
						<td>验证码：</td>
						<td><input class="text" type="text" name="identCode" /></td>
					</tr>
					<tr>
						<td><a href="javascript:changeimg()">换一张 </a></td>
						<td><img id="code" src="ident-code.jsp"></td>
					</tr>
					<%
						} else {
							session.setAttribute("identCode", null);
						}
					%>
					<tr>
						<td colspan="2"><input type="submit" id="submit"
							name="submit" value="登 录" /></td>
					</tr>
				</table>
				<div id="info">
					<%
						if (request.getParameter("error") != null) {
							out.print("用户名不存在或密码错误");
						} else if (request.getParameter("identCodeError") != null) {
							out.print("验证码输入错误");
						}
					%>
				</div>
			</form>
		</div>
	</div>
</body>
</html>