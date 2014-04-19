package qinyuan.hrmis.domain.att;

import static org.junit.Assert.*;

import org.junit.Test;

import qinyuan.hrmis.domain.att.manager.AccdItem;

public class AccdItemTest {

	@Test
	public void testNewInstance() {
		AccdItem item = AccdItem.newInstance(1);
		assertNotNull(item);
		assertSame(1, item.getId());
		assertEquals(2974, item.getEmpId());
		assertEquals("2012-09-28", item.getAttDate());
		assertTrue(item.isReach());
		assertSame(1, item.getTypeId());
		assertNull(item.getOperator());
		assertNull(item.getOperateTime());
	}
}
