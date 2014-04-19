package qinyuan.lib.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MyFileIO {

	private MyFileIO() {
	}

	public static void append(String fileName, String strToAppend)
			throws IOException {
		write(fileName, strToAppend, true);
	}

	public static void delete(String fileName) {
		delete(new File(fileName));
	}

	public static void delete(File file) {
		if (file.isFile()) {
			file.delete();
		} else if(file.isDirectory()){
			File[] subFiles = file.listFiles();
			for (File f : subFiles) {
				delete(f);
			}
			file.delete();
		}else{
			throw new RuntimeException("invalid file"+file.getPath());
		}
	}

	public static String readAll(String fileName) throws IOException {
		StringBuilder o = new StringBuilder();
		File file = getFile(fileName);
		try (FileInputStream fis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr)) {
			String str;
			while (true) {
				str = br.readLine();
				if (str == null) {
					break;
				}
				o.append(str + "\r\n");
			}
			return o.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("error to read file" + fileName
					+ " in class MyFileIO");
		}
	}

	public static String readAllAsOneLine(String fileName) throws IOException {
		String str = readAll(fileName);
		return str.replace("\r\n", "");
	}

	public static String[] readAsStrArr(String fileName) throws IOException {
		String str = readAll(fileName);
		return str.split("\r\n");
	}

	public static String readFirstLine(String fileName) throws IOException {
		File file = getFile(fileName);
		try (FileInputStream fis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr)) {
			String str = br.readLine();
			return str == null ? "" : str;
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("error to read file" + fileName
					+ " in class MyFileIO");
		}
	}

	public static void write(String fileName, String strToWrite)
			throws IOException {
		write(fileName, strToWrite, false);
	}

	private static void write(String fileName, String strToWrite, boolean append)
			throws IOException {
		File file = getFile(fileName);
		try (FileOutputStream fos = new FileOutputStream(file, append);
				OutputStreamWriter osw = new OutputStreamWriter(fos);
				BufferedWriter bw = new BufferedWriter(osw)) {
			bw.write(strToWrite);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("error to write file" + fileName
					+ " in class MyFileIO");
		}
	}

	private static File getFile(String fileName) {
		File file = new File(fileName);
		if (!file.isFile()) {
			throw new RuntimeException("invalid file name: " + fileName);
		}
		return file;
	}
}