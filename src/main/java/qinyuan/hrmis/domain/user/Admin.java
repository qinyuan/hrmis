package qinyuan.hrmis.domain.user;

import java.util.List;
import qinyuan.hrmis.domain.admin.PriUser;
import qinyuan.hrmis.domain.admin.Privilege;
import qinyuan.hrmis.domain.admin.SimpleUser;
import qinyuan.hrmis.domain.admin.UserDeleter;
import qinyuan.lib.db.HbnConn;

public class Admin extends User {

	public Admin(int userId, String username, String nickname,
			Privilege[] privileges) {
		super(userId, username, nickname, privileges);

		super.setPriDesc("用户管理");
	}

	public String addUser(String username, String password, String nickname) {
		HbnConn cnn = new HbnConn();
		List<SimpleUser> list = cnn.createList(SimpleUser.class,
				"SELECT su FROM SimpleUser AS su WHERE su.username='"
						+ username + "'");
		String result = "";
		if (list.size() == 0) {
			SimpleUser.add(username, password, nickname);
		} else {
			result = "账号名称已存在！";
		}
		try {
			cnn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String deleteUser(int userId) {
		UserDeleter deleter = new UserDeleter();
		deleter.setUserId(userId).delete();
		return deleter.getReason();
	}

	public String getPriCheckBoxes() {
		PriUser[] pus = PriUser.getInstances();
		StringBuilder o = new StringBuilder();
		for (PriUser pu : pus) {
			o.append("<fieldset>");
			o.append("<legend>" + pu.getUsername() + "(" + pu.getNickname()
					+ ")</legend>");
			o.append(pu.getPriCheckBoxes());
			o.append("</fieldset>");
		}
		return o.toString();
	}

	public List<SimpleUser> getUsers() {
		return SimpleUser.getInstances();
	}

	public static Admin createAdmin(User user) {
		return new Admin(user.getUserid(), user.getUsername(),
				user.getNickname(), user.getPrivilege());
	}
}
