package qinyuan.lib.date;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MyTimeTest {

	private MyTime t;

	@Before
	public void initialize() {
		t = new MyTime("16:1:13");
	}

	@Test
	public void testGetHour() {
		assertEquals(t.getHour(), 16);
	}

	@Test
	public void testGetMinute() {
		assertEquals(t.getMinute(), 1);
	}

	@Test
	public void testGetSecond() {
		assertEquals(t.getSecond(), 13);
	}

	@Test
	public void testToString() {
		assertEquals("16:01:13", t.toString());
	}

	@Test
	public void testHourDiffStringString() {
		assertEquals(8.25, MyTime.hourDiff("08:00:00", "16:15:00"), 0);
	}

	@Test
	public void testHourDiffStringStringInt() {
		assertEquals(8.33, MyTime.hourDiff("08:00:00", "16:20:00", 2), 0);
	}

	@Test
	public void testHourDiffMyTimeMyTime() {
		MyTime t1 = new MyTime("08:00:00");
		MyTime t2 = new MyTime("16:15:00");
		assertEquals(8.25, MyTime.hourDiff(t1, t2), 0);
	}

	@Test
	public void testHourDiffMyTimeMyTimeInt() {
		MyTime t1 = new MyTime("08:00:00");
		MyTime t2 = new MyTime("16:20:00");
		assertEquals(8.33, MyTime.hourDiff(t1, t2, 2), 0);
	}
}
