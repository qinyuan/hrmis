<%@page import="qinyuan.lib.web.IdentifyCode"%>
<%@page import="qinyuan.hrmis.domain.att.manager.AttInfoPanel"%>
<%@page import="qinyuan.hrmis.domain.user.AttManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>可用模块</title>
<%@include file="/cHead.jsp"%>
<q:css href="login" />
<q:js src="login"/>
</head>
<body>
	<%@include file="/cBody.jsp"%>
	<h3>可用模块</h3>
	<table>
		<%
			IdentifyCode.remoteBlackList(request.getRemoteAddr());

			int colIndex = 0;
			String[] attrNames = { "admin", "attAdmin", "attManager",
					"assAdmin", "assDealer", "recruitor", "rcrManager","manual" };
			String href, text;
			User user;
			for (String attrName : attrNames) {
				user = (User) session.getAttribute(attrName);
				if (user != null) {
					if (colIndex == 0) {
						out.print("<tr>");
					}
					out.print("<td>");
					user.setListNaviBean(attrName + "ListNavi");
					user.setRowNaviBean(attrName + "RowNavi");
					out.print(user.getListNavi());
					out.print("</td>");
					if (colIndex == 2) {
						out.print("</tr>");
						colIndex = -1;
					}
					colIndex++;
				}
			}
		%>
	</table>
	<%
		AttManager attManager = (AttManager) session
				.getAttribute("attManager");
		if (attManager != null) {
	%>
	<div id="info">
		<%=AttInfoPanel.getPanel()%>
	</div>
	<%
		}
	%>
</body>
</html>