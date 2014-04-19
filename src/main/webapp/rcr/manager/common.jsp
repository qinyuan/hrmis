<%@page import="qinyuan.hrmis.domain.user.RcrManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@include file="/cBody.jsp"%>
<%
RcrManager rcrManager = (RcrManager) session.getAttribute("rcrManager");
if (rcrManager == null) {
	response.sendRedirect(pageToIndex);
}
%>