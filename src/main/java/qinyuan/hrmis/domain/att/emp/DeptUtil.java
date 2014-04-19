package qinyuan.hrmis.domain.att.emp;

import java.util.ArrayList;
import java.util.List;

import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.web.html.Select;

public class DeptUtil {

	private DeptUtil() {
	}

	public static Select getSelect(int deptId, String tagId) {
		Select select = new Select();
		select.setId(tagId);
		Dept[] depts = getDepts(Dept.getInstance(1));
		for (Dept d : depts) {
			select.addOption(d.getId(), d.getDesc());
		}
		select.select(deptId);
		return select;
	}

	public static String getSelect(Dept[] depts, int selectedDeptId,
			String tagId) {
		Select select = new Select().setId(tagId);
		for (int i = 0; i < depts.length; i++) {
			select.addOption(i, depts[i].getDesc());
		}
		return select.select(selectedDeptId).toString();
	}

	public static String getDeptList(Dept[] depts, String linkPage) {
		StringBuilder o = new StringBuilder();
		for (int i = 0; i < depts.length; i++) {
			o.append("<p><a href='" + linkPage + "?deptIndex=" + i + "'>"
					+ depts[i].getDesc() + "</a></p>");
		}
		return o.toString();
	}

	public static Dept[] getDepts(Dept dept) {
		if (dept == null) {
			return new Dept[0];
		}
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare("SELECT deptid,deptname FROM department WHERE supdeptid=?");
			List<Dept> list = new ArrayList<Dept>();
			list.add(dept);
			list.addAll(getSubDept(dept.getId(), cnn));
			Dept[] depts = new Dept[list.size()];
			for (int i = 0; i < list.size(); i++) {
				depts[i] = list.get(i);
			}
			return depts;
		} catch (Exception e) {
			e.printStackTrace();
			return new Dept[0];
		}
	}

	private static List<Dept> getSubDept(int deptId, HRMISConn cnn)
			throws Exception {
		List<Dept> list = new ArrayList<Dept>();
		List<Dept> dirList = getDirSubDept(deptId, cnn);
		if (dirList.size() > 0) {
			for (Dept dept : dirList) {
				list.add(dept);
				list.addAll(getSubDept(dept.getId(), cnn));
			}
		}
		return list;
	}

	private static List<Dept> getDirSubDept(int deptId, HRMISConn cnn)
			throws Exception {
		List<Dept> list = new ArrayList<>();
		cnn.setInt(1, deptId).execute();
		while (cnn.next()) {
			list.add(new Dept().setId(cnn.getInt(1)).setDesc(cnn.getString(2)));
		}
		return list;
	}

	public static void main(String[] args) throws Exception {
		HRMISConn cnn = new HRMISConn();
		Dept[] depts = getDepts(Dept.getInstance(1));
		for (Dept dept : depts) {
			System.out.println(dept);
		}
		System.out.println(depts.length);
		cnn.close();
	}
}