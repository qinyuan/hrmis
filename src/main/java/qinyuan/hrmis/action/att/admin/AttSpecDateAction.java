package qinyuan.hrmis.action.att.admin;

import java.util.ArrayList;
import java.util.List;

import qinyuan.hrmis.lib.data.HRMISConn;

public class AttSpecDateAction extends AttAdminAction {

	private static final long serialVersionUID = 1L;

	public String execute() {
		if (!checkSession()) {
			return SUCCESS;
		}

		// change the week
		String weekToAdd = getParameter("weekToAdd");
		if (weekToAdd != null) {
			if (weekToAdd.equals("1")) {
				attAdmin.nextWeek();
			} else if (weekToAdd.equals("-1")) {
				attAdmin.previousWeek();
			}
		}

		// delete special date
		String delDateval = getParameter("delDateval");
		if (delDateval != null) {
			try (HRMISConn cnn = new HRMISConn()) {
				cnn.execute("DELETE FROM spec_date WHERE dateval='"
						+ delDateval + "'");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String reason = getParameter("reason");
		if (reason != null) {
			List<String> dateList = new ArrayList<String>();
			for (String key : getParameterNames()) {
				if (key.startsWith("hid_btn_")) {
					dateList.add(key.substring(8));
				}
			}
			attAdmin.addSpecDates(dateList, reason);
		}

		return SUCCESS;
	}
}
