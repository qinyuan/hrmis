<%@page import="qinyuan.hrmis.domain.user.AssAdmin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/cBody.jsp"%>
<%
	AssAdmin assAdmin = (AssAdmin) session.getAttribute("assAdmin");
	if (assAdmin == null) {
		response.sendRedirect(pageToIndex);
	}
%>
<%=assAdmin.getRowNavi()%>