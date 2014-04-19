package qinyuan.hrmis.domain.user;

import qinyuan.hrmis.domain.admin.Privilege;
import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.date.MyTime;
import qinyuan.lib.web.NaviLinks;
import qinyuan.lib.web.SpringUtil;

public class User {

	private int userId;
	private String username;
	private String nickname;
	private Privilege[] pris;
	private String priDesc = "";
	private NaviLinks listNavi;
	private NaviLinks rowNavi;

	public User(int userId, String username, String nickname,
			Privilege[] privileges) {
		this.userId = userId;
		this.username = username;
		this.nickname = nickname;
		this.pris = privileges;
	}

	public boolean changeNickName(String newNickname) {
		try (HRMISConn cnn = new HRMISConn()) {
			this.nickname = newNickname;
			cnn.prepare("UPDATE users SET nickname=? WHERE userid=?")
					.setString(1, newNickname).setInt(2, userId).execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean changePassword(String newPassword) {
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.execute("UPDATE users SET password='" + newPassword
					+ "' WHERE userId=" + userId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getGreetWords() {
		StringBuilder o = new StringBuilder("<p>" + nickname + ",");
		MyTime nowTime = new MyTime();
		int hour = nowTime.getHour();
		if (hour < 12) {
			o.append("早上好");
		} else if (hour >= 12 && hour <= 18) {
			o.append("下午好");
		} else {
			o.append("晚上好");
		}

		NaviLinks links = SpringUtil.getBean("headNavi", NaviLinks.class);
		o.append(" " + links + "</p>");

		return o.toString();
	}

	public String getListNavi() {
		StringBuilder o = new StringBuilder();
		o.append("<fieldset>");
		o.append("<legend>" + priDesc + "</legend>");
		o.append("<ul>");

		o.append(listNavi);

		o.append("<ul>");
		o.append("</fieldset>");
		return o.toString();
	}

	public String getRowNavi() {
		return rowNavi.toString();
	}

	public String getNickname() {
		return nickname;
	}

	public Privilege[] getPrivilege() {
		return pris;
	}

	public int getUserid() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public boolean hasPrivilege(int privilegeId) {
		for (Privilege pri : pris) {
			if (pri.getId() == privilegeId) {
				return true;
			}
		}
		return false;
	}

	final public void setListNaviBean(String naviBeanId) {
		listNavi = SpringUtil.getBean(naviBeanId, NaviLinks.class);
	}

	final public void setPriDesc(String priDesc) {
		this.priDesc = priDesc;
	}

	public boolean setPrivilege(String[] privilegeIds) {
		try (HRMISConn cnn = new HRMISConn()) {
			String query;

			// delete exists privileges
			query = "DELETE FROM user_pri WHERE userId=" + userId;
			cnn.execute(query);

			// insert new privileges
			for (String privilegeId : privilegeIds) {
				query = "INSERT INTO user_pri(userId,pri_id) VALUES(" + userId
						+ "," + privilegeId + ")";
				cnn.execute(query);
			}

			// update pris of this users
			int prisCount = privilegeIds.length;
			this.pris = new Privilege[prisCount];
			for (int i = 0; i < prisCount; i++) {
				pris[i] = new Privilege(Integer.parseInt(privilegeIds[i]));
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public final void setRowNaviBean(String naviBeanId) {
		rowNavi = SpringUtil.getBean(naviBeanId, NaviLinks.class);
	}

	public static User newInstance(int userId) {
		try (HRMISConn cnn = new HRMISConn()) {
			String query = "SELECT username,nickname FROM users WHERE userid=?";
			cnn.prepare(query).setInt(1, userId).execute();
			if (cnn.next()) {
				String username = cnn.getString(1);
				String nickname = cnn.getString(2);

				// set privilege
				Privilege[] pris = getPrivilegeFromDB(userId, cnn);

				return new User(userId, username, nickname, pris);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static User newInstance(String username, String password) {
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare(
					"SELECT userid FROM users WHERE username=? AND password=?")
					.setString(1, username).setString(2, password).execute();
			if (cnn.next()) {
				int userId = cnn.getInt(1);
				return newInstance(userId);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Privilege[] getPrivilegeFromDB(int userId, HRMISConn cnn) {
		try {
			cnn.prepare("SELECT pri_id FROM user_pri WHERE userid=?")
					.setInt(1, userId).execute();
			int priCount = cnn.getRowCount();
			Privilege[] pris = new Privilege[priCount];
			for (int i = 0; i < priCount; i++) {
				cnn.next();
				pris[i] = new Privilege(cnn.getInt(1));
			}
			return pris;
		} catch (Exception e) {
			e.printStackTrace();
			return new Privilege[0];
		}
	}
}