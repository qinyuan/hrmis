package qinyuan.hrmis.domain.admin;

import qinyuan.hrmis.lib.data.HRMISConn;

public class UserDeleter {

	private int userId = -1;
	private String reason;
	private boolean checked = false;
	private boolean canDelete = false;

	public UserDeleter setUserId(int userId) {
		this.userId = userId;
		checked = false;
		return this;
	}

	public boolean canDelete() {
		checked = true;

		try (HRMISConn cnn = new HRMISConn()) {
			String username = getUsername(userId, cnn);
			cnn.prepare("SELECT * FROM rcr_resume WHERE dealer=?")
					.setInt(1, userId).execute();
			if (cnn.next()) {
				reason = "'" + username + "'参与招聘简历的审核，只有删除相关的简历之后才能删除此人";
				return canDelete = false;
			}

			reason = null;
			return canDelete = true;
		} catch (Exception e) {
			e.printStackTrace();
			reason = "未知错误";
			return canDelete = false;
		}
	}

	public void delete() {
		if (checked) {
			if (canDelete) {
				SimpleUser.delete(userId);
			}
		} else if (canDelete()) {
			SimpleUser.delete(userId);
		}
	}

	public String getReason() {
		return reason;
	}

	public static String getUsername(int userId, HRMISConn cnn)
			throws Exception {
		cnn.prepare("SELECT username FROM users WHERE userid=?")
				.setInt(1, userId).execute();
		cnn.next();
		return cnn.getString(1);
	}
}
