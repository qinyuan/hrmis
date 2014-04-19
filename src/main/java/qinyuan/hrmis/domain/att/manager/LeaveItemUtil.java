package qinyuan.hrmis.domain.att.manager;

import qinyuan.hrmis.domain.att.emp.Employee;
import qinyuan.lib.date.DateTimePeriod;
import qinyuan.lib.date.MyDateTime;
import qinyuan.lib.db.HbnConn;
import qinyuan.lib.web.html.Table;
import qinyuan.lib.web.html.TableRow;

public class LeaveItemUtil {
	private final static TableRow th;
	private final static String FAIL = "添加失败(存在重复)";
	private final static String SUCCESS = "添加成功";
	static {
		th = new TableRow(true);
		th.add("工号，姓名，日期，开始时间，结束时间，原因，操作者，操作时间，结果");
	}

	private Employee bufEmp;
	private LeaveType bufLeaveType;

	public boolean addToDB(LeaveItem[] items) {
		try (HbnConn cnn = new HbnConn()) {
			for (LeaveItem item : items) {
				if (isDuplicated(item, cnn)) {
					return false;
				} else {
					cnn.save(item);
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean addToDB(LeaveItem[] items, Table table) {
		try (HbnConn cnn = new HbnConn()) {
			table.add(th);
			TableRow tr;
			for (LeaveItem item : items) {
				tr = new TableRow();
				tr.add(toTableRow(item));
				if (isDuplicated(item, cnn)) {
					tr.add(FAIL);
				} else {
					tr.add(SUCCESS);
					cnn.save(item);
				}
				table.add(tr);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private TableRow toTableRow(LeaveItem item) {
		TableRow tr = new TableRow();

		if (bufEmp == null || item.getEmpId() != bufEmp.getId()) {
			bufEmp = Employee.newInstance(item.getEmpId());
		}

		if (bufLeaveType == null || item.getTypeId() != bufLeaveType.getId()) {
			bufLeaveType = LeaveType.getInstance(item.getTypeId());
		}

		tr.add(bufEmp.getBadgenumber());
		tr.add(bufEmp.getName());
		tr.add(item.getAttDate());
		tr.add(item.getStartTime());
		tr.add(item.getEndTime());
		tr.add(bufLeaveType.getDesc());
		tr.add(item.getOperator());
		tr.add(item.getOperateTime());
		return tr;
	}

	private static boolean isDuplicated(LeaveItem item, HbnConn cnn) {
		final String query = "SELECT COUNT(*) FROM spec_leave WHERE userId="
				+ item.getEmpId() + " AND specdate='" + item.getAttDate()
				+ "' AND starttime<'" + item.getEndTime() + "' AND endtime>'"
				+ item.getStartTime() + "'";
		int count = (Integer) (cnn.setSQL(query).setInt("COUNT(*)").list()
				.get(0));
		return count > 0;
	}

	public static TableRow getTableHead() {
		TableRow tr = new TableRow(true);
		tr.add("工号，姓名，日期，开始时间，结束时间，原因，创建者，创建时间");
		return tr;
	}

	public static TableRow toTableRow(MergedLeaveItem item) {
		TableRow tr = new TableRow();
		tr.add(item.getBadgenumber());
		tr.add(item.getUsername());
		tr.add(item.getPeriod());
		tr.add(item.getLeaveType());
		tr.add(String.valueOf(item.getDayCount()).replaceAll("\\.0+$", ""));
		return tr;
	}

	public static TableRow toTableRow(DetailLeaveItem item) {
		TableRow tr = new TableRow();
		tr.add(item.getBadgenumber());
		tr.add(item.getUsername());
		tr.add(item.getAttDate());
		tr.add(item.getStartTime());
		tr.add(item.getEndTime());
		tr.add(item.getLeaveType());
		tr.add(item.getOperator());
		tr.add(item.getOperateTime());
		return tr;
	}

	public static void main(String[] args) {
		LeaveItem item = new LeaveItem();
		item.setEmpId(605);
		item.setAttDate("2013-05-18");
		item.setStartTime("12:00:00");
		item.setEndTime("14:00:00");
		HbnConn cnn = new HbnConn();
		System.out.println(isDuplicated(item, cnn));
		try {
			cnn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		LeaveItemUtil util = new LeaveItemUtil();
		MyDateTime startDateTime = new MyDateTime("2012-12-15 08:08:08");
		MyDateTime endDateTime = new MyDateTime("2012-12-18 16:16:16");
		DateTimePeriod period = new DateTimePeriod(startDateTime, endDateTime);
		LeaveItem[] items = LeaveItemFactory.createNewItems(16, period, 2,
				"qinyuan");
		for (LeaveItem li : items) {
			System.out.println(util.toTableRow(li));
		}

		Table table = new Table();
		util.addToDB(items, table);
		System.out.println(table);
	}
}
