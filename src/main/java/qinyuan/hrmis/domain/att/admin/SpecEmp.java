package qinyuan.hrmis.domain.att.admin;

import java.util.ArrayList;
import java.util.List;

import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.db.HbnConn;

public class SpecEmp {

	private int userId;
	private String reason;

	public int getUserId() {
		return userId;
	}

	public SpecEmp setUserId(int userId) {
		this.userId = userId;
		return this;
	}

	public String getReason() {
		return reason;
	}

	public SpecEmp setReason(String reason) {
		this.reason = reason;
		return this;
	}

	public String toString() {
		return "userId:" + userId + ",reason:" + reason;
	}

	public static void delete(int userId) {
		try (HbnConn cnn = new HbnConn()) {
			SpecEmp se = cnn.get(SpecEmp.class, userId);
			cnn.delete(se);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void modify(int userId, String reason) {
		try (HbnConn cnn = new HbnConn()) {
			SpecEmp se = cnn.get(SpecEmp.class, userId);
			if (se != null) {
				se.setReason(reason);
				cnn.update(se);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void add(int userId, String reason) {
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare("INSERT INTO spec_emp(userid,specreason) VALUES(?,?)")
					.setInt(1, userId).setString(2, reason).execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SpecEmp[] getInstancesByDeptId(int deptId) {
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare(
					"SELECT userid,specreason FROM spec_emp WHERE userid IN "
							+ "(SELECT userid FROM dept_user WHERE deptid=?)")
					.setInt(1, deptId).execute();
			SpecEmp[] emps = new SpecEmp[cnn.getRowCount()];
			int i = 0;
			while (cnn.next()) {
				emps[i++] = new SpecEmp().setUserId(cnn.getInt(1)).setReason(
						cnn.getString(2));
			}
			return emps;
		} catch (Exception e) {
			e.printStackTrace();
			return new SpecEmp[0];
		}
	}

	public static List<SpecEmp> getInstances() {
		try (HbnConn cnn = new HbnConn()) {
			List<SpecEmp> list = cnn.createList(SpecEmp.class, "FROM SpecEmp");
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<SpecEmp>();
		}
	}

	public static SpecEmp getInstance(int userId) {
		try (HbnConn cnn = new HbnConn()) {
			return cnn.get(SpecEmp.class, userId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		// List<SpecEmp> list = getInstances();
		// for (SpecEmp sp : list) {
		// System.out.println(sp);
		// }

		SpecEmp[] emps = getInstancesByDeptId(1);
		System.out.println(emps.length);
		for (SpecEmp sp : emps) {
			System.out.println(sp);
		}
	}

}