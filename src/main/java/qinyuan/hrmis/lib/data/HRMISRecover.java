package qinyuan.hrmis.lib.data;

import java.io.File;
import qinyuan.lib.db.MySQLRecovery;
import qinyuan.lib.file.MyFileIO;
import qinyuan.lib.file.ZipTool;

public class HRMISRecover {

	private String errorInfo;

	public boolean recover(String fileName) {
		File file = new File(fileName);
		if (!fileName.endsWith(".rar")) {
			errorInfo = "备份文件应以.rar结尾";
			return false;
		} else if (!file.isFile()) {
			errorInfo = "未找到文件" + fileName;
			return false;
		}

		String folderName = fileName.substring(0, fileName.length() - 4);
		try {
			ZipTool.unzip(fileName, folderName);
		} catch (Exception e) {
			errorInfo = "解压" + fileName + "失败";
			e.printStackTrace();
			return false;
		}

		MySQLRecovery recover;
		try {
			recover = new MySQLRecovery("root", "qinyuan");
		} catch (Exception e) {
			errorInfo = "数据库连接失败";
			e.printStackTrace();
			return false;
		}

		if (recover.ImportData(folderName)) {
			errorInfo = null;
			MyFileIO.delete(folderName);
			return true;
		} else {
			errorInfo = recover.getErrorInfo();
			MyFileIO.delete(folderName);
			return false;
		}
	}

	public String getErrorInfo() {
		return errorInfo;
	}
}
