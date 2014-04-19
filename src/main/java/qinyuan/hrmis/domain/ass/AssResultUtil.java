package qinyuan.hrmis.domain.ass;

import qinyuan.lib.lang.MyMath;

public class AssResultUtil {

	private AssResultUtil() {
	}

	public static String getResultSum(AssResult[] results) {
		float sum = 0;
		for (AssResult result : results) {
			sum += result.getResult();
		}
		return MyMath.round(sum, 2);
	}
}
