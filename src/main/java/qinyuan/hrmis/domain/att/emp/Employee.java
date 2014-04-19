package qinyuan.hrmis.domain.att.emp;

import java.util.ArrayList;
import java.util.List;

import qinyuan.lib.db.HbnConn;

public class Employee {

	private Integer id;
	private String badgenumber;
	private String name;
	private Integer deptId;

	public Integer getId() {
		return id;
	}

	public Employee setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getBadgenumber() {
		return badgenumber;
	}

	public Employee setBadgenumber(String badgenumber) {
		this.badgenumber = badgenumber;
		return this;
	}

	public String getName() {
		return name;
	}

	public Employee setName(String name) {
		this.name = name;
		return this;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public Employee setDeptId(Integer deptId) {
		this.deptId = deptId;
		return this;
	}

	public static List<Employee> newInstances() {
		try (HbnConn cnn = new HbnConn()) {
			return cnn.createList(Employee.class,
					"FROM Employee ORDER BY userId");
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Employee>();
		}
	}

	public static Employee newInstance(int userId) {
		try (HbnConn cnn = new HbnConn()) {
			return cnn.get(Employee.class, userId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(newInstances().size());
	}
}