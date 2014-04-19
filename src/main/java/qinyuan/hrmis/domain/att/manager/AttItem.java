package qinyuan.hrmis.domain.att.manager;

import qinyuan.hrmis.domain.att.emp.Employee;

public class AttItem {

	public static final int NOR = 1, PINK = 2, RED = 3, SPE_NOR = 4,
			SPE_OTH = 5;
	private static final int CLASS_NOR = 1, CLASS_MID = 2;
	private Employee emp;
	private int classType;
	private String date;
	private String reachTime;
	private String leaveTime;
	private String reachSpec;
	private String leaveSpec;
	private String reachAccd;
	private String leaveAccd;
	private String specEmp;
	private String specDate;

	public AttItem(Employee emp, String date, String reachTime,
			String leaveTime, String reachSpec, String leaveSpec,
			String reachAccd, String leaveAccd, String specEmp, String specDate) {
		this.emp = emp;
		this.date = date;
		this.reachTime = reachTime;
		this.leaveTime = leaveTime;
		this.reachSpec = reachSpec;
		this.leaveSpec = leaveSpec;
		this.reachAccd = reachAccd;
		this.leaveAccd = leaveAccd;
		this.specEmp = specEmp;
		this.specDate = specDate;

		if (reachTime == null || leaveTime == null) {
			classType = CLASS_NOR;
		} else if (leaveTime.compareTo("20:00:00") > 0) {
			classType = CLASS_MID;
		} else {
			classType = CLASS_NOR;
		}
	}

	public int getLeaveStatus() {
		if (leaveSpec != null || leaveAccd != null) {
			return SPE_NOR;
		}

		if (specEmp != null || specDate != null) {
			return SPE_OTH;
		}

		if (leaveTime == null) {
			return RED;
		}

		if (classType == CLASS_NOR) {
			if (leaveTime.compareTo("15:30:00") >= 0
					&& leaveTime.compareTo("15:55:00") < 0) {
				return PINK;
			} else if (leaveTime.compareTo("15:30:00") < 0) {
				return RED;
			} else {
				return NOR;
			}
		} else {
			if (leaveTime.compareTo("22:30:00") <= 0
					&& leaveTime.compareTo("22:55:00") < 0) {
				return PINK;
			} else if (leaveTime.compareTo("22:30:00") < 0) {
				return RED;
			} else {
				return NOR;
			}
		}
	}

	public String getLeaveTime() {
		if (leaveTime == null) {
			return "";
		} else {
			return leaveTime.substring(0, 5);
		}
	}

	public String getLongDate() {
		return getValue(date);
	}

	public int getReachStatus() {
		if (reachSpec != null || reachAccd != null) {
			return SPE_NOR;
		}

		if (specEmp != null || specDate != null) {
			return SPE_OTH;
		}

		if (reachTime == null) {
			return RED;
		}

		if (classType == CLASS_NOR) {
			if (reachTime.compareTo("08:30:00") > 0) {
				return RED;
			} else if (reachTime.compareTo("08:05:00") > 0
					&& reachTime.compareTo("08:30:00") <= 0) {
				return PINK;
			} else {
				return NOR;
			}
		} else {
			if (reachTime.compareTo("16:30:00") > 0) {
				return RED;
			} else if (reachTime.compareTo("16:05:00") > 0
					&& reachTime.compareTo("16:30:00") <= 0) {
				return PINK;
			} else {
				return NOR;
			}
		}
	}

	public String getReachTime() {
		if (reachTime == null) {
			return "";
		} else {
			return reachTime.substring(0, 5);
		}
	}

	public String getShortDate() {
		if (date == null) {
			return "";
		} else {
			return date.substring(5);
		}
	}

	public int getUserid() {
		return emp.getId();
	}

	private static String getValue(String str) {
		return str == null ? "" : str;
	}

	public static void main(String[] args) {
		Employee emp = Employee.newInstance(16);
		AttItem item = new AttItem(emp, null, "08:00:00", "16:00:00", null,
				null, null, null, null, null);
		System.out.println(item.getReachStatus());
		System.out.println(item.getLeaveStatus());
	}
}