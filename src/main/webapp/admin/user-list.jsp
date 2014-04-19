<%@page import="qinyuan.hrmis.domain.admin.SimpleUser"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
<%@include file="/cHead.jsp"%>
<q:css href="user-list" />
<q:js src="/lib/cSelect" />
<q:js src="/lib/cTable" />
<q:js src="/lib/inputCurtain" />
<q:js src="/lib/autoScroll" />
<q:js src="user-list" />
</head>
<body>
	<%@include file="common.jsp"%>
	<h3>用户列表</h3>
	<input type="hidden" id="result" value="${result}" />
	<form action="user-list.action" method="post">
		<table>
			<tr>
				<th><input type="checkbox" id="selectAll" /></th>
				<th>用户</th>
				<th>密码</th>
				<th>昵称</th>
			</tr>
			<%
				for (SimpleUser su : admin.getUsers()) {
					out.print(su.toTableRow());
				}
			%>
		</table>
		<div id="control">
			<q:submit value="删除" id="delUser" />
			<q:button value="添加" id="addUserButton" />
		</div>
	</form>
	<div id="addForm">
		<table>
			<tr>
				<td>用户名：</td>
				<td><input type="text" id="username" /></td>
			</tr>
			<tr>
				<td>密码：</td>
				<td><input type="password" id="password" /></td>
			</tr>
			<tr>
				<td>昵称：</td>
				<td><input type="text" id="nickname" /></td>
			</tr>
			<tr>
				<td><q:button value="确定" id="addUserOK" /></td>
				<td><q:button value="取消" id="cancel" /></td>
			</tr>
		</table>
		<q:button value="生成随机数" id="createRandom" />
		<input type="text" id="randomText" disabled="disabled" />
	</div>
</body>
</html>