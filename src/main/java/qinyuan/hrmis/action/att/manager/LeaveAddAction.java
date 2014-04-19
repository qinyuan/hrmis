package qinyuan.hrmis.action.att.manager;

public class LeaveAddAction extends AttAction {

	private static final long serialVersionUID = 1L;

	public String execute() throws Exception {
		if (!checkSession())
			return SUCCESS;
		changeDept();
		changeDefUser();
		changeStartDate();
		changeStartTime();
		changeEndDate();
		changeEndTime();
		if (hasParameter("addSpecItemSubmit")) {
			String leaveTypeId = getParameter("leave");
			if (isNumeric(leaveTypeId)) {
				String result = attManager.addLeave(parseInt(leaveTypeId));
				setAttribute("result", result);
			}
		}
		return SUCCESS;
	}
}
