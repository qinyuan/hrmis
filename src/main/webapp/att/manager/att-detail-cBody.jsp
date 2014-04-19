<%@page import="qinyuan.lib.web.html.NumSelect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="header">
	<%@include file="common.jsp"%>
</div>
<%
	if (!attManager.isDeptSelected()) {
%>
<jsp:forward page="dept-select.jsp">
	<jsp:param value="att-detail.action" name="linkPage" />
</jsp:forward>
<%
	}
%>
<input type="hidden" id="startDate" value="${attManager.startDate}" />
<div id="date">
	<img src="img/date.gif">
	<div id="calendar"></div>
</div>
<div id="weekAndDept">
	<a href='att-detail.action?weekToAdd=-1'>上一周</a> <a
		href='att-detail.action?weekToAdd=1'>下一周</a>
	<%
		NumSelect next = new NumSelect(2, 20);
		next.setId("next");
		NumSelect previous = new NumSelect(2, 20);
		previous.setId("previous");
	%>
	<%=("前" + previous + "周 后" + next + "周")%>
</div>
<div id="dayback">${attManager.dateBackground}</div>
<div id="day">${attManager.dateRow}</div>
<div id="emp">${attManager.empTable}</div>
<div id="att">${attManager.attTable}</div>
<div id="search">
	部门：${attManager.deptSelect} 查找：<input type="text" name="searchText"
		id="searchText" />
</div>
<%@include file="lib/spec-select-form.jsp"%>
<div id="submitResult"></div>

<%@include file="lib/ann-leave-year.jsp"%>
<%
	if (year > 0) {
		out.print(attManager.getAnnLeaveHiddenInputs(year));
	}
%>