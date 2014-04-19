package qinyuan.hrmis.action.att.manager;

public class SpecListAction extends AttAction {

	private static final long serialVersionUID = 1L;

	public String execute() throws Exception {
		if(!checkSession())
			return SUCCESS;
		
		changeDept();
		changeDefUser();
		changeStartDate();
		changeEndDate();

		String accdId = getParameter("delAccdId");
		if (isNumeric(accdId)) {
			attManager.deleteAccd(parseInt(accdId));
		}

		String leaveId = getParameter("delLeaveId");
		if (isNumeric(leaveId)) {
			attManager.deleteLeave(parseInt(leaveId));
		}
		return SUCCESS;
	}
}
