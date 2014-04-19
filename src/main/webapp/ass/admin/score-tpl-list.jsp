<%@page import="qinyuan.hrmis.domain.ass.AssDept"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>模板（分值）</title>
<%@include file="tpl-list-head.jsp"%>
<q:js src="score-tpl-list" />
</head>
<body>
	<%@include file="common.jsp"%>
	<form action="score-tpl-list.action" method="post">
		<jsp:include page="tpl-list-body.jsp" />
		${assAdmin.scoreTplsTable}

		<div id="input">
			<input type="hidden" id="iId" name="iId" />
			<table>
				<tr>
					<td>被考核部门</td>
					<td><%=AssDept.getSelect("iCheckee")%></td>
				</tr>
				<tr>
					<td>考核部门</td>
					<td><%=AssDept.getSelect("iChecker")%></td>
				</tr>
				<tr>
					<td>项目</td>
					<td><q:text id="iItem" /></td>
				</tr>
				<tr>
					<td>指标</td>
					<td><textarea name="iTarget" id="iTarget" rows="3"
							cols="80"></textarea></td>
				</tr>
				<tr>
					<td>分值</td>
					<td><q:text id="iWeight" /></td>
				</tr>
				<tr>
					<td>评分标准</td>
					<td><textarea id="iFormula" name="iFormula" rows="8"
							cols="80"></textarea></td>
				</tr>
				<tr>
					<td><q:button value="取消" id="cancel" /></td>
					<td>
						<q:submit value="修改" id="mdfSubmit" />
						<q:submit value="添加" id="addSubmit" />
					</td>
				</tr>
			</table>
		</div>		
	</form>
</body>
</html>