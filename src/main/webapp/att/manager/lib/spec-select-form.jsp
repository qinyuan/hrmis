<%@page import="qinyuan.hrmis.domain.att.manager.AccdType"%>
<%@page import="qinyuan.hrmis.domain.att.manager.LeaveType"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="norLeaveMenu">
	<img src="img/norLeave.png" alt="请假" width="70" height="40" />
	<div class="subMenu">
		<%
			LeaveType[] lts = LeaveType.getNorInstances();
			for (LeaveType lt : lts) {
				out.print("<input type='submit' id='L" + lt.getId()
						+ "' value='" + lt.getDesc() + "'><br />");
			}
		%>
	</div>
</div>
<div id="othLeaveMenu">
	<img src="img/othLeave.png" alt="公出" width="70" height="40" />
	<div class="subMenu">
		<%
			lts = LeaveType.getOthInstances();
			for (LeaveType lt : lts) {
				out.print("<input type='submit' id='L" + lt.getId()
						+ "' value='" + lt.getDesc() + "'><br />");
			}
		%>
	</div>
</div>
<div id="accdMenu">
	<img src="img/accd.png" alt="特殊情况" width="70" height="40" />
	<div class="subMenu">
		<%
			AccdType[] ats = AccdType.getInstances();
			for (AccdType at : ats) {
				out.print("<input type='submit' id='A" + at.getId()
						+ "' value='" + at.getDesc() + "'><br />");
			}
		%>
	</div>
</div>