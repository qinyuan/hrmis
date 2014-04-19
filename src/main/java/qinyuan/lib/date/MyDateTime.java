package qinyuan.lib.date;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import qinyuan.lib.lang.MyMath;

public class MyDateTime {

	private Calendar cal;
	private static final DecimalFormat f = new DecimalFormat("00");

	public MyDateTime() {
		cal = new GregorianCalendar();
	}

	/**
	 * Constructor, receive a dateTime String such as "2012-01-01 08:00:00"
	 * 
	 * @param dateTime
	 */
	public MyDateTime(String dateTime) {
		cal = createCal(dateTime);
	}

	public void addDate(int dayNum) {
		cal.add(Calendar.DATE, dayNum);
	}

	public int compareTo(MyDateTime otherDateTime) {
		return getTimeStamp() - otherDateTime.getTimeStamp();
	}

	/**
	 * return String likes 2012-01-01
	 * 
	 * @return
	 */
	public String getDate() {
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		return year + "-" + f.format(month) + "-" + f.format(day);
	}

	/**
	 * return String likes 08:00:00
	 * 
	 * @return
	 */
	public String getTime() {
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		return f.format(hour) + ":" + f.format(minute) + ":" + f.format(second);
	}

	public int getTimeStamp() {
		return (int) (cal.getTimeInMillis() / 1000);
	}

	public void setDate(String date) {
		cal = createCal(date + " " + getTime());
	}

	public void setTime(String time) {
		cal = createCal(getDate() + " " + time);
	}

	/**
	 * return String likes 2000-01-01 00:00:00
	 */
	public String toString() {
		return getDate() + " " + getTime();
	}

	public static double DayDiff(MyDateTime startDateTime,
			MyDateTime endDateTime) {
		int startStamp = startDateTime.getTimeStamp();
		int endStamp = endDateTime.getTimeStamp();
		return ((double) (endStamp - startStamp)) / 3600 / 24;
	}

	public static double DayDiff(MyDateTime startDateTime,
			MyDateTime endDateTime, int precision) {
		double interval = DayDiff(startDateTime, endDateTime);
		return Double.parseDouble(MyMath.round(interval, precision));
	}

	private static Calendar createCal(String dateTime) {
		String[] strArr = dateTime.split("-|\\s|:");
		if (strArr.length < 6) {
			throw new RuntimeException("Incorrect date time string: '"
					+ dateTime + "'");
		}
		int year = Integer.parseInt(strArr[0]);
		int month = Integer.parseInt(strArr[1]) - 1;
		int day = Integer.parseInt(strArr[2]);
		int hour = Integer.parseInt(strArr[3]);
		int minute = Integer.parseInt(strArr[4]);
		int second = Integer.parseInt(strArr[5]);
		Calendar cal = new GregorianCalendar(year, month, day, hour, minute,
				second);
		return cal;
	}
}