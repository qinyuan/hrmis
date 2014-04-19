package qinyuan.hrmis.action.ass.admin;

import java.util.Map;

import qinyuan.hrmis.lib.data.HRMISConn;

public class AssDealerAction extends AssAdminAction {

	private static final long serialVersionUID = 1L;

	public String execute() throws Exception {
		if (!checkSession()) {
			return SUCCESS;
		}

		if (hasParameter("assDealerSubmit")) {
			HRMISConn cnn = new HRMISConn();
			cnn.execute("TRUNCATE TABLE ass_dealer");
			cnn.close();
			Map<String, String[]> map = getParameterMap();
			for (String key : map.keySet()) {
				if (key.startsWith("user")) {
					String userId = key.substring(4);
					assAdmin.setDeptsOfAssDealer(parseInt(userId),
							parseInt(map.get(key)));
				}
			}
		}

		return SUCCESS;
	}
}
