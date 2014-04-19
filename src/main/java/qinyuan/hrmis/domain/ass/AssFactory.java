package qinyuan.hrmis.domain.ass;

import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.lang.MyMath;

public class AssFactory {

	private int id;
	private int monId;
	private int checkeeId;
	private int checkerId;

	public AssFactory() {
		AssMon lastMon = AssMon.getLastInstance();
		if (lastMon != null) {
			monId = lastMon.getId();
		}
	}

	private final static String SCORE_QUERY = "SELECT "
			+ "id,mon,checkee,checker,item,target,weight,formula,data,result "
			+ "FROM ass_score ";

	private static final String SCORE_TPL_QUERY = "SELECT "
			+ "id,checkee,checker,item,target,weight,formula FROM ass_score_tpl";

	private final static String VALUE_QUERY = "SELECT "
			+ "id,mon,checkee,checker,item,target,unit,other,formula,data,result "
			+ "FROM ass_value ";

	private final static String VALUE_TPL_QUERY = "SELECT id,checkee,checker,item,target,"
			+ "unit,formula,other FROM ass_value_tpl";

	public AssScore[] getScores() {
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.execute(SCORE_QUERY + getWhereClause(true));
			AssScore[] scores = new AssScore[cnn.getRowCount()];
			for (int i = 0; i < scores.length; i++) {
				cnn.next();
				scores[i] = getScoreByConn(cnn);
			}
			return scores;
		} catch (Exception e) {
			e.printStackTrace();
			return new AssScore[0];
		}
	}

	public ScoreTpl[] getScoreTpls() {
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.execute(SCORE_TPL_QUERY + getWhereClause(false));

			ScoreTpl[] sts = new ScoreTpl[cnn.getRowCount()];
			for (int i = 0; i < sts.length; i++) {
				cnn.next();
				sts[i] = getScoreTplByConn(cnn);
			}
			return sts;
		} catch (Exception e) {
			e.printStackTrace();
			return new ScoreTpl[0];
		}
	}

	public AssValue[] getValues() {
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.execute(VALUE_QUERY + getWhereClause(true));
			AssValue[] values = new AssValue[cnn.getRowCount()];
			for (int i = 0; i < values.length; i++) {
				cnn.next();
				values[i] = getValueByConn(cnn);
			}
			return values;
		} catch (Exception e) {
			e.printStackTrace();
			return new AssValue[0];
		}
	}

	public ValueTpl[] getValueTpls() {
		try (HRMISConn cnn = new HRMISConn()) {
			String query = VALUE_TPL_QUERY + getWhereClause(false);
			cnn.execute(query);

			ValueTpl[] vts = new ValueTpl[cnn.getRowCount()];
			for (int i = 0; i < vts.length; i++) {
				cnn.next();
				vts[i] = getValueTplByConn(cnn);
			}
			return vts;
		} catch (Exception e) {
			e.printStackTrace();
			return new ValueTpl[0];
		}
	}

	public AssFactory setCheckeeId(int checkeeId) {
		this.checkeeId = checkeeId;
		return this;
	}

	public AssFactory setCheckerId(int checkerId) {
		this.checkerId = checkerId;
		return this;
	}

	public AssFactory setId(int id) {
		this.id = id;
		return this;
	}

	public AssFactory setMonId(int monId) {
		this.monId = monId;
		return this;
	}

	private static AssScore getScoreByConn(HRMISConn cnn) throws Exception {
		int id = cnn.getInt(1);
		AssDept checkee = AssDept.getInstance(cnn.getInt(3));
		AssDept checker = AssDept.getInstance(cnn.getInt(4));
		String item = cnn.getString(5);
		String target = cnn.getString(6);
		float weight = getFloat(cnn.getString(7));
		String formula = cnn.getString(8);
		AssItem result = new AssItem(AssMon.getInstance(cnn.getInt(2)),
				cnn.getString(9), getFloat(cnn.getString(10)));

		return new AssScore(id, checkee, checker, item, target, formula,
				weight, result);
	}

	private static ScoreTpl getScoreTplByConn(HRMISConn cnn) throws Exception {
		int id = cnn.getInt(1);
		AssDept checkee = AssDept.getInstance(cnn.getInt(2));
		AssDept checker = AssDept.getInstance(cnn.getInt(3));
		String item = getValue(cnn.getString(4));
		String target = getValue(cnn.getString(5));
		float weight = getFloat(cnn.getString(6));
		String formula = getValue(cnn.getString(7));

		return new ScoreTpl(id, checkee, checker, item, target, formula, weight);
	}

	private static AssValue getValueByConn(HRMISConn cnn) throws Exception {
		int id = cnn.getInt(1);
		AssDept checkee = AssDept.getInstance(cnn.getInt(3));
		AssDept checker = AssDept.getInstance(cnn.getInt(4));
		String item = cnn.getString(5);
		String target = cnn.getString(6);
		String unit = cnn.getString(7);
		String other = cnn.getString(8);
		String formula = cnn.getString(9);
		AssItem result = new AssItem(AssMon.getInstance(cnn.getInt(2)),
				cnn.getString(10), Float.parseFloat(cnn.getString(11)));

		return new AssValue(id, checkee, checker, item, target, formula, unit,
				other, result);
	}

	private static ValueTpl getValueTplByConn(HRMISConn cnn) throws Exception {
		int id = cnn.getInt(1);
		AssDept checkee = AssDept.getInstance(cnn.getInt(2));
		AssDept checker = AssDept.getInstance(cnn.getInt(3));
		String item = getValue(cnn.getString(4));
		String target = getValue(cnn.getString(5));
		String unit = getValue(cnn.getString(6));
		String formula = getValue(cnn.getString(7));
		String other = getValue(cnn.getString(8));

		return new ValueTpl(id, checkee, checker, item, target, formula, unit,
				other);
	}

	private static void setWherePrefix(StringBuilder s, boolean first) {
		s.append(first ? " WHERE " : " AND ");
	}

	private static String getValue(String str) {
		return str == null ? "" : str;
	}

	private static float getFloat(String str) {
		if (str == null || (!MyMath.isNumeric(str))) {
			return 0;
		} else {
			return Float.parseFloat(str);
		}
	}

	private String getWhereClause(boolean hasMon) {
		StringBuilder s = new StringBuilder();
		boolean first = true;
		if (id > 0) {
			s.append(" WHERE id=" + id);
			first = false;
		}
		if (checkeeId > 0) {
			setWherePrefix(s, first);
			first = false;
			s.append("checkee=" + checkeeId);
		}
		if (checkerId > 0) {
			setWherePrefix(s, first);
			first = false;
			s.append("checker=" + checkerId);
		}
		if (monId > 0 && hasMon) {
			setWherePrefix(s, first);
			first = false;
			s.append("mon=" + monId);
		}
		return s.toString();
	}

	public static void main(String[] args) {
		AssFactory factory = new AssFactory();
		System.out.println(factory.getValueTpls().length);
	}
}
