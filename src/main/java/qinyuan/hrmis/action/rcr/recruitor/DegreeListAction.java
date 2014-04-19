package qinyuan.hrmis.action.rcr.recruitor;

import qinyuan.hrmis.domain.rcr.RcrDegree;

public class DegreeListAction extends RecruitorAction {

	private static final long serialVersionUID = 1L;

	public String execute() {
		if (!checkSession()) {
			return SUCCESS;
		}

		String delDegreeId = getParameter("delDegreeId");
		if (isNumeric(delDegreeId)) {
			delete(parseInt(delDegreeId));
		}

		if (hasParameter("addDegreeSubmit")) {
			String degreeName = getParameter("degreeName");
			if (degreeName != null) {
				if (RcrDegree.hasDegree(degreeName)) {
					setAttribute("result", "新添加与的学历名称与原有的学历重复");
				} else {
					recruitor.addDegree(degreeName);
				}
			}
		}

		String mdfDegreeId = getParameter("mdfDegreeId");
		String mdfDegreeName = getGetParameter("mdfDegreeName");
		if (isNumeric(mdfDegreeId)) {
			if (RcrDegree.hasDegree(mdfDegreeName)) {
				setAttribute("result", "新输入的学历名称与其他学历重复");
			} else {
				recruitor.mdfDegree(parseInt(mdfDegreeId), mdfDegreeName);
			}
		}

		return SUCCESS;
	}

	private void delete(int degreeId) {
		RcrDegree degree = RcrDegree.getDegree(degreeId);
		if (degree == null) {
			return;
		}

		if (degree.isUsed()) {
			setAttribute("result", "'" + degree.getName()
					+ "'已某些简历使用，在删除这些简历之前，不允许删除此学历");
		} else {
			recruitor.deleteDegree(degreeId);
		}
	}
}
