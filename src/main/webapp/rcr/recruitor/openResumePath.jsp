<%@page import="qinyuan.lib.file.FileUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String remoteAddr = request.getRemoteAddr();
	if (remoteAddr.equals("127.0.0.1")
			|| remoteAddr.equals("192.168.9.42")) {
		try {
			FileUtil.show("d:/hrmis/htdocs/resume");
		} catch (Exception e) {
			e.printStackTrace();
			out.print("打开文件d:/hrmis/htdocs/resume失败!");
		}
	} else {
		out.print("只有在服务器所在电脑上操作是时，本功能才有效！");
	}
%>