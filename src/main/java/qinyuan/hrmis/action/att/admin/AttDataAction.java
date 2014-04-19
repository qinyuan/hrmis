package qinyuan.hrmis.action.att.admin;

import java.io.File;
import java.io.IOException;

import qinyuan.hrmis.domain.att.manager.AttInfoPanel;
import qinyuan.hrmis.lib.data.AccessToMySQL;
import qinyuan.lib.web.WebFileIO;

public class AttDataAction extends AttAdminAction {

	private static final long serialVersionUID = 1L;
	private static final String ACCESS_CONF_FILE = "/WEB-INF/conf/accessPath.txt";

	public String execute() throws Exception {
		if (!checkSession()) {
			return SUCCESS;
		}

		setAccessFile();

		if (hasParameter("importAccessData")) {
			importAccessData();
		}

		return SUCCESS;
	}

	private void setAccessFile() {
		String accessPath = getGetParameter("accessPath");
		if (accessPath == null) {
			return;
		}

		if (!validateAccessFile(accessPath)) {
			return;
		}

		WebFileIO io = getWebFileIO();
		try {
			io.write(ACCESS_CONF_FILE, accessPath);
		} catch (IOException e) {
			e.printStackTrace();
			setAttribute("mdfResult", "文件修改失败");
		}
	}

	private void importAccessData() {
		WebFileIO io = getWebFileIO();
		String accessFile;
		try {
			accessFile = io.readFirstLine(ACCESS_CONF_FILE);
		} catch (IOException e) {
			e.printStackTrace();
			setAttribute("impResult", "程序配置文件读取失败");
			return;
		}

		if (!validateAccessFile(accessFile)) {
			return;
		}

		AccessToMySQL atm = new AccessToMySQL(accessFile);
		try {
			atm.importData();
			setAttribute("impResult", "数据导入成功");
			AttInfoPanel.refresh();
		} catch (Exception e) {
			setAttribute("impResult", "数据导入失败");
			e.printStackTrace();
		}
	}

	private boolean validateAccessFile(String fullFileName) {
		if (!isAccessFile(fullFileName)) {
			setAttribute("impResult", fullFileName
					+ "不是有效的Access文件，Access文件应以.mdb或.MDB结尾");
			return false;
		}

		if (!new File(fullFileName).isFile()) {
			setAttribute("impResult", "未找到文件: " + fullFileName + "，请确认文件路径是否正确");
			return false;
		}

		return true;
	}

	private static boolean isAccessFile(String fileName) {
		if (fileName == null) {
			return false;
		}
		return fileName.toUpperCase().endsWith(".MDB");
	}
}
