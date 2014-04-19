package qinyuan.hrmis.domain.rcr;

import java.util.HashMap;
import java.util.Map;

import qinyuan.hrmis.lib.data.HRMISConn;

public class RcrStatus {

	private int id;
	private String desc;

	public RcrStatus(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	public boolean equals(Object other) {
		if (other instanceof RcrStatus) {
			return getId() == ((RcrStatus) other).getId();
		} else {
			return false;
		}
	}

	public int getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}

	public String toString() {
		return getDesc();
	}

	private static Map<Integer, String> map;

	public static RcrStatus getStatus(int id) {
		if (map == null)
			initializeMap();

		if (map == null || map.size() == 0) {
			return null;
		}

		RcrStatus operate = new RcrStatus(id, map.get(id));
		return operate;
	}

	public static RcrStatus[] getStatuses() {
		if (map == null)
			initializeMap();

		if (map == null || map.size() == 0) {
			return new RcrStatus[0];
		}

		RcrStatus[] operates = new RcrStatus[map.size()];
		int i = 0;
		for (int key : map.keySet()) {
			operates[i++] = getStatus(key);
		}
		return operates;
	}

	private static void initializeMap() {
		map = new HashMap<Integer, String>();
		String query = "SELECT status_id,status FROM rcr_status";
		try (HRMISConn cnn = new HRMISConn()) {
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
		RcrStatus[] operates = getStatuses();
		System.out.println("status size: " + operates.length);
		System.out.println("each status: ");
		for (RcrStatus operate : operates) {
			System.out.println(operate.getId() + " " + operate.getDesc());
		}

		RcrStatus status1 = getStatus(1);
		RcrStatus status2 = getStatus(2);
		RcrStatus status3 = getStatus(1);
		System.out
				.println("status1 equals status2: " + status1.equals(status2));
		System.out
				.println("status1 equals status3: " + status1.equals(status3));
	}
}
