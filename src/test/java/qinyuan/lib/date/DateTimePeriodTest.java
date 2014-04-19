package qinyuan.lib.date;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DateTimePeriodTest {

	private DateTimePeriod period;

	@Before
	public void initialize() {
		MyDateTime startDateTime = new MyDateTime("2012-1-1 8:00:00");
		MyDateTime endDateTime = new MyDateTime("2012-1-8 16:0:0");
		period = new DateTimePeriod(startDateTime, endDateTime);
	}

	@Test
	public void testAddEndDate() {
		period.addEndDate(2);
		assertEquals("2012-01-10", period.getEndDate());
		period.addEndDate(-2);
		assertEquals("2012-01-08", period.getEndDate());
	}

	@Test
	public void testAddStartDate() {
		period.addStartDate(2);
		assertEquals("2012-01-03", period.getStartDate());
		period.addStartDate(-2);
		assertEquals("2012-01-01", period.getStartDate());
	}

	@Test
	public void testGetDatePeriod() {
		assertEquals("2012-01-01~2012-01-08", period.getDatePeriod().toString());
	}

	@Test
	public void testGetEndDate() {
		assertEquals("2012-01-08", period.getEndDate());
	}

	@Test
	public void testGetEndDateTime() {
		assertEquals("2012-01-08 16:00:00", period.getEndDateTime());
	}

	@Test
	public void testGetEndTime() {
		assertEquals("16:00:00", period.getEndTime());
	}

	@Test
	public void testGetIntervalHour() {
		assertEquals(176, period.getIntervalHour(), 0);
	}

	@Test
	public void testGetStartDate() {
		assertEquals("2012-01-01", period.getStartDate());
	}

	@Test
	public void testGetStartDateTime() {
		assertEquals("2012-01-01 08:00:00", period.getStartDateTime());
	}

	@Test
	public void testGetStartTime() {
		assertEquals("08:00:00", period.getStartTime());
	}

	@Test
	public void testIsValid() {
		assertTrue(period.isValid());
		period.addStartDate(8);
		assertFalse(period.isValid());
		period.addStartDate(-7);
	}

	@Test
	public void testGetDates() {
		String[] expects = { "2012-01-01", "2012-01-02", "2012-01-03",
				"2012-01-04", "2012-01-05", "2012-01-06", "2012-01-07",
				"2012-01-08" };
		String[] actuals = period.getDates();
		for (int i = 0; i < actuals.length; i++) {
			assertEquals(expects[i], actuals[i]);
		}
	}

	@Test
	public void testSetEndDate() {
		assertEquals("2012-01-08", period.getEndDate());
	}

	@Test
	public void testSetEndTime() {
		assertEquals("16:00:00", period.getEndTime());
	}

	@Test
	public void testSetStartDate() {
		period.setStartDate("2012-01-03");
		assertEquals("2012-01-03", period.getStartDate());
		period.setStartDate("2012-01-01");
	}

	@Test
	public void testSetStartTime() {
		period.setStartTime("10:00:00");
		assertEquals("10:00:00", period.getStartTime());
		period.setStartTime("08:00:00");
	}

	@Test
	public void testToString() {
		assertEquals("2012-01-01 08:00:00~2012-01-08 16:00:00",
				period.toString());
	}
}