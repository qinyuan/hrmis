package qinyuan.hrmis.domain.att.admin;

import qinyuan.hrmis.lib.data.HRMISConn;

public class SimpleAnnLeave {

	private String badgenumbe;
	private String username;
	private String workDate;
	private String joinDate;
	private String insurePlace;
	private String usableDays;

	SimpleAnnLeave() {
	}

	public String getBadgenumbe() {
		return badgenumbe;
	}

	public void setBadgenumbe(String badgenumbe) {
		this.badgenumbe = badgenumbe;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getUsableDays() {
		return usableDays;
	}

	public void setUsableDays(String usableDays) {
		this.usableDays = usableDays;
	}

	public String toString() {
		return "badgenumber:" + badgenumbe + ",username:" + username
				+ ",workDate:" + workDate + ",joinDate:" + joinDate
				+ ",insurePlace:" + insurePlace + ",usabledDays:" + usableDays;
	}

	public static void add(String badgenumber, String workDate,
			String joinDate, String insurePlace, String usabledDays) {
		String query = "INSERT INTO annual_leave"
				+ "(badgenumber,workdate,joindate,insure_place,total_leave) "
				+ "VALUES('" + badgenumber + "','" + workDate + "','"
				+ joinDate + "','" + insurePlace + "','" + usabledDays + "')";
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.execute(query);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void modify(String badgenumber, String workDate,
			String joinDate, String insurePlace, String usabledDays) {
		String query = "UPDATE annual_leave SET workdate='" + workDate
				+ "',joindate='" + joinDate + "',insure_place='" + insurePlace
				+ "',total_leave='" + usabledDays + "' WHERE badgenumber='"
				+ badgenumber + "'";
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.execute(query);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void delete(String badgenumber) {
		String query = "DELETE FROM annual_leave WHERE badgenumber='"
				+ badgenumber + "'";
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.execute(query);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
