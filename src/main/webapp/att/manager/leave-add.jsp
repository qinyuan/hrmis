<%@page import="qinyuan.lib.web.html.Select"%>
<%@page import="qinyuan.hrmis.domain.att.manager.LeaveType"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加请假/公出/调休</title>
<%@include file="/cHead.jsp"%>
<q:css href="leave-add" />
<q:css href="/lib/cForm" />
<q:js src="lib/dept-select-form" />
<q:js src="lib/date-emp-select-form" />
<q:js src="leave-add" />
<q:js src="/lib/cTable" />
</head>
<body>
	<%@include file="common.jsp"%>
	<h2>添加请假/公出/调休</h2>
	<form action="leave-add.action" method="post">
		<p>部门：${attManager.deptSelect}</p>
		<p>人员：${attManager.empSelectWithoutAll}</p>
		<p>
			时间：<br />
			<q:text id="startDate" value="<%=attManager.getStartDate()%>" />
			${attManager.startTimeForm} 至<br />
			<q:text id="endDate" value="<%=attManager.getEndDate()%>" />
			${attManager.endTimeForm}
		</p>
		<p>
			原因：
			<%
			LeaveType[] lts = LeaveType.getInstances();
			Select select = new Select().setId("leave");
			for (LeaveType lt : lts) {
				select.addOption(lt.getId(), lt.getDesc());
			}
			out.print(select);
		%>
		</p>
		<p>
			<q:submit value="添加" id="addSpecItemSubmit" />
		</p>
	</form>
	<div class="result">${requestScope.result}</div>
	<jsp:include page="lib/remaining-ann-leave.jsp" />
</body>
</html>