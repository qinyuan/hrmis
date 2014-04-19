package qinyuan.hrmis.action.ass.dealer;

public class AssResultAction extends AssAction {

	private static final long serialVersionUID = 1L;

	public String execute() {
		if (!checkSession()) {
			return SUCCESS;
		}
		changeDept();
		changeMon();
		return SUCCESS;
	}
}
