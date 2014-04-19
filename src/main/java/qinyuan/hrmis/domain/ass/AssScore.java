package qinyuan.hrmis.domain.ass;

import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.web.html.TableRow;

public class AssScore extends ScoreTpl implements AssResult {

	private AssItem result;

	public AssScore(int id, AssDept checkee, AssDept checker, String item,
			String target, String formula, float weight, AssItem result) {
		super(id, checkee, checker, item, target, formula, weight);
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
		tr.add(getTd("wider", getData()));
		tr.add(getTd("mid", getResult()));
		return tr;
	}

	public static TableRow getTableHead() {
		return appendTh(ScoreTpl.getTableHead());
	}

	public static TableRow getTableHeadAsCheckee() {
		return appendTh(ScoreTpl.getTableHeadAsCheckee());
	}

	public static TableRow getTableHeadAsChecker() {
		return appendTh(ScoreTpl.getTableHeadAsChecker());
	}

	private static TableRow appendTh(TableRow tr) {
		tr.add(getTh("wider", "数据"));
		tr.add(getTh("mid", "结果"));
		return tr;
	}

	public static TableRow getSumTableRow(AssScore[] scores) {
		TableRow tr = new TableRow().add("合计");
		for (int i = 0; i < 5; i++) {
			tr.add("");
		}
		return tr.add(AssResultUtil.getResultSum(scores));
	}

	public static AssScore getInstance(int id) {
		AssScore[] scores = new AssFactory().setId(id).getScores();
		return scores.length == 0 ? null : scores[0];
	}

	public static boolean addByMon(int monId) {
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare("SELECT COUNT(*) FROM ass_score WHERE mon=?")
					.setInt(1, monId).execute();
			cnn.next();
			if (cnn.getInt(1) > 0) {
				return false;
			}

			final String query = "INSERT INTO ass_score"
					+ "(mon,checkee,checker,item,target,weight,formula,data,result) "
					+ "SELECT ?,checkee,checker,item,target,weight,formula,'',0 "
					+ "FROM ass_score_tpl";
			cnn.prepare(query).setInt(1, monId).execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean addScore(int monId, int checkee, int checker, String item,
			String target, float weight, String formula, String data,
			float result) {
		try (HRMISConn cnn = new HRMISConn()) {
			String query = "INSERT INTO ass_score"
					+ "(mon,checkee,checker,item,target,weight,formula,data,result) "
					+ "VALUES(?,?,?,?,?,?,?,?,?)";
			cnn.prepare(query).setInt(1, monId).setInt(2, checkee)
					.setInt(3, checker).setString(4, item).setString(5, target)
					.setDouble(6, weight).setString(7, formula)
					.setString(8, data).setDouble(9, result).execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean deleteByMon(int monId) {
		try (HRMISConn cnn = new HRMISConn()) {
			String query = "DELETE FROM ass_score WHERE mon=?";
			cnn.prepare(query).setInt(1, monId).execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean update(int id, String data, float result) {
		try (HRMISConn cnn = new HRMISConn()) {
			final String query = "UPDATE ass_score SET data=?,result=? "
					+ "WHERE id=? AND mon IN (SELECT id FROM ass_mon WHERE locked=FALSE)";
			cnn.prepare(query).setString(1, data).setDouble(2, result)
					.setInt(3, id).execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean updateAll(int id, int checkeeId, int checkerId,
			String item, String target, float weight, String formula,
			String data, float result) {
		try (HRMISConn cnn = new HRMISConn()) {
			final String query = "UPDATE ass_score SET "
					+ "checkee=?,checker=?,item=?,target=?,weight=?,"
					+ "formula=?,data=?,result=? WHERE id=? AND mon IN "
					+ "(SELECT id FROM ass_mon WHERE locked=FALSE)";
			cnn.prepare(query).setInt(1, checkeeId).setInt(2, checkerId)
					.setString(3, item).setString(4, target)
					.setDouble(5, weight).setString(6, formula)
					.setString(7, data).setDouble(8, result).setInt(9, id)
					.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}