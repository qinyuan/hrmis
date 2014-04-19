package qinyuan.hrmis.action.ass.dealer;

import java.io.IOException;

import qinyuan.lib.web.WebFileIO;

public class DownloadExcelAction extends AssAction {

	private static final long serialVersionUID = 1L;
	private static final String EXCEL_CONF_FILE = "/WEB-INF/conf/excelPath.txt";

	public String execute() {
		if (!checkSession()) {
			return SUCCESS;
		}
		try {
			String excelPath = getExcelPath();
			String inName = getInName(excelPath);

			if (inName != null) {
				String outName = assDealer.getMonStr() + ".xls";
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
	
	/**
	 * create temporary excel file, then get the name of it
	 * @param excelPath
	 * @return
	 */
	private String getInName(String excelPath) {
		String excelType = getParameter("excelType");
		String inName = null;
		if (excelType.equals("other")) {
			inName = assDealer.createExcelAsChecker(excelPath);
		} else if (excelType.equals("result")) {
			inName = assDealer.createExcelAsCheckee(excelPath);
		}

		return inName;
	}
}
