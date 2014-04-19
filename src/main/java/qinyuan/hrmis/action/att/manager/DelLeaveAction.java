package qinyuan.hrmis.action.att.manager;

public class DelLeaveAction extends AttAction {

	private static final long serialVersionUID = 1L;

	public String execute() throws Exception {
		if(!checkSession()){
			return SUCCESS;
		}
		
		String leaveid = getParameter("leaveid");
		if (leaveid != null) {
			attManager.deleteLeave(parseInt(leaveid));
		}
		return SUCCESS;
	}
}
