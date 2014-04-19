package qinyuan.lib.file;

import java.io.File;
import java.io.FileWriter;

public class MyFileWriter {

	private File file;
	private boolean firstWrite = true;

	/**
	 * Constructor, accept a string as file name
	 * 
	 * @param fileName
	 * @throws FileFormatException
	 */
	public MyFileWriter(String fileName) throws Exception {

		// initialize the file variable
		file = new File(fileName);

		// if file not exists, create it
		if (!file.isFile()) {
			file.createNewFile();
		}
	}

	/**
	 * write content to file
	 * 
	 * @param content
	 * @throws WriteException
	 */
	public void write(String content) throws Exception {
		FileWriter writer;
		// if it's first time to write to file, clear all file content, else
		// append the content at the end
		if (firstWrite) {
			writer = new FileWriter(file, false);
			firstWrite = false;
		} else {
			writer = new FileWriter(file, true);
		}

		writer.write(content);
		writer.close();
	}
}