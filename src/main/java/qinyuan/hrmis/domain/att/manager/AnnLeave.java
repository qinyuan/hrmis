package qinyuan.hrmis.domain.att.manager;

public class AnnLeave {

	private int userId;
	private String badgenumber;
	private String username;
	private double usedDays = 0;
	private double usabledDays;
	private String workDate;
	private String joinDate;
	private String insurePlace;

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

	public double getUsabledDays() {
		return usabledDays;
	}

	public void setUsabledDays(double usabledDays) {
		this.usabledDays = usabledDays;
	}

	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	public String getInsurePlace() {
		return insurePlace;
	}

	public void setInsurePlace(String insurePlace) {
		this.insurePlace = insurePlace;
	}

	public void addUsedDays(double days) {
		usedDays += days;
	}

	public double getUsedDays() {
		return usedDays;
	}

	public double getRemainingDays() {
		return getUsabledDays() - getUsedDays();
	}
}
