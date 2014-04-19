package qinyuan.hrmis.action.user;

import qinyuan.hrmis.domain.user.User;
import qinyuan.lib.web.MyBasicAction;

public class MdfPasswordAction extends MyBasicAction {

	private static final long serialVersionUID = 1L;

	public String execute() {
		String password = getParameter("password");
		String password2 = getParameter("password2");
		User visitor = (User) getSession("visitor");
		String resultInfo = "";
		if (password != null) {
			if (password.equals("")) {
				resultInfo = "密码不能为空";
			} else if (!password.equals(password2)) {
				resultInfo = "两次输入的密码要相同";
			} else {
				visitor.changePassword(password);
				resultInfo = "修改成功";
			}
		}
		setAttribute("result", resultInfo);
		return SUCCESS;
	}
}
