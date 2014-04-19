package qinyuan.hrmis.action.att.admin;

import qinyuan.hrmis.domain.user.AttAdmin;
import qinyuan.lib.web.MyBasicAction;

public class AttAdminAction extends MyBasicAction {

	private static final long serialVersionUID = 1L;
	protected AttAdmin attAdmin;

	protected boolean checkSession() {
		attAdmin = (AttAdmin) getSession("attAdmin");
		return attAdmin != null;
	}
}
