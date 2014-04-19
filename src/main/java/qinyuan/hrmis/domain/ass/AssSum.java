package qinyuan.hrmis.domain.ass;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.web.html.Table;
import qinyuan.lib.web.html.TableData;
import qinyuan.lib.web.html.TableRow;

public class AssSum {

	private AssSum() {
	}

	public static Map<String, Double> getValueSumMap(int monId) {
		return getSumMap(monId, "ass_value");
	}

	public static Map<String, Double> getScoreSumMap(int monId) {
		return getSumMap(monId, "ass_score");
	}

	private static Map<String, Double> getSumMap(int monId, String table) {
		final String query = "SELECT deptname,SUM(result) FROM "
				+ "(SELECT checkee,result FROM " + table
				+ " WHERE mon=?) AS s "
				+ "INNER JOIN ass_dept AS d ON s.checkee=d.deptid "
				+ "GROUP BY checkee";
		Map<String, Double> map = new HashMap<String, Double>();
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare(query).setInt(1, monId).execute();
			while (cnn.next()) {
				map.put(cnn.getString(1), cnn.getDouble(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public static String getAssSumTable(int monId) {
		StringBuilder o = new StringBuilder();
		o.append(getTable(getScoreSumMap(monId), "分值考核体系"));
		o.append(getTable(getValueSumMap(monId), "金额考核体系"));
		return o.toString();
	}

	private static final int maxCol = 4;
	private static final DecimalFormat f = new DecimalFormat("#.##");

	public static Table getTable(Map<String, Double> map, String title) {
		Table table = new Table();
		if (map.size() == 0) {
			return table;
		}

		TableRow tr = new TableRow().add(new TableData().setText(
				"<b>" + title + "</b>").setAttr("colspan",
				String.valueOf(maxCol)));
		table.add(tr);
		String result;
		int colIndex = 0;
		for (String key : map.keySet()) {
			if (colIndex == 0) {
				tr = new TableRow();
				table.add(tr);
			}
			result = f.format(map.get(key));
			String space = StringUtils.repeat("&nbsp;",
					Math.max(0, 20 - key.length() * 2 - result.length()));
			tr.add(key + space + result);
			if (++colIndex == maxCol) {
				colIndex = 0;
			}
		}
		while (colIndex > 0 && colIndex < maxCol) {
			tr.add("");
			colIndex++;
		}
		return table;
	}
}
