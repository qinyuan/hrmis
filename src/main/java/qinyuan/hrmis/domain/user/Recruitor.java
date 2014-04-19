package qinyuan.hrmis.domain.user;

import qinyuan.hrmis.domain.admin.Privilege;
import qinyuan.hrmis.domain.rcr.Post;
import qinyuan.hrmis.domain.rcr.RcrDegree;
import qinyuan.hrmis.domain.rcr.Resume;
import qinyuan.hrmis.domain.rcr.ResumeFactoryUtil;
import qinyuan.hrmis.domain.rcr.ResumeUtil;
import qinyuan.hrmis.domain.rcr.SimpleRcrManager;
import qinyuan.lib.web.html.Table;
import qinyuan.lib.web.html.TableRow;

public class Recruitor extends RcrParticipant {

	public Recruitor(int userId, String username, String nickname,
			Privilege[] pris) {
		super(userId, username, nickname, pris);
		super.setPriDesc("招聘设置");
		rf.setPost(Post.getPosts());
	}

	public boolean addDegree(String degreeName) {
		return RcrDegree.addDegree(degreeName);
	}

	public boolean addPost(String postName) {
		return Post.addPost(postName);
	}

	public void addResume(int postId, String applicant, int genderId,
			String salary, String birthday, String nativePlace, int degreeId,
			String school, String major, String resumeLink) {
		Resume re = new Resume();
		re.setPostId(postId);
		re.setApplicant(applicant);
		re.setGenderId(genderId);
		re.setSalary(salary);
		re.setBirthday(birthday);
		re.setNativePlace(nativePlace);
		re.setDegreeId(degreeId);
		re.setSchool(school);
		re.setMajor(major);
		re.setResumeLink(resumeLink);
		ResumeUtil.add(re);
	}

	public boolean deleteDegree(int degreeId) {
		return RcrDegree.deleteDegree(degreeId);
	}

	public boolean deletePost(int postId) {
		return Post.deletePost(postId);
	}

	public void deleteResume(int resumeId) {
		ResumeUtil.delete(resumeId);
	}

	public Table getDegreeTable() {
		Table table = new Table();

		TableRow tr = new TableRow(true);
		tr.add("学历,操作");
		table.add(tr);

		RcrDegree[] degrees = RcrDegree.getDegrees();
		for (RcrDegree degree : degrees) {
			tr = new TableRow().setId("tr" + degree.getId());
			tr.add(degree.getName());
			tr.add("<a href='degree-list.action?delDegreeId=" + degree.getId()
					+ "'>删除</a>");
			table.add(tr);
		}

		return table;
	}

	public Table getPostTable() {
		Table table = new Table();

		TableRow tr = new TableRow(true);
		tr.add("岗位，操作");
		table.add(tr);

		Post[] posts = Post.getPosts();
		for (Post post : posts) {
			tr = new TableRow().setId("tr" + post.getId());
			tr.add(post.getName());
			tr.add("<a href='post-list.action?delPostId=" + post.getId()
					+ "'>删除</a>");
			table.add(tr);
		}

		return table;
	}

	public String getRcrManagerCheckBoxes() {
		StringBuilder o = new StringBuilder();
		for (SimpleRcrManager item : SimpleRcrManager.getInstances()) {
			o.append(item.getCheckBoxes());
		}
		return o.toString();
	}

	public String getResumesTable() {
		return ResumeFactoryUtil.getResumesTableAsRecruitor(rf);
	}

	public Table getResumeTable() {
		return getResumeTable(-1);
	}

	public Table getResumeTable(int resumeId) {
		return ResumeUtil.getTable(resumeId);
	}

	public boolean mdfDegree(int degreeId, String degreeName) {
		return RcrDegree.mdfDegree(degreeId, degreeName);
	}

	public boolean mdfPost(int postId, String postName) {
		return Post.mdfPost(postId, postName);
	}

	public void mdfResume(int resumeId, int postId, String applicant,
			int genderId, String salary, String birthday, String nativePlace,
			int degreeId, String school, String major, String resumeLink) {
		Resume re = Resume.newInstance(resumeId);
		re.setPostId(postId);
		re.setApplicant(applicant);
		re.setGenderId(genderId);
		re.setSalary(salary);
		re.setBirthday(birthday);
		re.setNativePlace(nativePlace);
		re.setDegreeId(degreeId);
		re.setSchool(school);
		re.setMajor(major);
		re.setResumeLink(resumeLink);
		ResumeUtil.update(re);
	}

	public static Recruitor createRecruitor(User user) {
		return new Recruitor(user.getUserid(), user.getUsername(),
				user.getNickname(), user.getPrivilege());
	}

	public static void main(String[] args) {
		User user = User.newInstance(2);
		Recruitor recruitor = Recruitor.createRecruitor(user);
		recruitor.getResumesTable();
	}
}
