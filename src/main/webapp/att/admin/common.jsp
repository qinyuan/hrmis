<%@page import="qinyuan.hrmis.domain.user.AttAdmin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/cBody.jsp"%>
<%
	AttAdmin attAdmin = (AttAdmin) session.getAttribute("attAdmin");
%>
${attAdmin.rowNavi}
