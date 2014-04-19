package qinyuan.lib.file;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class FileUtil {
	
	private FileUtil(){}

	public static void show(String fileName) throws IOException{
		File file=new File(fileName);
		Desktop.getDesktop().open(file);
	}
}
