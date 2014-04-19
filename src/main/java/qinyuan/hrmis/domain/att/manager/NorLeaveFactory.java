package qinyuan.hrmis.domain.att.manager;

import java.util.ArrayList;
import java.util.List;

import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.date.MyTime;

public class NorLeaveFactory {

	private static String getSqlQuery(int deptId, String startDate,
			String endDate) {
		String query = "SELECT "
				+ "userId,badgenumber,username,starttime,endtime,"
				+ "leavetypeid FROM spec_leave AS l INNER JOIN "
				+ "dept_user AS du USING(userId) "
				+ "LEFT JOIN leavetype USING(leavetypeid) "
				+ "	WHERE (specdate BETWEEN '" + startDate + "' AND '"
				+ endDate + "') AND isnorm=TRUE AND du.deptId=" + deptId
				+ " ORDER BY du.badgenumber";
		return query;
	}

	public static List<MergedLeaveItem> getDetailItems(int deptId, int userId,
			String startDate, String endDate) {
		LeaveItemFactory lif = new LeaveItemFactory(deptId, startDate, endDate);
		lif.setUserId(userId).setBoardType(LeaveItemFactory.NOR);
		return lif.getMergedLeaveItem(deptId);
	}

	public static List<NorLeave> getNorLeaves(int deptId, String startDate,
			String endDate) {
		List<NorLeave> list = new ArrayList<NorLeave>();
		NorLeave nl = null;
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.execute(getSqlQuery(deptId, startDate, endDate));
			while (cnn.next()) {
				if (nl == null || nl.getUserId() != cnn.getInt(1)) {
					nl = new NorLeave();
					nl.setUserId(cnn.getInt(1));
					nl.setBadgenumber(cnn.getString(2));
					nl.setUsername(cnn.getString(3));
					list.add(nl);
				}
				nl.putDayCount(cnn.getInt(6),
						getDayCount(cnn.getString(4), cnn.getString(5)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	private static double getDayCount(String startTime, String endTime) {
		double hourDiff = MyTime.hourDiff(startTime, endTime);
		if (hourDiff > 8) {
			return 1;
		} else {
			return hourDiff / 8;
		}
	}

	public static void main(String[] args) {
		String startDate = "2013-01-01";
		String endDate = "2013-12-31";
		System.out.println(getNorLeaves(51, startDate, endDate).size());
	}
}
