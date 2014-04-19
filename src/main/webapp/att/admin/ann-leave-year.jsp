<%@page import="qinyuan.lib.web.WebFileIO"%>
<%@page import="qinyuan.lib.web.html.NumSelect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>年休假设置</title>
<%@include file="/cHead.jsp"%>
<q:css href="ann-leave-year" />
<q:css href="/lib/cForm" />
<q:js src="/lib/cTable" />
<q:js src="/lib/cSelect" />
<q:js src="ann-leave-year" />
<q:js src="/lib/inputCurtain" />
<q:js src="/lib/autoScroll" />
</head>
<body>
	<%@include file="common.jsp"%>
	<%
		WebFileIO io = new WebFileIO(application);
		String year = io.readFirstLine("/WEB-INF/conf/annLeaveYear.txt");
		NumSelect nsf = new NumSelect(2000, 2100).setId("year")
				.setEmptyOpt(false).select(year.trim());
	%>
	<form action="ann-leave-year.action" method="post">
		<p>
			当前年份：<%=nsf%>
			<input type="submit" name="setYearSubmit" value="提交修改" />
			&nbsp;&nbsp; 部门：${attAdmin.deptSelect} &nbsp;&nbsp;
			<q:submit value="删除" id="delAnnLeave" />
		</p>
		${attAdmin.annLeaveTable} ${mdfResult} ${attAdmin.empWithoutAnnLeave}
		${addResult}
		<div id="addForm">
			<input type="hidden" name="badgenumber" />
			<fieldset id="add">
				<legend>添加年休假</legend>
				<table>
					<tr>
						<td>工号</td>
						<td id="badgenumber"></td>
					</tr>
					<tr>
						<td>姓名</td>
						<td id="username"></td>
					</tr>
					<tr>
						<td>参加工作时间</td>
						<td><q:text id="workDate" /></td>
					</tr>
					<tr>
						<td>入司时间</td>
						<td><q:text id="joinDate" /></td>
					</tr>
					<tr>
						<td>参保地</td>
						<td><q:text id="insurePlace" /></td>
					</tr>
					<tr>
						<td>可用天数</td>
						<td><q:text id="usabledDays" /></td>
					</tr>
					<tr>
						<td><q:submit value="确定" id="addAnnLeaveSubmit" /></td>
						<td><q:button value="取消" id="addCancel" /></td>
					</tr>
				</table>
			</fieldset>
		</div>
		<div id="mdfForm">
			<input type="hidden" name="mdf_badgenumber" />
			<table>
				<tr>
					<td>工号</td>
					<td id="mdf_badgenumber"></td>
				</tr>
				<tr>
					<td>姓名
					<td id="mdf_username"></td>
				</tr>
				<tr>
					<td>参加工作时间</td>
					<td><q:text id="mdf_workDate" /></td>
				</tr>
				<tr>
					<td>入司时间</td>
					<td><q:text id="mdf_joinDate" /></td>
				</tr>
				<tr>
					<td>参保地</td>
					<td><q:text id="mdf_insurePlace" /></td>
				</tr>
				<tr>
					<td>可用天数</td>
					<td><q:text id="mdf_usabledDays" /></td>
				</tr>
				<tr>
					<td><q:submit value="提交修改" id="mdfAnnLeaveSubmit" /></td>
					<td><q:button value="取消" id="mdfCancel" /></td>
				</tr>
			</table>
		</div>
	</form>
</html>