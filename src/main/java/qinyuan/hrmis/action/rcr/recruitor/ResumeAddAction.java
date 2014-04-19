package qinyuan.hrmis.action.rcr.recruitor;

public class ResumeAddAction extends RecruitorAction {

	private static final long serialVersionUID = 1L;

	public String execute() {
		if (!checkSession()) {
			return SUCCESS;
		}

		if (hasParameter("resumeAddSubmit")) {
			String postId = getParameter("postId");
			String applicant = getParameter("applicant");
			String genderId = getParameter("genderId");
			String salary = getParameter("salary");
			String birthday = getParameter("birthday");
			String nativePlace = getParameter("nativePlace");
			String degreeId = getParameter("degreeId");
			String school = getParameter("school");
			String major = getParameter("major");
			String resumeLink = getParameter("resumeLink");

			if (postId != null && applicant != null && genderId != null
					&& salary != null && birthday != null
					&& nativePlace != null && degreeId != null
					&& school != null && major != null && resumeLink != null
					&& isNumeric(postId) && isNumeric(genderId)
					&& isNumeric(degreeId)) {
				recruitor.addResume(parseInt(postId), applicant,
						parseInt(genderId), salary, birthday, nativePlace,
						parseInt(degreeId), school, major, resumeLink);
				setAttribute("result", "成功添加: " + applicant);
			}
		}

		return SUCCESS;
	}
}
