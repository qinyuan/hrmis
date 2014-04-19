package qinyuan.lib.date;

public class MyTimeRecord {

	private long startTimeStamp;
	private long lastTimeStamp;

	public MyTimeRecord() {
		// record current time stamp
		startTimeStamp = System.currentTimeMillis();
		lastTimeStamp = startTimeStamp;
	}

	public void print(String mark) {
		long nowTimeStamp = System.currentTimeMillis();
		double total = (nowTimeStamp - startTimeStamp) / 1000.0;
		double interval = (nowTimeStamp - lastTimeStamp) / 1000.0;
		lastTimeStamp = nowTimeStamp;
		System.out.println("interval:" + interval + "; total:" + total + "; "
				+ mark);
	}
}