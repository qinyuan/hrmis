package qinyuan.hrmis.domain.att.emp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.db.HbnConn;
import qinyuan.lib.web.html.Select;

public class Dept {

	private int id;
	private String desc;

	public int getId() {
		return id;
	}

	public Dept setId(int id) {
		this.id = id;
		return this;
	}

	public String getDesc() {
		return desc;
	}

	public Dept setDesc(String desc) {
		this.desc = desc;
		return this;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Dept)) {
			return false;
		} else {
			return id == ((Dept) obj).id;
		}
	}

	public int hashCode() {
		return Integer.valueOf(id).hashCode();
	}

	public String toString() {
		return "id:" + id + ",desc:" + desc;
	}

	public boolean containEmp(int userId) {
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare("SELECT * FROM dept_user WHERE deptid=? AND userid=?")
					.setInt(1, id).setInt(2, userId).execute();
			return cnn.next();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Select getEmpSelect(int userId) {
		Employee[] emps = getEmps();
		Select select = new Select().setId("userId").select(userId);
		for (Employee emp : emps) {
			select.addOption(emp.getId(),
					emp.getBadgenumber() + " " + emp.getName());
		}
		return select;
	}

	public Employee[] getEmps() {
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare(
					"SELECT userid,badgenumber,username,dir_deptid "
							+ "FROM dept_user WHERE deptid=? ORDER BY badgenumber")
					.setInt(1, id).execute();
			Employee[] emps = new Employee[cnn.getRowCount()];
			int i = 0;
			while (cnn.next()) {
				emps[i++] = new Employee().setId(cnn.getInt(1))
						.setBadgenumber(cnn.getString(2))
						.setName(cnn.getString(3)).setDeptId(cnn.getInt(4));
			}
			return emps;
		} catch (Exception e) {
			e.printStackTrace();
			return new Employee[0];
		}
	}

	private static Map<Integer, Dept> map;

	public static Dept[] getInstances() {
		if (map == null) {
			initializeMap();
		}

		Dept[] depts = new Dept[map.size()];
		int i = 0;
		for (int key : map.keySet()) {
			depts[i++] = map.get(key);
		}
		return depts;
	}

	private static void initializeMap() {
		map = new HashMap<Integer, Dept>();
		try (HbnConn cnn = new HbnConn()) {
			List<Dept> list = cnn.createList(Dept.class, "FROM Dept");
			for (Dept dept : list) {
				map.put(dept.id, dept);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Dept getInstance(int deptId) {
		if (map == null) {
			initializeMap();
		}
		return map.get(deptId);
	}
}
