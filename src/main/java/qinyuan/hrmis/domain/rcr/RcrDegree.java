package qinyuan.hrmis.domain.rcr;

import java.util.HashMap;
import java.util.Map;

import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.web.html.Select;

public class RcrDegree {

	private int id;
	private String name;

	public RcrDegree(int id, String name) {
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
			cnn.prepare("SELECT * FROM rcr_resume WHERE degree_id=?")
					.setInt(1, id).execute();
			return cnn.next();
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}

	public String toString() {
		return getName();
	}

	private static Map<Integer, String> map;

	public static boolean addDegree(String degreeName) {
		String query = "INSERT INTO rcr_degree(degree) VALUES(?)";
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare(query).setString(1, degreeName).execute();
			refresh();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean deleteDegree(int degreeId) {
		String query = "DELETE FROM rcr_degree WHERE degree_id=" + degreeId;
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.execute(query);
			refresh();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static RcrDegree getDegree(int degreeId) {
		if (map == null)
			refresh();

		if (map == null)
			return null;

		String degreeName = map.get(degreeId);
		if (degreeName == null) {
			return null;
		} else {
			return new RcrDegree(degreeId, degreeName);
		}
	}

	public static RcrDegree[] getDegrees() {
		if (map == null)
			refresh();

		if (map == null || map.size() == 0) {
			return new RcrDegree[0];
		}

		int i = 0;
		RcrDegree[] degrees = new RcrDegree[map.size()];
		for (int key : map.keySet()) {
			degrees[i++] = getDegree(key);
		}
		return degrees;
	}

	public static Select getSelect(int defDegreeId) {
		RcrDegree[] degrees = getDegrees();
		Select select = new Select();
		select.setId("degreeId");
		select.select(defDegreeId);
		for (RcrDegree degree : degrees) {
			select.addOption(degree.getId(), degree.getName());
		}
		return select;
	}

	public static boolean hasDegree(String degreeName) {
		return map.containsValue(degreeName);
	}

	public static boolean mdfDegree(int degreeId, String degreeName) {
		String query = "UPDATE rcr_degree SET degree='" + degreeName
				+ "' WHERE degree_id=" + degreeId;
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.execute(query);
			refresh();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void refresh() {
		map = new HashMap<Integer, String>();
		try (HRMISConn cnn = new HRMISConn()) {
			String query = "SELECT degree_id,degree FROM rcr_degree";
			cnn.execute(query);
			while (cnn.next()) {
				map.put(cnn.getInt(1), cnn.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
			map = null;
		}
	}
}
