<%@page import="qinyuan.hrmis.domain.user.AssDealer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/cBody.jsp"%>
<%
	AssDealer assDealer = (AssDealer) session.getAttribute("assDealer");
%>
${assDealer.rowNavi}
<%
	if (assDealer.getDeptCount() == 0) {
		out.print("<h3>部门未设置</h3>");
		return;
	}else if(assDealer.getMonCount()==0){
		out.print("<h3>无数据</h3>");
		return;
	}
%>
<p>
	部门： ${assDealer.deptSelect}
	月份：${assDealer.monSelect}
</p>