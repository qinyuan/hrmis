<%@page import="qinyuan.hrmis.domain.user.AttManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/cHead.jsp"%>
<q:css href="att-detail" />
<q:css href="lib/spec-select-form" />
<q:js src="att-detail" />
<q:js src="att-detail-button-select" />
<q:js src="att-detail-date-select" />
<q:js src="att-detail-ann-leave" />
<q:js src="lib/dept-select-form" />
<q:js src="lib/spec-select-form" />
<q:js src="/lib/autoScroll" />
<%
	AttManager localAttMng = (AttManager) session
			.getAttribute("attManager");
	if (localAttMng.getStartDate().equals(localAttMng.getEndDate())) {
		localAttMng.setAttDate(localAttMng.getStartDate());
	}
%>