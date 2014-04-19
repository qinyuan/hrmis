<%@page import="qinyuan.lib.web.IdentifyCode"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	IdentifyCode.addBlackList(request.getRemoteAddr());
	System.out.println("login-error: " + request.getRemoteAddr());
	if (request.getAttribute("identCodeError") == null) {
		response.sendRedirect("../index.jsp?error=true");
	} else {
		response.sendRedirect("../index.jsp?identCodeError=true");
	}
%>