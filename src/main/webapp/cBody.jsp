<%@page import="qinyuan.hrmis.domain.user.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	User visitor = (User) session.getAttribute("visitor");
	String pageToIndex = "/hrmis/user/returnIndex.jsp";
	if (visitor == null) {
		response.sendRedirect(pageToIndex);
		return;
	} else {
		out.print("<div class='greet'>" + visitor.getGreetWords()
				+ "</div>");
	}
%>