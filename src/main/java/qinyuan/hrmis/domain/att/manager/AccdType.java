package qinyuan.hrmis.domain.att.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import qinyuan.lib.db.HbnConn;

public class AccdType {

	private Integer id;
	private String desc;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private static Map<Integer, AccdType> map;

	public static AccdType getInstance(int id) {
		if (map == null)
			initializeMap();

		return map.get(id);
	}

	public static AccdType[] getInstances() {
		if (map == null)
			initializeMap();

		AccdType[] ats = new AccdType[map.size()];
		int i = 0;
		for (int key : map.keySet()) {
			ats[i++] = map.get(key);
		}
		return ats;
	}

	private static void initializeMap() {
		map = new HashMap<Integer, AccdType>();
		try (HbnConn cnn = new HbnConn()) {
			List<AccdType> list = cnn.createList(AccdType.class,
					"FROM AccdType");
			for (AccdType at : list) {
				map.put(at.getId(), at);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
