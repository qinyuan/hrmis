package qinyuan.hrmis.action.ass.admin;

import java.io.IOException;

import qinyuan.lib.web.WebFileIO;

public class InfoPanelAction extends AssAdminAction {

	private static final long serialVersionUID = 1L;
	private static final String FILE_BASE = "/WEB-INF/info/",
			OTHER_FILE_NAME = FILE_BASE + "ass-other.txt",
			RESULT_FILE_NAME = FILE_BASE + "ass-result.txt";

	public String execute() throws IOException {
		if (!checkSession()) {
			return SUCCESS;
		}

		if (hasParameter("infoSubmit")) {
			WebFileIO wfio = new WebFileIO(getServletContext());

			String otherInfo = getParameter("otherInfo");
			if (otherInfo != null) {
				wfio.write(OTHER_FILE_NAME, otherInfo);
			}

			String resultInfo = getParameter("resultInfo");
			if (resultInfo != null) {
				wfio.write(RESULT_FILE_NAME, resultInfo);
			}
		}

		return SUCCESS;
	}
}
