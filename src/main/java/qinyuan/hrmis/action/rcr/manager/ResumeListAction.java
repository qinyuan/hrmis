package qinyuan.hrmis.action.rcr.manager;

import qinyuan.hrmis.domain.user.RcrManager;
import qinyuan.lib.web.MyBasicAction;

public class ResumeListAction extends MyBasicAction {

	private static final long serialVersionUID = 1L;

	public String execute() {
		RcrManager rcrManager = (RcrManager) getSession("rcrManager");
		if (rcrManager == null) {
			return SUCCESS;
		}

		String statusIdStr = getParameter("todo");
		String resumeIdStr = getParameter("resumeId");
		if (isNumeric(statusIdStr) && isNumeric(resumeIdStr)) {
			rcrManager.changeResumeStatus(parseInt(resumeIdStr),
					parseInt(statusIdStr));
		}

		if (hasParameter("nextPage")) {
			rcrManager.nextPage();
		}

		if (hasParameter("previousPage")) {
			rcrManager.previousPage();
		}

		String pageNumStr = getParameter("pageNum");
		if (isNumeric(pageNumStr)) {
			rcrManager.setPageNum(parseInt(pageNumStr));
		}

		String postId = getParameter("postId");
		if (isNumeric(postId)) {
			rcrManager.setPostId(parseInt(postId));
		}

		return SUCCESS;
	}
}
