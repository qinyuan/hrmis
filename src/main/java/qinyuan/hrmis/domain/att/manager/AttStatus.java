package qinyuan.hrmis.domain.att.manager;

public class AttStatus {
	public final static int NORMAL = 0;
	public final static int PINK = 1;
	public final static int RED = 2;

	private int reachStatus = NORMAL;
	private int leaveStatus = NORMAL;

	public void setReachStatus(int status) {
		reachStatus = status;
	}

	public void setLeaveStatus(int status) {
		leaveStatus = status;
	}

	public int getReachStatus() {
		return reachStatus;
	}

	public int getLeaveStatus() {
		return leaveStatus;
	}
}
