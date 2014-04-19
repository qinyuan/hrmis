package qinyuan.hrmis.domain.att.admin;

import java.sql.SQLException;
import java.util.List;
import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.date.Week;
import qinyuan.lib.web.html.Anchor;
import qinyuan.lib.web.html.TableRow;

public class SpecDate {
	private Week week;
	private String reason;

	public SpecDate() {
		week = new Week();
		reason = "";
	}

	public boolean add(List<String> dateList, String reason) {
		try (HRMISConn cnn = new HRMISConn()) {
			this.reason = reason;
			StringBuilder query = new StringBuilder(
					"INSERT INTO spec_date(dateval,specreason) VALUES");
			for (String date : dateList) {
				query.append("('" + date + "','" + reason + "'),");
			}
			if (query.charAt(query.length() - 1) == ',') {
				query.deleteCharAt(query.length() - 1);
				cnn.execute(query);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getTable() {
		try (HRMISConn cnn = new HRMISConn()) {
			// query special dates
			String query = "SELECT dt.dateval,sd.specreason FROM "
					+ "datelist AS dt LEFT JOIN spec_date AS sd USING(dateval) "
					+ "WHERE dt.dateval BETWEEN ? AND ? ORDER BY dt.dateval";
			cnn.prepare(query).setString(1, week.getLongDay(0))
					.setString(2, week.getLongDay(6)).execute();

			// fetch the query result
			StringBuilder o = new StringBuilder("<table>");
			o.append("<tr><th>日期</th><th>原因</th><th>操作</th></tr>");
			while (cnn.next()) {
				o.append(getTableRowByConn(cnn));
			}
			o.append("</table>");
			o.append("原因：<input type='text' name='reason' value='" + reason
					+ "' />");
			o.append("<input type='submit' name='addSubmit' value='添加' />");
			return o.toString();
		} catch (Exception e) {
			return "";
		}
	}

	public void nextWeek() {
		week.add(1);
	}

	public void previousWeek() {
		week.add(-1);
	}

	private static TableRow getTableRowByConn(HRMISConn cnn)
			throws SQLException {
		String date = cnn.getString(1);
		String reason = cnn.getString(2);

		TableRow tr = new TableRow();
		tr.add(date).add(getButton(date, reason));
		if (reason == null) {
			tr.add("");
		} else {
			tr.add(new Anchor().setText("删除").setHref(
					"att-spec-date.action?delDateval=" + date));
		}

		return tr;
	}

	private static String getButton(String date, String reason) {
		StringBuilder result = new StringBuilder();
		String mark = date.replace("-", "");
		result.append("<button type='button' id='btn_" + mark + "'");
		if (reason != null) {
			result.append(" disabled='disabled'>" + reason);
		} else {
			result.append(">");
		}
		result.append("</button>");
		return result.toString();
	}
}
