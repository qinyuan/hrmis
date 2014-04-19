<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>考核结果</title>
<%@include file="/cHead.jsp"%>
<q:css href="../ass-list" />
<q:css href="/lib/cForm" />
<q:js src="/lib/cTable" />
<q:js src="common" />
</head>
<body>
	<%@include file="common.jsp"%>
	<div id="print">
		<a href="download-excel.action?excelType=result" target="_blank">导出Excel</a>
		<a href="print-result.jsp" target="_blank">显示打印版</a>
	</div>
	${assDealer.scoresTableAsCheckee}
	${assDealer.valuesTableAsCheckee}
	<jsp:include page="info-panel.jsp">
		<jsp:param value="ass-result.txt" name="fileName" />
	</jsp:include>
</body>
</html>