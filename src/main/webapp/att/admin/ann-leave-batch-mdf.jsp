<%@page import="qinyuan.hrmis.domain.att.admin.SimpleAnnLeaveFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>年休假设置</title>
<%@include file="/cHead.jsp"%>
<q:css href="/lib/cForm" />
<q:css href="ann-leave-batch-mdf" />
<q:js src="/lib/cTable" />
<q:js src="/lib/cSelect" />
<q:js src="/lib/inputCurtain" />
<q:js src="ann-leave-batch-mdf" />
<q:js src="/lib/autoScroll" />
</head>
<body>
	<%@include file="common.jsp"%>
	<form action="ann-leave-batch-mdf.action" method="post">
		<div id="control">
			<q:button value="筛选" id="filterShow" />
			<br /> <br />
			<q:button value="批量增加" id="addShow" />
			<br /> <br />
			<q:button value="批量减少" id="reduceShow" />
			<br /> <br />
			<q:button value="批量设值" id="setShow" />
		</div>
		${attAdmin.batchAnnLeaveTable}

		<div id="add">
			<p>请输入批量增加的天数：</p>
			<p>
				<q:text id="addValue" value="1" />
			</p>
			<p>
				<q:button value="取消" id="addCancel" />
				<q:submit value="确定" id="addSubmit" />
			</p>
		</div>
		<div id="reduce">
			<p>请输入批量减少的天数：</p>
			<p>
				<q:text id="reduceValue" value="1" />
			</p>
			<p>
				<q:button value="取消" id="reduceCancel" />
				<q:submit value="确定" id="reduceSubmit" />
			</p>
		</div>
		<div id="set">
			<p>请输入批量设置的天数值：</p>
			<p>
				<q:text id="setValue" value="10" />
			</p>
			<p>
				<q:button value="取消" id="setCancel" />
				<q:submit value="确定" id="setSubmit" />
			</p>
		</div>

		<div id="filter">
			${attAdmin.filterTable}
			<q:button value="取消" id="filterCancel" />
			<q:submit value="确定" id="filterSubmit" />
		</div>
	</form>
</html>