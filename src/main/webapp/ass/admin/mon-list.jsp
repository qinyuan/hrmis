<%@page import="qinyuan.hrmis.domain.ass.AssMon"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>时间列表</title>
<%@include file="/cHead.jsp"%>
<q:css href="mon-list" />
<q:js src="/lib/cSelect" />
<q:js src="/lib/inputCurtain" />
<q:js src="mon-list" />
<q:js src="/lib/cTable" />
</head>
<body>
	<%@include file="common.jsp"%>
	<form action="mon-list.action" method="post">
		<table>
			<tr>
				<th><input type="checkbox" id="selectAll" /></th>
				<th class="width">年份</th>
				<th class="width">月份</th>
				<th class="width">状态</th>
				<th class="width">其他</th>
			</tr>
			<%
				AssMon[] assMons = AssMon.getInstances();
				int id;
				for (AssMon assMon : assMons) {
					id = assMon.getId();
			%>
			<tr id="tr<%=id%>">
				<td><input type="checkbox" name="chk<%=id%>" /></td>
				<td><%=assMon.getYear()%></td>
				<td><%=assMon.getMon()%></td>
				<td><%=assMon.getLockedStr()%></td>
				<td><a href="ass-sum.jsp?monId=<%=id%>" target="_blank">结果汇总</a></td>
			</tr>
			<%
				}
			%>
		</table>
		<q:submit value="删除" id="delSubmit" />
		<q:submit value="激活" id="unlockSubmit" />
		<q:submit value="锁定" id="lockSubmit" />
		<q:button value="创建" id="showAddForm" />
		<div id="addForm">
			<table>
				<tr>
					<td>年份</td>
					<td><q:year id="year" /></td>
				</tr>
				<tr>
					<td>月份</td>
					<td><q:mon id="mon" /></td>
				</tr>
				<tr>
					<td><q:button value="取消" id="addCancel" /></td>
					<td><q:submit value="确定" id="addSubmit" /></td>
				</tr>
			</table>
		</div>
		<div id="mdfForm">
			<input type="hidden" id="mdfId" name="mdfId" />
			<table>
				<tr>
					<td>年份</td>
					<td><q:year id="mdfYear" /></td>
				</tr>
				<tr>
					<td>月份</td>
					<td><q:mon id="mdfMon" /></td>
				</tr>
				<tr>
					<td><q:button value="取消" id="mdfCancel" /></td>
					<td><q:submit value="确定" id="mdfSubmit" /></td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>