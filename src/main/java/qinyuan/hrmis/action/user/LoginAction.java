package qinyuan.hrmis.action.user;

import qinyuan.hrmis.domain.user.Admin;
import qinyuan.hrmis.domain.user.AssAdmin;
import qinyuan.hrmis.domain.user.AssDealer;
import qinyuan.hrmis.domain.user.AttAdmin;
import qinyuan.hrmis.domain.user.AttManager;
import qinyuan.hrmis.domain.user.RcrManager;
import qinyuan.hrmis.domain.user.User;
import qinyuan.lib.web.MyBasicAction;

public class LoginAction extends MyBasicAction {
	private static final long serialVersionUID = 1L;

	public String execute() throws Exception {
		// get user name and password
		String username = getParameter("username");
		String password = getParameter("password");

		// check if the user name or password is null
		if (username == null || password == null) {
			return ERROR;
		}

		// create object of visitor
		User visitor = User.newInstance(username, password);
		if (visitor == null) {
			return ERROR;
		}

		if (checkIdentCode()) {
			setSession("visitor", visitor);
			createActor(visitor);
			return SUCCESS;
		} else {
			setAttribute("identCodeError", Boolean.valueOf(true));
			return ERROR;
		}
	}

	private boolean checkIdentCode() {
		String identCode = (String) getSession("identCode");
		if (identCode == null)
			return true;

		String inputIdentCode = getParameter("identCode");
		if (inputIdentCode == null)
			return false;

		return identCode.equals(inputIdentCode);
	}

	/**
	 * create actor such as admin, attManager, recruitor, recruitManager then
	 * save them to session
	 * 
	 * @param visitor
	 * @throws Exception
	 */
	private void createActor(User visitor) throws Exception {
		if (visitor.hasPrivilege(1)) {
			setSession("admin", Admin.createAdmin(visitor));
		}
		if (visitor.hasPrivilege(2)) {
			setSession("attAdmin", AttAdmin.createAttAdmin(visitor));
		}
		if (visitor.hasPrivilege(3)) {
			setSession("attManager", AttManager.newInstance(visitor));
		}
		if (visitor.hasPrivilege(4)) {
			setSession("assAdmin", AssAdmin.newAssAdmin(visitor));
		}
		if (visitor.hasPrivilege(5)) {
			setSession("assDealer", AssDealer.newAssDealer(visitor));
		}
		if (visitor.hasPrivilege(6)) {
			setSession("recruitor",
					qinyuan.hrmis.domain.user.Recruitor
							.createRecruitor(visitor));
		}
		if (visitor.hasPrivilege(7)) {
			setSession("rcrManager", RcrManager.createRcrManager(visitor));
		}
		if (visitor.hasPrivilege(8)) {
			User user = User.newInstance(visitor.getUserid());
			user.setPriDesc("参考手册");
			setSession("manual", user);
		}
	}
}
