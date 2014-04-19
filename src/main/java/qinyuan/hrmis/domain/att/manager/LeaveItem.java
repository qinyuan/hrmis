package qinyuan.hrmis.domain.att.manager;

import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.date.MyTime;
import qinyuan.lib.db.HbnConn;

public class LeaveItem {

	private Integer id;
	private int empId;
	private String attDate;
	private String startTime;
	private String endTime;
	private int typeId;
	private String operator;
	private String operateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getAttDate() {
		return attDate;
	}

	public void setAttDate(String attDate) {
		this.attDate = attDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
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

	public String toString() {
		StringBuilder o = new StringBuilder();
		o.append("id:" + id);
		o.append(" empId:" + empId);
		o.append(" attDate:" + attDate);
		o.append(" startTime:" + startTime);
		o.append(" endTime:" + endTime);
		o.append(" typeId:" + typeId);
		o.append(" operator:" + operator);
		o.append(" operateTime:" + operateTime);
		return o.toString();
	}

	public double getHourCount() {
		return MyTime.hourDiff(startTime, endTime);
	}

	public static LeaveItem newInstance(int id) {
		try (HbnConn cnn = new HbnConn()) {
			return cnn.get(LeaveItem.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean delete(int id, int deptId) {
		String query = "DELETE FROM spec_leave WHERE leaveid=? AND userid IN "
				+ "(SELECT userid FROM dept_user WHERE deptid=?)";
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare(query).setInt(1, id).setInt(2, deptId).execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}