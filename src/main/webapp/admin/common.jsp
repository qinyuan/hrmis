<%@page import="qinyuan.hrmis.domain.user.Admin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/cBody.jsp"%>
<%
	Admin admin = (Admin) session.getAttribute("admin");
	if (admin == null) {
		response.sendRedirect(pageToIndex);
		return;
	}
%>
<%=admin.getRowNavi()%>