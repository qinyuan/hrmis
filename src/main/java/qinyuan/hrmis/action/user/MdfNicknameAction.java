package qinyuan.hrmis.action.user;

import qinyuan.hrmis.domain.user.User;
import qinyuan.lib.web.MyBasicAction;

public class MdfNicknameAction extends MyBasicAction {

	private static final long serialVersionUID = 1L;

	public String execute() {
		String newNickname = getParameter("nickName");
		User visitor = (User) getSession("visitor");
		String resultInfo = "";
		if (newNickname != null) {
			if (newNickname.equals("")) {
				resultInfo = "昵称不能为空";
			} else {
				visitor.changeNickName(newNickname);
				resultInfo = "修改成功";
			}
		}
		setAttribute("result", resultInfo);
		return SUCCESS;
	}
}
