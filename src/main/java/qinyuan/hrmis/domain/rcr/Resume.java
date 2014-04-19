package qinyuan.hrmis.domain.rcr;

import qinyuan.lib.db.HbnConn;

public class Resume {

	private Integer resumeId;
	private int postId;
	private String applicant;
	private int genderId;
	private String salary;
	private String birthday;
	private String nativePlace;
	private int degreeId;
	private String school;
	private String major;
	private String resumeLink;
	private String createTime;
	private int statusId;
	private String dealTime;
	private Integer dealer;

	public Integer getResumeId() {
		return resumeId;
	}

	public void setResumeId(Integer resumeId) {
		this.resumeId = resumeId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public int getGenderId() {
		return genderId;
	}

	public void setGenderId(int genderId) {
		this.genderId = genderId;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public int getDegreeId() {
		return degreeId;
	}

	public void setDegreeId(int degreeId) {
		this.degreeId = degreeId;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getResumeLink() {
		return resumeLink;
	}

	public void setResumeLink(String resumeLink) {
		this.resumeLink = resumeLink;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getDealTime() {
		return dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

	public Integer getDealer() {
		return dealer;
	}

	public void setDealer(Integer dealer) {
		this.dealer = dealer;
	}

	public static Resume newInstance(int resumeId) {
		try (HbnConn cnn = new HbnConn()) {
			return cnn.get(Resume.class, resumeId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		Resume resume =Resume.newInstance(9);
		System.out.println(resume.getResumeId());
		System.out.println(resume.getApplicant());
	}
}
