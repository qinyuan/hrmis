package qinyuan.hrmis.domain.ass;

import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.web.html.TableData;
import qinyuan.lib.web.html.TableRow;

public class ValueTpl extends AssTpl {

	private String unit;
	private String other;

	public ValueTpl(int id, AssDept checkee, AssDept checker, String item,
			String target, String formula, String unit, String other) {
		super(id, checkee, checker, item, target, formula);
		this.unit = unit;
		this.other = other;
	}

	public TableData getCheckBoxTd() {
		return getTd("mini", "<input type='checkbox' name='chk_v_" + getId()
				+ "' />");
	}

	public TableData getFormulaTd() {
		return getTd("wide", getFormula());
	}

	public String getUnit() {
		return unit;
	}

	public TableData getUnitTd() {
		return getTd("nar", getUnit());
	}

	public String getOther() {
		return other;
	}

	public TableData getOtherTd() {
		return getTd("wide", getOther());
	}

	public TableData getTargetTd() {
		return getTd("mid", getTarget());
	}

	@Override
	public TableRow toTableRow() {
		TableRow tr = new TableRow().setSplitStr(null).setId(
				"v_" + getId() + "_" + getCheckeeId() + "_" + getCheckerId());
		tr.add(getCheckeeTd());
		tr.add(getCheckerTd());
		tr.add(getItemTd());
		tr.add(getTargetTd());
		tr.add(getUnitTd());
		tr.add(getFormulaTd());
		tr.add(getOtherTd());
		return tr;
	}

	@Override
	public TableRow toTableRowAsCheckee() {
		TableRow tr = new TableRow().setSplitStr(null).setId("v_" + getId());
		tr.add(getCheckerTd());
		tr.add(getItemTd());
		tr.add(getTargetTd());
		tr.add(getUnitTd());
		tr.add(getFormulaTd());
		tr.add(getOtherTd());
		return tr;
	}

	@Override
	public TableRow toTableRowAsChecker() {
		TableRow tr = new TableRow().setSplitStr(null).setId("v_" + getId());
		tr.add(getCheckeeTd());
		tr.add(getItemTd());
		tr.add(getTargetTd());
		tr.add(getUnitTd());
		tr.add(getFormulaTd());
		tr.add(getOtherTd());
		return tr;
	}

	public static TableRow getTableHead() {
		TableRow tr = new TableRow(true);
		tr.add(getTh("mid", "被考核"));
		tr.add(getTh("mid", "考核部门"));
		tr.add(getTh("mid", "项目"));
		tr.add(getTh("mid", "指标"));
		tr.add(getTh("nar", "单位"));
		tr.add(getTh("wide", "评分标准"));
		tr.add(getTh("wide", "备注"));
		return tr;
	}

	public static TableRow getTableHeadAsCheckee() {
		TableRow tr = new TableRow(true);
		tr.add(getTh("mid", "考核部门"));
		tr.add(getTh("mid", "项目"));
		tr.add(getTh("mid", "指标"));
		tr.add(getTh("nar", "单位"));
		tr.add(getTh("wide", "评分标准"));
		tr.add(getTh("wide", "备注"));
		return tr;
	}

	public static TableRow getTableHeadAsChecker() {
		TableRow tr = new TableRow(true);
		tr.add(getTh("mid", "被考核部门"));
		tr.add(getTh("mid", "项目"));
		tr.add(getTh("mid", "指标"));
		tr.add(getTh("nar", "单位"));
		tr.add(getTh("wide", "评分标准"));
		tr.add(getTh("wide", "备注"));
		return tr;
	}

	public static boolean add(int checkee, int checker, String item,
			String target, String unit, String formula, String other) {
		try (HRMISConn cnn = new HRMISConn()) {
			String query = "INSERT INTO ass_value_tpl"
					+ "(checkee,checker,item,target,unit,formula,other) VALUES"
					+ "(?,?,?,?,?,?,?)";
			cnn.prepare(query).setInt(1, checkee).setInt(2, checker)
					.setString(3, item).setString(4, target).setString(5, unit)
					.setString(6, formula).setString(7, other).execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static ValueTpl getValueTpl(int id) {
		ValueTpl[] tpls = new AssFactory().setId(id).getValueTpls();
		return tpls.length == 0 ? null : tpls[0];
	}

	public static boolean modify(int id, int checkee, int checker, String item,
			String target, String unit, String formula, String other) {
		try (HRMISConn cnn = new HRMISConn()) {
			String query = "UPDATE ass_value_tpl SET "
					+ "checkee=?,checker=?,item=?,target=?,unit=?,"
					+ "formula=?,other=? WHERE id=?";
			cnn.prepare(query).setInt(1, checkee).setInt(2, checker)
					.setString(3, item).setString(4, target).setString(5, unit)
					.setString(6, formula).setString(7, other).setInt(8, id)
					.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
