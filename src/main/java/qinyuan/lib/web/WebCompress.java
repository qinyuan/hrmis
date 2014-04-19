package qinyuan.lib.web;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import qinyuan.lib.file.MyFileIO;

public class WebCompress {

	private Set<String> excludeFiles = new HashSet<String>();

	public WebCompress excludeFile(String fileName) {
		excludeFiles.add(fileName.toLowerCase());
		return this;
	}

	public void compress(String folderName) throws IOException {
		compress(new File(folderName));
	}

	public void compress(File folder) throws IOException {
		if (!folder.isDirectory()) {
			throw new RuntimeException("incorrect folder: " + folder.getPath());
		}

		for (File file : folder.listFiles()) {
			String fileName = file.getName();
			if (excludeFiles.contains(fileName.toLowerCase())) {
				continue;
			}
			if (fileName.endsWith(".js")) {
				compresJsFile(file.getPath());
			} else if (fileName.endsWith(".css")) {
				compressCssFile(file.getPath());
			} else if (file.isDirectory()) {
				compress(file);
			}
		}
	}

	private static String[] jsDelStr = { "//.*[\\r\\n]", "/\\*.*\\*/",
			"\\r\\n", "\\n" };
	private static String[] cssDelStr = { "/\\*.*\\*/", "\\r\\n", "\\n" };
	private static String[] jsSpaceStr = { "=", "\\{", "\\}", ":", ";", ",",
			"&&", "\\|\\|", "\\+", "\\-", "\\(", "\\)" };
	private static String[] cssSpaceStr = { "\\{", "\\}", ":", ";", "," };

	private static String compressJS(String str) {
		for (String s : jsDelStr) {
			str = str.replaceAll(s, "");
		}
		for (String s : jsSpaceStr) {
			str = str.replaceAll("\\s*" + s + "\\s*", s);
		}
		return str.replaceAll("\\s{2,}", " ");
	}

	private static String compressCss(String str) {
		for (String s : cssDelStr) {
			str = str.replaceAll(s, "");
		}
		for (String s : cssSpaceStr) {
			str = str.replaceAll("\\s*" + s + "\\s*", s);
		}
		return str.replace("\\s{2,}", " ");
	}

	private static void compressCssFile(String fileName) throws IOException {
		String compressedContent = compressCss(MyFileIO.readAll(fileName));
		MyFileIO.write(fileName, compressedContent);
	}

	private static void compresJsFile(String fileName) throws IOException {
		String compressedContent = compressJS(MyFileIO.readAll(fileName));
		MyFileIO.write(fileName, compressedContent);
	}

	public static void main(String[] args) throws Exception {
		WebCompress p = new WebCompress();
		p.excludeFile("jquery.js").excludeFile("DatePicker")
				.compress("D:/apache-tomcat-7.0.27/webapps/hrmis");
	}
}