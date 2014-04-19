package qinyuan.lib.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import qinyuan.lib.file.MyFileIO;

public class MySQLConn implements AutoCloseable {
	private Connection cnn;
	private ResultSet rs;
	private PreparedStatement stmt;

	public MySQLConn() throws SQLException {
		cnn = MysqlDs.getConnection();
	}

	public void close() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try {
			cnn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void beforeFirst() throws SQLException {
		rs.beforeFirst();
	}

	public MySQLConn prepare(String query) throws SQLException {
		rs = null;
		stmt = cnn.prepareStatement(query);
		return this;
	}

	public int execute() throws SQLException {
		rs = null;
		if (stmt.execute()) {
			rs = stmt.getResultSet();
		}
		return stmt.getUpdateCount();
	}

	public int execute(String query) throws SQLException {
		return prepare(query).execute();
	}

	public int execute(StringBuilder query) throws SQLException {
		return execute(query.toString());
	}

	public int[] executeSource(String sourceFile) throws Exception {
		String str = MyFileIO.readAll(sourceFile);
		String[] sqls = str.split(";");
		int[] ints = new int[(str.endsWith(";") ? sqls.length : sqls.length - 1)];
		for (int i = 0; i < ints.length; i++) {
			ints[i] = execute(sqls[i]);
		}
		return ints;
	}

	public boolean getBoolean(int index) throws SQLException {
		return rs.getBoolean(index);
	}

	public boolean getBoolean(String col) throws SQLException {
		return rs.getBoolean(col);
	}

	public int getColCount() throws SQLException {
		return rs.getMetaData().getColumnCount();
	}

	public double getDouble(int index) throws SQLException {
		return rs.getDouble(index);
	}

	public double getDouble(String col) throws SQLException {
		return rs.getDouble(col);
	}

	public int getInt(int index) throws SQLException {
		return rs.getInt(index);
	}

	public int getInt(String col) throws SQLException {
		return rs.getInt(col);
	}

	public int getRowCount() throws SQLException {
		rs.last();
		int rowCount = rs.getRow();
		rs.beforeFirst();
		return rowCount;
	}

	public String getString(int index) throws SQLException {
		return rs.getString(index);
	}

	public String getString(String col) throws SQLException {
		return rs.getString(col);
	}

	public String getValues() throws SQLException {
		StringBuilder o = new StringBuilder();
		for (int i = 1; i <= getColCount(); i++) {
			o.append(getString(i) + ",");
		}
		if (o.length() > 0) {
			o.deleteCharAt(o.length() - 1);
		}
		return o.toString();
	}

	public boolean next() throws SQLException {
		return rs.next();
	}

	public MySQLConn setBoolean(int index, boolean arg) throws SQLException {
		stmt.setBoolean(index, arg);
		return this;
	}

	public MySQLConn setDouble(int index, double arg) throws SQLException {
		stmt.setDouble(index, arg);
		return this;
	}

	public MySQLConn setInt(int index, int arg) throws SQLException {
		stmt.setInt(index, arg);
		return this;
	}

	public MySQLConn setString(int index, String arg) throws SQLException {
		stmt.setString(index, arg);
		return this;
	}

	public MySQLConn use(String database) throws SQLException {
		execute("use " + database);
		return this;
	}
}