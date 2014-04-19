package qinyuan.lib.db;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConn implements AutoCloseable {
	private Statement stmt;
	private Connection conn;
	private ResultSet rs;

	public DatabaseConn(String className, String url, String user,
			String password) throws Exception {
		Class.forName(className);
		conn = DriverManager.getConnection(url, user, password);
		this.stmt = conn.createStatement();
	}

	public void beforeFirst() throws SQLException {
		rs.beforeFirst();
	}

	public void close() throws SQLException {
		if (rs != null) {
			rs.close();
		}
		if (stmt != null) {
			stmt.close();
		}
		if (conn != null) {
			conn.close();
		}
	}

	public void execute(String query) throws SQLException {
		stmt.execute(query);
	}

	public void execute(StringBuilder query) throws SQLException {
		execute(query.toString());
	}

	public void executeSource(File sqlFile) throws Exception {
		FileInputStream fis = new FileInputStream(sqlFile);
		BufferedInputStream bis = new BufferedInputStream(fis);

		StringBuilder query = new StringBuilder("");
		int charCode;
		int semicolonCode = (int) (';');

		while (true) {
			charCode = bis.read();
			if (charCode == -1) {
				// point to the end of file, exit
				break;
			} else if (charCode == semicolonCode) {
				// read to the end of a query, execute it
				stmt.execute(query.toString());
				query = new StringBuilder("");
			} else {
				query.append((char) charCode);
			}
		}

		// close the i/o stream
		bis.close();
		fis.close();
	}

	/**
	 * execute a SQL command, then return how many rows affected
	 * 
	 * @param query
	 * @return
	 * @throws Exception
	 * @throws QueryException
	 * @throws SQLException
	 */
	public int executeUpdate(String query) throws SQLException {
		return stmt.executeUpdate(query);
	}

	/**
	 * move pointer to the first row of result set
	 * 
	 * @return
	 * @throws SQLException
	 */
	public boolean first() throws SQLException {
		return rs.first();
	}

	public boolean getBool(int index) throws Exception {
		String str = getString(index);
		if (str.equals("0")) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * get the columns count of the result set
	 * 
	 * @return
	 */
	public int getColumnCount() {
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			return rsmd.getColumnCount();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int getInt(int index) throws Exception {
		return Integer.parseInt(getString(index));
	}

	/**
	 * get the total row number count of the result set
	 * 
	 * @return
	 */
	public int getRowCount() {
		int count;
		try {
			if (!rs.last()) {
				return 0;
			}
			count = rs.getRow();
			rs.beforeFirst();
			return count;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * get the result set value, be careful that the index number begin with 1,
	 * not 0.
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 * @throws SQLException
	 */
	public String getString(int index) throws SQLException {
		return rs.getString(index);
	}

	/**
	 * get the values of query result, enclose the values with parentheses and
	 * separate them with commas, such as ('1','2','3',NULL)
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String getValues() throws SQLException {
		StringBuilder values = new StringBuilder("(");
		int ColCount = getColumnCount();
		String value;

		// fetch the value of each column
		for (int i = 1; i <= ColCount; i++) {
			value = rs.getString(i);
			if (value == null) {
				values.append("NULL,");
			} else {
				values.append("'" + value + "',");
			}
		}
		// delete the last useless comma
		values.deleteCharAt(values.length() - 1);
		values.append(')');
		return values.toString();
	}

	/**
	 * get the query values then enclose them with table data tag, which is
	 * usually shown in web page
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getTD() throws Exception {
		StringBuilder result = new StringBuilder();
		String str;
		int ColCount = getColumnCount();
		for (int i = 1; i <= ColCount; i++) {
			str = getString(i);
			if (str == null) {
				str = "";
			}
			result.append("<td>" + str + "</td>");
		}
		return result.toString();
	}

	/**
	 * this method execute several SQL commands, the queries contains the SQL
	 * commands to execute
	 * 
	 * @param queries
	 * @throws SQLException
	 * @throws Exception
	 */
	public void multiExecute(String[] queries) throws SQLException {
		int queriesCount = queries.length;
		for (int i = 0; i < queriesCount; i++) {
			stmt.execute(queries[i]);
		}
	}

	/**
	 * move pointer to next row of result set
	 * 
	 * @return
	 * @throws SQLException
	 */
	public boolean next() throws SQLException {
		return rs.next();
	}

	/**
	 * execute a SQL command, save the result set, which can be manipulated by
	 * next() and so on.
	 * 
	 * @param query
	 * @throws QueryException
	 * @throws SQLException
	 */
	public void query(String query) throws SQLException {
		rs = stmt.executeQuery(query);
	}

	public void query(StringBuilder query) throws SQLException {
		query(query.toString());
	}
}
