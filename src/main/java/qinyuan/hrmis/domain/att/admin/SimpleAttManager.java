package qinyuan.hrmis.domain.att.admin;

import java.util.ArrayList;
import java.util.List;

import qinyuan.hrmis.domain.admin.PriUser;
import qinyuan.hrmis.domain.admin.Privilege;
import qinyuan.hrmis.domain.admin.SimpleUser;
import qinyuan.hrmis.domain.att.emp.Dept;
import qinyuan.hrmis.domain.att.emp.DeptUtil;
import qinyuan.lib.db.HbnConn;
import qinyuan.lib.web.html.TableRow;

public class SimpleAttManager {

	private SimpleUser su;
	private Dept dept;

	public SimpleAttManager(int userId) {
		this(SimpleUser.newInstance(userId));
	}

	public SimpleAttManager(SimpleUser su) {
		this.su = su;

		HbnConn cnn = new HbnConn();
		final String query = "SELECT deptId FROM att_manager WHERE userId="
				+ su.getUserId();
		List<?> list = cnn.setSQL(query).setInt("deptId").list();
		if (list.size() == 1) {
			dept = Dept.getInstance((Integer) list.get(0));
		}
		try {
			cnn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Dept getDept() {
		return dept;
	}

	public int getDeptId() {
		if (dept == null) {
			return -1;
		}
		return dept.getId();
	}

	public String getDeptName() {
		if (dept == null) {
			return "";
		}
		return dept.getDesc();
	}

	public TableRow toTableRow() {
		TableRow tr = new TableRow();

		tr.add(su.getUsername());
		tr.add(su.getNickname());
		if (dept == null) {
			tr.add(DeptUtil.getSelect(-1, "user" + su.getUserId())
					.appendOption(-1, ""));
		} else {
			tr.add(DeptUtil.getSelect(dept.getId(), "user" + su.getUserId())
					.appendOption(-1, ""));
		}

		return tr;
	}

	private static List<SimpleAttManager> sams;

	public static List<SimpleAttManager> getInstances() {
		if (sams == null) {
			initializeArray();
		}
		return sams;
	}

	private static List<int[]> userDeptPair = new ArrayList<int[]>();;

	public static void setDept(int userId, int deptId) {
		if (userId > 0 && deptId > 0) {
			userDeptPair.add(new int[] { userId, deptId });
		}
	}

	public static void updateDepts() {
		sams = null;
		HbnConn cnn = new HbnConn();
		cnn.setSQL("DELETE FROM att_manager").executeUpdate();

		if (userDeptPair.size() == 0) {
			try {
				cnn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}

		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO att_manager(userId,deptId) VALUES");
		for (int[] pair : userDeptPair) {
			query.append("(" + pair[0] + "," + pair[1] + "),");
		}
		query.deleteCharAt(query.length() - 1);
		cnn.setSQL(query.toString()).executeUpdate();

		try {
			cnn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		userDeptPair = new ArrayList<int[]>();
	}

	/**
	 * this method initialize sams
	 */
	private static void initializeArray() {
		PriUser[] pus = PriUser.getInstances();
		sams = new ArrayList<SimpleAttManager>();
		for (PriUser pu : pus) {
			if (pu.hasPri(Privilege.ATT_PRI_ID)) {
				sams.add(new SimpleAttManager(pu.getSimpleUser()));
			}
		}
	}

	public static void main(String[] args) {
		List<SimpleAttManager> sams = SimpleAttManager.getInstances();
		int userIndex = 1;
		for (SimpleAttManager sam : sams) {
			System.out.println("userIndex: " + userIndex++);
			System.out.println(sam.toTableRow());
		}
	}
}
