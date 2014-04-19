<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.OutputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String inName = (String) request.getAttribute("inName");
	String outName = (String) request.getAttribute("outName");
	if (inName == null || outName == null) {
		return;
	}

	response.reset();
	response.setContentType("application/x-download");
	response.addHeader("Content-Disposition", "attachment;filename="
			+ outName);

	OutputStream outp = null;
	FileInputStream in = null;
	outp = response.getOutputStream();
	in = new FileInputStream(inName);

	byte[] b = new byte[1024];
	int i = 0;

	while ((i = in.read(b)) > 0) {
		outp.write(b, 0, i);
	}
	outp.flush();
	out.clear();
	out = pageContext.pushBody();
	if (in != null) {
		in.close();
		in = null;
	}
%>