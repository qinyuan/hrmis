package qinyuan.hrmis.action.ass.dealer;

public class AssOtherAction extends AssAction {

	private static final long serialVersionUID = 1L;

	public String execute() {
		if (!checkSession()) {
			return SUCCESS;
		}

		changeDept();
		changeMon();
		submitDataAndResult();
		return SUCCESS;
	}

	private void submitDataAndResult() {
		String data = getParameter("data");
		if (data == null) {
			return;
		}

		String result = getParameter("result");
		if (result == null || !isNumeric(result)) {
			return;
		}

		if (hasParameter("assScoreSubmit")) {
			String assScoreId = getParameter("hidScoreId");
			if (assScoreId != null && isNumeric(assScoreId)) {
				assDealer.updateAssScore(parseInt(assScoreId), data,
						parseFloat(result));
			}
		}
		if (hasParameter("assValueSubmit")) {
			String assValueId = getParameter("hidValueId");
			if (assValueId != null && data != null && result != null
					&& isNumeric(assValueId) && isNumeric(result)) {
				assDealer.updateAssValue(parseInt(assValueId), data,
						parseFloat(result));
			}
		}
	}
}