package qinyuan.hrmis.domain.ass;

import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.web.html.TableData;
import qinyuan.lib.web.html.TableRow;

public class ScoreTpl extends AssTpl {

	private float weight;

	public ScoreTpl(int id, AssDept checkee, AssDept checker, String item,
			String target, String formula, float weight) {
		super(id, checkee, checker, item, target, formula);
		this.weight = weight;
	}

	public TableData getCheckBoxTd() {
		return getTd("mini", "<input type='checkbox' name='chk_s_" + getId()
				+ "' />");
	}

	public TableData getFormulaTd() {
		return getTd("wider", getFormula());
	}

	public TableData getTargetTd() {
		return getTd("wider", getTarget());
	}

	public float getWeight() {
		return weight;
	}

	public TableData getWeightTd() {
		return getTd("nar", getWeight());
	}

	@Override
	public TableRow toTableRow() {
		TableRow tr = new TableRow().setSplitStr(null).setId(
				"s_" + getId() + "_" + getCheckeeId() + "_" + getCheckerId());
		tr.add(getCheckeeTd());
		tr.add(getCheckerTd());
		tr.add(getItemTd());
		tr.add(getTargetTd());
		tr.add(getWeightTd());
		tr.add(getFormulaTd());
		return tr;
	}

	@Override
	public TableRow toTableRowAsCheckee() {
		TableRow tr = new TableRow().setSplitStr(null).setId("s_" + getId());
		tr.add(getCheckerTd());
		tr.add(getItemTd());
		tr.add(getTargetTd());
		tr.add(getWeightTd());
		tr.add(getFormulaTd());
		return tr;
	}

	@Override
	public TableRow toTableRowAsChecker() {
		TableRow tr = new TableRow().setSplitStr(null).setId("s_" + getId());
		tr.add(getCheckeeTd());
		tr.add(getItemTd());
		tr.add(getTargetTd());
		tr.add(getWeightTd());
		tr.add(getFormulaTd());
		return tr;
	}

	public static TableRow getTableHead() {
		TableRow tr = new TableRow(true);
		tr.add(getTh("mid", "被考核"));
		tr.add(getTh("mid", "考核部门"));
		tr.add(getTh("mid", "项目"));
		tr.add(getTh("wider", "指标"));
		tr.add(getTh("nar", "分值"));
		tr.add(getTh("wider", "评分标准"));
		return tr;
	}

	public static TableRow getTableHeadAsCheckee() {
		TableRow tr = new TableRow(true);
		tr.add(getTh("mid", "考核部门"));
		tr.add(getTh("mid", "项目"));
		tr.add(getTh("wider", "指标"));
		tr.add(getTh("nar", "分值"));
		tr.add(getTh("wider", "评分标准"));
		return tr;
	}

	public static TableRow getTableHeadAsChecker() {
		TableRow tr = new TableRow(true);
		tr.add(getTh("mid", "被考核部门"));
		tr.add(getTh("mid", "项目"));
		tr.add(getTh("wider", "指标"));
		tr.add(getTh("nar", "分值"));
		tr.add(getTh("wider", "评分标准"));
		return tr;
	}

	public static boolean add(int checkee, int checker, String item,
			String target, float weight, String formula) {
		try (HRMISConn cnn = new HRMISConn()) {
			String query = "INSERT INTO ass_score_tpl"
					+ "(checkee,checker,item,target,weight,formula) "
					+ "VALUES(?,?,?,?,?,?)";
			cnn.prepare(query).setInt(1, checkee).setInt(2, checker)
					.setString(3, item).setString(4, target)
					.setDouble(5, weight).setString(6, formula).execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static ScoreTpl getInstance(int id) {
		ScoreTpl[] tpls = new AssFactory().setId(id).getScoreTpls();
		return tpls.length == 0 ? null : tpls[0];
	}

	public static boolean modify(int id, int checkee, int checker, String item,
			String target, float weight, String formula) {
		try (HRMISConn cnn = new HRMISConn()) {
			String query = "UPDATE ass_score_tpl SET "
					+ "checkee=?,checker=?,item=?,target=?,weight=?,"
					+ "formula=? WHERE id=?";
			cnn.prepare(query).setInt(1, checkee).setInt(2, checker)
					.setString(3, item).setString(4, target)
					.setDouble(5, weight).setString(6, formula).setInt(7, id)
					.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
