package qinyuan.hrmis.domain.att.manager;

import java.util.HashMap;
import java.util.Map;

public class NorLeave {

	private int userId;
	private String badgenumber;
	private String username;
	private Map<Integer, Double> dayCount = new HashMap<Integer, Double>();

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getBadgenumber() {
		return badgenumber;
	}

	public void setBadgenumber(String badgenumber) {
		this.badgenumber = badgenumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void putDayCount(int leaveTypeId, double days) {
		if (dayCount.containsKey(leaveTypeId)) {
			dayCount.put(leaveTypeId, dayCount.get(leaveTypeId) + days);
		} else {
			dayCount.put(leaveTypeId, days);
		}
	}

	public double getDayCount(int leaveTypeId) {
		Double result = dayCount.get(leaveTypeId);
		return result == null ? 0 : result;
	}
}
