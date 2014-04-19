package qinyuan.hrmis.domain.att.manager;

import java.util.ArrayList;
import java.util.List;

import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.date.MyDateTime;
import qinyuan.lib.date.Week;

public class AccdItemFactory {

	private static final String IN = "上班";
	private static final String OUT = "下班";

	private static String getSqlQuery(int deptId, int userId, String startDate,
			String endDate) {
		StringBuilder o = new StringBuilder();
		
		o.append("SELECT accdid,badgenumber,username,accddate,isreach,"
				+ "accdtype,operator,operatetime FROM spec_accd AS a "
				+ "LEFT JOIN dept_user du USING(userId) "
				+ "LEFT JOIN accdtype USING(accdtypeid) "
				+ "WHERE (accddate BETWEEN '" + startDate + "' AND '" + endDate
				+ "') AND du.deptId=" + deptId);
		if (userId > 0) {
			o.append(" AND a.userId=" + userId);
		}
		o.append(" ORDER BY a.userId,a.accdtypeid");
		return o.toString();
	}

	private static DetailAccdItem createDetailAccdItemByConn(HRMISConn cnn)
			throws Exception {
		DetailAccdItem item = new DetailAccdItem();
		item.setId(cnn.getInt(1));
		item.setBadgenumber(cnn.getString(2));
		item.setUsername(cnn.getString(3));
		item.setAttDate(cnn.getString(4));
		item.setClassType(cnn.getInt(5) == 0 ? OUT : IN);
		item.setAccdType(cnn.getString(6));
		item.setOperator(cnn.getString(7));
		if (cnn.getString(8) != null) {
			item.setOperateTime(cnn.getString(8).substring(0, 19));
		}
		return item;
	}

	private static MergedAccdItem createMergedAccdItem(HRMISConn cnn)
			throws Exception {
		MergedAccdItem item = new MergedAccdItem();
		item.setId(cnn.getInt(1));
		item.setBadgenumber(cnn.getString(2));
		item.setUsername(cnn.getString(3));
		item.addAccdDate(cnn.getString(4), cnn.getInt(5) != 0);
		item.setAccdType(cnn.getString(6));
		item.setOperator(cnn.getString(7));
		if (cnn.getString(8) != null) {
			item.setOperateTime(cnn.getString(8).substring(0, 19));
		}
		return item;
	}

	public static List<MergedAccdItem> getMergeAccdItem(int deptId, int userId,
			String startDate, String endDate) {
		List<MergedAccdItem> list = new ArrayList<MergedAccdItem>();
		MergedAccdItem lastItem = null;
		try (HRMISConn cnn = new HRMISConn()) {

			cnn.execute(getSqlQuery(deptId, userId, startDate, endDate));
			while (cnn.next()) {
				if (lastItem != null
						&& lastItem.getBadgenumber().equals(cnn.getString(2))
						&& lastItem.getAccdType().equals(cnn.getString(6))) {
					lastItem.addAccdDate(cnn.getString(4), cnn.getInt(5) != 0);
				} else {
					lastItem = createMergedAccdItem(cnn);
					list.add(lastItem);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static DetailAccdItem[] getDetailAccdItems(int deptId, int userId,
			String startDate, String endDate) {
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.execute(getSqlQuery(deptId, userId, startDate, endDate));
			DetailAccdItem[] items = new DetailAccdItem[cnn.getRowCount()];
			int i = 0;
			while (cnn.next()) {
				items[i++] = createDetailAccdItemByConn(cnn);
			}
			return items;
		} catch (Exception e) {
			e.printStackTrace();
			return new DetailAccdItem[0];
		}
	}

	/**
	 * the accdType parameter likes 'A1', the element in hidList likes
	 * 'hid_L_47_2013-05-16'
	 * 
	 * @param accdType
	 * @param hidList
	 * @param operator
	 * @return
	 */
	public static AccdItem[] createNewItems(String accdType,
			List<String> hidList, String operator) {
		if (accdType.charAt(0) != 'A') {
			return new AccdItem[0];
		}

		String operateTime = new MyDateTime().toString();
		AccdItem[] items = new AccdItem[hidList.size()];
		int accdTypeId = Integer.parseInt(accdType.substring(1));
		int i = 0;
		for (String str : hidList) {
			AccdItem item = createItemByHidValue(str);
			item.setTypeId(accdTypeId);
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
	private static AccdItem createItemByHidValue(String hidValue) {
		String[] strs = hidValue.split("_");

		boolean reach = strs[1].equals("R");
		int userId = Integer.parseInt(strs[2]);
		String attDate = strs[3];
		AccdItem item = new AccdItem();
		item.setEmpId(userId);
		item.setAttDate(attDate);
		item.setReach(reach);
		return item;
	}

	/**
	 * this ajaxData likes
	 * 'specType=A1&hid_BL16_2=hid_BL16_2&hid_BR16_3=hid_BR16_3'
	 * 
	 * @param ajaxData
	 * @param week
	 * @param userId
	 * @param operator
	 * @return
	 */
	public static AccdItem[] createNewItems(String ajaxData, Week week,
			String operator) {
		if (!ajaxData.startsWith("specType=A")) {
			return new AccdItem[0];
		}

		String[] strs = ajaxData.split("&");
		if (strs.length == 1) {
			return new AccdItem[0];
		}

		String operateTime = new MyDateTime().toString();

		int accdTypeId = Integer.parseInt(strs[0].replace("specType=A", ""));
		AccdItem item;
		AccdItem[] items = new AccdItem[strs.length - 1];
		for (int i = 1; i < strs.length; i++) {
			item = createItemByHidValue(strs[i], week);
			item.setTypeId(accdTypeId);
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
	private static AccdItem createItemByHidValue(String hidValue, Week week) {
		String s = hidValue.substring(5, hidValue.lastIndexOf('='));
		boolean reach;
		if (s.charAt(0) == 'R') {
			reach = true;
		} else {
			reach = false;
		}
		String[] strs = s.split("_");
		int userId = Integer.parseInt(strs[0].substring(1));
		String attDate = week.getLongDay(Integer.parseInt(strs[1]));

		AccdItem item = new AccdItem();
		item.setEmpId(userId);
		item.setAttDate(attDate);
		item.setReach(reach);
		return item;
	}

	private static void testCreateNewItems() {
		String str = "specType=A1&hid_BL16_2=hid_BL16_2&hid_BR16_3=hid_BR16_3";
		AccdItem[] items = createNewItems(str, new Week(), "qinyuan");
		for (AccdItem item : items) {
			System.out.println(item);
		}
	}

	private static void testGetDetailAccdItems() {
		DetailAccdItem[] items = getDetailAccdItems(1, -1, "2013-3-1",
				"2013-6-5");
		System.out.println(items.length);
	}

	public static void main(String[] args) {
		testCreateNewItems();
		testGetDetailAccdItems();
	}
}