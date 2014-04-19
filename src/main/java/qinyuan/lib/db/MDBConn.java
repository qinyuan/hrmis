package qinyuan.lib.db;

public class MDBConn extends DatabaseConn {
	private final static String CLASS_NAME = "sun.jdbc.odbc.JdbcOdbcDriver";
	private final static String URL_PRIFIX = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";

	/**
	 * Constructor, receive a string parameter as the full name of the MDB file
	 * 
	 * @param filePath
	 * @throws MDBConnectionException
	 */
	public MDBConn(String filePath) throws Exception {
		super(CLASS_NAME, URL_PRIFIX + filePath, "", "");
	}
}
