package qinyuan.lib.date;

import static org.junit.Assert.*;

import org.junit.Test;

public class WeekTest {

	Week week = new Week("2013-5-28");

	@Test
	public void testAdd() {
	}

	@Test
	public void testGetLongDay() {
		assertEquals("2013-05-26", week.getLongDay(0));
	}

	@Test
	public void testGetLongDays() {
		String[] days = week.getLongDays();
		for (int i = 0; i < 6; i++) {
			assertEquals("2013-05-" + (i + 26), days[i]);
		}
		assertEquals("2013-06-01", days[6]);
		assertEquals("2013-05-26", week.getLongDay(0));
	}

	@Test
	public void testGetShortDay() {
		String[] days = week.getShortDays();
		for (int i = 0; i < 6; i++) {
			assertEquals("05-" + (i + 26), days[i]);
		}
		assertEquals("06-01", days[6]);
		assertEquals("2013-05-26", week.getLongDay(0));
	}

	@Test
	public void testGetShortDays() {
		assertEquals("05-26", week.getShortDay(0));
		assertEquals("2013-05-26", week.getLongDay(0));
	}

	@Test
	public void testGetWeeknum() {
		//assertEquals(0, week.getWeeknum());
		assertEquals("2013-05-26", week.getLongDay(0));
	}

	@Test
	public void testGetWeekName() {
		assertEquals("周日", Week.getWeekName(0));
		assertEquals("周一", Week.getWeekName(1));
		assertEquals("周二", Week.getWeekName(2));
		assertEquals("周三", Week.getWeekName(3));
		assertEquals("周四", Week.getWeekName(4));
		assertEquals("周五", Week.getWeekName(5));
		assertEquals("周六", Week.getWeekName(6));
		assertEquals("2013-05-26", week.getLongDay(0));
	}
}
