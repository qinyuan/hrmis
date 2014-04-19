package qinyuan.hrmis.action.admin;

import qinyuan.hrmis.domain.user.Admin;
import qinyuan.lib.web.MyBasicAction;

public class AdminAction extends MyBasicAction {

	private static final long serialVersionUID = 1L;
	protected Admin admin;

	protected boolean checkSession() {
		admin = (Admin) getSession("admin");
		return admin != null;
	}
}
