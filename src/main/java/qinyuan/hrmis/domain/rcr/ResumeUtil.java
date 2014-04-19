package qinyuan.hrmis.domain.rcr;

import qinyuan.hrmis.domain.admin.SimpleUser;
import qinyuan.lib.date.MyDateTime;
import qinyuan.lib.db.HbnConn;
import qinyuan.lib.web.html.Anchor;
import qinyuan.lib.web.html.Table;
import qinyuan.lib.web.html.TableRow;
import qinyuan.lib.web.html.Text;

public class ResumeUtil {

	private static String head;
	static {
		TableRow tr = new TableRow(true);
		tr.add("岗位，姓名，性别，收入要求，出生年月，籍贯，学历，学校，专业，创建时间，状态，处理时间，操作");
		head = tr.toString();
	}

	private String resumeBase = "";

	public void setResumeBase(String resumeBase) {
		this.resumeBase = resumeBase.endsWith("/") ? resumeBase
				: (resumeBase + "/");
	}

	public String getTableHead() {
		return head;
	}

	public TableRow getTrAsRecruitor(Resume resume) {
		TableRow row = getTableRowByStatusId(resume.getStatusId());
		row.add(getCommonTr(resume));
		int resumeId = resume.getResumeId();
		String anchor = "<a href='resume-mdf.jsp?resumeId=" + resumeId
				+ "'>修改</a> " + "<a href='resume-list.action?delResumeId="
				+ resumeId + "'>删除</a>";
		row.add(anchor);
		return row;
	}

	public TableRow getTrAsRcrManager(Resume resume) {
		TableRow row = getTableRowByStatusId(resume.getStatusId());
		row.add(getCommonTr(resume));

		RcrStatus[] statuses = RcrStatus.getStatuses();
		StringBuilder o = new StringBuilder();
		for (RcrStatus status : statuses) {
			if (status.getId() != resume.getStatusId()) {
				o.append("<a href='resume-list.action?todo=" + status.getId()
						+ "&resumeId=" + resume.getResumeId() + "'>" + status
						+ "</a> ");
			}
		}
		row.add(o.toString().replace("未处理", "撤销"));

		return row;
	}

	public static void add(Resume resume) {
		try (HbnConn cnn = new HbnConn()) {
			// set 1 as the default status id
			resume.setStatusId(1);
			resume.setCreateTime(new MyDateTime().toString());
			cnn.save(resume);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void changeResumeStatus(int resumeId, int statusId, int dealer) {
		Resume resume = Resume.newInstance(resumeId);
		if (resume == null) {
			return;
		}

		try (HbnConn cnn = new HbnConn()) {
			resume.setStatusId(statusId);
			if (statusId == 1) {
				resume.setDealer(null);
				resume.setDealTime(null);
			} else {
				resume.setDealer(dealer);
				resume.setDealTime(new MyDateTime().toString());
			}
			cnn.update(resume);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void update(Resume resume) {
		try (HbnConn cnn = new HbnConn()) {
			cnn.update(resume);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void delete(int resumeId) {
		try (HbnConn cnn = new HbnConn()) {
			Resume resume = Resume.newInstance(resumeId);
			cnn.delete(resume);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Table getTable(int resumeId) {
		int postId, genderId, degreeId;
		String applicant, salary, birthday, nativePlace, school, major, resumeLink;

		Resume resume = null;
		if (resumeId > 0) {
			resume = Resume.newInstance(resumeId);
		}
		if (resume == null) {
			postId = 0;
			genderId = 0;
			degreeId = 0;
			applicant = "";
			salary = "";
			birthday = "";
			nativePlace = "";
			school = "";
			major = "";
			resumeLink = "";
		} else {
			postId = resume.getPostId();
			genderId = resume.getGenderId();
			degreeId = resume.getDegreeId();
			applicant = resume.getApplicant();
			salary = resume.getSalary();
			birthday = resume.getBirthday();
			nativePlace = resume.getNativePlace();
			school = resume.getSchool();
			major = resume.getMajor();
			resumeLink = resume.getResumeLink();
		}

		Table table = new Table();
		table.add(getTwoColRow("岗位", Post.getSelect(postId)))
				.add(getTwoColRow("姓名", getText("applicant", applicant)))
				.add(getTwoColRow("性别", Gender.getSelect(genderId)))
				.add(getTwoColRow("收入要求", getText("salary", salary)))
				.add(getTwoColRow("出生年月", getText("birthday", birthday)))
				.add(getTwoColRow("籍贯", getText("nativePlace", nativePlace)))
				.add(getTwoColRow("学历", RcrDegree.getSelect(degreeId)))
				.add(getTwoColRow("学校", getText("school", school)))
				.add(getTwoColRow("专业", getText("major", major)))
				.add(getTwoColRow("简历(链接)", getText("resumeLink", resumeLink)));
		return table;
	}

	private static TableRow getTwoColRow(Object obj1, Object obj2) {
		return new TableRow().add(obj1).add(obj2);
	}

	private static Text getText(String id, String value) {
		return new Text().setId(id).setValue(value);
	}

	private TableRow getCommonTr(Resume re) {
		TableRow row = new TableRow();

		row.add(getPostName(re.getPostId()));

		Anchor a = new Anchor().setHref(resumeBase + re.getResumeLink())
				.setText(re.getApplicant()).setNew(true);
		row.add(a);

		row.add(getGenderName(re.getGenderId()));
		row.add(re.getSalary());
		row.add(re.getBirthday());
		row.add(re.getNativePlace());
		row.add(getDegree(re.getDegreeId()));
		row.add(re.getSchool());
		row.add(re.getMajor());
		row.add(re.getCreateTime().substring(5, 16));
		row.add(getStatusName(re.getStatusId()));
		if (re.getDealTime() == null || re.getDealer() == null) {
			row.add("");
		} else {
			row.add(re.getDealTime().substring(5, 16) + "("
					+ getDealer(re.getDealer()) + ")");
		}

		return row;
	}

	private static String getPostName(int postId) {
		return Post.getPost(postId).getName();
	}

	private static String getDegree(int degreeId) {
		return RcrDegree.getDegree(degreeId).toString();
	}

	private static String getGenderName(int genderId) {
		return Gender.getGender(genderId).getName();
	}

	private static String getStatusName(int statusId) {
		return RcrStatus.getStatus(statusId).toString();
	}

	private static String getDealer(int userId) {
		return SimpleUser.newInstance(userId).getNickname();
	}

	private static TableRow getTableRowByStatusId(int statusId) {
		String[] style = { "undeal", "accept", "reject" };
		return new TableRow().setClass(style[statusId - 1]);
	}

	public static void main(String[] args) {
		System.out.println(Post.getPost(0));
	}
}
