package qinyuan.lib.lang;

public class Padding {
	private int stringLength;
	private char padChar;

	public Padding(char padChar, int stringLength) {
		this.padChar = padChar;
		this.stringLength = stringLength;
	}

	public String pad(int integer) {
		return pad(String.valueOf(integer));
	}

	public String pad(String string) {
		StringBuilder prefix = new StringBuilder();
		int inputStringLength = string.length();
		if (inputStringLength < stringLength) {
			for (int i = inputStringLength; i < stringLength; i++) {
				prefix.append(padChar);
			}
		}
		return prefix.toString() + string;
	}

	public static void main(String[] args) {
		String str = "8";
		Padding padding = new Padding('0', 2);
		System.out.println("padding 8 with Padding(0,2): " + padding.pad(str));
		System.out.println();

		padding = new Padding('0', 3);
		System.out.println("padding 1234 with Padding(0,3): "
				+ padding.pad("1234"));
		System.out.println();

		padding = new Padding('#', 5);
		System.out
				.println("padding 12 with Padding(#,5): " + padding.pad("12"));
	}
}
