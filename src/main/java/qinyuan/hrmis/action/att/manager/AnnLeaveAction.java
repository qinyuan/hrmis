package qinyuan.hrmis.action.att.manager;

public class AnnLeaveAction extends AttAction {

	private static final long serialVersionUID = 1L;

	public String execute() throws Exception {
		if(!checkSession()){
			return SUCCESS;
		}
		
		changeDept();
		return SUCCESS;
	}
}