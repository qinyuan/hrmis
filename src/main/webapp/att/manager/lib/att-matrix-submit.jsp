<%@page import="qinyuan.hrmis.domain.user.AttManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	// check if the user has privilege to attendance management
	AttManager attManager = (AttManager) session
			.getAttribute("attManager");
	if (attManager == null) {
		out.print("fail");
		return;
	}

	// get the submit data then validate it
	String submitData = request.getParameter("submitData");
	if (submitData == null || submitData.length() == 0) {
		out.print("success");
		return;
	} else {
		if (attManager.addSpecByAttMatrix(submitData)) {
			out.print("success");
		} else {
			out.print("fail");
		}
	}
%>