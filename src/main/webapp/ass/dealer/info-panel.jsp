<%@page import="qinyuan.lib.web.WebFileIO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String fileName = request.getParameter("fileName");
	if (fileName == null) {
		return;
	}
%>
<div id="info">
	<fieldset>
		<legend>温馨提示：</legend>
		<%
			WebFileIO reader = new WebFileIO(application);
			String[] infos = reader.readAsStrArr("/WEB-INF/info/" + fileName);
			out.print("<ul>");
			for (String info : infos) {
				out.print("<li>" + info + "</li>");
			}
			out.print("</ul>");
		%>
	</fieldset>
</div>