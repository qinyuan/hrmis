package qinyuan.hrmis.domain.att.manager;

import qinyuan.lib.date.MyDate;
import qinyuan.lib.date.MyTime;

public class MergedLeaveItem {

	private Integer id;
	private String badgenumber;
	private String username;
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;
	private String leaveType;
	private String operator;
	private String operateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public boolean after(MergedLeaveItem other) {
		if (other == null) {
			return false;
		}
		if (badgenumber == null || leaveType == null || startDate == null
				|| startTime == null || other.badgenumber == null
				|| other.leaveType == null || other.endDate == null
				|| other.endTime == null) {
			return false;
		}

		if (!badgenumber.equals(other.badgenumber)) {
			return false;
		}
		if (!leaveType.equals(other.leaveType)) {
			return false;
		}

		if (startDate.equals(other.endDate) && startTime.equals(other.endTime)) {
			return true;
		}

		return startTime.equals(IN) && other.endTime.equals(OUT)
				&& MyDate.dayDiff(other.endDate, startDate) == 1;
	}

	public void merged(MergedLeaveItem other) {
		if (after(other)) {
			startDate = other.startDate;
			startTime = other.startTime;
		} else if (other.after(this)) {
			endDate = other.endDate;
			endTime = other.endTime;
		} else {
			throw new RuntimeException("unable to merge in class" + getClass());
		}
	}

	public String getPeriod() {
		return startDate + " " + startTime + "~" + endDate + " " + endTime;
	}

	public double getDayCount() {
		double days = MyDate.dayDiff(startDate, endDate);
		double hourDiff = MyTime.hourDiff(startTime, endTime);
		if (hourDiff > WORKS_HOUR) {
			days++;
		} else {
			days += hourDiff / WORKS_HOUR;
		}
		return days;
	}

	private final static String IN = LeaveItemFactory.IN;
	private final static String OUT = LeaveItemFactory.OUT;
	private final static int WORKS_HOUR = 8;
}
