<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出勤列表</title>
<%@include file="/cHead.jsp"%>
<q:css href="lib/spec-select-form" />
<q:css href="att-list" />
<q:js src="lib/spec-select-form" />
<q:js src="lib/date-emp-select-form" />
<q:js src="lib/dept-select-form" />
<q:js src="att-list" />
<q:js src="/lib/autoScroll" />
</head>
<body>
	<div class="head">
		<%@include file="common.jsp"%>
		<p class="head">
			部门：${attManager.deptSelect} 人员：${attManager.empSelectWithoutAll}
			时间：${attManager.startDateForm} —— ${attManager.endDateForm}
			<jsp:include page="lib/remaining-ann-leave.jsp" />
		</p>
		<table>
			<tr>
				<th class="badgenumber" rowspan="2">工号</th>
				<th class="name" rowspan="2">姓名</th>
				<th class="date" rowspan="2">日期</th>
				<th class="check" rowspan="2">签到</th>
				<th class="check" rowspan="2">签退</th>
				<th colspan="2">请假/公出</th>
				<th colspan="2">意外情况</th>
				<th rowspan="2">特殊日期</th>
				<th rowspan="2">特殊人员</th>
			</tr>
			<tr>
				<th>上班</th>
				<th>下班</th>
				<th>上班</th>
				<th>下班</th>
		</table>
	</div>
	<form id="mainForm" action="att-list.action" method="post">
		<div class="body">${attManager.attList}</div>
		<%@include file="lib/spec-select-form.jsp"%>
	</form>
</body>
</html>