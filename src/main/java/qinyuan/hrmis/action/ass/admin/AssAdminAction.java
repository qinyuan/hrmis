package qinyuan.hrmis.action.ass.admin;

import qinyuan.hrmis.domain.user.AssAdmin;
import qinyuan.lib.web.MyBasicAction;

public class AssAdminAction extends MyBasicAction {

	private static final long serialVersionUID = 1L;

	protected AssAdmin assAdmin;

	protected boolean checkSession() {
		assAdmin = (AssAdmin) getSession("assAdmin");
		return assAdmin != null;
	}

	protected void changeCheckee() {
		String checkeeId = getParameter("checkee");
		if (isNumeric(checkeeId)) {
			assAdmin.setCheckee(parseInt(checkeeId));
		}
	}

	protected void changeChecker() {
		String checkerId = getParameter("checker");
		if (isNumeric(checkerId)) {
			assAdmin.setChecker(parseInt(checkerId));
		}
	}
}
