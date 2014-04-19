package qinyuan.hrmis.action.att.admin;

import qinyuan.hrmis.action.admin.AdminAction;
import qinyuan.hrmis.domain.att.admin.SimpleAttManager;

/**
 * this class set the department of each attManager
 * 
 * @author qinyuan
 * 
 */
public class AttDirectorAction extends AdminAction {

	private static final long serialVersionUID = 1L;

	public String execute() throws Exception {
		if (!checkSession()) {
			return SUCCESS;
		}

		if (!hasParameter("setDeptSubmit")) {
			return SUCCESS;
		}

		String userId, deptId;
		for (String key : getParameterNames()) {
			if (key.startsWith("user")) {
				userId = key.substring(4);
				deptId = getParameter(key);
				if (isNumeric(userId) && isNumeric(deptId)) {
					SimpleAttManager
							.setDept(parseInt(userId), parseInt(deptId));
				}
			}
		}
		SimpleAttManager.updateDepts();
		return SUCCESS;
	}
}
