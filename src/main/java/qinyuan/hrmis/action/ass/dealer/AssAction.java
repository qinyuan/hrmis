package qinyuan.hrmis.action.ass.dealer;

import qinyuan.hrmis.domain.user.AssDealer;
import qinyuan.lib.lang.MyMath;
import qinyuan.lib.web.MyBasicAction;

public class AssAction extends MyBasicAction {

	private static final long serialVersionUID = 1L;

	protected AssDealer assDealer;

	protected void changeDept() {
		String deptIdStr = getParameter("deptId");
		if (deptIdStr != null && MyMath.isNumeric(deptIdStr)) {
			assDealer.setCurrentDept(parseInt(deptIdStr));
		}
	}

	protected void changeMon() {
		String monIdStr = getParameter("monId");
		if (monIdStr != null && MyMath.isNumeric(monIdStr)) {
			assDealer.setMon(parseInt(monIdStr));
		}
	}

	protected boolean checkSession() {
		assDealer = (AssDealer) getSession("assDealer");
		return assDealer != null;
	}
}
