<%@page import="qinyuan.hrmis.lib.filter.RequestMonitor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据管理</title>
<%@include file="/cHead.jsp"%>
<q:css href="data" />
<q:js src="/lib/inputCurtain" />
<q:js src="data" />
<q:js src="/lib/showResult" />
</head>
<body>
	<%@include file="common.jsp"%>
	<%@include file="/lib/result.jsp"%>
	<div id="body">
		<q:button value="数据备份" id="backup" />
		<br />
		<q:button value="数据恢复" id="recover" />
		<div id="recoverInputForm">
			<s:form action="data" enctype="multipart/form-data" method="post">
				<p>
					请选择要恢复的rar数据备份文件：<br /> <input type="file" name="upload"
						id="upload" />
				</p>
				<p>
					<q:submit value="恢复" id="recoverSubmit" />
					<q:button value="取消" id="cancel" />
				</p>
			</s:form>
		</div>
	</div>
</body>
</html>