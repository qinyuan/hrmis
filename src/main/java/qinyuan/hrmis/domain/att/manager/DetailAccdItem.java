package qinyuan.hrmis.domain.att.manager;

/**
 * This class is similar with class AccdItem, except that this class contains
 * meaningful fields such as badgenumber,username,classType,accdType and so on
 * 
 * @author qinyuan
 * 
 */
public class DetailAccdItem {

	private Integer id;
	private String badgenumber;
	private String username;
	private String attDate;
	private String classType;
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

	public String getAttDate() {
		return attDate;
	}

	public void setAttDate(String attDate) {
		this.attDate = attDate;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
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
}
