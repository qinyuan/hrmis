package qinyuan.hrmis.action.att.manager;

import qinyuan.hrmis.domain.user.AttManager;
import qinyuan.lib.web.MyBasicAction;

public class AttAction extends MyBasicAction {

	private static final long serialVersionUID = 1L;

	protected AttManager attManager;

	protected boolean checkSession() {
		attManager = (AttManager) getSession("attManager");
		return attManager != null;
	}

	protected void changeDefUser() throws Exception {
		// set default user id
		if (hasParameter("userId")) {
			String userIdStr = getParameter("userId");
			attManager.setDefaultUserId(parseInt(userIdStr));
		}
	}

	protected void changeDept() throws Exception {
		String deptIndex = getParameter("deptIndex");
		if (deptIndex != null) {
			attManager.setDeptIndex(parseInt(deptIndex));
		}
	}

	protected void changeEndDate() throws Exception {
		// set end date
		if (hasParameter("endDate")) {
			String endDate = getParameter("endDate");
			attManager.setEndDate(endDate);
		}
	}

	protected void changeEndTime() {
		String endTime = getParameter("endTime");
		if (endTime != null) {
			attManager.setEndTime(endTime);
		}
	}

	protected void changeStartDate() throws Exception {
		// set start date
		if (hasParameter("startDate")) {
			attManager.setStartDate(getParameter("startDate"));
		}
	}

	protected void changeStartTime() {
		String startTime = getParameter("startTime");
		if (startTime != null) {
			attManager.setStartTime(startTime);
		}
	}
}
