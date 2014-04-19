package qinyuan.hrmis.action.att.admin;

import qinyuan.hrmis.domain.att.admin.SpecEmp;

public class SpecEmpAction extends AttAdminAction {

	private static final long serialVersionUID = 1L;

	public String execute() {
		if (!checkSession()) {
			return SUCCESS;
		}

		// set department
		String deptId = getParameter("deptId");
		if (isNumeric(deptId)) {
			attAdmin.setDeptId(parseInt(deptId));
		}

		// delete special employee
		for (String key : getParameterNames()) {
			if (key.startsWith("chk")) {
				String delUserId = key.substring(3);
				if (isNumeric(delUserId)) {
					SpecEmp.delete(parseInt(delUserId));
				}
			}
		}

		// modify special employee
		if (hasParameter("mdfReason")) {
			String userId = getParameter("userId");
			String reason = getGetParameter("reason");
			SpecEmp.modify(parseInt(userId), reason);
		}

		// add special employee
		if (hasParameter("addUserSubmit")) {
			String userId = getParameter("userId");
			String reason = getParameter("reason");
			if (isNumeric(userId)) {
				SpecEmp.add(parseInt(userId), reason);
			}
		}

		return SUCCESS;
	}

}
