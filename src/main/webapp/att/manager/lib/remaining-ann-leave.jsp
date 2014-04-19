<%@page import="qinyuan.hrmis.domain.user.AttManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="ann-leave-year.jsp"%>
<%
	AttManager attManager = (AttManager) session
			.getAttribute("attManager");
	if (year > 0) {
		out.print(attManager.getAnnLeave(year));
	}
%>