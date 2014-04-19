package qinyuan.lib.file;

public class FileFormat {
	private FileFormat() {
	}

	/**
	 * get folder style of Linux operating system, such as /root/folder1/folder2
	 * 
	 * @param folderName
	 * @return
	 */
	public static String getLinStyleFolder(String folderName) {
		String result = folderName.replace("\\", "/");
		return lastChar(result) == '/' ? result : result + "/";
	}

	/**
	 * get folder style of windows operating system, such as
	 * D:\folder1\folder11\
	 * 
	 * @param folderName
	 * @return
	 */
	public static String getWinStyleFolder(String folderName) {
		String result = folderName.replace("/", "\\");
		return lastChar(result) == '\\' ? result : result + '\\';
	}

	private static char lastChar(String str) {
		return str.charAt(str.length() - 1);
	}

	public static void main(String[] args) {
		String mixStyle = "D:/folder1\\folder11/folder11a";
		System.out.println("mixStyle: " + mixStyle);
		String winStyle = getWinStyleFolder(mixStyle);
		System.out.println("winStyle: " + winStyle);
		String linStyle = getLinStyleFolder(mixStyle);
		System.out.println("linStyle: " + linStyle);
	}
}
