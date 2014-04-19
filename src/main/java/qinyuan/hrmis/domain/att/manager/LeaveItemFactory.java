package qinyuan.hrmis.domain.att.manager;

import java.util.ArrayList;
import java.util.List;

import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.date.DateTimePeriod;
import qinyuan.lib.date.MyDateTime;
import qinyuan.lib.date.Week;

public class LeaveItemFactory {
	public final static String NOON = "12:00:00";
	public final static String IN = "08:00:00";
	public final static String OUT = "16:00:00";
	public final static byte BOTH = 0;
	public final static byte NOR = 1;
	public final static byte OTH = 2;

	private int deptId;
	private int userId = -1;
	private String startDate;
	private String endDate;
	private int leaveTypeId = -1;
	private byte boardType = BOTH;

	public LeaveItemFactory(int deptId, String startDate, String endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.deptId = deptId;
	}

	public LeaveItemFactory setUserId(int userId) {
		this.userId = userId;
		return this;
	}

	public LeaveItemFactory setLeaveTypeId(int leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
		return this;
	}

	public LeaveItemFactory setBoardType(byte type) {
		this.boardType = type;
		return this;
	}

	public DetailLeaveItem[] getDetailLeaveItem(int deptId) {
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.execute(getSqlQuery());
			DetailLeaveItem[] items = new DetailLeaveItem[cnn.getRowCount()];
			int i = 0;
			while (cnn.next()) {
				items[i++] = createDetailLeaveItemByConn(cnn);
			}
			return items;
		} catch (Exception e) {
			e.printStackTrace();
			return new DetailLeaveItem[0];
		}
	}

	public List<MergedLeaveItem> getMergedLeaveItem(int deptId) {
		List<MergedLeaveItem> list = new ArrayList<MergedLeaveItem>();
		MergedLeaveItem previousItem = null, currentItem;

		try (HRMISConn cnn = new HRMISConn()) {
			cnn.execute(getSqlQuery());
			while (cnn.next()) {
				currentItem = createMergedLeaveItemByConn(cnn);
				if (previousItem != null && currentItem.after(previousItem)) {
					previousItem.merged(currentItem);
				} else {
					previousItem = currentItem;
					list.add(previousItem);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	private String getSqlQuery() {
		StringBuilder o = new StringBuilder();
		o.append("SELECT leaveid,badgenumber,username,specdate,starttime,"
				+ "endtime,leavetype,operator,operatetime FROM spec_leave AS l "
				+ "INNER JOIN dept_user AS du USING(userId) "
				+ "LEFT JOIN leavetype AS lt USING(leavetypeid) "
				+ "WHERE (specdate BETWEEN '" + startDate + "' AND '" + endDate
				+ "') AND du.deptId=" + deptId);
		if (userId > 0) {
			o.append(" AND l.userId=" + userId);
		}
		if (leaveTypeId > 0) {
			o.append(" AND l.leavetypeid=" + leaveTypeId);
		}
		if (boardType == NOR) {
			o.append(" AND lt.isnorm=TRUE");
		} else if (boardType == OTH) {
			o.append(" AND lt.isnorm=FALSE");
		}
		o.append(" ORDER BY du.badgenumber,l.leavetypeid,l.specdate,l.starttime");
		return o.toString();
	}

	private static DetailLeaveItem createDetailLeaveItemByConn(HRMISConn cnn)
			throws Exception {
		DetailLeaveItem item = new DetailLeaveItem();
		item.setId(cnn.getInt(1));
		item.setBadgenumber(cnn.getString(2));
		item.setUsername(cnn.getString(3));
		item.setAttDate(cnn.getString(4));
		item.setStartTime(cnn.getString(5));
		item.setEndTime(cnn.getString(6));
		item.setLeaveType(cnn.getString(7));
		item.setOperator(cnn.getString(8));
		if (cnn.getString(9) != null) {
			item.setOperateTime(cnn.getString(9).substring(0, 19));
		}
		return item;
	}

	private static MergedLeaveItem createMergedLeaveItemByConn(HRMISConn cnn)
			throws Exception {
		MergedLeaveItem item = new MergedLeaveItem();
		item.setId(cnn.getInt(1));
		item.setBadgenumber(cnn.getString(2));
		item.setUsername(cnn.getString(3));
		item.setStartDate(cnn.getString(4));
		item.setEndDate(cnn.getString(4));
		item.setStartTime(cnn.getString(5));
		item.setEndTime(cnn.getString(6));
		item.setLeaveType(cnn.getString(7));
		item.setOperator(cnn.getString(8));
		if (cnn.getString(9) != null) {
			item.setOperateTime(cnn.getString(9).substring(0, 19));
		}
		return item;
	}

	/**
	 * the leaveType parameter likes 'L1', the element in hidList likes
	 * 'hid_L_47_2013-05-16'
	 * 
	 * @param accdType
	 * @param hidList
	 * @param operator
	 * @return
	 */
	public static LeaveItem[] createNewItems(String leaveType,
			List<String> hidList, String operator) {
		if (leaveType.charAt(0) != 'L') {
			return new LeaveItem[0];
		}

		String operateTime = new MyDateTime().toString();
		LeaveItem[] items = new LeaveItem[hidList.size()];
		int leaveTypeId = Integer.parseInt(leaveType.substring(1));
		int i = 0;
		for (String str : hidList) {
			LeaveItem item = createItemByHidValue(str);
			item.setTypeId(leaveTypeId);
			item.setOperator(operator);
			item.setOperateTime(operateTime);
			items[i++] = item;
		}
		return items;
	}

	/**
	 * this method accept a hidValue string parameter such as
	 * 'hid_L_47_2013-05-16'
	 * 
	 * @param str
	 * @return
	 */
	private static LeaveItem createItemByHidValue(String hidValue) {
		String[] strs = hidValue.split("_");

		int userId = Integer.parseInt(strs[2]);
		String attDate = strs[3];
		LeaveItem item = new LeaveItem();
		item.setEmpId(userId);
		item.setAttDate(attDate);
		if (strs[1].equals("R")) {
			item.setStartTime(IN);
			item.setEndTime(NOON);
		} else {
			item.setStartTime(NOON);
			item.setEndTime(OUT);
		}
		return item;
	}

	/**
	 * this ajaxData likes
	 * 'specType=L1&hid_BL16_2=hid_BL16_2&hid_BR16_3=hid_BR16_3'
	 * 
	 * @param ajaxData
	 * @param week
	 * @param userId
	 * @param operator
	 * @return
	 */
	public static LeaveItem[] createNewItems(String ajaxData, Week week,
			String operator) {
		if (!ajaxData.startsWith("specType=L")) {
			return new LeaveItem[0];
		}

		String[] strs = ajaxData.split("&");
		if (strs.length == 1) {
			return new LeaveItem[0];
		}

		String operateTime = new MyDateTime().toString();

		int leaveTypeId = Integer.parseInt(strs[0].replace("specType=L", ""));
		LeaveItem item;
		LeaveItem[] items = new LeaveItem[strs.length - 1];
		for (int i = 1; i < strs.length; i++) {
			item = createItemByHidValue(strs[i], week);
			item.setTypeId(leaveTypeId);
			item.setOperator(operator);
			item.setOperateTime(operateTime);
			items[i - 1] = item;
		}
		return items;
	}

	/**
	 * this method accept a hidValue string parameter such as
	 * 'hid_HR16_3=hid_HR16_3'
	 * 
	 * @param str
	 * @return
	 */
	private static LeaveItem createItemByHidValue(String hidValue, Week week) {
		String s = hidValue.substring(5, hidValue.lastIndexOf('='));
		String startTime, endTime;
		if (s.charAt(0) == 'R') {
			startTime = IN;
			endTime = NOON;
		} else {
			startTime = NOON;
			endTime = OUT;
		}
		String[] strs = s.split("_");
		int userId = Integer.parseInt(strs[0].substring(1));
		String attDate = week.getLongDay(Integer.parseInt(strs[1]));

		LeaveItem item = new LeaveItem();
		item.setEmpId(userId);
		item.setAttDate(attDate);
		item.setStartTime(startTime);
		item.setEndTime(endTime);
		return item;
	}

	public static LeaveItem[] createNewItems(int userId, DateTimePeriod period,
			int leaveTypeId, String operator) {
		String operateTime = new MyDateTime().toString();
		List<DateTimePeriod> list = splitPeriod(period);
		LeaveItem[] items = new LeaveItem[list.size()];
		LeaveItem item;
		int i = 0;
		for (DateTimePeriod p : list) {
			item = new LeaveItem();
			item.setEmpId(userId);
			item.setAttDate(p.getStartDate());
			item.setStartTime(p.getStartTime());
			item.setEndTime(p.getEndTime());
			item.setOperator(operator);
			item.setOperateTime(operateTime);
			item.setTypeId(leaveTypeId);
			items[i++] = item;
		}
		return items;
	}

	/**
	 * if a period is '2012-01-01 13:00:00~2012-01-02 12:00:00', then this
	 * function will split it as '2012-01-01 13:00:00~2012-01-01 16:00:00' and
	 * '2012-01-02 08:00:00~2012-02-02 12:00:00'
	 */
	private static List<DateTimePeriod> splitPeriod(DateTimePeriod period) {
		String[] dates = period.getDates();
		String startTime = period.getStartTime();
		String endTime = period.getEndTime();
		List<DateTimePeriod> list = new ArrayList<DateTimePeriod>();

		if (dates.length == 0) {
			return list;
		}

		if (dates.length == 1) {
			if (startTime.compareTo(NOON) < 0 && endTime.compareTo(NOON) > 0) {
				list.add(createPeriod(dates[0], startTime, NOON));
				list.add(createPeriod(dates[0], NOON, endTime));
			} else {
				list.add(period);
			}
			return list;
		}

		// add the first day
		if (startTime.compareTo(NOON) < 0) {
			list.add(createPeriod(dates[0], startTime, NOON));
			list.add(createPeriod(dates[0], NOON, OUT));
		} else {
			list.add(createPeriod(dates[0], startTime, OUT));
		}

		// add days between first day and last day
		for (int i = 1; i < dates.length - 1; i++) {
			list.add(createPeriod(dates[i], IN, NOON));
			list.add(createPeriod(dates[i], NOON, OUT));
		}

		// add last day
		if (endTime.compareTo(NOON) > 0) {
			list.add(createPeriod(dates[dates.length - 1], IN, NOON));
			list.add(createPeriod(dates[dates.length - 1], NOON, endTime));
		} else {
			list.add(createPeriod(dates[dates.length - 1], IN, endTime));
		}

		return list;
	}

	private static DateTimePeriod createPeriod(String date, String startTime,
			String endTime) {
		MyDateTime startDateTime = new MyDateTime(date + " " + startTime);
		MyDateTime endDateTime = new MyDateTime(date + " " + endTime);
		return new DateTimePeriod(startDateTime, endDateTime);
	}

	private static void testSplitPeriod() {
		MyDateTime startDateTime = new MyDateTime("2013-02-20 08:00:00");
		MyDateTime endDateTime = new MyDateTime("2013-03-12 13:00:00");
		DateTimePeriod period = new DateTimePeriod(startDateTime, endDateTime);
		List<DateTimePeriod> periods = splitPeriod(period);

		for (DateTimePeriod p : periods) {
			System.out.println(p);
		}
	}

	private static void testCreateNewItems() {
		MyDateTime startDateTime = new MyDateTime("2013-02-20 08:00:00");
		MyDateTime endDateTime = new MyDateTime("2013-03-12 13:00:00");
		DateTimePeriod period = new DateTimePeriod(startDateTime, endDateTime);
		LeaveItem[] items = createNewItems(16, period, 2, "operator");
		for (LeaveItem item : items) {
			System.out.println(item);
		}

		String str = "specType=L1&hid_BL16_2=hid_BL16_2&hid_BR16_3=hid_BR16_3";
		items = createNewItems(str, new Week(), "qinyuan");
		for (LeaveItem item : items) {
			System.out.println(item);
		}

		List<String> list = new ArrayList<String>();
		list.add("hid_R_2958_2013-05-08");
		items = createNewItems("L1", list, "qinyuan");
		for (LeaveItem item : items) {
			System.out.println(item);
		}
	}

	public static void main(String[] args) {
		testSplitPeriod();
		testCreateNewItems();
	}
}