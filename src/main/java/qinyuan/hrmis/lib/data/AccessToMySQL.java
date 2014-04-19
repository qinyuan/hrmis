package qinyuan.hrmis.lib.data;

import java.sql.SQLException;

import qinyuan.lib.date.MyTimeRecord;
import qinyuan.lib.db.MDBConn;

public class AccessToMySQL {

	private final static int batchSize = 500;
	private MDBConn acnn;
	private HRMISConn mcnn;
	private String accessPath;

	public AccessToMySQL(String accessPath) {
		this.accessPath = accessPath;
	}

	public void importData() throws Exception {
		// declare database connection
		acnn = new MDBConn(accessPath);
		mcnn = new HRMISConn();

		// import department
		acnn.query("SELECT DEPTID,DEPTNAME,SUPDEPTID FROM DEPARTMENTS");
		mcnn.execute("TRUNCATE TABLE department");
		String insertQuery = "INSERT INTO department"
				+ "(deptId,deptname,supdeptId) VALUES";
		copyValues(acnn, mcnn, insertQuery);

		// import user info
		acnn.query("SELECT USERID,Badgenumber,Name,DEFAULTDEPTID FROM USERINFO");
		mcnn.execute("TRUNCATE TABLE userinfo");
		insertQuery = "INSERT INTO userinfo"
				+ "(userid,badgenumber,username,deptid) VALUES";
		copyValues(acnn, mcnn, insertQuery);

		// import checkinout
		acnn.query("SELECT USERID,DATEVALUE(CHECKTIME),TIMEVALUE(CHECKTIME)"
				+ " FROM CHECKINOUT");
		insertQuery = "INSERT IGNORE INTO checkinout "
				+ "(userId,checkdate,checktime) VALUES";
		copyValues(acnn, mcnn, insertQuery);

		// create data in checkbydate
		createCheckByDate(mcnn);

		// create data in dept-user
		createDeptUser(mcnn);

		mcnn.close();
		acnn.close();
	}

	private static void copyValues(MDBConn acnn, HRMISConn mcnn,
			String insertQuery) throws SQLException {
		int rowCount = 0;
		StringBuilder values = new StringBuilder();
		while (acnn.next()) {
			rowCount++;
			values.append(acnn.getValues() + ",");
			if (rowCount % batchSize == 0) {
				values.deleteCharAt(values.length() - 1);
				mcnn.execute(insertQuery + values);
				values = new StringBuilder();
			}
		}
		if (values.length() > 0) {
			values.deleteCharAt(values.length() - 1);
			mcnn.execute(insertQuery + values);
		}
	}

	private static void createCheckByDate(HRMISConn cnn) throws Exception {
		String query;

		// clear table recent_checkbydate
		query = "TRUNCATE TABLE checkbydate";
		cnn.execute(query);

		// create data of recent_checkbydate according to table tmp_checkinout
		query = "INSERT INTO checkbydate"
				+ "(userid,checkdate,reachtime,leavetime) "
				+ "SELECT userid,checkdate,Min(checktime),Max(checktime) "
				+ "FROM checkinout GROUP BY userid,checkdate";
		cnn.execute(query);

		// delete the reach records that are too late
		query = "UPDATE checkbydate SET reachtime=NULL "
				+ "WHERE reachtime>'20:00:00'";
		cnn.execute(query);

		// delete the leave records that are too early
		query = "UPDATE checkbydate SET leavetime=NULL "
				+ "WHERE leavetime<'12:00:00'";
		cnn.execute(query);

		// delete the reach records or leave records where interval of
		// reach and leave are less that 2 hours
		query = "UPDATE checkbydate SET reachtime=NULL "
				+ "WHERE TIMEDIFF(leavetime,reachtime)<'02:00:00'"
				+ " AND reachtime>'12:00:00'";
		cnn.execute(query);
		query = "UPDATE checkbydate SET leavetime=NULL "
				+ "WHERE TIMEDIFF(leavetime,reachtime)<'02:00:00'"
				+ " AND reachtime<='12:00:00'";
		cnn.execute(query);
	}

	private static void createDeptUser(HRMISConn cnn) throws Exception {
		cnn.execute("TRUNCATE TABLE dept_user");
		cnn.execute("SELECT deptid FROM department");
		HRMISConn cnn2 = new HRMISConn();
		while (cnn.next()) {
			createDeptUserByDeptId(cnn.getInt(1), cnn.getInt(1), cnn2);
		}
	}

	private static void createDeptUserByDeptId(int signedDeptId, int deptId,
			HRMISConn cnn) throws Exception {
		final String query = "INSERT INTO dept_user"
				+ "(deptid,userid,badgenumber,username,dir_deptid) "
				+ "SELECT ?,userid,badgenumber,username,deptid "
				+ "FROM userinfo WHERE deptid=?";
		cnn.prepare(query).setInt(1, signedDeptId).setInt(2, deptId).execute();

		int[] subDeptIds = createSubDeptIds(deptId, cnn);
		for (int d : subDeptIds) {
			createDeptUserByDeptId(signedDeptId, d, cnn);
		}
	}

	private static int[] createSubDeptIds(int deptId, HRMISConn cnn)
			throws Exception {
		cnn.prepare("SELECT deptId FROM department WHERE supdeptid=?")
				.setInt(1, deptId).execute();
		int[] subDeptIds = new int[cnn.getRowCount()];
		int i = 0;
		while (cnn.next()) {
			subDeptIds[i++] = cnn.getInt(1);
		}
		return subDeptIds;
	}

	public static void main(String[] args) throws Exception {
		MyTimeRecord record = new MyTimeRecord();
		AccessToMySQL atm = new AccessToMySQL("d:/att2000.mdb");
		atm.importData();
		record.print("total time");
	}
}