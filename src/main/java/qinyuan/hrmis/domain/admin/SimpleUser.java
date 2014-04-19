package qinyuan.hrmis.domain.admin;

import java.util.List;
import qinyuan.lib.db.HbnConn;
import qinyuan.lib.web.html.ChkBox;
import qinyuan.lib.web.html.TableRow;

public class SimpleUser {

	private Integer userId;
	private String username, password, nickname;

	public Integer getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public TableRow toTableRow() {
		TableRow tr = new TableRow();

		tr.add(new ChkBox().setId("user" + userId));
		tr.add(username);
		tr.add(password);
		tr.add(nickname);

		return tr;
	}

	public static void add(String username, String password, String nickname) {
		SimpleUser su = new SimpleUser();
		su.setUsername(username);
		su.setPassword(password);
		su.setNickname(nickname);

		HbnConn cnn = new HbnConn();
		cnn.save(su);
		suList = null;
		try {
			cnn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void delete(int userId) {
		HbnConn cnn = new HbnConn();
		SimpleUser su = cnn.get(SimpleUser.class, userId);
		cnn.delete(su);
		suList = null;
		try {
			cnn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SimpleUser newInstance(int userId) {
		HbnConn cnn = new HbnConn();
		SimpleUser su = cnn.get(SimpleUser.class, new Integer(userId));
		try {
			cnn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return su;
	}

	private static List<SimpleUser> suList;

	public static List<SimpleUser> getInstances() {
		if (suList == null) {
			HbnConn cnn = new HbnConn();
			suList = cnn.createList(SimpleUser.class,
					"SELECT p FROM SimpleUser AS p");
			try {
				cnn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return suList;
	}

	/**
	 * this method is just used for testing
	 * 
	 * @param su
	 */
	private static void print(SimpleUser su) {
		System.out.println("userId: " + su.getUserId());
		System.out.println("username: " + su.getUsername());
		System.out.println("password: " + su.getPassword());
		System.out.println("nickname: " + su.getNickname());
		System.out.println();
	}

	public static void main(String[] args) throws Exception {
		List<SimpleUser> sus = getInstances();
		for (SimpleUser su : sus) {
			print(su);
		}

		SimpleUser su = newInstance(2);
		print(su);

		// HbnConn cnn=new HbnConn();
		// HbnConn cnn2=new HbnConn();
		// cnn2.close();
		// su=new SimpleUser();
		// su.setNickname("test2");
		// su.setUsername("username4");
		// su.setPassword("test");
		// cnn.save(su);
		// cnn.close();
	}
}
