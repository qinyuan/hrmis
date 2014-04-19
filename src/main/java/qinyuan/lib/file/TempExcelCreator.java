package qinyuan.lib.file;

import java.io.File;

public class TempExcelCreator extends ExcelCreator {

	private String fullFileName;

	@Override
	public boolean create(String folder) {
		if (!folder.endsWith("/")) {
			folder = folder + "/";
		}
		File file = new File(folder);
		if (!file.isDirectory()) {
			file.mkdirs();
		}
		fullFileName = folder + System.currentTimeMillis() + ".xls";
		clearOverDueExcel(folder);
		return super.create(fullFileName);
	}

	public void clearOverDueExcel(String folder) {
		File f = new File(folder);
		if (!f.isDirectory())
			return;

		File[] files = new File(folder).listFiles();
		for (File file : files) {
			if (isTempExcel(file) && isOverdue(file)) {
				file.delete();
			}
		}
	}

	public String getFullFileName() {
		return fullFileName;
	}

	private static boolean isTempExcel(File file) {
		String fileName = file.getName();
		return fileName.matches("(\\d+)((.xls)|(.xlsx))");
	}

	private static final int OVERDUE_SECONDS = 60;

	private static boolean isOverdue(File file) {
		String fileName = file.getName();
		String timeStampStr = fileName.replaceAll("\\D", "");
		if (timeStampStr.isEmpty()) {
			return false;
		}

		long timeStamp = Long.parseLong(timeStampStr);
		long currentTimeStamp = System.currentTimeMillis();
		if (currentTimeStamp - timeStamp > 1000 * OVERDUE_SECONDS) {
			return true;
		} else {
			return false;
		}
	}
}
