package qinyuan.hrmis.domain.att.manager;

import java.util.ArrayList;
import java.util.List;
import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.date.MyTime;

public class AnnLeaveFactory {

	private final static int WORK_HOUR = 8;
	public final static int ANN_LEAVE_TYPE_ID = 10;

	private static AnnLeave createAnnLeaveByConn(HRMISConn cnn)
			throws Exception {
		AnnLeave al = new AnnLeave();
		al.setUserId(cnn.getInt(1));
		al.setBadgenumber(cnn.getString(2));
		al.setUsername(cnn.getString(3));
		al.setWorkDate(cnn.getString(6));
		al.setJoinDate(cnn.getString(7));
		al.setInsurePlace(cnn.getString(8));
		if (cnn.getString(9) != null)
			al.setUsabledDays(cnn.getInt(9));
		return al;
	}

	static double getDayCount(String startTime, String endTime) {
		if (startTime == null || endTime == null) {
			return 0;
		}

		double hourCount = MyTime.hourDiff(startTime, endTime);
		if (hourCount > WORK_HOUR) {
			return 1;
		} else {
			return hourCount / WORK_HOUR;
		}
	}

	public static List<AnnLeave> getAnnLeaveList(int deptId, int year) {
		List<AnnLeave> list = new ArrayList<AnnLeave>();
		AnnLeave lastItem = null;

		final String startDate = AnnLeaveUtil.getStartDate(year);
		final String endDate = AnnLeaveUtil.getEndDate(year);

		final String subQuery = "(SELECT * "
				+ "FROM spec_leave WHERE (specdate BETWEEN ? AND ?) "
				+ "AND leavetypeid=?)";
		final String query = "SELECT "
				+ "du.userId,du.badgenumber,du.username,l.starttime,l.endtime,"
				+ "a.workdate,a.joindate,a.insure_place,a.total_leave "
				+ "FROM dept_user AS du "
				+ "LEFT JOIN annual_leave AS a USING(badgenumber) "
				+ "LEFT JOIN " + subQuery + " AS l USING(userId) "
				+ "WHERE deptId=? ORDER BY du.badgenumber";
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare(query).setString(1, startDate).setString(2, endDate)
					.setInt(3, ANN_LEAVE_TYPE_ID).setInt(4, deptId).execute();
			while (cnn.next()) {
				if (lastItem == null || lastItem.getUserId() != cnn.getInt(1)) {
					lastItem = createAnnLeaveByConn(cnn);
					list.add(lastItem);
				}
				lastItem.addUsedDays(getDayCount(cnn.getString(4),
						cnn.getString(5)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<MergedLeaveItem> getDetailItems(int deptId, int userId,
			int year) {
		String startDate = year + "-01-01";
		String endDate = year + "-12-31";
		LeaveItemFactory lif = new LeaveItemFactory(deptId, startDate, endDate);
		lif.setUserId(userId).setLeaveTypeId(ANN_LEAVE_TYPE_ID);
		return lif.getMergedLeaveItem(deptId);
	}
}