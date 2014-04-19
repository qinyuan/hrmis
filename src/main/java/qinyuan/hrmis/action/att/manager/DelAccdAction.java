package qinyuan.hrmis.action.att.manager;

import qinyuan.hrmis.domain.user.AttManager;

public class DelAccdAction extends AttAction {

	private static final long serialVersionUID = 1L;

	public String execute() throws Exception {
		if(!checkSession())
			return SUCCESS;
		
		String accdid = getParameter("accdid");
		if (accdid != null) {
			AttManager attManager = (AttManager) getSession("attManager");
			attManager.deleteAccd(parseInt(accdid));
		}
		return SUCCESS;
	}
}
