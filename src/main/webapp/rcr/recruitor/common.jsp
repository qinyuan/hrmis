<%@page import="qinyuan.hrmis.domain.user.Recruitor"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/cBody.jsp"%>
<%
	Recruitor recruitor = (Recruitor) session.getAttribute("recruitor");
	if (recruitor == null) {
		response.sendRedirect(pageToIndex);
	}
%>
${recruitor.rowNavi}
