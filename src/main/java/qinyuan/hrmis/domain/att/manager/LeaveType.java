package qinyuan.hrmis.domain.att.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import qinyuan.lib.db.HbnConn;

public class LeaveType {

	private Integer id;
	private String desc;
	private boolean norm;

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

	public boolean isNorm() {
		return norm;
	}

	public void setNorm(boolean norm) {
		this.norm = norm;
	}

	private static Map<Integer, LeaveType> norLts;
	private static Map<Integer, LeaveType> othLts;

	public static LeaveType getInstance(int id) {
		if (norLts == null || othLts == null) {
			initializeMap();
		}

		LeaveType lt = norLts.get(id);
		if (lt == null) {
			lt = othLts.get(id);
		}
		return lt;
	}

	public static LeaveType[] getInstances() {
		if(norLts==null || othLts==null){
			initializeMap();
		}
		
		LeaveType[] lts = new LeaveType[norLts.size() + othLts.size()];
		int i = 0;
		for (int key : norLts.keySet()) {
			lts[i++] = norLts.get(key);
		}
		for (int key : othLts.keySet()) {
			lts[i++] = othLts.get(key);
		}
		return lts;
	}

	public static LeaveType[] getNorInstances() {
		if(norLts==null || othLts==null)
			initializeMap();
		
		LeaveType[] lts = new LeaveType[norLts.size()];
		int i = 0;
		for (int key : norLts.keySet()) {
			lts[i++] = norLts.get(key);
		}
		return lts;
	}

	public static LeaveType[] getOthInstances() {
		if(norLts==null || othLts==null)
			initializeMap();
		
		LeaveType[] lts = new LeaveType[othLts.size()];
		int i = 0;
		for (int key : othLts.keySet()) {
			lts[i++] = othLts.get(key);
		}
		return lts;
	}

	private static void initializeMap() {
		norLts = new HashMap<Integer, LeaveType>();
		othLts = new HashMap<Integer, LeaveType>();
		final String query = "FROM LeaveType";
		try (HbnConn cnn = new HbnConn()) {
			List<LeaveType> list = cnn.createList(LeaveType.class, query);
			for (LeaveType lt : list) {
				if (lt.isNorm()) {
					norLts.put(lt.getId(), lt);
				} else {
					othLts.put(lt.getId(), lt);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
