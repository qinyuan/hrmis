<%@page import="qinyuan.lib.web.WebFileIO"%>
<%@page import="qinyuan.lib.file.FileSearcher"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String applicant = request.getParameter("applicant");
	if (applicant == null) {
		return;
	}
	applicant = new String(applicant.getBytes("ISO-8859-1"), "UTF-8");

	WebFileIO io = new WebFileIO(application);
	String resumePath = io
			.readFirstLine("/WEB-INF/conf/resumePath.txt").trim();
	FileSearcher fs = new FileSearcher(resumePath);
	boolean firstItem = true;
	for (String fileName : fs.search(applicant)) {
		if (firstItem) {
			firstItem = false;
		} else {
			out.print(",");
		}
		out.print(fileName.substring(resumePath.length() + 1));
	}
%>