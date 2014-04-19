package qinyuan.lib.lang;

public class ArrayUtil {

	private ArrayUtil() {
	}

	public static void print(String[] strs) {
		System.out.print("[");
		boolean first = true;
		for (String str : strs) {
			if (first) {
				first = false;
			} else {
				System.out.print(",");
			}
			System.out.print(str);
		}
		System.out.println("]");
	}

}
