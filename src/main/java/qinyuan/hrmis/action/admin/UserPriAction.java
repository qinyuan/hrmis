package qinyuan.hrmis.action.admin;

import qinyuan.hrmis.lib.data.HRMISConn;

public class UserPriAction extends AdminAction {

	private static final long serialVersionUID = 1L;

	public String execute() throws Exception {
		if(!checkSession()){
			return SUCCESS;
		}
		
		if (!hasSession("admin")) {
			return SUCCESS;
		}

		try (HRMISConn cnn = new HRMISConn()) {
			cnn.execute("TRUNCATE TABLE user_pri");

			StringBuilder insertQuery = new StringBuilder(
					"INSERT INTO user_pri(userId,pri_id) VALUES");
			boolean hasUser = false;
			for (String key : getParameterNames()) {
				if (key.startsWith("user")) {
					hasUser = true;
					insertQuery.append(getValues(key));
				}
			}
			if (hasUser) {
				insertQuery.deleteCharAt(insertQuery.length() - 1);
				cnn.execute(insertQuery.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private String getValues(String key) {
		StringBuilder output = new StringBuilder();
		String userId = key.substring(4);
		String[] priIds = getParameters(key);
		for (String priId : priIds) {
			output.append("(" + userId + "," + priId + "),");
		}
		return output.toString();
	}
}
