<%@page import="qinyuan.lib.web.IdentifyCode"%>
<%@page contentType="image/jpeg"%>
<%@page language="java" pageEncoding="utf-8"%>
<%
	// this jsp file creates indentifying codes
	String str = IdentifyCode.getPicture(response);
	session.setAttribute("identCode", str);
	out.clear();
%>