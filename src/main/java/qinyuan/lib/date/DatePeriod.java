package qinyuan.lib.date;

public class DatePeriod {

	private MyDate startDate;
	private MyDate endDate;

	public DatePeriod(MyDate startDate, MyDate endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getEndDate() {
		return endDate.toString();
	}

	public String getStartDate() {
		return startDate.toString();
	}

	public int getDayDiff() {
		return MyDate.dayDiff(startDate, endDate);
	}

	public String toString() {
		return startDate + "~" + endDate;
	}
}
