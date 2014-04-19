<%@page import="qinyuan.hrmis.domain.user.AttManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/cBody.jsp"%>
<%
	// check if the user has privilege to attendance management
	AttManager attManager = (AttManager) session
			.getAttribute("attManager");
%>
${attManager.rowNavi}