package qinyuan.hrmis.action.admin;

import qinyuan.hrmis.lib.data.HRMISBackup;

public class BackupAction extends AdminAction {

	private static final long serialVersionUID = 1L;

	public String execute() {
		if (!checkSession()) {
			return SUCCESS;
		}

		try {
			String dataPath = getWebFileIO().readFirstLine(DATA_PATH_CONF_FILE);
			HRMISBackup backup = new HRMISBackup();
			if (backup.export(dataPath)) {
				String inName = backup.getFileName();
				String outName = inName.substring(inName.lastIndexOf('/') + 1);
				setAttribute("inName", inName);
				setAttribute("outName", outName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}
}
