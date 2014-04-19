package qinyuan.hrmis.action.rcr.recruitor;

import qinyuan.hrmis.domain.user.Recruitor;
import qinyuan.lib.web.MyBasicAction;

public class RecruitorAction extends MyBasicAction {

	private static final long serialVersionUID = 1L;

	protected Recruitor recruitor;

	protected boolean checkSession() {
		recruitor = (Recruitor) getSession("recruitor");
		return recruitor != null;
	}
}
