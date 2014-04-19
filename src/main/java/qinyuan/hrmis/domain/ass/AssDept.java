package qinyuan.hrmis.domain.ass;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.web.html.Select;

public class AssDept {

	private int id;
	private String name;

	public AssDept(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isUsed() {
		try (HRMISConn cnn = new HRMISConn()) {
			String[] tables = { "ass_score", "ass_score_tpl", "ass_value",
					"ass_value_tpl" };
			for (String table : tables) {
				String query = "SELECT * FROM " + table + " WHERE checkee="
						+ id + " OR checker=" + id;
				cnn.execute(query);
				if (cnn.next()) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}

	public Select toSelect(String tagId) {
		return getSelect(tagId).select(id);
	}

	public Select toSelectAll(String tagId) {
		return getSelectAll(tagId).select(id);
	}

	public String toString() {
		return name;
	}

	public static boolean add(String deptName) {
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.execute("INSERT INTO ass_dept(deptname) VALUES('" + deptName
					+ "')");
			refresh();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean contains(String deptName) {
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare("SELECT * FROM ass_dept WHERE deptname=?")
					.setString(1, deptName).execute();
			return cnn.next();
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}

	public static boolean delete(int deptId) {
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare("DELETE FROM ass_dept WHERE deptId=?")
					.setInt(1, deptId).execute();
			refresh();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean modify(int deptId, String deptName) {
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare("UPDATE ass_dept SET deptname=? WHERE deptid=?")
					.setString(1, deptName).setInt(2, deptId).execute();
			refresh();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Select getSelect(String tagId) {
		Select select = new Select().setId(tagId);
		for (AssDept dept : getInstances()) {
			select.addOption(dept.getId(), dept.getName());
		}
		return select;
	}

	public static Select getSelectAll(String tagId) {
		return getSelect(tagId).appendOption("-1", "(全部)");
	}

	public static AssDept getInstance(int deptId) {
		if (map == null)
			initialize();

		String deptName = map.get(deptId);
		return deptName == null ? null : new AssDept(deptId, deptName);
	}

	public static AssDept[] getInstances() {
		if (map == null)
			initialize();

		AssDept[] depts = new AssDept[map.size()];
		int i = 0;
		for (Entry<Integer, String> entry : map.entrySet()) {
			depts[i++] = new AssDept(entry.getKey(), entry.getValue());
		}
		return depts;
	}

	private static Map<Integer, String> map;

	private static void initialize() {
		map = new HashMap<Integer, String>();
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.execute("SELECT deptid,deptname FROM ass_dept");
			while (cnn.next()) {
				map.put(cnn.getInt(1), cnn.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
			refresh();
		}
	}

	private static void refresh() {
		map = null;
	}

	public static void main(String[] args) {
		for (AssDept dept : getInstances()) {
			System.out.println(dept);
		}
	}
}
