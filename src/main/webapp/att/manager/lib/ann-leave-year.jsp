<%@page import="qinyuan.lib.lang.MyMath"%>
<%@page import="qinyuan.lib.web.WebFileIO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	int year = -1;
	WebFileIO io = new WebFileIO(application);
	String yearStr = io.readFirstLine("/WEB-INF/conf/annLeaveYear.txt");
	if (MyMath.isNumeric(yearStr)) {
		year = Integer.parseInt(yearStr);
	}
%>