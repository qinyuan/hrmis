<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>考核其他部门</title>
<%@include file="/cHead.jsp"%>
<q:css href="../ass-list" />
<q:css href="ass-other" />
<q:css href="/lib/cForm" />
<q:js src="common" />
<q:js src="/lib/cTable" />
<q:js src="/lib/inputCurtain" />
<q:js src="ass-other" />
<q:js src="/lib/autoScroll" />
</head>
<body>
	<%@include file="common.jsp"%>
	<div id="print">
		<a href="download-excel.action?excelType=other" target="_blank">导出Excel</a>
		<a href="print-other.jsp" target="_blank">显示打印版</a>
	</div>
	<input type="hidden" id="monLocked"
		value="<%=assDealer.getMonLocked()%>" />
	${assDealer.scoresTableAsChecker} ${assDealer.valuesTableAsChecker}
	<%
		String[] scoreHead = AssDealer.getHeadStrArr(true, true);
		String[] valueHead = AssDealer.getHeadStrArr(true, false);
	%>
	<form action="ass-other.action" method="post">
		<div id="scoreInput">
			<table>
				<%
					for (int i = 0; i < scoreHead.length; i++) {
						out.print("<tr><td class='mid'><b>" + scoreHead[i]
								+ "</b></td><td class='widest' id='si" + (i + 1)
								+ "'></td>");
					}
				%>
				<tr>
					<td colspan="2"><q:button value="取消" id="scoreClose" />
						&nbsp;&nbsp;&nbsp; <q:submit value="确定" id="assScoreSubmit" /></td>
				</tr>
			</table>
			<q:hidden value="" id="hidScoreId" />
		</div>
		<div id="valueInput">
			<table>
				<%
					for (int i = 0; i < valueHead.length; i++) {
						out.print("<tr><td class='mid'><b>" + valueHead[i]
								+ "</b></td><td class='widest' id='vi" + (i + 1)
								+ "'></td>");
					}
				%>
				<tr>
					<td colspan="2"><q:button value="取消" id="valueClose" />
						&nbsp;&nbsp;&nbsp; <q:submit value="确定" id="assValueSubmit" /></td>
				</tr>
			</table>
			<q:hidden value="" id="hidValueId" />
		</div>
	</form>
	<jsp:include page="info-panel.jsp">
		<jsp:param value="ass-other.txt" name="fileName" />
	</jsp:include>
</body>
</html>