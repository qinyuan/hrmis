package qinyuan.hrmis.domain.att.manager;

import java.util.List;
import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.web.html.Table;
import qinyuan.lib.web.html.TableRow;

public class AnnLeaveUtil {

	public static TableRow toTableRow(AnnLeave al, int year) {
		TableRow tr = new TableRow();

		int userId = al.getUserId();
		tr.add(al.getBadgenumber());
		tr.add("<a href='ann-leave-detail.jsp?userId=" + userId
				+ "' target='_blank'>" + al.getUsername() + "</a>");
		tr.add(adapt(al.getUsedDays()));
		tr.add(adapt(al.getUsabledDays()));
		tr.add(adapt(al.getRemainingDays()));
		tr.add(al.getWorkDate());
		tr.add(al.getJoinDate());
		tr.add(al.getInsurePlace());

		String joinDate = al.getJoinDate();
		if (joinDate == null) {
			tr.add("");
		} else {
			int joinYear = Integer.parseInt(joinDate.substring(0, 4));
			if (year - joinYear == 1
					&& (!joinDate.equals(getStartDate(year - 1)))) {
				tr.add(year + joinDate.substring(4) + "起享受年休假");
			} else {
				tr.add("");
			}
		}
		return tr;
	}

	private static String adapt(double num) {
		if (num == 0) {
			return "";
		} else {
			return String.valueOf(num);
		}
	}

	public final static TableRow head = new TableRow(true)
			.add("工号，姓名，已用(天)，可用(天)，剩余(天)，参加工作时间，进司时间，参保地，备注");

	public static String getAnnLeaveHiddenValues(int deptId, int year) {
		List<AnnLeave> list = AnnLeaveFactory.getAnnLeaveList(deptId, year);
		StringBuilder o = new StringBuilder();
		for (AnnLeave item : list) {
			o.append("<input type='hidden' id='al" + item.getUserId()
					+ "' value='" + item.getUsername() + ","
					+ item.getRemainingDays() + "' />");
		}
		return o.toString();
	}

	public static String getAnnLeaveHiddenValues2(int deptId, int year) {
		StringBuilder o = new StringBuilder();
		final String subQuery = "(SELECT * "
				+ "FROM spec_leave WHERE (specdate BETWEEN ? AND ?) "
				+ "AND leavetypeid=?)";
		final String query = "SELECT "
				+ "du.userId,du.username,l.starttime,l.endtime,a.total_leave "
				+ "FROM dept_user AS du "
				+ "LEFT JOIN annual_leave AS a USING(badgenumber) "
				+ "LEFT JOIN " + subQuery + " AS l USING(userId) "
				+ "WHERE deptId=? ORDER BY du.badgenumber";
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare(query).setString(1, getStartDate(year))
					.setString(2, getEndDate(year))
					.setInt(3, AnnLeaveFactory.ANN_LEAVE_TYPE_ID)
					.setInt(4, deptId).execute();

			int userId = -1;
			String username = null;
			double usabledDays = 0;
			while (cnn.next()) {
				if (userId != cnn.getInt(1)) {
					if (userId > 0) {
						o.append("<input type='hidden' id='al" + userId
								+ "' value='" + username + "," + usabledDays
								+ "' />");
					}
					userId = cnn.getInt(1);
					username = cnn.getString(2);
					usabledDays = cnn.getDouble(5);
				}
				usabledDays -= AnnLeaveFactory.getDayCount(cnn.getString(3),
						cnn.getString(4));
			}
			if (userId > 0) {
				o.append("<input type='hidden' id='al" + userId + "' value='"
						+ username + "," + usabledDays + "' />");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o.toString();
	}

	public static double getRemainAnnLeave(int userId, int year) {
		try (HRMISConn cnn = new HRMISConn()) {
			String query = "SELECT TIMESTAMPDIFF(MINUTE,"
					+ "CONCAT(specdate,' ',starttime),CONCAT(specdate,' ',endtime)) "
					+ "FROM spec_leave "
					+ "WHERE (specdate BETWEEN ? AND ?) AND leavetypeid=? AND userid=?";
			cnn.prepare(query).setString(1, getStartDate(year))
					.setString(2, getEndDate(year))
					.setInt(3, AnnLeaveFactory.ANN_LEAVE_TYPE_ID);
			double used = 0;
			cnn.setInt(4, userId).execute();
			while (cnn.next()) {
				int minuteDiff = cnn.getInt(1);
				used += (minuteDiff > 480 ? 1 : minuteDiff / 480.0);
			}

			query = "SELECT total_leave FROM annual_leave "
					+ "WHERE badgenumber IN "
					+ "(SELECT badgenumber FROM userinfo WHERE userid=?)";
			cnn.prepare(query).setInt(1, userId).execute();
			if (cnn.next()) {
				return cnn.getInt(1) - used;
			} else {
				return 0 - used;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -9999;
		}
	}

	public static Table getTable(int deptId, int year) {
		Table table = new Table();
		table.add(head);
		List<AnnLeave> list = AnnLeaveFactory.getAnnLeaveList(deptId, year);
		for (AnnLeave item : list) {
			table.add(toTableRow(item, year));
		}
		return table;
	}

	public static String getStartDate(int year) {
		return year + "-01-01";
	}

	public static String getEndDate(int year) {
		return year + "-12-31";
	}
}