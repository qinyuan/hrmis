<%@page import="qinyuan.hrmis.domain.ass.AssDept"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="common.jsp"%>
<%
	String deptId = request.getParameter("deptId");
	if (deptId != null) {
		AssDept.delete(Integer.parseInt(deptId));
		response.sendRedirect("dept-list.jsp");
	}
%>