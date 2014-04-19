<%@page import="qinyuan.hrmis.domain.user.AssDealer"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>考核负责人</title>
<%@include file="/cHead.jsp"%>
<q:css href="ass-dealer" />
<q:js src="ass-dealer" />
</head>
<body>
	<%@include file="common.jsp"%>
	<form action="ass-dealer.action" method="post">
		<%
			AssDealer[] assDealers = AssDealer.getAssDealers();
			for (AssDealer dealer : assDealers) {
				out.print(dealer.getDeptCheckBoxes());
			}
		%>
		<div id="submit">
			<q:submit value="提交修改" id="assDealerSubmit" style="common" />
		</div>
	</form>
</body>
</html>