package qinyuan.hrmis.domain.user;

import qinyuan.hrmis.domain.admin.Privilege;
import qinyuan.hrmis.domain.rcr.ResumeFactory;

public abstract class RcrParticipant extends User {

	protected ResumeFactory rf = new ResumeFactory();

	public RcrParticipant(int userId, String username, String nickname,
			Privilege[] pris) {
		super(userId, username, nickname, pris);
	}

	public void previousPage() {
		rf.previousPage();
	}

	public void nextPage() {
		rf.nextPage();
	}

	public void setPageNum(int pageNum) {
		rf.setPageNum(pageNum);
	}

	public void setPostId(int postId) {
		rf.setPostId(postId);
	}
}
