package qinyuan.hrmis.domain.user;

/**
 * Object to hold user_privilege information, including user id, user name,
 * nickname, privilege description
 * 
 * @author Administrator
 * 
 */
public class UserPriInfo {

	private int userId;
	private String username;
	private String nickname;
	private String priDesc;

	public UserPriInfo(int userId, String username, String nickname,
			String priDesc) {
		this.userId = userId;
		this.username = username;
		this.nickname = nickname;
		this.priDesc = priDesc;
	}

	public int getUserid() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public String getNickname() {
		return nickname;
	}

	public String getPriDesc() {
		return priDesc;
	}
}
