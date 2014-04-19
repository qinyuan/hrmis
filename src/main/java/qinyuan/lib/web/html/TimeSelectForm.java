package qinyuan.lib.web.html;

import qinyuan.lib.date.MyTime;

public class TimeSelectForm {

	private String id;
	private MyTime time;

	public TimeSelectForm(String id, MyTime time) {
		this.id = id;
		this.time = time;
	}

	public String toString() {
		NumSelect hourForm = new NumSelect(0, 23).select(time.getHour()).setId(
				id + "Hour");
		NumSelect minForm = new NumSelect(0, 59).select(time.getMinute())
				.setId(id + "Min");

		return hourForm + "时" + minForm + "分";
	}
}