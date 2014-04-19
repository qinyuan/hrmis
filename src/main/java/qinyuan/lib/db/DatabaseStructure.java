package qinyuan.lib.db;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * This Class are used to create a HTML file to show the structure of certain
 * database
 * 
 * @author qinyuan15@sina.com
 * 
 */
public class DatabaseStructure {

	private File file;

	public DatabaseStructure(String fileName) {
		file = new File(fileName);
		if (!file.isFile()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
	}

	public void output(String DBName) throws Exception {
		FileWriter fw = new FileWriter(file, false);
		MySQLConn cnn = new MySQLConn();
		cnn.use(DBName);
		ArrayList<String> tables = new ArrayList<String>();

		// set the css style
		StringBuilder strOutput = new StringBuilder("<style type='text/css'>"
				+ "table{border-collapse:collapse;font-size:10px}"
				+ "td,th{border:1px solid #000000;}" + "</style>");

		// query the tables then record them to tables ArrayList
		String query = "SHOW TABLES";
		cnn.execute(query);
		while (cnn.next()) {
			tables.add(cnn.getString(1));
		}

		if (tables.size() == 0) {
			fw.close();
			cnn.close();
			return;
		}

		// show tables structure to out file
		for (String table : tables) {
			query = "DESC " + table;
			cnn.execute(query);
			strOutput.append("<table><caption><big><b>" + table
					+ "</b></big></caption>");
			strOutput.append("<tr><th>Field</th><th>Type</th>"
					+ "<th>Null</th><th>Key</th>"
					+ "<th>Default</th><th>Extra</th></tr>");

			while (cnn.next()) {
				strOutput.append("<tr>");
				for (int i = 1; i <= 6; i++) {
					strOutput.append("<td>" + cnn.getString(i) + "</td>");
				}
				strOutput.append("</tr>");
			}
			strOutput.append("</table>");
		}
		fw.write(strOutput.toString());
		fw.close();
		cnn.close();
	}

	public static void main(String[] args) throws Exception {
		DatabaseStructure dbs = new DatabaseStructure("E:/dbstructure.html");
		dbs.output("hrmis");
	}
}
