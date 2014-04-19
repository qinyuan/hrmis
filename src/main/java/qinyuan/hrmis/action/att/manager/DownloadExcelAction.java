package qinyuan.hrmis.action.att.manager;

import java.io.IOException;
import qinyuan.lib.web.WebFileIO;

public class DownloadExcelAction extends AttAction {

	private static final long serialVersionUID = 1L;
	private static final String EXCEL_CONF_FILE = "/WEB-INF/conf/excelPath.txt";

	public String execute() {
		if (!checkSession()) {
			return SUCCESS;
		}
		
		try {
			String excelPath = getExcelPath();
			String inName = attManager.exportNorLeaveSum(excelPath);

			if (inName != null) {
				String outName = attManager.getStartDate() + "~"
						+ attManager.getEndDate() + ".xls";
				setAttribute("inName", inName);
				setAttribute("outName", outName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	private String getExcelPath() throws IOException {
		WebFileIO reader = new WebFileIO(getServletContext());
		String excelPath = reader.readFirstLine(EXCEL_CONF_FILE);
		return excelPath.trim();
	}
}
