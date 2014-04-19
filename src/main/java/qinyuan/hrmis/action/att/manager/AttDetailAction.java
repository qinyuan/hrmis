package qinyuan.hrmis.action.att.manager;

public class AttDetailAction extends AttAction {

	private static final long serialVersionUID = 1L;

	public String execute() throws Exception {
		if (!checkSession()) {
			return SUCCESS;
		}
		changeDept();

		String weekToAdd = getParameter("weekToAdd");
		if (isNumeric(weekToAdd)) {
			attManager.setAttDate(parseInt(weekToAdd));
		}

		String attDate = getParameter("attDate");
		if (attDate != null) {
			attManager.setAttDate(attDate);
		}
		return SUCCESS;
	}
}
