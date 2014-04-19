package qinyuan.lib.date;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Week {

	private final static String[] WEEK_NAMES = { "周日", "周一", "周二", "周三", "周四",
			"周五", "周六" };
	private final static int MONTH_START_INDEX = 5;
	private final static int WEEK_DAY_COUNT = 7;
	private Calendar cal;
	private int weeknum;

	public Week() {
		this(0);
	}

	public Week(int weeknum) {
		this.weeknum = weeknum;
		cal = new GregorianCalendar();

		// locate to the Monday of certain week
		addDay(1 - getDayOfWeek() + weeknum * WEEK_DAY_COUNT);
	}

	public Week(int year, int month, int day) {
		constructByDate(year, month, day);
	}

	public Week(String dateStr) {
		if (!MyDate.isDate(dateStr)) {
			throw new RuntimeException("incorrect date string format: "
					+ dateStr + " in class " + getClass());
		}

		String[] dateArr = dateStr.split("-");
		int year = Integer.parseInt(dateArr[0]);
		int month = Integer.parseInt(dateArr[1]);
		int day = Integer.parseInt(dateArr[2]);
		constructByDate(year, month, day);
	}

	public void add(int weeksToAdd) {
		this.weeknum += weeksToAdd;
		addDay(weeksToAdd * WEEK_DAY_COUNT);
	}

	/**
	 * return date String such as 2000-01-01
	 * 
	 * @param dayOfWeek
	 * @return
	 */
	public String getLongDay(int dayOfWeek) {
		addDay(dayOfWeek);
		String result = getCalStr();
		addDay(-dayOfWeek);
		return result;
	}

	public String[] getLongDays() {
		String[] days = new String[WEEK_DAY_COUNT];
		for (int i = 0; i < WEEK_DAY_COUNT; i++) {
			days[i] = getCalStr();
			addDay(1);
		}
		addDay(-WEEK_DAY_COUNT);
		return days;
	}

	/**
	 * return String such as 12-05
	 * 
	 * @param dayOfWeek
	 * @return
	 */
	public String getShortDay(int dayOfWeek) {
		return getLongDay(dayOfWeek).substring(MONTH_START_INDEX);
	}

	public String[] getShortDays() {
		String[] days = new String[WEEK_DAY_COUNT];
		for (int i = 0; i < WEEK_DAY_COUNT; i++) {
			days[i] = getCalStr().substring(MONTH_START_INDEX);
			addDay(1);
		}
		addDay(-WEEK_DAY_COUNT);
		return days;
	}

	public int getWeeknum() {
		return weeknum;
	}

	private void addDay(int dayCount) {
		cal.add(Calendar.DATE, dayCount);
	}

	/**
	 * return week name such as 周一
	 * 
	 * @param weeknum
	 * @return
	 */
	public static String getWeekName(int weeknum) {
		return WEEK_NAMES[weeknum];
	}

	private String getCalStr() {
		int intYear = cal.get(Calendar.YEAR);
		String strMonth = padZero(cal.get(Calendar.MONTH) + 1);
		String strDay = padZero(cal.get(Calendar.DATE));

		return intYear + "-" + strMonth + "-" + strDay;
	}

	private int getDayOfWeek() {
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	private void constructByDate(int year, int month, int day) {
		cal = new GregorianCalendar(year, month - 1, day);

		// locate to the Monday of certain week
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		addDay(1 - dayOfWeek);

		long stamp = cal.getTimeInMillis();
		long nowStamp = System.currentTimeMillis();
		weeknum = (int) (stamp - nowStamp) / 1000 / 3600 / 24 / 7;
	}

	private static String padZero(int num) {
		return (num < 10 ? "0" : "") + num;
	}

	public static void main(String[] args) {
		Week week = new Week();
		System.out.println(week.getLongDay(0));
		System.out.println(week.getLongDay(7));
		week = new Week(-1);
		System.out.println(week.getLongDay(0));
		week = new Week(1);
		System.out.println(week.getLongDay(0));
	}
}