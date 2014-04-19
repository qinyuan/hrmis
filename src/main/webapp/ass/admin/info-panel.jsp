<%@page import="qinyuan.lib.web.WebFileIO"%>
<%@page import="qinyuan.lib.file.MyFileIO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公告栏设置</title>
<%@include file="/cHead.jsp"%>
<q:css href="info-panel" />
<q:css href="/lib/cForm" />
</head>
<body>
	<%@include file="common.jsp"%>
	<%
		WebFileIO wf = new WebFileIO(application);
	%>
	<form action="info-panel.action" method="post">
		<table>
			<tr>
				<td>考核其他部门</td>
				<td><textarea name="otherInfo" rows="5" cols="80"><%=wf.readAll("/WEB-INF/info/ass-other.txt")%></textarea>
				</td>
			</tr>
			<tr>
				<td>考核结果</td>
				<td><textarea name="resultInfo" rows="5" cols="80"><%=wf.readAll("/WEB-INF/info/ass-result.txt")%></textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2"><q:submit id="infoSubmit" value="提交修改" /></td>
			</tr>
		</table>
	</form>
</body>
</html>