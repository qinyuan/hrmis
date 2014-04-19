package qinyuan.hrmis.domain.rcr;

import java.util.HashMap;
import java.util.Map;

import qinyuan.hrmis.lib.data.HRMISConn;

public class UserPostUpdate {

	Map<Integer, int[]> map = new HashMap<Integer, int[]>();

	public void add(int userId, int[] postIds) {
		map.put(userId, postIds);
	}

	public void update() {
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.execute("TRUNCATE TABLE rcr_manager");
			cnn.prepare("INSERT INTO rcr_manager(userid,post_id) VALUES(?,?)");
			for (int key : map.keySet()) {
				for (int postId : map.get(key)) {
					cnn.setInt(1, key).setInt(2, postId);
					cnn.execute();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
