package qinyuan.hrmis.action.ass.admin;

import qinyuan.hrmis.domain.ass.AssDept;

public class DeptListAction extends AssAdminAction {

	private static final long serialVersionUID = 1L;

	public String execute() {
		if (!checkSession()) {
			return SUCCESS;
		}

		if (hasParameter("addSubmit")) {
			String deptname = getParameter("deptname");
			if (deptname != null) {
				if (AssDept.contains(deptname)) {
					setAttribute("result", "部门已存在，不允许再添加");
				} else {
					AssDept.add(deptname);
				}
			}
		} else if (hasParameter("delSubmit")) {
			deleteDept();
		} else {
			String mdfId = getGetParameter("mdfId");
			String mdfName = getGetParameter("mdfName");
			if (isNumeric(mdfId) && !empty(mdfName)) {
				AssDept.modify(parseInt(mdfId), mdfName);
			}
		}

		return SUCCESS;
	}

	private void deleteDept() {
		for (String key : getParameterNames()) {
			if (key.startsWith("chk")) {
				String id = key.substring(3);
				if (isNumeric(id)) {
					AssDept dept = AssDept.getInstance(parseInt(id));
					if (dept.isUsed()) {
						setAttribute("result", "'" + dept.getName()
								+ "'已经被使用，不能删除");
					} else {
						AssDept.delete(dept.getId());
					}
				}
			}
		}
	}
}