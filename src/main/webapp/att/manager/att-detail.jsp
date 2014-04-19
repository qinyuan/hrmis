<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="common.jsp"%>
<%
	if (attManager.getNarrow()) {
		response.sendRedirect("att-detail-narrow.jsp");
	} else {
		response.sendRedirect("att-detail-width.jsp");
	}
%>