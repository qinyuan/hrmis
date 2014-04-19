package qinyuan.hrmis.action.admin;

public class UserListAction extends AdminAction {

	private static final long serialVersionUID = 1L;

	public String execute() {
		if (!checkSession()) {
			return SUCCESS;
		}

		// add user
		String username = getGetParameter("username");
		String password = getParameter("password");
		String nickname = getGetParameter("nickname");
		if (username != null && password != null && nickname != null) {
			setAttribute("result", admin.addUser(username, password, nickname));
		}

		// delete user
		if (hasParameter("delUser")) {
			deleteUser();
		}

		return SUCCESS;
	}

	private void deleteUser() {
		for (String str : getParameterNames()) {
			if (str.startsWith("user")) {
				String userId = str.substring(4);
				if (isNumeric(userId)) {
					String result = admin.deleteUser(parseInt(userId));
					if (result != null) {
						setAttribute("result", result);
						return;
					}
				}
			}
		}
	}
}
