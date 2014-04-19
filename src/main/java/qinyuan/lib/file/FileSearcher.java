package qinyuan.lib.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSearcher {

	private File folder;

	public FileSearcher(File folder) {
		if (!folder.isDirectory()) {
			throw new RuntimeException("invalid folder: " + folder);
		}
		this.folder = folder;
	}

	public FileSearcher(String folderName) {
		this(new File(folderName));
	}

	public String[] search(String fileName) {
		if(fileName==null || fileName.isEmpty()){
			throw new RuntimeException("incorrect fileName: "+fileName);
		}
		List<String> list = new ArrayList<String>();

		searchRec(fileName, folder, list);

		String[] result = new String[list.size()];
		list.toArray(result);
		return result;
	}

	private static void searchRec(String fileName, File folder,
			List<String> list) {
		for (File file : folder.listFiles()) {
			if (file.isFile() && file.getName().contains(fileName)) {
				list.add(file.getPath());
			} else if (file.isDirectory()) {
				searchRec(fileName, file, list);
			}
		}
	}
	
	public static void main(String[] args) {
		FileSearcher fs=new FileSearcher("d:/hrmis/htdocs/resume");
		fs.search(null);
	}
}
