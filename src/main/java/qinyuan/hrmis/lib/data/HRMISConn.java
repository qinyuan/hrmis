package qinyuan.hrmis.lib.data;

import java.sql.SQLException;

import qinyuan.lib.db.MySQLConn;

public class HRMISConn extends MySQLConn {

	public HRMISConn() throws SQLException {
		super();
		use("hrmis");
	}

	public static void main(String[] args) throws SQLException {
		HRMISConn cnn = new HRMISConn();
		String query = "select * from leavetype where isnorm=?";
		cnn.prepare(query).setBoolean(1, false).execute();
		while (cnn.next()) {
			System.out.println(cnn.getValues());
		}
		cnn.close();
	}
}
