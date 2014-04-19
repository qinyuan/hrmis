package qinyuan.hrmis.domain.ass;

import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.web.html.TableRow;

public class AssValue extends ValueTpl implements AssResult {

	private AssItem result;

	public AssValue(int id, AssDept checkee, AssDept checker, String item,
			String target, String formula, String unit, String other,
			AssItem result) {
		super(id, checkee, checker, item, target, formula, unit, other);
		this.result = result;
	}

	@Override
	public String getMon() {
		return result.getMon();
	}

	@Override
	public String getData() {
		return result.getData();
	}

	@Override
	public float getResult() {
		return result.getResult();
	}

	@Override
	public TableRow toTableRow() {
		return append(super.toTableRow());
	}

	@Override
	public TableRow toTableRowAsCheckee() {
		return append(super.toTableRowAsCheckee());
	}

	@Override
	public TableRow toTableRowAsChecker() {
		return append(super.toTableRowAsChecker());
	}

	private TableRow append(TableRow tr) {
		tr.add(getTd("wide", getData()));
		tr.add(getTd("mid", getResult()));
		return tr;
	}

	public static TableRow getTableHead() {
		return appendTh(ValueTpl.getTableHead());
	}

	public static TableRow getTableHeadAsCheckee() {
		return appendTh(ValueTpl.getTableHeadAsCheckee());
	}

	public static TableRow getTableHeadAsChecker() {
		return appendTh(ValueTpl.getTableHeadAsChecker());
	}

	private static TableRow appendTh(TableRow tr) {
		tr.add(getTh("wide", "数据"));
		tr.add(getTh("mid", "结果"));
		return tr;
	}

	public static TableRow getSumTableRow(AssValue[] values) {
		TableRow tr = new TableRow().add("合计");
		for (int i = 0; i < 6; i++) {
			tr.add("");
		}
		return tr.add(AssResultUtil.getResultSum(values));
	}

	public static AssValue getInstance(int id) {
		AssValue[] values = new AssFactory().setId(id).getValues();
		return values.length == 0 ? null : values[0];
	}

	public static boolean addByMon(int monId) {
		try (HRMISConn cnn = new HRMISConn()) {
			String query = "SELECT COUNT(*) FROM ass_value WHERE mon=?";
			cnn.prepare(query).setInt(1, monId).execute();
			cnn.next();
			if (cnn.getInt(1) > 0) {
				return false;
			}
			query = "INSERT INTO ass_value"
					+ "(mon,checkee,checker,item,target,unit,other,formula,data,result) "
					+ "SELECT ?,checkee,checker,item,target,unit,other,formula,'',0 "
					+ "FROM ass_value_tpl";
			cnn.prepare(query).setInt(1, monId).execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean addValue(int monId, int checkee, int checker,
			String item, String target, String unit, String other,
			String formula, String data, float result) {
		try (HRMISConn cnn = new HRMISConn()) {
			String query = "INSERT INTO ass_value"
					+ "(mon,checkee,checker,item,target,unit,other,formula,data,result) "
					+ "VALUES(?,?,?,?,?,?,?,?,?,?)";
			cnn.prepare(query).setInt(1, monId).setInt(2, checkee)
					.setInt(3, checker).setString(4, item).setString(5, target)
					.setString(6, unit).setString(7, other)
					.setString(8, formula).setString(9, data)
					.setDouble(10, result).execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean deleteByMon(int monId) {
		try (HRMISConn cnn = new HRMISConn()) {
			String query = "DELETE FROM ass_value WHERE mon=?";
			cnn.prepare(query).setInt(1, monId).execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean update(int id, String data, float result) {
		try (HRMISConn cnn = new HRMISConn()) {
			final String query = "UPDATE ass_value SET "
					+ "data=?,result=? WHERE id=? AND mon IN "
					+ "(SELECT id FROM ass_mon WHERE locked=FALSE)";
			cnn.prepare(query).setString(1, data).setDouble(2, result)
					.setInt(3, id).execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean updateAll(int id, int checkeeId, int checkerId,
			String item, String target, String unit, String other,
			String formula, String data, float result) {
		try (HRMISConn cnn = new HRMISConn()) {
			final String query = "UPDATE ass_value SET "
					+ "checkee=?,checker=?,item=?,target=?,unit=?,"
					+ "other=?,formula=?,data=?,result=? "
					+ "WHERE id=? AND mon IN "
					+ "(SELECT id FROM ass_mon WHERE locked=FALSE)";
			cnn.prepare(query).setInt(1, checkeeId).setInt(2, checkerId)
					.setString(3, item).setString(4, target).setString(5, unit)
					.setString(6, other).setString(7, formula)
					.setString(8, data).setDouble(9, result).setInt(10, id)
					.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
