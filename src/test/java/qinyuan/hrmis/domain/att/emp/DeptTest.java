package qinyuan.hrmis.domain.att.emp;

import static org.junit.Assert.*;

import org.junit.Test;

public class DeptTest {

	@Test
	public void testGetInstance() {
		Dept dept = Dept.getInstance(1);
		assertNotNull(dept);
		assertEquals(1, (int) dept.getId());
		assertEquals("总公司", dept.getDesc());
	}
}
