<%@page import="java.io.File"%>
<%@page import="qinyuan.lib.web.WebFileIO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据管理</title>
<%@include file="/cHead.jsp"%>
<q:css href="att-data" />
<q:js src="att-data" />
</head>
<body>
	<%@include file="common.jsp"%>
	<%
		WebFileIO io = new WebFileIO(application);
		String accessPath = io
				.readFirstLine("/WEB-INF/conf/accessPath.txt");
	%>
	<div id="body">
		<p>
			Access文件路径: <input type="text" id="accessPath"
				value="<%=accessPath%>" disabled="disabled" title="<%=accessPath%>" />
			<q:button value="修改" id="mdfAccessPath" />
			${mdfResult}
		</p>
		<form action="att-data.action" method="post">
			<p>
				<q:submit value="导入Access数据" id="importAccessData" />
				${impResult}
			</p>
		</form>
	</div>
</body>
</html>