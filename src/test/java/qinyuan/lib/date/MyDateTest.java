package qinyuan.lib.date;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MyDateTest {

	private MyDate date;
	private MyDate date2;

	@Before
	public void initialize() {
		date = new MyDate("2012-1-1");
		date2 = new MyDate("2012-1-8");
	}

	@Test
	public void testCompareTo() {
		assertEquals(7, date.compareTo(date2), 0);
	}

	@Test
	public void testAdd() {
		date.add(2);
		assertEquals("2012-01-03", date.toString());
		date.add(-2);
		assertEquals("2012-01-01", date.toString());
	}

	@Test
	public void testClone() {
		MyDate date3 = date.clone();
		assertNotSame(date, date3);
		assertEquals(date, date3);
	}

	@Test
	public void testEqualsMyDate() {
		assertEquals(date, date);
	}

	@Test
	public void testGetTimeStamp() {
		MyDate date3 = new MyDate("2012-01-02");
		assertEquals(date3.getTimeStamp() - date.getTimeStamp(), 24 * 3600);
	}

	@Test
	public void testToString() {
		assertEquals("2012-01-01", date.toString());
		assertEquals("2012-01-08", date2.toString());
	}

	@Test
	public void testDayDiffStringString() {
		assertEquals(MyDate.dayDiff(date, date2), 7);
	}

	@Test
	public void testDayDiffMyDateMyDate() {
		assertEquals(10, MyDate.dayDiff("2012-01-01", "2012-01-11"));
	}

	@Test
	public void testIsDate() {
		assertTrue(MyDate.isDate("2012-01-01"));
		assertTrue(MyDate.isDate("2012-2-29"));
		assertFalse(MyDate.isDate("2013-02-29"));
		assertFalse(MyDate.isDate("1900-2-29"));
		assertFalse(MyDate.isDate("2013-01-32"));
		assertFalse(MyDate.isDate("2013-06-31"));
		
	}
}
