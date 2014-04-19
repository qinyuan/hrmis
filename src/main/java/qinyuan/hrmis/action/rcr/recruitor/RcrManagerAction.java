package qinyuan.hrmis.action.rcr.recruitor;

import qinyuan.hrmis.domain.rcr.UserPostUpdate;

public class RcrManagerAction extends RecruitorAction {

	private static final long serialVersionUID = 1L;

	public String execute() {
		if (!checkSession()) {
			return SUCCESS;
		}

		if (hasParameter("mdfSubmit")) {
			UserPostUpdate up = new UserPostUpdate();
			for (String key : getParameterNames()) {
				if (key.startsWith("user")) {
					int userId = parseInt(key.substring(4));
					up.add(userId, getIntArr(getParameters(key)));
				}
			}
			up.update();
		}

		return SUCCESS;
	}

	private static int[] getIntArr(String[] strs) {
		int[] intArr = new int[strs.length];
		for (int i = 0; i < intArr.length; i++) {
			intArr[i] = parseInt(strs[i]);
		}
		return intArr;
	}
}
