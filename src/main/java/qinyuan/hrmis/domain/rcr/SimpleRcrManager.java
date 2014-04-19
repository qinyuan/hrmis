package qinyuan.hrmis.domain.rcr;

import qinyuan.hrmis.domain.admin.Privilege;
import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.web.html.ChkBox;

public class SimpleRcrManager {

	private int userId;
	private String username;
	private String nickname;
	private int[] postIds;

	private SimpleRcrManager(int userId, String username, String nickname,
			int[] postIds) {
		this.userId = userId;
		this.username = username;
		this.nickname = nickname;
		this.postIds = postIds;
	}

	public String getCheckBoxes() {
		StringBuilder o = new StringBuilder();
		o.append("<fieldset>");
		o.append("<legend>" + username + " " + nickname + "</legend>");
		Post[] posts = Post.getPosts();
		o.append("<table>");
		for (int i = 0; i < posts.length; i++) {
			int postId = posts[i].getId();
			if (i % 4 == 0) {
				o.append("<tr>");
			}
			o.append("<td>"
					+ new ChkBox().setId("user" + userId).setValue(postId)
							.setChecked(containPost(postId)) + posts[i]
					+ "</td>");
			if ((i + 1) % 4 == 0) {
				o.append("</tr>");
			}
		}
		o.append("</table>");
		o.append("</fieldset>");
		return o.toString();
	}

	private boolean containPost(int postId) {
		for (int i = 0; i < postIds.length; i++) {
			if (postIds[i] == postId) {
				return true;
			}
		}
		return false;
	}

	private static String queryUser = "SELECT userid,username,nickname FROM users WHERE userid IN "
			+ "(SELECT userid FROM user_pri WHERE pri_id="
			+ Privilege.RCR_PRI_ID + ")";

	public static SimpleRcrManager[] getInstances() {
		try (HRMISConn cnn = new HRMISConn(); HRMISConn cnn2 = new HRMISConn()) {
			cnn.execute(queryUser);
			cnn2.prepare("SELECT post_id FROM rcr_manager WHERE userid=?");
			SimpleRcrManager[] items = new SimpleRcrManager[cnn.getRowCount()];
			for (int i = 0; i < items.length; i++) {
				cnn.next();
				int userId = cnn.getInt(1);
				items[i] = new SimpleRcrManager(userId, cnn.getString(2),
						cnn.getString(3), getPostIdsByUserId(cnn2, userId));
			}
			return items;
		} catch (Exception e) {
			e.printStackTrace();
			return new SimpleRcrManager[0];
		}
	}
	
	private static int[] getPostIdsByUserId(HRMISConn cnn, int userId)
			throws Exception {
		cnn.setInt(1, userId);
		cnn.execute();
		int[] postIds = new int[cnn.getRowCount()];
		for (int i = 0; i < postIds.length; i++) {
			cnn.next();
			postIds[i] = cnn.getInt(1);
		}
		return postIds;
	}

	public static void main(String[] args) {
		for (SimpleRcrManager item : getInstances()) {
			System.out.println(item.username + " " + item.postIds.length);
		}
	}
}
