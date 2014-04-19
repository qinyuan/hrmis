package qinyuan.hrmis.domain.att.manager;

import java.util.Map;
import java.util.TreeMap;

public class MergedAccdItem {

	private Integer id;
	private String badgenumber;
	private String username;
	private Map<String, Integer> accdDateSet = new TreeMap<String, Integer>();
	private String AccdType;
	private String operator;
	private String operateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBadgenumber() {
		return badgenumber;
	}

	public void setBadgenumber(String badgenumber) {
		this.badgenumber = badgenumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccdType() {
		return AccdType;
	}

	public void setAccdType(String accdType) {
		AccdType = accdType;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public void addAccdDate(String dateStr, boolean reach) {
		if (accdDateSet.containsKey(dateStr)) {
			int keyValue = accdDateSet.get(dateStr);
			if ((reach && keyValue == OUT) || ((!reach) && keyValue == IN)) {
				accdDateSet.put(dateStr, BOTH);
			}
		} else {
			accdDateSet.put(dateStr, (reach ? IN : OUT));
		}
	}

	public String getShortTimeDesc() {
		StringBuilder o = new StringBuilder();
		for (String key : accdDateSet.keySet()) {
			o.append(key + CLASS_DESC[accdDateSet.get(key)] + "、");
		}
		if (o.length() > 0) {
			o.deleteCharAt(o.length() - 1);
		}
		return o.toString();
	}

	private static final String[] CLASS_DESC = { "", "上班", "下班", "上下班" };
	private static final int IN = 1;
	private static final int OUT = 2;
	private static final int BOTH = 3;

	public static void main(String[] args) {
		MergedAccdItem item = new MergedAccdItem();
		item.addAccdDate("2012-11-11", true);
		item.addAccdDate("2012-11-11", false);
		item.addAccdDate("2012-11-13", true);
		System.out.println(item.getShortTimeDesc());
	}
}