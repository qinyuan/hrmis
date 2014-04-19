package qinyuan.hrmis.domain.att;

import static org.junit.Assert.*;

import org.junit.Test;

import qinyuan.hrmis.domain.att.manager.LeaveType;
import qinyuan.lib.db.HbnConn;

public class LeaveTypeTest {

	@Test
	public void testGetInstances() {
		LeaveType[] lts = LeaveType.getInstances();
		LeaveType[] norLts = LeaveType.getNorInstances();
		LeaveType[] othLts = LeaveType.getOthInstances();

		assertTrue(lts.length == norLts.length + othLts.length);
	}

	@Test
	public void testGetInstance() {
		HbnConn cnn = new HbnConn();
		LeaveType lt = cnn.get(LeaveType.class, 1);
		assertEquals((int) lt.getId(), 1);
		assertEquals("公出", lt.getDesc());
		assertEquals(false, lt.isNorm());

		lt = LeaveType.getInstance(1);
		assertEquals(1, (int) lt.getId());
		assertEquals("公出", lt.getDesc());
		assertEquals(false, lt.isNorm());

		lt = LeaveType.getInstance(3);
		assertEquals(3, (int) lt.getId());
		assertEquals("病假", lt.getDesc());
		assertEquals(true, lt.isNorm());
		try {
			cnn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
