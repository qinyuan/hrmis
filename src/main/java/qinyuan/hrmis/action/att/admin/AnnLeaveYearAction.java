package qinyuan.hrmis.action.att.admin;

import java.io.IOException;
import qinyuan.hrmis.domain.att.admin.SimpleAnnLeave;
import qinyuan.lib.date.MyDate;
import qinyuan.lib.web.WebFileIO;

public class AnnLeaveYearAction extends AttAdminAction {

	private static final long serialVersionUID = 1L;

	private final static String ANN_LEAVE_CONF_FILE = "/WEB-INF/conf/annLeaveYear.txt";

	public String execute() {
		if (!checkSession()) {
			return SUCCESS;
		}

		changeDeptId();
		changeYear();
		deleteAnnLeave();

		String badgenumber = null, workDate = null, joinDate = null, insurePlace = null, usabledDays = null;
		String result = null;
		if (hasParameter("mdfAnnLeaveSubmit")) {
			badgenumber = getParameter("mdf_badgenumber");
			workDate = getParameter("mdf_workDate");
			joinDate = getParameter("mdf_joinDate");
			insurePlace = getParameter("mdf_insurePlace");
			usabledDays = getParameter("mdf_usabledDays");
			result = "mdfResult";
		} else if (hasParameter("addAnnLeaveSubmit")) {
			badgenumber = getParameter("badgenumber");
			workDate = getParameter("workDate");
			joinDate = getParameter("joinDate");
			insurePlace = getParameter("insurePlace");
			usabledDays = getParameter("usabledDays");
			result = "addResult";
		}

		if (result != null) {
			if (!isNumeric(badgenumber)) {
				setAttribute(result, "工号格式错误");
			} else if (!MyDate.isDate(workDate)) {
				setAttribute(result, "参加工作时间格式错误");
			} else if (!MyDate.isDate(joinDate)) {
				setAttribute(result, "入司时间格式错误");
			} else if (!isNumeric(usabledDays)) {
				setAttribute(result, "可用天数格式错误");
			} else {
				if (result.equals("mdfResult")) {
					SimpleAnnLeave.modify(badgenumber, workDate, joinDate,
							insurePlace, usabledDays);
				} else if (result.equals("addResult")) {
					SimpleAnnLeave.add(badgenumber, workDate, joinDate,
							insurePlace, usabledDays);
				}
			}
		}

		return SUCCESS;
	}

	private void deleteAnnLeave() {
		if (!hasParameter("delAnnLeave")) {
			return;
		}

		for (String str : getParameterNames()) {
			if (str.startsWith("chk")) {
				String badgenumber = str.substring(3);
				if (isNumeric(badgenumber)) {
					SimpleAnnLeave.delete(badgenumber);
				}
			}
		}
	}

	private void changeYear() {
		String year = getParameter("year");
		if (isNumeric(year)) {
			WebFileIO io = new WebFileIO(getServletContext());
			try {
				io.write(ANN_LEAVE_CONF_FILE, year);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void changeDeptId() {
		String deptId = getParameter("deptId");
		if (isNumeric(deptId)) {
			attAdmin.setDeptId(parseInt(deptId));
		}
	}
}