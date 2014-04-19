package qinyuan.lib.db;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import qinyuan.lib.file.FileFormat;
import qinyuan.lib.file.MyXMLReader;

public class MySQLRecovery {

	private MySQLConn cnn;
	private final static String XML_FILE_NAME = MySQLBackup.XML_FILE_NAME;
	private final static String SQL_FILE_NAME = MySQLBackup.SQL_FILE_NAME;
	private final static String TABLE_TAGNAME = "table";
	private String errorInfo;

	public MySQLRecovery(String user, String password) throws Exception {
		cnn = new MySQLConn();
	}

	/**
	 * import data, if database exists drop it
	 * 
	 * @param ImportDataFolderName
	 * @return
	 */
	public boolean ImportData(String ImportDataFolderName) {
		String folderName = FileFormat.getLinStyleFolder(ImportDataFolderName);

		// validation
		if (!(new File(folderName).isDirectory())) {
			errorInfo = "文件夹" + folderName + "不存在";
			return false;
		} else if (!(new File(folderName + SQL_FILE_NAME).isFile())) {
			errorInfo = "未找到文件" + folderName + SQL_FILE_NAME;
			return false;
		} else if (!(new File(folderName + XML_FILE_NAME).isFile())) {
			errorInfo = "未找到文件" + folderName + XML_FILE_NAME;
			return false;
		}

		// get name of each table according to XML file
		File xmlFile = new File(folderName + XML_FILE_NAME);
		String[] tables = getTablesFromXML(xmlFile);

		// check if data of each table exists
		if (!checkDataFiles(folderName, tables)) {
			errorInfo = "数据文件不全";
			return false;
		}

		try {
			// get the database's name
			String dbName = getDatabaseName(xmlFile);

			// if the database exists, drop it
			if (databaseExists(cnn, dbName)) {
				cnn.execute("DROP DATABASE " + dbName);
			}

			cnn.executeSource(folderName + SQL_FILE_NAME);
			cnn.execute("USE " + dbName);
			ImportValues(cnn, folderName, dbName, tables);

			errorInfo = null;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	private static boolean checkDataFiles(String folderName, String[] tables) {
		// check if each table's data file exists
		File tableTextFile;
		for (String table : tables) {
			tableTextFile = new File(folderName + table + ".txt");
			if (!tableTextFile.isFile()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * this method accept a string as the name of database, then judge if the
	 * database exists
	 * 
	 * @param hrmis
	 * @return
	 */
	private static boolean databaseExists(MySQLConn cnn, String databaseName) {
		try {
			cnn.execute("SHOW DATABASES LIKE '" + databaseName + "'");
			return cnn.next();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * this method parse the XML file then get the name of database then return
	 * it
	 * 
	 * @return
	 */
	private static String getDatabaseName(File xmlFile) throws Exception {
		MyXMLReader reader = new MyXMLReader(xmlFile);
		String dbName = reader.getByTagName("name");
		return dbName;
	}

	/**
	 * this subroutine get a XML file object, then read the value of each table
	 * tag in the XML file, finally return an array contains them
	 * 
	 * @param xmlFile
	 * @return
	 */
	private static String[] getTablesFromXML(File xmlFile) {
		try {
			// parse the XML file
			Document doc = getXMLDocument(xmlFile);

			// read the value of each table tag
			NodeList nodelist = doc.getElementsByTagName(TABLE_TAGNAME);
			int tableCount = nodelist.getLength();
			String[] tables = new String[tableCount];
			for (int i = 0; i < tableCount; i++) {
				tables[i] = nodelist.item(i).getFirstChild().getNodeValue();
			}

			return tables;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * this method parse XML file as a Document object
	 * 
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	private static Document getXMLDocument(File xmlFile) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(xmlFile);
		return doc;
	}

	private static void ImportValues(MySQLConn cnn, String folderName,
			String database, String[] tables) throws Exception {
		for (String table : tables) {
			String textFileName = folderName + table + ".txt";
			String sqlCommand = "LOAD DATA INFILE '" + textFileName
					+ "' INTO TABLE " + database + "." + table
					+ " FIELDS TERMINATED BY ','";
			cnn.execute(sqlCommand);
		}
	}

	public static void main(String[] args) throws Exception {
		MySQLRecovery recover = new MySQLRecovery("root", "qinyuan");
		System.out.print(recover.ImportData("e:/test/test2013.05.26.14.56.00"));
		System.out.println(recover.getErrorInfo());
	}
}
