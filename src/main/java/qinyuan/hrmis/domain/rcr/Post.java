package qinyuan.hrmis.domain.rcr;

import java.util.HashMap;
import java.util.Map;

import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.web.html.Select;

public class Post {

	private int id;
	private String name;

	public Post(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isUsed() {
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare("SELECT * FROM rcr_resume WHERE post_id=?")
					.setInt(1, id).execute();
			return cnn.next();
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}

	public String toString() {
		return getName();
	}

	private static Map<Integer, String> map;

	public static boolean addPost(String postName) {
		try (HRMISConn cnn = new HRMISConn()) {
			String query = "INSERT INTO rcr_post(post) VALUES(?)";
			cnn.prepare(query).setString(1, postName).execute();
			refresh();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean deletePost(int postId) {
		String query = "DELETE FROM rcr_post WHERE post_id=" + postId;
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.execute(query);
			refresh();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Post getPost(int postId) {
		if (map == null)
			refresh();

		if (map == null)
			return null;

		String postName = map.get(postId);
		if (postName == null) {
			return null;
		} else {
			return new Post(postId, postName);
		}
	}

	public static Post[] getPostsByUserId(int userId) {
		try (HRMISConn cnn = new HRMISConn()) {
			String query = "SELECT post_id,post FROM rcr_post WHERE post_id IN "
					+ "(SELECT post_id FROM rcr_manager WHERE userid=?)";
			cnn.prepare(query).setInt(1, userId).execute();
			Post[] posts = new Post[cnn.getRowCount()];
			for (int i = 0; i < posts.length; i++) {
				cnn.next();
				posts[i] = new Post(cnn.getInt(1), cnn.getString(2));
			}
			return posts;
		} catch (Exception e) {
			e.printStackTrace();
			return new Post[0];
		}
	}

	public static Post[] getPosts() {
		if (map == null)
			refresh();

		if (map == null || map.size() == 0) {
			return new Post[0];
		}

		int i = 0;
		Post[] posts = new Post[map.size()];
		for (int key : map.keySet()) {
			posts[i++] = new Post(key, map.get(key));
		}
		return posts;
	}

	public static Select getSelect(int postId) {
		Post[] posts = getPosts();
		Select select = new Select();
		select.select(postId);
		for (Post post : posts) {
			select.addOption(post.getId(), post.getName());
		}
		select.setId("postId");
		return select;
	}

	public static boolean hasPost(String postName) {
		return map.containsValue(postName);
	}

	public static boolean mdfPost(int postId, String postName) {
		String query = "UPDATE rcr_post SET post='" + postName
				+ "' WHERE post_id=" + postId;
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.execute(query);
			refresh();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void refresh() {
		map = new HashMap<Integer, String>();
		try (HRMISConn cnn = new HRMISConn()) {
			String query = "SELECT post_id,post FROM rcr_post";
			cnn.execute(query);
			while (cnn.next()) {
				map.put(cnn.getInt(1), cnn.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
			map = null;
		}
	}

	public static void main(String[] args) {
		System.out.println(getPostsByUserId(2).length);
	}
}
