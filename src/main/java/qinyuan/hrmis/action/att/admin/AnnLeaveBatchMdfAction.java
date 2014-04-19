package qinyuan.hrmis.action.att.admin;

import qinyuan.hrmis.domain.att.admin.SimpleAnnLeaveFactory;
import qinyuan.hrmis.lib.data.HRMISConn;

public class AnnLeaveBatchMdfAction extends AttAdminAction {

	private static final long serialVersionUID = 1L;

	public String execute() {
		if (!checkSession()) {
			return SUCCESS;
		}

		if (hasParameter("filterSubmit")) {
			SimpleAnnLeaveFactory saf = attAdmin.getAnnLeaveFactory();

			String deptId = getParameter("deptId");
			attAdmin.setDeptId(empty(deptId) ? 1 : parseInt(deptId));

			String badgenumber = getParameter("badgenumber");
			saf.setBadgenumber(empty(badgenumber) ? -1 : parseInt(badgenumber));

			saf.setInsurePlace(getParameter("insurePlace"));

			saf.setJoinDate(getParameter("sJoinDate"),
					getParameter("eJoinDate"));

			saf.setWorkDate(getParameter("sWorkDate"),
					getParameter("eWorkDate"));

			String sUsabledDays = getParameter("sUsabledDays");
			String eUsabledDays = getParameter("eUsabledDays");
			saf.setUsabledDays(empty(sUsabledDays) ? -1
					: parseInt(sUsabledDays), empty(eUsabledDays) ? -1
					: parseInt(eUsabledDays));
		}

		StringBuilder query = new StringBuilder();
		if (hasParameter("addSubmit")) {
			String addValue = getParameter("addValue");
			if (isNumeric(addValue)) {
				query.append("UPDATE annual_leave SET total_leave=total_leave+"
						+ addValue);
			}
		} else if (hasParameter("reduceSubmit")) {
			String reduceValue = getParameter("reduceValue");
			if (isNumeric(reduceValue)) {
				query.append("UPDATE annual_leave SET total_leave=total_leave-"
						+ reduceValue);
			}
		} else if (hasParameter("setSubmit")) {
			String setValue = getParameter("setValue");
			if (isNumeric(setValue)) {
				query.append("UPDATE annual_leave SET total_leave=" + setValue);
			}
		}
		if (query.length() > 0) {
			query.append(" WHERE badgenumber IN (");
			for (String key : getParameterNames()) {
				if (key.startsWith("chk")) {
					query.append(key.substring(3) + ",");
				}
			}
			if (query.charAt(query.length() - 1) == ',') {
				query.deleteCharAt(query.length() - 1);
				query.append(")");
				try (HRMISConn cnn = new HRMISConn()) {
					cnn.execute(query);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return SUCCESS;
	}
}