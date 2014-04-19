package qinyuan.lib.date;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DatePeriodTest {
	
	private DatePeriod period;
	
	@Before
	public void initialize(){
		MyDate startDate=new MyDate("2012-1-1");
		MyDate endDate=new MyDate("2012-1-8");
		period=new DatePeriod(startDate, endDate);
	}

	@Test
	public void testGetEndDate() {
		assertEquals("2012-01-01", period.getStartDate());
	}

	@Test
	public void testGetStartDate() {
		assertEquals("2012-01-08", period.getEndDate());
	}

	@Test
	public void testGetDayDiff() {
		assertEquals(7, period.getDayDiff());
	}
}
