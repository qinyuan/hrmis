package qinyuan.hrmis.domain.user;

import java.util.List;
import qinyuan.hrmis.domain.admin.Privilege;
import qinyuan.hrmis.domain.att.admin.SimpleAttManager;
import qinyuan.hrmis.domain.att.emp.Dept;
import qinyuan.hrmis.domain.att.emp.DeptUtil;
import qinyuan.hrmis.domain.att.manager.AccdItem;
import qinyuan.hrmis.domain.att.manager.AccdItemFactory;
import qinyuan.hrmis.domain.att.manager.AccdItemUtil;
import qinyuan.hrmis.domain.att.manager.AnnLeaveFactory;
import qinyuan.hrmis.domain.att.manager.AnnLeaveUtil;
import qinyuan.hrmis.domain.att.manager.AttList;
import qinyuan.hrmis.domain.att.manager.AttMatrix;
import qinyuan.hrmis.domain.att.manager.DetailAccdItem;
import qinyuan.hrmis.domain.att.manager.DetailLeaveItem;
import qinyuan.hrmis.domain.att.manager.LeaveItem;
import qinyuan.hrmis.domain.att.manager.LeaveItemFactory;
import qinyuan.hrmis.domain.att.manager.LeaveItemUtil;
import qinyuan.hrmis.domain.att.manager.MergedAccdItem;
import qinyuan.hrmis.domain.att.manager.MergedLeaveItem;
import qinyuan.hrmis.domain.att.manager.NorLeaveFactory;
import qinyuan.hrmis.domain.att.manager.NorLeaveUtil;
import qinyuan.lib.date.DateTimePeriod;
import qinyuan.lib.date.MyDateTime;
import qinyuan.lib.date.MyTime;
import qinyuan.lib.date.Week;
import qinyuan.lib.web.html.Select;
import qinyuan.lib.web.html.Table;
import qinyuan.lib.web.html.TableRow;
import qinyuan.lib.web.html.TimeSelectForm;

public class AttManager extends User {
	private final static String SPEC_LIST_ACTION = "spec-list.action";

	private Dept[] depts;
	private DateTimePeriod period;
	private AttMatrix attMatrix;
	private int defUserId = -1;
	private boolean narrow = false;
	private int deptId;
	private int deptIndex;
	private boolean deptSelected;

	protected AttManager(int userId, String username, String nickname,
			Privilege[] pris, Dept[] depts) {
		super(userId, username, nickname, pris);
		super.setPriDesc("考勤管理");

		this.depts = depts;
		if (depts.length > 0) {
			deptId = depts[0].getId();
		}
		deptSelected = depts.length <= 1 ? true : false;

		attMatrix = new AttMatrix();
		Week week = attMatrix.getWeek();
		MyDateTime startDateTime = new MyDateTime(week.getLongDay(0)
				+ " 08:00:00");
		MyDateTime endDateTime = new MyDateTime(week.getLongDay(6)
				+ " 16:00:00");
		period = new DateTimePeriod(startDateTime, endDateTime);
	}

	public String addLeave(int leaveTypeId) {
		if (defUserId <= 0) {
			return "<h3>人员未选择</h3>";
		} else if (!period.isValid()) {
			return "<h3>时间无效</h3>";
		}

		Table table = new Table();
		LeaveItem[] items = LeaveItemFactory.createNewItems(defUserId, period,
				leaveTypeId, getUsername());
		LeaveItemUtil util = new LeaveItemUtil();
		if (util.addToDB(items, table)) {
			return table.toString() + "<h4>注：每半天只能添加一类请假</h4>";
		} else {
			return "<h3>出现未知错误</h3>";
		}
	}

	public boolean addSpecByAttList(String specType, List<String> hidList) {
		if (specType.charAt(0) == 'A') {
			AccdItem[] items = AccdItemFactory.createNewItems(specType,
					hidList, getUsername());
			return AccdItemUtil.addToDB(items);
		} else if (specType.charAt(0) == 'L') {
			LeaveItem[] items = LeaveItemFactory.createNewItems(specType,
					hidList, getUsername());
			return new LeaveItemUtil().addToDB(items);
		} else {
			return false;
		}
	}

	public boolean addSpecByAttMatrix(String submitData) {
		String operator = getUsername();
		if (submitData.startsWith("specType=L")) {
			LeaveItem[] items = LeaveItemFactory.createNewItems(submitData,
					attMatrix.getWeek(), operator);
			LeaveItemUtil util = new LeaveItemUtil();
			return util.addToDB(items);
		} else if (submitData.startsWith("specType=A")) {
			AccdItem[] items = AccdItemFactory.createNewItems(submitData,
					attMatrix.getWeek(), operator);
			return AccdItemUtil.addToDB(items);
		} else {
			return false;
		}
	}

	public boolean deleteLeave(int leaveId) {
		return LeaveItem.delete(leaveId, deptId);
	}

	public boolean deleteAccd(int accdId) {
		return AccdItem.delete(accdId, deptId);
	}

	public String exportNorLeaveSum(String folder) {
		return NorLeaveUtil.createExcel(folder, deptId, period.getStartDate(),
				period.getEndDate());
	}

	public String getAccdTable() {
		DetailAccdItem[] items = AccdItemFactory.getDetailAccdItems(deptId,
				defUserId, period.getStartDate(), period.getEndDate());
		Table table = new Table();
		TableRow tr;
		table.add(AccdItemUtil.getTableHead().add("操作"));
		for (DetailAccdItem item : items) {
			tr = AccdItemUtil.toTableRow(item);
			tr.add("<a href='" + SPEC_LIST_ACTION + "?delAccdId="
					+ item.getId() + "'>删除</a>");
			table.add(tr);
		}
		return table.toString();
	}

	public String getAnnLeave(int year) {
		if (defUserId <= 0)
			return "";

		double remainingDays = AnnLeaveUtil.getRemainAnnLeave(defUserId, year);
		return "剩余年休假：" + remainingDays + "天";
	}

	public Table getAnnLeaveDetail(int userId, int year) {
		Table table = new Table();
		if (!depts[0].containEmp(userId)) {
			return table;
		}

		table.add(new TableRow(true).add("工号，姓名，时间，原因，天数"));
		List<MergedLeaveItem> annList = AnnLeaveFactory.getDetailItems(deptId,
				userId, year);
		for (MergedLeaveItem item : annList) {
			table.add(LeaveItemUtil.toTableRow(item));
		}
		return table;
	}

	public String getAnnLeaveHiddenInputs(int year) {
		return AnnLeaveUtil.getAnnLeaveHiddenValues(deptId, year);
	}

	public Table getAnnLeaveTable(int year) {
		return AnnLeaveUtil.getTable(deptId, year);
	}

	public String getAttList() {
		return AttList.getList(defUserId, period.getDatePeriod());
	}

	public String getAttTable() {
		return attMatrix.getAttTable(deptId);
	}

	public String getDateRow() {
		return attMatrix.getDateRow(narrow);
	}

	public String getDateBackground() {
		return attMatrix.getDateBackground();
	}

	public boolean isDeptSelected() {
		return deptSelected;
	}

	public String getDeptList(String linkPage) {
		return DeptUtil.getDeptList(depts, linkPage);
	}

	public String getDeptSelect() {
		return DeptUtil.getSelect(depts, deptIndex, "deptIndex");
	}

	public Select getEmpSelect() {
		return depts[deptIndex].getEmpSelect(defUserId)
				.appendOption("", "(全部)");
	}

	public Select getEmpSelectWithoutAll() {
		return depts[deptIndex].getEmpSelect(defUserId).appendOption("",
				"(未选择)");
	}

	public String getEmpTable() {
		return attMatrix.getEmpTable(deptId);
	}

	public String getEndDate() {
		return period.getEndDate();
	}

	public String getEndDateForm() {
		return "<input type='text' id='endDate' name='endDate' value='"
				+ getEndDate() + "' />";
	}

	public String getEndTimeForm() {
		MyTime endTime = new MyTime(period.getEndTime());
		TimeSelectForm endTimeForm = new TimeSelectForm("end", endTime);
		return endTimeForm.toString();
	}

	public String getLeaveTable() {
		LeaveItemFactory lif = new LeaveItemFactory(deptId, getStartDate(),
				getEndDate());
		lif.setUserId(defUserId);
		DetailLeaveItem[] items = lif.getDetailLeaveItem(deptId);
		Table table = new Table();
		TableRow tr;
		table.add(LeaveItemUtil.getTableHead().add("操作"));
		for (DetailLeaveItem item : items) {
			tr = LeaveItemUtil.toTableRow(item);
			tr.add("<a href='" + SPEC_LIST_ACTION + "?delLeaveId="
					+ item.getId() + "'>删除</a>");
			table.add(tr);
		}
		return table.toString();
	}

	public boolean getNarrow() {
		return narrow;
	}

	public Table getNorLeaveDetail(int userId) {
		Table table = new Table();
		if (!depts[0].containEmp(userId)) {
			return table;
		}

		table.add(new TableRow(true).add("工号，姓名，时间，原因，天数"));
		List<MergedLeaveItem> norLeaves = NorLeaveFactory.getDetailItems(
				deptId, userId, getStartDate(), getEndDate());
		for (MergedLeaveItem item : norLeaves) {
			table.add(LeaveItemUtil.toTableRow(item));
		}
		return table;
	}

	public Table getNorLeaveHeadTable() {
		return NorLeaveUtil.getHeadTable();
	}

	public Table getNorLeaveBodyTable() {
		return NorLeaveUtil.getBodyTable(deptId, getStartDate(), getEndDate());
	}

	public Table getSpecTable() {
		Table table = new Table();
		table.add(new TableRow(true).add("序号，工号，姓名，时间，原因，天数"));
		int index = 1;

		LeaveItemFactory lif = new LeaveItemFactory(deptId,
				period.getStartDate(), period.getEndDate());
		lif.setUserId(defUserId);
		List<MergedLeaveItem> leaveList = lif.getMergedLeaveItem(deptId);

		for (MergedLeaveItem item : leaveList) {
			table.add(new TableRow().add(index++).add(
					LeaveItemUtil.toTableRow(item)));
		}

		List<MergedAccdItem> accdList = AccdItemFactory.getMergeAccdItem(
				deptId, defUserId, period.getStartDate(), period.getEndDate());
		for (MergedAccdItem item : accdList) {
			table.add(new TableRow().add(index++).add(
					AccdItemUtil.toTableRow(item)));
		}
		return table;
	}

	public String getStartDate() {
		return period.getStartDate();
	}

	public String getStartDateForm() {
		return "<input type='text' name='startDate' id='startDate' value='"
				+ getStartDate() + "' />";
	}

	public String getStartTimeForm() {
		MyTime time = new MyTime(period.getStartTime());
		TimeSelectForm startTimeForm = new TimeSelectForm("start", time);
		return startTimeForm.toString();
	}

	public void setAttDate(String dateStr) {
		Week week = new Week(dateStr);
		attMatrix.setWeek(week);
		period.setStartDate(week.getLongDay(0));
		period.setEndDate(week.getLongDay(6));
	}

	public void setAttDate(int weekToAdd) {
		attMatrix.setWeek(weekToAdd);
		period.setStartDate(attMatrix.getWeek().getLongDay(0));
		period.setEndDate(attMatrix.getWeek().getLongDay(6));
	}

	public void setDefaultUserId(int userId) {
		if (userId <= 0) {
			defUserId = userId;
			return;
		}

		if (depts[0].containEmp(userId)) {
			defUserId = userId;
		} else {
			throw new RuntimeException(
					"unable to set default employee id in class" + getClass());
		}
	}

	public void setDeptIndex(int deptIndex) {
		if (deptIndex <= depts.length) {
			this.deptIndex = deptIndex;
			deptId = depts[deptIndex].getId();
			deptSelected = true;
			defUserId = -1;
		}
	}

	public void setEndDate(String endDate) {
		period.setEndDate(endDate);
		if (!period.isValid()) {
			period.setStartDate(endDate);
			attMatrix.setWeek(new Week(endDate));
		}
	}

	public void setEndTime(String endTime) {
		period.setEndTime(endTime);
	}

	public void setNarrow(boolean narrow) {
		this.narrow = narrow;
	}

	public void setStartDate(String startDate) {
		period.setStartDate(startDate);
		if (!period.isValid()) {
			period.setEndDate(startDate);
		}
		attMatrix.setWeek(new Week(startDate));
	}

	public void setStartTime(String endTime) {
		period.setStartTime(endTime);
	}

	public static AttManager newInstance(User user) {
		int userId = user.getUserid();
		SimpleAttManager sam = new SimpleAttManager(userId);
		return new AttManager(userId, user.getUsername(), user.getNickname(),
				user.getPrivilege(), DeptUtil.getDepts(sam.getDept()));
	}

	public static void main(String[] args) {
		User user = User.newInstance(2);
		AttManager att = newInstance(user);
		System.out.println(att.getEmpSelect());
		att.setDeptIndex(1);
		System.out.println(att.getEmpSelect());
	}
}