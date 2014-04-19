package qinyuan.hrmis.domain.att.manager;

import java.sql.SQLException;
import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.date.MyTimeRecord;
import qinyuan.lib.date.Week;

public class AttMatrix {

	private Week week = new Week();

	public String getAttTable(int deptId) {
		// create a temporary table, which is named tmp_date, to hold the date
		// data
		StringBuilder result = new StringBuilder();
		try (HRMISConn cnn = new HRMISConn()) {
			String query = "SELECT te.userId,dl.dateval,ch.reachtime,ch.leavetime,"
					+ "rlt.leavetype,llt.leavetype,rat.accdtype,lat.accdtype,"
					+ "sd.specreason,se.specreason "
					+ "FROM (SELECT * FROM dept_user WHERE deptId=?)"
					+ " AS te CROSS JOIN "
					+ "datelist AS dl "
					+ "LEFT JOIN checkbydate AS ch ON "
					+ "(te.userId=ch.userId AND dl.dateval=ch.checkdate) "
					+ "LEFT JOIN spec_leave AS rsl ON "
					+ "(te.userId=rsl.userId AND dl.dateval=rsl.specdate "
					+ "AND rsl.starttime<'12:00:00') "
					+ "LEFT JOIN leavetype AS rlt ON rsl.leavetypeid=rlt.leavetypeid "
					+ "LEFT JOIN spec_leave AS lsl ON "
					+ "(te.userId=lsl.userId AND dl.dateval=lsl.specdate "
					+ "AND lsl.endtime>'12:00:00') "
					+ "LEFT JOIN leavetype AS llt ON lsl.leavetypeid=llt.leavetypeid "
					+ "LEFT JOIN spec_accd AS rsa ON "
					+ "(te.userId=rsa.userId AND dl.dateval=rsa.accddate "
					+ "AND rsa.isreach=TRUE) "
					+ "LEFT JOIN accdtype AS rat ON rsa.accdtypeid=rat.accdtypeid "
					+ "LEFT JOIN spec_accd AS lsa ON "
					+ "(te.userId=lsa.userId AND dl.dateval=lsa.accddate "
					+ "AND lsa.isreach=FALSE) "
					+ "LEFT JOIN accdtype AS lat ON lsa.accdtypeid=lat.accdtypeid "
					+ "LEFT JOIN spec_date AS sd ON (dl.dateval=sd.dateval) "
					+ "LEFT JOIN spec_emp AS se ON (te.userId=se.userId) "
					+ "WHERE dl.dateval BETWEEN ? AND ? "
					+ "ORDER BY te.badgenumber,dl.dateval ";
			cnn.prepare(query).setInt(1, deptId)
					.setString(2, week.getLongDay(0))
					.setString(3, week.getLongDay(6)).execute();

			result = new StringBuilder("<table id='attT'>");
			int dayOfWeek = 0;
			while (cnn.next()) {
				// begin a new row at Sunday
				if (dayOfWeek == 0) {
					result.append("<tr>");
				}

				result.append(getButtonByConnect(cnn, dayOfWeek));

				// end a row at Saturday
				dayOfWeek++;
				if (dayOfWeek == 7) {
					dayOfWeek = 0;
					result.append("</tr>");
				}
			}

			result.append("</table>");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result.toString();
	}

	public String getDateBackground() {
		StringBuilder result = new StringBuilder("<table><tr class='day'>");

		for (int i = 0; i < 7; i++) {
			result.append("<td class='dayback' id='r" + i + "'></td>");
			result.append("<td class='dayback' id='l" + i + "'></td>");
		}

		result.append("</tr></table>");
		return result.toString();
	}

	public String getDateRow(boolean narrow) {
		String[] dateStrs = narrow ? week.getShortDays() : week.getLongDays();

		StringBuilder o = new StringBuilder();
		o.append("<table><tr>");
		for (int i = 0; i < dateStrs.length; i++) {
			o.append("<td class='day'>" + dateStrs[i] + " "
					+ Week.getWeekName(i) + "</td>");
		}
		o.append("</tr></table>");

		return o.toString();
	}

	public String getEmpTable(int deptId) {
		StringBuilder result = new StringBuilder();

		String query = "SELECT userId,badgenumber,username FROM dept_user "
				+ "WHERE deptId=? ORDER BY badgenumber";
		try (HRMISConn cnn = new HRMISConn()) {
			result = new StringBuilder("<table id='empT'>");
			cnn.prepare(query).setInt(1, deptId).execute();

			while (cnn.next()) {
				String userId = cnn.getString(1);
				String badgenumber = cnn.getString(2);
				String username = cnn.getString(3);
				result.append("<tr class='emp' id='E" + userId + "'>");
				result.append("<td class='empId'>" + badgenumber + "</td>");
				result.append("<td class='name'>" + username + "</td>");
				result.append("</tr>");
			}
			result.append("</table>");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result.toString();
	}

	public Week getWeek() {
		return week;
	}

	public void setWeek(Week week) {
		this.week = week;
	}

	public void setWeek(int weeksToAdd) {
		week.add(weeksToAdd);
	}

	private static AttStatus getAttStatus(String reachtime, String leavetime,
			String reachSpec, String leaveSpec) {
		AttStatus status = new AttStatus();

		// if there is special case in reach
		if (notEmpty(reachSpec)) {
			status.setReachStatus(AttStatus.NORMAL);
			if (notEmpty(leaveSpec)) {
				status.setLeaveStatus(AttStatus.NORMAL);
				return status;
			} else {
				status.setLeaveStatus(getStatusOfNormalLeave(leavetime));
				return status;
			}
		}

		// if there is special case in leave
		if (notEmpty(leaveSpec)) {
			status.setLeaveStatus(AttStatus.NORMAL);
			status.setReachStatus(getStatusOfNormalReach(reachtime));
			return status;
		}

		if (reachtime == null || leavetime == null) {
			status.setReachStatus(getStatusOfNormalReach(reachtime));
			status.setLeaveStatus(getStatusOfNormalLeave(leavetime));
			return status;
		}

		if (getHourDiff(reachtime, leavetime) >= 8) {
			status.setReachStatus(AttStatus.NORMAL);
			status.setLeaveStatus(AttStatus.NORMAL);
			return status;
		}

		if (leavetime.compareTo("20:00:00") >= 0) {
			status.setReachStatus(getStatusOfMiddleReach(reachtime));
			status.setLeaveStatus(getStatusOfMiddleLeave(leavetime));
			return status;
		} else {
			status.setReachStatus(getStatusOfNormalReach(reachtime));
			status.setLeaveStatus(getStatusOfNormalLeave(leavetime));
			return status;
		}
	}

	private static String getButtonByConnect(final HRMISConn cnn, int dayOfWeek)
			throws SQLException {
		StringBuilder result = new StringBuilder();

		// record user/date information
		String userId = cnn.getString(1);
		String reMark = "R" + userId + "_" + dayOfWeek;
		String leMark = "L" + userId + "_" + dayOfWeek;

		// record attendance information
		String reachtime = cnn.getString(3);
		String leavetime = cnn.getString(4);

		// record special case information
		String rLeave = cnn.getString(5);
		String lLeave = cnn.getString(6);
		String rAccd = cnn.getString(7);
		String lAccd = cnn.getString(8);

		// set normal special situation
		String reachNormalSpec = getNormalSpec(rLeave, rAccd);
		String leaveNormalSpec = getNormalSpec(lLeave, lAccd);

		// record special employee and day information
		String specDay = cnn.getString(9);
		String specEmp = cnn.getString(10);

		// set other special situation
		String otherSpec = getOtherSpec(specDay, specEmp);

		// set the attendance status
		AttStatus status = getAttStatus(reachtime, leavetime, reachNormalSpec,
				leaveNormalSpec);

		result.append(getButtonByText(reMark, reachtime, reachNormalSpec,
				otherSpec, status.getReachStatus()));
		result.append(getButtonByText(leMark, leavetime, leaveNormalSpec,
				otherSpec, status.getLeaveStatus()));

		return result.toString();
	}

	private static String getButtonByText(String attMark, String time,
			String normalSpec, String otherSpec, int attStatus) {
		StringBuilder result = new StringBuilder();

		// begin the button
		String totalSpec = getTotalSpec(normalSpec, otherSpec, attStatus);
		result.append("<td class='att'><button type='button' class='"
				+ getButtonClassStyle(attStatus, otherSpec) + "' id='B"
				+ attMark + "'");
		if (totalSpec != null && totalSpec.length() > 0) {
			result.append(" title='" + totalSpec + "'");
		}
		if (normalSpec.length() > 0) {
			result.append(" disabled='disabled'");
		}
		result.append(">");

		// set the text of the button
		if (time != null) {
			result.append(time.substring(0, 5));
		}
		result.append(totalSpec);

		// end the button
		result.append("</button></td>");

		return result.toString();
	}

	private static String getButtonClassStyle(int attStatus, String otherSpec) {
		if (otherSpec != null && otherSpec.length() > 0) {
			return "nor";
		} else {
			switch (attStatus) {
			case AttStatus.NORMAL:
				return "nor";
			case AttStatus.PINK:
				return "pink";
			default:
				return "red";
			}
		}
	}

	private static double getHourDiff(String startTime, String endTime) {
		String[] starts = startTime.split(":");
		String[] ends = endTime.split(":");
		int startHour = Integer.parseInt(starts[0]);
		int startMin = Integer.parseInt(starts[1]);
		int startSec = Integer.parseInt(starts[2]);

		int endHour = Integer.parseInt(ends[0]);
		int endMin = Integer.parseInt(ends[1]);
		int endSec = Integer.parseInt(ends[2]);

		return (endHour - startHour + (endMin - startMin) / 60.0 + (endSec - startSec) / 3600.0);
	}

	/**
	 * this method combine the information of leave, accident, special day,
	 * special employee then return a string to summarize the special case
	 * 
	 * @param leave
	 * @param accd
	 * @param specDay
	 * @param specEmp
	 * @return
	 */
	private static String getNormalSpec(String leave, String accd) {
		StringBuilder result = new StringBuilder();
		if (leave != null) {
			result.append(leave);
		}
		if (accd != null) {
			result.append(accd);
		}
		return result.toString();
	}

	private static String getOtherSpec(String specDay, String specEmp) {
		StringBuilder result = new StringBuilder();
		if (specDay != null) {
			result.append(specDay);
		} else if (specEmp != null) {
			result.append(specEmp);
		}
		return result.toString();
	}

	private static int getStatusOfNormalLeave(String leavetime) {
		return getStatusOfLeave(leavetime, "15:30:00", "15:55:00");
	}

	private static int getStatusOfNormalReach(String reachtime) {
		return getStatusOfReach(reachtime, "08:05:00", "08:30:00");
	}

	private static int getStatusOfMiddleLeave(String leavetime) {
		return getStatusOfLeave(leavetime, "22:30:00", "22:55:00");
	}

	private static int getStatusOfMiddleReach(String reachtime) {
		return getStatusOfReach(reachtime, "16:05:00", "16:30:00");
	}

	private static int getStatusOfLeave(String leavetime, String minTime,
			String norTime) {
		if (leavetime == null || leavetime.compareTo(minTime) < 0) {
			return AttStatus.RED;
		} else if (leavetime.compareTo(minTime) >= 0
				&& leavetime.compareTo(norTime) < 0) {
			return AttStatus.PINK;
		} else {
			return AttStatus.NORMAL;
		}
	}

	private static int getStatusOfReach(String reachtime, String norTime,
			String maxTime) {
		if (reachtime == null || reachtime.compareTo(maxTime) > 0) {
			return AttStatus.RED;
		} else if (reachtime.compareTo(norTime) > 0
				&& reachtime.compareTo(maxTime) <= 0) {
			return AttStatus.PINK;
		} else {
			return AttStatus.NORMAL;
		}
	}

	private static String getTotalSpec(String normalSpec, String otherSpec,
			int attStatus) {
		if (attStatus == AttStatus.NORMAL) {
			return (normalSpec == null ? "" : normalSpec);
		} else {
			return (otherSpec == null ? "" : otherSpec);
		}
	}

	private static boolean notEmpty(String str) {
		return (str != null && str.length() > 0);
	}

	public static void main(String[] args) {
		MyTimeRecord record = new MyTimeRecord();
		record.print("attMatrix created");
	}
}
