package qinyuan.lib.lang;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

public class MyMath {

	public static String round(double decimal, int precision) {
		String pattern = "#";
		if (precision > 0) {
			pattern += "." + StringUtils.repeat("#", precision);
		}
		DecimalFormat format = new DecimalFormat(pattern);
		return format.format(decimal);
	}

	public static boolean isNumeric(String str) {
		if (str == null || str.isEmpty())
			return false;
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	public static void main(String[] args) {
		double pi = Math.PI;
		System.out.println("pi: " + pi);
		System.out.println("round(pi,5): " + round(pi, 5));
		System.out.println("round(pi,4): " + round(pi, 4));
		System.out.println("round(pi,2): " + round(pi, 2));
		System.out.println("round(3.14,4): " + round(3.14, 4));
		System.out.println("isNumberic('134'): " + isNumeric("134"));
		System.out.println("isNumberic('12.34'): " + isNumeric("12.34"));
		System.out.println("isNumberic('a123'): " + isNumeric("a123"));
		System.out.println("isNumberic(null): " + isNumeric(null));
	}
}
