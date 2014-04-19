package qinyuan.hrmis.lib.filter;

import qinyuan.lib.date.MyTime;
import qinyuan.lib.web.html.TableRow;

public class Requester {

	private String ip;
	private String page;
	private String time;
	private String name;

	public Requester() {
		time = new MyTime().toString();
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TableRow toTableRow() {
		TableRow tr = new TableRow();
		tr.add(ip).add(name).add(page).add(time);
		return tr;
	}
}
