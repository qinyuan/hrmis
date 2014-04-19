package qinyuan.lib.date;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MyDate {
	private static int DAY_COUNT_OF_COMMON[] = new int[] { 31, 28, 31, 30, 31,
			30, 31, 31, 30, 31, 30, 31 };
	private static int DAY_COUNT_OF_LEAP[] = new int[] { 31, 29, 31, 30, 31,
			30, 31, 31, 30, 31, 30, 31 };
	private Calendar cal;

	public MyDate() {
		cal = new GregorianCalendar();
	}

	/**
	 * Constructor, receive a date string as parameter, the format of date
	 * string should likes 2000-1-1
	 * 
	 * @param dateString
	 * @throws DateFormatException
	 */
	public MyDate(String dateString) {
		if (isDate(dateString)) {
			int[] dateArray = ConvertToDateArray(dateString);
			cal = new GregorianCalendar(dateArray[0], dateArray[1] - 1,
					dateArray[2]);
		} else {
			throw new RuntimeException("Incorrect date format: " + dateString);
		}
	}

	public int compareTo(MyDate otherDate) {
		return dayDiff(this, otherDate);
	}

	public void add(int daysCount) {
		cal.add(Calendar.DATE, daysCount);
	}

	@Override
	public MyDate clone() {
		return new MyDate(this.toString());
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof MyDate) {
			return getTimeStamp() == ((MyDate) other).getTimeStamp();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Integer.valueOf(getTimeStamp()).hashCode();
	}

	public int getTimeStamp() {
		return (int) (cal.getTimeInMillis() / 1000);
	}

	/**
	 * return meaningful string represents the date, such as 2000-01-01
	 */
	@Override
	public String toString() {
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		DecimalFormat monDayFormat = new DecimalFormat("00");

		return year + "-" + monDayFormat.format(month) + "-"
				+ monDayFormat.format(day);
	}

	public static int dayDiff(String startDate, String endDate) {
		return dayDiff(new MyDate(startDate), new MyDate(endDate));
	}

	public static int dayDiff(MyDate startDate, MyDate endDate) {
		int startTimeStamp = startDate.getTimeStamp();
		int endTimeStemp = endDate.getTimeStamp();

		return (endTimeStemp - startTimeStamp) / 24 / 3600;
	}

	/**
	 * this function accept a string represented a date, then judge whether it
	 * is correct day format
	 * 
	 * @return
	 */
	public static boolean isDate(String dateString) {
		// split dateString into an array
		int[] dateArray;
		try {
			dateArray = ConvertToDateArray(dateString);
		} catch (Exception e) {
			return false;
		}
		if (dateArray.length != 3) {
			return false;
		}

		// record the year/month/day
		int year = dateArray[0];
		int month = dateArray[1];
		int day = dateArray[2];

		// check the year value
		if (year < 0) {
			return false;
		}

		// check the month value
		if (month < 1 || month > 12) {
			return false;
		}

		// check the day value
		if (day < 1 || day > getDayCountOfMonth(year, month)) {
			return false;
		}

		return true;
	}

	private static int[] ConvertToDateArray(String dateString) {
		String[] strDateArray = dateString.split("-");

		// record year/month/day value to intDateArray
		int[] intDateArray = new int[3];
		for (int i = 0; i < 3; i++) {
			intDateArray[i] = Integer.parseInt(strDateArray[i]);
		}
		return intDateArray;
	}

	private static int getDayCountOfMonth(int year, int month) {
		if (isLeapYear(year)) {
			return DAY_COUNT_OF_LEAP[month - 1];
		} else {
			return DAY_COUNT_OF_COMMON[month - 1];
		}
	}

	private static boolean isLeapYear(int year) {
		if (year % 4 == 0 && (year % 100) != 0) {
			return true;
		} else if (year % 400 == 0) {
			return true;
		} else {
			return false;
		}
	}
}
