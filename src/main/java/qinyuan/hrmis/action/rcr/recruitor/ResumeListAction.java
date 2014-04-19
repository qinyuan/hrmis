package qinyuan.hrmis.action.rcr.recruitor;

public class ResumeListAction extends RecruitorAction {

	private static final long serialVersionUID = 1L;

	public String execute() {
		if (!checkSession()) {
			return SUCCESS;
		}

		String delResumeId = getParameter("delResumeId");
		if (delResumeId != null && isNumeric(delResumeId)) {
			recruitor.deleteResume(parseInt(delResumeId));
		}

		if (hasParameter("nextPage")) {
			recruitor.nextPage();
		}

		if (hasParameter("previousPage")) {
			recruitor.previousPage();
		}

		String pageNumStr = getParameter("pageNum");
		if (isNumeric(pageNumStr)) {
			recruitor.setPageNum(parseInt(pageNumStr));
		}

		String postId = getParameter("postId");
		if (isNumeric(postId)) {
			recruitor.setPostId(parseInt(postId));
		}

		return SUCCESS;
	}
}
