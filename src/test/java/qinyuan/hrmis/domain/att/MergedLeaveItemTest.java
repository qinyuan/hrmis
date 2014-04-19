package qinyuan.hrmis.domain.att;

import static org.junit.Assert.*;

import org.junit.Test;

import qinyuan.hrmis.domain.att.manager.MergedLeaveItem;

public class MergedLeaveItemTest {

	@Test
	public void testAfter() {
		MergedLeaveItem item1 = new MergedLeaveItem();
		MergedLeaveItem item2 = new MergedLeaveItem();

		assertFalse(item1.after(item2));

		item1.setBadgenumber("11");
		item1.setStartDate("2012-12-31");
		item1.setStartTime("12:00:00");
		item1.setLeaveType("公出");
		item2.setBadgenumber("11");
		item2.setEndDate("2012-12-31");
		item2.setEndTime("12:00:00");
		item2.setLeaveType("公出");

		assertTrue(item1.after(item2));

		item1.setBadgenumber("12");
		assertFalse(item1.after(item2));
		item1.setBadgenumber("11");

		item1.setStartTime("12:00:01");
		assertFalse(item1.after(item2));

		item1.setStartDate("2013-01-01");
		item1.setStartTime("08:00:00");
		item2.setEndDate("2012-12-31");
		item2.setEndTime("16:00:00");
		assertTrue(item1.after(item2));

		item1.setStartTime("08:00:01");
		assertFalse(item1.after(item2));

		item1.setStartDate("2012-03-01");
		item1.setStartTime("08:00:00");
		item2.setEndDate("2012-02-29");
		item2.setEndTime("16:00:00");
		assertTrue(item1.after(item2));
	}

}
