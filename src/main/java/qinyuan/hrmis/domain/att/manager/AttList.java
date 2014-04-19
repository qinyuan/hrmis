package qinyuan.hrmis.domain.att.manager;

import java.sql.SQLException;
import qinyuan.hrmis.domain.att.emp.Employee;
import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.date.DatePeriod;

public class AttList {

	public static String getList(int userId, DatePeriod period) {
		if (userId <= 0) {
			return "";
		}
		Employee emp = Employee.newInstance(userId);
		if (emp == null) {
			return "";
		}

		try (HRMISConn cnn = new HRMISConn()) {
			// execute query
			String query = getQuery(emp.getId(), period.getStartDate(),
					period.getEndDate());
			cnn.execute(query);

			StringBuilder output = new StringBuilder("<table>");

			// add info to table object
			while (cnn.next()) {
				AttItem attItem = createAttItem(emp, cnn);
				output.append("<tr>");
				output.append("<td class='badgenumber'>" + emp.getBadgenumber()
						+ "</td>");
				output.append("<td class='name'>" + emp.getName() + "</td>");
				output.append("<td class='date'>" + attItem.getShortDate()
						+ "</td>");
				output.append("<td class='check'>" + getReachButton(attItem)
						+ "</td>");
				output.append("<td class='check'>" + getLeaveButton(attItem)
						+ "</td>");
				for (int i = 4; i <= 9; i++) {
					output.append("<td>" + cnn.getString(i) + "</td>");
				}
				output.append("</tr>");
			}
			output.append("</table>");
			return output.toString().replace("null", "");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private static AttItem createAttItem(Employee emp, HRMISConn cnn)
			throws Exception {
		String date = cnn.getString(1);
		String reachTime = cnn.getString(2);
		String leaveTime = cnn.getString(3);
		String reachSpec = cnn.getString(4);
		String leaveSpec = cnn.getString(5);
		String reachAccd = cnn.getString(6);
		String leaveAccd = cnn.getString(7);
		String specEmp = cnn.getString(8);
		String specDate = cnn.getString(9);
		AttItem attItem = new AttItem(emp, date, reachTime, leaveTime,
				reachSpec, leaveSpec, reachAccd, leaveAccd, specEmp, specDate);
		return attItem;
	}

	private static String getLeaveButton(AttItem attItem) {
		int status = attItem.getLeaveStatus();
		return getButton(attItem, status, attItem.getLeaveTime(), "L");
	}

	private static String getReachButton(AttItem attItem) {
		int status = attItem.getReachStatus();
		return getButton(attItem, status, attItem.getReachTime(), "R");
	}

	private static String getButton(AttItem attItem, int status, String text,
			String reachOrLeave) {

		String out = "<button type='button'";
		if (status == AttItem.RED) {
			out += "class='red' ";
		} else if (status == AttItem.PINK) {
			out += "class='pink' ";
		} else if (status == AttItem.SPE_NOR) {
			out += "class='nor' disabled='disabled' ";
		} else {
			out += "class='nor' ";
		}
		out += "id='" + reachOrLeave + "_" + attItem.getUserid() + "_"
				+ attItem.getLongDate() + "' ";
		out += ">";
		out += text;
		out += "</button>";

		return out;
	}

	private static String getQuery(int userId, String startDate, String endDate) {
		return "SELECT dl.dateval,ch.reachtime,ch.leavetime,"
				+ "rlt.leavetype,llt.leavetype,rat.accdtype,lat.accdtype,"
				+ "sd.specreason,se.specreason FROM datelist AS dl "
				+ "LEFT JOIN checkbydate AS ch ON (ch.userId="
				+ userId
				+ " AND dl.dateval=ch.checkdate) "
				+ "LEFT JOIN spec_leave AS rsl ON "
				+ "(rsl.userId="
				+ userId
				+ " AND dl.dateval=rsl.specdate "
				+ "AND rsl.starttime<'12:00:00') "
				+ "LEFT JOIN leavetype AS rlt ON rsl.leavetypeid=rlt.leavetypeid "
				+ "LEFT JOIN spec_leave AS lsl ON "
				+ "(lsl.userId="
				+ userId
				+ " AND dl.dateval=lsl.specdate "
				+ "AND lsl.endtime>'12:00:00') "
				+ "LEFT JOIN leavetype AS llt ON lsl.leavetypeid=llt.leavetypeid "
				+ "LEFT JOIN spec_accd AS rsa ON "
				+ "(rsa.userId="
				+ userId
				+ " AND dl.dateval=rsa.accddate "
				+ "AND rsa.isreach=TRUE) "
				+ "LEFT JOIN accdtype AS rat ON rsa.accdtypeid=rat.accdtypeid "
				+ "LEFT JOIN spec_accd AS lsa ON "
				+ "(lsa.userId="
				+ userId
				+ " AND dl.dateval=lsa.accddate "
				+ "AND lsa.isreach=FALSE) "
				+ "LEFT JOIN accdtype AS lat ON lsa.accdtypeid=lat.accdtypeid "
				+ "LEFT JOIN spec_date AS sd ON (dl.dateval=sd.dateval) "
				+ "LEFT JOIN spec_emp AS se ON (se.userId="
				+ userId
				+ ") WHERE dl.dateval BETWEEN '"
				+ startDate
				+ "' AND '"
				+ endDate + "'";
	}

	public static boolean getLeaveSpec(HRMISConn cnn) throws SQLException {
		int[] lIndex = { 5, 7, 8, 9 };
		for (int i : lIndex) {
			if (cnn.getString(i) != null) {
				return true;
			}
		}
		return false;
	}

	public static boolean getReachSpec(HRMISConn cnn) throws SQLException {
		int[] rIndex = { 4, 6, 8, 9 };
		for (int i : rIndex) {
			if (cnn.getString(i) != null) {
				return true;
			}
		}
		return false;
	}
}