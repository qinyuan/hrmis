<%@page import="qinyuan.hrmis.domain.ass.AssDept"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>考核结果</title>
<%@include file="/cHead.jsp"%>
<q:css href="../ass-list" />
<q:css href="ass-item-list.css" />
<q:js src="/lib/inputCurtain" />
<q:js src="ass-item-list" />
<q:js src="/lib/cTable" />
<q:js src="/lib/cSelect" />
<q:js src="/lib/autoScroll" />
</head>
<body>
	<%@include file="common.jsp"%>
	<%
		if (assAdmin.getMonCount() == 0) {
			out.print("<h2>无数据</h2>");
			return;
		}
	%>
	<input type="hidden" id="monLocked" 
		value="<%=assAdmin.getMonLocked()%>" />
	<form action="ass-item-list.action" method="post">
		<p>
			<jsp:include page="dept-select-form.jsp" />
			&nbsp;&nbsp;月份选择：${assAdmin.monSelect}
			&nbsp;&nbsp;<input type="submit" value="删除" id="delSubmit" name="delSubmit" />
			&nbsp;&nbsp;<button type="button" id="addScoreButton">添加（分值）</button>
			&nbsp;&nbsp;<button type="button" id="addValueButton">添加（金额）</button>
		</p>
		${assAdmin.scoresTable}
		${assAdmin.valuesTable}
		<div id="score">
			<input type="hidden" id="sId" name="sId" />
			<table>
				<tr>
					<td>被考核部门</td>
					<td><%=AssDept.getSelect("sCheckee")%></td>
				</tr>
				<tr>
					<td>考核部门</td>
					<td><%=AssDept.getSelect("sChecker")%></td>
				</tr>
				<tr>
					<td>项目</td>
					<td><q:text id="sItem" /></td>
				</tr>
				<tr>
					<td>指标</td>
					<td><textarea name="sTarget" id="sTarget" rows="3" cols="80"></textarea></td>
				</tr>
				<tr>
					<td>分值</td>
					<td><q:text id="sWeight" /></td>
				</tr>
				<tr>
					<td>评分标准</td>
					<td><textarea name="sFormula" id="sFormula" rows="5" cols="80"></textarea></td>
				</tr>
				<tr>
					<td>数据</td>
					<td><textarea name="sData" id="sData" rows="5" cols="80"></textarea></td>
				</tr>
				<tr>
					<td>结果</td>
					<td><q:text id="sResult" /></td>
				</tr>
				<tr>
					<td><q:button value="取消" id="scoreCancel" /></td>
					<td>
						<q:submit id="scoreSubmit" value="修改" />
						<q:submit value="添加" id="scoreAddSubmit" />
					</td>
				</tr>
			</table>
		</div>
		<div id="value">
			<input type="hidden" id="vId" name="vId" />
			<table>
				<tr>
					<td>被考核部门</td>
					<td><%=AssDept.getSelect("vCheckee")%></td>
				</tr>
				<tr>
					<td>考核部门</td>
					<td><%=AssDept.getSelect("vChecker")%></td>
				</tr>
				<tr>
					<td>项目</td>
					<td><q:text id="vItem" /></td>
				</tr>
				<tr>
					<td>指标</td>
					<td><textarea name="vTarget" id="vTarget" rows="2" cols="80"></textarea></td>
				</tr>
				<tr>
					<td>单位</td>
					<td><q:text id="vUnit" /></td>
				</tr>
				<tr>
					<td>评分标准</td>
					<td><textarea name="vFormula" id="vFormula" rows="4" cols="80"></textarea></td>
				</tr>
				<tr>
					<td>备注</td>
					<td><textarea name="vOther" id="vOther" rows="4" cols="80"></textarea>
				</tr>
				<tr>
					<td>数据</td>
					<td><textarea name="vData" id="vData" rows="4" cols="80"></textarea></td>
				</tr>
				<tr>
					<td>结果</td>
					<td><q:text id="vResult" /></td>
				</tr>
				<tr>
					<td><q:button value="取消" id="valueCancel" /></td>
					<td>
						<q:submit value="修改" id="valueSubmit" />
						<q:submit value="添加" id="valueAddSubmit" />
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>