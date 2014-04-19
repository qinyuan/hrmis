package qinyuan.hrmis.action.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import qinyuan.hrmis.lib.data.HRMISRecover;
import qinyuan.lib.file.FileFormat;

public class DataAction extends AdminAction {

	private static final long serialVersionUID = 1L;

	private File upload;
	private String uploadFileName;

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String execute() throws IOException {
		if (!checkSession()) {
			return SUCCESS;
		}

		if (upload != null && uploadFileName != null) {
			String dataPath = getWebFileIO().readFirstLine(DATA_PATH_CONF_FILE);
			String fullFileName = FileFormat.getLinStyleFolder(dataPath)
					+ uploadFileName;
			File zipFile = new File(fullFileName);
			if (zipFile.isFile()) {
				zipFile.delete();
			}
			try (FileOutputStream fos = new FileOutputStream(fullFileName);
					FileInputStream fis = new FileInputStream(upload)) {
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				HRMISRecover recover = new HRMISRecover();
				if (recover.recover(fullFileName)) {
					setAttribute("result", "数据恢复成功，请重新启动服务器");
				} else {
					setAttribute("result", "数据恢复失败," + recover.getErrorInfo());
				}

				zipFile.delete();
			} catch (Exception e) {
				setAttribute("result", "数据恢复失败");
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}
}
