package qinyuan.lib.date;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MyDateTimeTest {
	private MyDateTime dt;
	private MyDateTime dt2;

	@Before
	public void initialize() {
		dt = new MyDateTime("2012-1-1 8:00:00");
		dt2 = new MyDateTime("2012-1-8 16:00:00");
	}

	@Test
	public void testAddDate() {
		dt.addDate(3);
		assertEquals("2012-01-04 08:00:00", dt.toString());
		dt.addDate(-3);
	}

	@Test
	public void testCompareTo() {
		assertEquals(dt.getTimeStamp() - dt2.getTimeStamp(), dt.compareTo(dt2));
	}

	@Test
	public void testGetDate() {
		assertEquals("2012-01-01", dt.getDate());
	}

	@Test
	public void testGetTime() {
		assertEquals("08:00:00", dt.getTime());
	}

	@Test
	public void testGetTimeStamp() {
		assertEquals(dt2.getTimeStamp() - dt.getTimeStamp(), 7 * 24 * 3600 + 8
				* 3600);
	}

	@Test
	public void testSetDate() {
		dt.setDate("2012-3-1");
		assertEquals("2012-03-01 08:00:00", dt.toString());
		dt.setDate("2012-1-01");
	}

	@Test
	public void testSetTime() {
		dt.setTime("12:08:08");
		assertEquals("2012-01-01 12:08:08", dt.toString());
		dt.setTime("08:00:00");
	}

	@Test
	public void testToString() {
		assertEquals("2012-01-01 08:00:00", dt.toString());
		assertEquals("2012-01-08 16:00:00", dt2.toString());
	}

	@Test
	public void testDayDiffMyDateTimeMyDateTime() {
		assertEquals(7 + 8.0 / 24, MyDateTime.DayDiff(dt, dt2), 0);
	}

	@Test
	public void testDayDiffMyDateTimeMyDateTimeInt() {
		assertEquals(7.33, MyDateTime.DayDiff(dt, dt2, 2), 0);
	}
}
