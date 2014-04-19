package qinyuan.hrmis.domain.att.admin;

import static qinyuan.lib.date.MyDate.isDate;
import qinyuan.hrmis.domain.att.emp.DeptUtil;
import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.web.html.ChkBox;
import qinyuan.lib.web.html.Select;
import qinyuan.lib.web.html.Table;
import qinyuan.lib.web.html.TableRow;
import qinyuan.lib.web.html.Text;

public class SimpleAnnLeaveFactory {

	private int deptId = 1;
	private int badgenumber;
	private String sJoinDate;
	private String eJoinDate;
	private String sWorkDate;
	private String eWorkDate;
	private String insurePlace;
	private int sUsabledDays = -1;
	private int eUsabledDays = -1;

	public SimpleAnnLeaveFactory setDeptId(int deptId) {
		this.deptId = deptId;
		return this;
	}

	public SimpleAnnLeaveFactory setBadgenumber(int badgenumber) {
		this.badgenumber = badgenumber;
		return this;
	}

	public SimpleAnnLeaveFactory setJoinDate(String sJoinDate, String eJoinDate) {
		this.sJoinDate = isDate(sJoinDate) ? sJoinDate : null;
		this.eJoinDate = isDate(eJoinDate) ? eJoinDate : null;
		return this;
	}

	public SimpleAnnLeaveFactory setWorkDate(String sWorkDate, String eWorkDate) {
		this.sWorkDate = isDate(sWorkDate) ? sWorkDate : null;
		this.eWorkDate = isDate(eWorkDate) ? eWorkDate : null;
		return this;
	}

	public SimpleAnnLeaveFactory setInsurePlace(String insurePlace) {
		this.insurePlace = insurePlace;
		return this;
	}

	public SimpleAnnLeaveFactory setUsabledDays(int min, int max) {
		sUsabledDays = min;
		eUsabledDays = max;
		return this;
	}

	private final static String QUERY = "SELECT "
			+ "du.badgenumber,username,workdate,joindate,insure_place,"
			+ "total_leave FROM annual_leave AS al "
			+ "LEFT JOIN dept_user as du USING(badgenumber)";

	private String getWhereClause() {
		StringBuilder s = new StringBuilder();
		boolean first = true;
		if (deptId > 0) {
			s.append(" WHERE ");
			first = false;
			s.append("deptid=" + deptId);
		}
		if (badgenumber > 0) {
			setWherePrefix(s, first);
			first = false;
			s.append("du.badgenumber=" + badgenumber);
		}
		if (notEmpty(sJoinDate)) {
			setWherePrefix(s, first);
			first = false;
			s.append("joindate>='" + sJoinDate + "'");
		}
		if (notEmpty(eJoinDate)) {
			setWherePrefix(s, first);
			first = false;
			s.append("joindate<='" + eJoinDate + "'");
		}
		if (notEmpty(sWorkDate)) {
			setWherePrefix(s, first);
			first = false;
			s.append("workdate>='" + sWorkDate + "'");
		}
		if (notEmpty(eWorkDate)) {
			setWherePrefix(s, first);
			first = false;
			s.append("workdate<='" + eWorkDate + "'");
		}
		if (notEmpty(insurePlace)) {
			setWherePrefix(s, first);
			first = false;
			s.append("insure_place='" + insurePlace + "'");
		}
		if (sUsabledDays > 0) {
			setWherePrefix(s, first);
			first = false;
			s.append("total_leave>=" + sUsabledDays);
		}
		if (eUsabledDays > 0) {
			setWherePrefix(s, first);
			first = false;
			s.append("total_leave<=" + eUsabledDays);
		}

		return s.toString();
	}

	private static void setWherePrefix(StringBuilder s, boolean first) {
		s.append(first ? " WHERE " : " AND ");
	}

	private static boolean notEmpty(String str) {
		return str != null && (!str.trim().isEmpty());
	}

	private static SimpleAnnLeave fetchSimpleAnnLeave(HRMISConn cnn)
			throws Exception {
		SimpleAnnLeave item = new SimpleAnnLeave();
		item.setBadgenumbe(cnn.getString(1));
		item.setUsername(cnn.getString(2));
		item.setWorkDate(cnn.getString(3));
		item.setJoinDate(cnn.getString(4));
		item.setInsurePlace(cnn.getString(5));
		item.setUsableDays(cnn.getString(6));
		return item;
	}

	public SimpleAnnLeave getAnnLeave(int badgenumber) {
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare(QUERY + " WHERE du.badgenumber=?")
					.setInt(1, badgenumber).execute();
			cnn.next();
			return fetchSimpleAnnLeave(cnn);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public SimpleAnnLeave[] getAnnLeaves() {
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.execute(QUERY + getWhereClause() + " ORDER BY du.badgenumber");
			SimpleAnnLeave[] items = new SimpleAnnLeave[cnn.getRowCount()];
			int i = 0;
			while (cnn.next()) {
				items[i++] = fetchSimpleAnnLeave(cnn);
			}
			return items;
		} catch (Exception e) {
			e.printStackTrace();
			return new SimpleAnnLeave[0];
		}
	}

	public Select getDeptSelect() {
		return DeptUtil.getSelect(deptId, "deptId");
	}

	public Table toTable() {
		Table table = new Table();
		table.add(new TableRow(true).add(new ChkBox().setId("selectAll")).add(
				"工号，姓名，参加工作时间，入司时间，参保地，可用天数"));
		for (SimpleAnnLeave item : getAnnLeaves()) {
			TableRow tr = new TableRow().setId('b' + item.getBadgenumbe());
			tr.add(new ChkBox().setId("chk" + item.getBadgenumbe()));
			tr.add(item.getBadgenumbe()).add(item.getUsername())
					.add(item.getWorkDate()).add(item.getJoinDate())
					.add(item.getInsurePlace()).add(item.getUsableDays());
			table.add(tr);
		}
		return table;
	}

	public Table toFilterTable() {
		Table t = new Table();
		t.add(getCol("部门：", getDeptSelect(), "工号：",
				getText("badgenumber", badgenumber > 0 ? badgenumber : "")));
		t.add(getCol("参加工作时间：", getText("sWorkDate", sWorkDate), "至",
				getText("eWorkDate", eWorkDate)));
		t.add(getCol("入司时间：", getText("sJoinDate", sJoinDate), "至",
				getText("eJoinDate", eJoinDate)));
		t.add(getCol("参保地：", getText("insurePlace", insurePlace), "", ""));
		t.add(getCol("年休假天数：",
				getText("sUsabledDays", sUsabledDays > 0 ? sUsabledDays : ""),
				"至",
				getText("eUsabledDays", eUsabledDays > 0 ? eUsabledDays : "")));
		return t;
	}

	private static TableRow getCol(Object... col) {
		TableRow tr = new TableRow();
		for (Object obj : col) {
			tr.add(obj);
		}
		return tr;
	}

	private static Text getText(Object id, Object value) {
		return new Text().setId(id == null ? "" : id.toString()).setValue(
				value == null ? "" : value.toString());
	}
}