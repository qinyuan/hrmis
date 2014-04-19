package qinyuan.hrmis.domain.rcr;

import static org.junit.Assert.*;

import org.junit.Test;

public class ResumeFactoryUtilTest {

	@Test
	public void test() {
		ResumeFactory rf = new ResumeFactory();
		String str = ResumeFactoryUtil.getResumesTableAsRecruitor(rf);
		assertNotNull(str);
		System.out.println(str);
	}
}
