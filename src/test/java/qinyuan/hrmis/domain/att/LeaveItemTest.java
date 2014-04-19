package qinyuan.hrmis.domain.att;

import static org.junit.Assert.*;

import org.junit.Test;

import qinyuan.hrmis.domain.att.manager.LeaveItem;

public class LeaveItemTest {

	@Test
	public void testNewInstance() {
		LeaveItem li = LeaveItem.newInstance(1);
		assertSame(1, li.getId());
		assertEquals(195, li.getEmpId());
		assertEquals("2012-09-24", li.getAttDate());
		assertEquals("08:00:00", li.getStartTime());
		assertEquals("16:00:00", li.getEndTime());
		assertSame(1, li.getTypeId());
		assertNull(li.getOperator());
		assertNull(li.getOperateTime());
	}

}
