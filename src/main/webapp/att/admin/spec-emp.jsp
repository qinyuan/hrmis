<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>特殊人员</title>
<%@include file="/cHead.jsp"%>
<q:css href="spec-emp" />
<q:css href="/lib/cForm" />
<q:js src="/lib/cSelect" />
<q:js src="/lib/inputCurtain" />
<q:js src="spec-emp" />
<q:js src="/lib/cTable.js" />
</head>
<body>
	<%@include file="common.jsp"%>
	<div id="info">
		<ul>
			<li>此处的"<u>删除</u>"只是清除特殊原因而将某人改为一般人员，但不会删除此人
			</li>
			<li>双击指定行即可修改"<u>特殊原因</u>"
			</li>
		</ul>
	</div>
	<form action="spec-emp.action" method="post">
		<div id="button">
			<q:submit value="删除" id="deleteSubmit" />
			<q:button value="添加" id="addSpecEmp" />
		</div>
		<div id="add">
			<table>
				<tr>
					<td>人员选择</td>
					<td>${attAdmin.specEmpSelect}</td>
				</tr>
				<tr>
					<td>原因</td>
					<td><q:text id="reason" /></td>
				</tr>
				<tr>
					<td colspan="2"><q:submit value="确定" id="addUserSubmit" /> <q:button
							value="取消" id="addUserCancel" /></td>
				</tr>
			</table>
		</div>
		<p>部门选择：${attAdmin.deptSelect}</p>
		${attAdmin.specEmpTable}
	</form>
</body>
</html>