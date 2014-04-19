package qinyuan.lib.db;

import java.io.File;
import java.sql.SQLException;
import qinyuan.lib.date.MyDateTime;
import qinyuan.lib.date.MyTimeRecord;
import qinyuan.lib.file.FileFormat;
import qinyuan.lib.file.MyFileIO;

public class MySQLBackup {

	private MySQLConn cnn;
	private String database;
	final static String XML_FILE_NAME = "structure.xml";
	final static String SQL_FILE_NAME = "structure.sql";
	private String errorInfo;
	private String backupFolderName;

	public MySQLBackup(String user, String password, String database)
			throws Exception {
		// create MySQL connection
		this.database = database;
		cnn = new MySQLConn();
		cnn.use(database);
	}

	public String getBackupFolderName() {
		return backupFolderName;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	/**
	 * method to export data to out file, include structure and data of the
	 * tables; if success return true, else return false
	 * 
	 * @param supFolder
	 * @throws Exception
	 */
	public boolean export(String supFolder) {
		// create backup folder
		backupFolderName = getFolderName(supFolder, database);
		File folder = new File(backupFolderName);
		if (!folder.isDirectory()) {
			if (!folder.mkdirs()) {
				errorInfo = "无法在" + supFolder + "下创建文件";
				return false;
			}
		}

		try {
			// record the name of all tables into array tables
			String[] tables = getTableNames(cnn);
			createXMLFile(database, tables, backupFolderName);
			createDataStructureFile(cnn, database, tables, backupFolderName);
			createDataFile(cnn, tables, backupFolderName);

			errorInfo = null;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			errorInfo = "数据备份失败";
			return false;
		}
	}

	/**
	 * this method output data in every tables to each text file
	 * 
	 * @param tables
	 * @param backupFolder
	 * @throws ExecuteException
	 */
	private static void createDataFile(MySQLConn cnn, String[] tables,
			String backupFolder) throws Exception {
		String outputQuery;
		for (String table : tables) {
			String textFileName = backupFolder + table + ".txt";
			outputQuery = "SELECT * INTO OUTFILE '" + textFileName
					+ "' FIELDS TERMINATED BY ',' FROM " + table;
			cnn.execute(outputQuery);
		}
	}

	/**
	 * this method create a SQL file(structure.sql), which describes the
	 * structure of each table
	 * 
	 * @param tables
	 * @param backupFolder
	 * @throws Exception
	 */
	private static void createDataStructureFile(MySQLConn cnn, String database,
			String[] tables, String backupFolder) throws Exception {
		// create SQL file
		String fileName = backupFolder + SQL_FILE_NAME;
		File file = new File(fileName);
		if (!file.isFile()) {
			file.createNewFile();
		}

		// set content to write
		StringBuilder s = new StringBuilder();
		s.append(getCreateDatabaseSQL(database));
		for (String table : tables) {
			s.append(getCreateTableSQL(cnn, table));
		}

		MyFileIO.write(fileName, s.toString());
	}

	/**
	 * subroutine to create XML file(structure.xml), which describes the name of
	 * backup database and each table
	 * 
	 * @param tables
	 * @param backupFolder
	 * @throws Exception
	 */
	private static void createXMLFile(String database, String[] tables,
			String backupFolder) throws Exception {
		// create XML file
		String fileName = backupFolder + XML_FILE_NAME;
		File file = new File(fileName);
		if (!file.isFile()) {
			file.createNewFile();
		}
		StringBuilder s = new StringBuilder();

		// set content to write
		s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
		s.append("<database>\r\n");
		s.append("\t<name>" + database + "</name>\r\n");
		for (String table : tables) {
			s.append("\t<table>" + table + "</table>\r\n");
		}
		s.append("</database>");

		// write to XML file
		MyFileIO.write(fileName, s.toString());
	}

	/**
	 * accept a string as database's name, return the SQL command to create the
	 * database
	 * 
	 * @param database
	 * @return
	 * @throws Exception
	 */
	private static String getCreateDatabaseSQL(String database)
			throws Exception {
		StringBuilder sqlCommand = new StringBuilder("\n");
		// create note
		sqlCommand.append("/* create database */\n");

		// create statement
		sqlCommand.append("CREATE DATABASE `" + database + "`;\n");

		// use statement
		sqlCommand.append("USE `" + database + "`;\n");

		return sqlCommand.toString();
	}

	/**
	 * accept a string as table's name, then return the SQL command to create
	 * the table
	 * 
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	private static String getCreateTableSQL(MySQLConn cnn, String tableName)
			throws Exception {
		// create note
		String createTableSQL = "\n/* create table " + tableName + " */\n";

		// create table statement
		cnn.execute("SHOW CREATE TABLE " + tableName);
		cnn.next();
		createTableSQL += cnn.getString(2) + ";\n\n";

		return createTableSQL;
	}

	/**
	 * accept a string as the name super folder, then create name of a folder,
	 * which will contain the backup files
	 * 
	 * @param supFolder
	 * @return
	 */
	private static String getFolderName(String supFolderName, String database) {
		supFolderName = FileFormat.getLinStyleFolder(supFolderName);

		String currentTime = new MyDateTime().toString();
		String folderName = supFolderName + database
				+ currentTime.replaceAll("[-]|[:]|[\\s]", ".") + "/";
		return folderName;
	}

	private static String[] getTableNames(MySQLConn cnn) throws SQLException {
		String query = "SHOW TABLES";
		cnn.execute(query);

		String[] tables;
		tables = new String[cnn.getRowCount()];
		int i = 0;
		while (cnn.next()) {
			tables[i++] = cnn.getString(1);
		}
		return tables;
	}

	public static void main(String[] args) throws Exception {
		MyTimeRecord timeRecord = new MyTimeRecord();
		MySQLBackup backup = new MySQLBackup("root", "qinyuan", "test");
		backup.export("e:/test");
		timeRecord.print("total time");
	}
}
