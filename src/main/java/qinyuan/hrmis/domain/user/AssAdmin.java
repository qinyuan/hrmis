package qinyuan.hrmis.domain.user;

import qinyuan.hrmis.domain.admin.Privilege;
import qinyuan.hrmis.domain.ass.AssDept;
import qinyuan.hrmis.domain.ass.AssFactory;
import qinyuan.hrmis.domain.ass.AssScore;
import qinyuan.hrmis.domain.ass.AssSum;
import qinyuan.hrmis.domain.ass.AssTpl;
import qinyuan.hrmis.domain.ass.AssValue;
import qinyuan.hrmis.domain.ass.ScoreTpl;
import qinyuan.hrmis.domain.ass.ValueTpl;
import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.web.html.ChkBox;
import qinyuan.lib.web.html.Select;
import qinyuan.lib.web.html.Table;
import qinyuan.lib.web.html.TableRow;

public class AssAdmin extends AssParticipant {

	private AssDept checker;
	private AssDept checkee;

	protected AssAdmin(int userId, String username, String nickname,
			Privilege[] privileges) {
		super(userId, username, nickname, privileges);

		super.setPriDesc("考核设置");
	}

	public String getAssSum(int monId) {
		return AssSum.getAssSumTable(monId);
	}

	public int getCheckerId() {
		return getId(checker);
	}

	public Select getCheckerSelect() {
		return getSelect(checker, "checker");
	}

	public Select getCheckerSelectAll() {
		return getSelectAll(checker, "checker");
	}

	public int getCheckeeId() {
		return getId(checkee);
	}

	public Select getCheckeeSelect() {
		return getSelect(checkee, "checkee");
	}

	public Select getCheckeeSelectAll() {
		return getSelectAll(checkee, "checkee");
	}

	public Table getDeptTable() {
		Table table = new Table();
		TableRow tr = new TableRow(true);
		tr.add("<input type='checkbox' id='selectAll' />").add("部门名称");
		table.add(tr);
		for (AssDept dept : AssDept.getInstances()) {
			tr = new TableRow().setId("tr" + dept.getId());
			tr.add(new ChkBox().setId("chk" + dept.getId()));
			tr.add(dept.getName());
			table.add(tr);
		}
		return table;
	}

	public String getScoresTable() {
		AssScore[] scores = af.getScores();
		return toTable(AssScore.getTableHead(), scores);
	}

	public String getScoreTplsTable() {
		return toTable(ScoreTpl.getTableHead(), af.getScoreTpls());
	}

	public String getValuesTable() {
		AssValue[] values = af.getValues();
		return toTable(AssValue.getTableHead(), values);
	}

	public String getValueTplsTable() {
		return toTable(ValueTpl.getTableHead(), af.getValueTpls());
	}

	public boolean setDeptsOfAssDealer(int userId, int[] deptIds) {
		if (deptIds.length == 0) {
			return true;
		}
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare("INSERT INTO ass_dealer(userid,deptid) VALUES(?,?)");
			for (int deptId : deptIds) {
				cnn.setInt(1, userId).setInt(2, deptId).execute();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void setCheckee(int deptId) {
		checkee = AssDept.getInstance(deptId);
		af.setCheckeeId(deptId);
	}

	public void setChecker(int deptId) {
		checker = AssDept.getInstance(deptId);
		af.setCheckerId(deptId);
	}

	public static AssAdmin newAssAdmin(User user) {
		return new AssAdmin(user.getUserid(), user.getUsername(),
				user.getNickname(), user.getPrivilege());
	}

	private static int getId(AssDept assDept) {
		return assDept == null ? -1 : assDept.getId();
	}

	private static Select getSelect(AssDept assDept, String tagId) {
		return assDept == null ? AssDept.getSelect(tagId) : assDept
				.toSelect(tagId);
	}

	private static Select getSelectAll(AssDept assDept, String tagId) {
		return assDept == null ? AssDept.getSelectAll(tagId) : assDept
				.toSelectAll(tagId);
	}

	public static String toTable(TableRow head, AssTpl[] items) {
		if (items.length == 0)
			return "";

		Table table = new Table().add(head.append(AssTpl.selectAllTh));
		for (AssTpl item : items) {
			table.add(item.toTableRow().append(item.getCheckBoxTd()));
		}
		return table.toString().replaceAll("\r\n", "\r\n<br />");
	}

	public static void main(String[] args) {
		AssFactory af = new AssFactory();
		System.out.println(toTable(ValueTpl.getTableHead(), af.getValueTpls()));
	}
}
