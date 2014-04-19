package qinyuan.hrmis.domain.user;

import java.util.List;
import qinyuan.hrmis.domain.admin.Privilege;
import qinyuan.hrmis.domain.att.admin.SimpleAnnLeave;
import qinyuan.hrmis.domain.att.admin.SimpleAnnLeaveFactory;
import qinyuan.hrmis.domain.att.admin.SimpleAttManager;
import qinyuan.hrmis.domain.att.admin.SpecDate;
import qinyuan.hrmis.domain.att.admin.SpecEmp;
import qinyuan.hrmis.domain.att.emp.Dept;
import qinyuan.hrmis.domain.att.emp.Employee;
import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.web.html.ChkBox;
import qinyuan.lib.web.html.Select;
import qinyuan.lib.web.html.Table;
import qinyuan.lib.web.html.TableRow;
import qinyuan.lib.web.html.Text;

public class AttAdmin extends User {

	private SpecDate sd = new SpecDate();
	private Dept dept = Dept.getInstance(1);
	private SimpleAnnLeaveFactory saf = new SimpleAnnLeaveFactory();
	private SimpleAnnLeaveFactory bsaf = new SimpleAnnLeaveFactory();

	public AttAdmin(int userId, String username, String nickname,
			Privilege[] privileges) {
		super(userId, username, nickname, privileges);
		super.setPriDesc("考勤设置");
	}

	public boolean addSpecDates(List<String> dateList, String reason) {
		return sd.add(dateList, reason);
	}

	public String getSpecDateTable() {
		return sd.getTable();
	}

	public void nextWeek() {
		sd.nextWeek();
	}

	public void previousWeek() {
		sd.previousWeek();
	}

	public SimpleAnnLeaveFactory getAnnLeaveFactory() {
		return bsaf;
	}

	public Table getAttManagerTable() {
		Table table = new Table();
		table.add(new TableRow(true).add("用户名，昵称，考勤部门"));
		for (SimpleAttManager sam : SimpleAttManager.getInstances()) {
			table.add(sam.toTableRow());
		}
		return table;
	}

	public Select getDeptSelect() {
		return saf.getDeptSelect();
	}

	public Table getFilterTable() {
		return bsaf.toFilterTable();
	}

	public Select getSpecEmpSelect() {
		final String query = "SELECT userid,badgenumber,username "
				+ "FROM dept_user WHERE deptid=? AND userid NOT IN "
				+ "(SELECT userid FROM spec_emp) ORDER BY badgenumber";
		Select select = new Select().setId("userId");
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare(query).setInt(1, dept.getId()).execute();
			while (cnn.next()) {
				select.addOption(cnn.getInt(1),
						cnn.getString(2) + " " + cnn.getString(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return select;
	}

	public String getEmpWithoutAnnLeave() {
		StringBuilder o = new StringBuilder();

		final String query = "SELECT badgenumber,username FROM dept_user "
				+ "WHERE (badgenumber NOT IN "
				+ "(SELECT badgenumber FROM annual_leave)) AND " + "deptid=?";
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare(query).setInt(1, dept.getId()).execute();
			if (cnn.getRowCount() == 0) {
				return o.toString();
			}

			o.append("<h3>未添加年休假人员</h3>");
			o.append("<table>");
			while (cnn.next()) {
				o.append("<tr>");
				o.append("<td>" + cnn.getInt(1) + "</td>");
				o.append("<td>" + cnn.getString(2) + "</td>");
				o.append("<td><button type='button' class='add'>添加</button></tr>");
				o.append("</tr>");
			}
			o.append("</table>");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return o.toString();
	}

	public Table getSpecEmpTable() {
		Table table = new Table();
		table.add(new TableRow(true).add(new ChkBox().setId("selectAll")).add(
				"工号，姓名，特殊原因"));
		SpecEmp[] emps = SpecEmp.getInstancesByDeptId(dept.getId());
		for (SpecEmp se : emps) {
			Employee emp = Employee.newInstance(se.getUserId());
			if (emp != null) {
				table.add(new TableRow().setId("tr" + emp.getId())
						.add(new ChkBox().setId("chk" + emp.getId()))
						.add(emp.getBadgenumber()).add(emp.getName())
						.add(se.getReason()));
			}
		}

		return table;
	}

	public Table getPerAnnLeaveTable(int badgenumber) {
		SimpleAnnLeave sal = saf.getAnnLeave(badgenumber);
		Table table = new Table();
		if (sal == null) {
			return table;
		}

		table.add(new TableRow().add("工号").add(sal.getBadgenumbe()));
		table.add(new TableRow().add("姓名").add(sal.getUsername()));
		table.add(new TableRow().add("参加工作时间").add(
				new Text().setId("workDate").setValue(sal.getWorkDate())));
		table.add(new TableRow().add("入司时间").add(
				new Text().setId("joinDate").setValue(sal.getJoinDate())));
		table.add(new TableRow().add("参保地").add(
				new Text().setId("insurePlace").setValue(sal.getInsurePlace())));
		table.add(new TableRow().add("可用天数").add(
				new Text().setId("usabledDays").setValue(sal.getUsableDays())));

		return table;
	}

	public Table getAnnLeaveTable() {
		return saf.toTable();
	}

	public Table getBatchAnnLeaveTable() {
		return bsaf.toTable();
	}

	public AttAdmin setDeptId(int deptId) {
		if (deptId != dept.getId()) {
			dept = Dept.getInstance(deptId);
			saf.setDeptId(deptId);
			bsaf.setDeptId(deptId);
		}
		return this;
	}

	public static AttAdmin createAttAdmin(User user) {
		return new AttAdmin(user.getUserid(), user.getUsername(),
				user.getNickname(), user.getPrivilege());
	}
}
