package qinyuan.hrmis.domain.user;

import qinyuan.hrmis.domain.admin.Privilege;
import qinyuan.hrmis.domain.rcr.Post;
import qinyuan.hrmis.domain.rcr.ResumeFactoryUtil;
import qinyuan.hrmis.domain.rcr.ResumeUtil;

public class RcrManager extends RcrParticipant {

	public RcrManager(int userId, String username, String nickname,
			Privilege[] pris) {
		super(userId, username, nickname, pris);
		super.setPriDesc("招聘管理");
		rf.setPost(Post.getPostsByUserId(userId));
	}

	public String getResumesTable() {
		return ResumeFactoryUtil.getResumesTableAsRcrManager(rf);
	}

	public void changeResumeStatus(int resumeId, int statusId) {
		ResumeUtil.changeResumeStatus(resumeId, statusId, getUserid());
	}

	public static RcrManager createRcrManager(User user) {
		return new RcrManager(user.getUserid(), user.getUsername(),
				user.getNickname(), user.getPrivilege());
	}
}
