package qinyuan.lib.date;

public class DateTimePeriod {

	private MyDateTime startDateTime;
	private MyDateTime endDateTime;

	public DateTimePeriod(MyDateTime startDateTime, MyDateTime endDateTime) {
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}

	public DateTimePeriod addEndDate(int dayNum) {
		endDateTime.addDate(dayNum);
		return this;
	}

	public DateTimePeriod addStartDate(int dayNum) {
		startDateTime.addDate(dayNum);
		return this;
	}

	public DatePeriod getDatePeriod() {
		MyDate startDate = new MyDate(startDateTime.getDate());
		MyDate endDate = new MyDate(endDateTime.getDate());
		return new DatePeriod(startDate, endDate);
	}

	public String getEndDate() {
		return endDateTime.getDate();
	}

	public String getEndDateTime() {
		return endDateTime.toString();
	}

	public String getEndTime() {
		return endDateTime.getTime();
	}

	public double getIntervalHour() {
		int startTimeStamp = startDateTime.getTimeStamp();
		int endTimeStamp = endDateTime.getTimeStamp();
		return (endTimeStamp - startTimeStamp) / 3600.0;
	}

	public String getStartDate() {
		return startDateTime.getDate();
	}

	public String getStartDateTime() {
		return startDateTime.toString();
	}

	public String getStartTime() {
		return startDateTime.getTime();
	}

	public boolean isValid() {
		return startDateTime.getTimeStamp() <= endDateTime.getTimeStamp();
	}

	public String[] getDates() {
		if (!isValid()) {
			return new String[0];
		}

		MyDate startDate = new MyDate(getStartDate());
		MyDate endDate = new MyDate(getEndDate());
		int dayDiffCount = MyDate.dayDiff(startDate, endDate);

		MyDate temp = startDate.clone();
		String[] dateArr = new String[dayDiffCount + 1];
		for (int i = 0; i <= dayDiffCount; i++) {
			dateArr[i] = temp.toString();
			temp.add(1);
		}
		return dateArr;
	}

	public void setEndDate(String endDate) {
		endDateTime.setDate(endDate);
	}

	public void setEndTime(String endTime) {
		endDateTime.setTime(endTime);
	}

	public void setStartDate(String startDate) {
		startDateTime.setDate(startDate);
	}

	public void setStartTime(String startTime) {
		startDateTime.setTime(startTime);
	}

	public String toString() {
		return startDateTime + "~" + endDateTime;
	}
}