package qinyuan.hrmis.action.ass.admin;

import qinyuan.hrmis.domain.ass.AssScore;
import qinyuan.hrmis.domain.ass.AssValue;
import qinyuan.hrmis.lib.data.HRMISConn;

public class AssItemListAction extends AssAdminAction {

	private static final long serialVersionUID = 1L;

	public String execute() {
		if (!checkSession()) {
			return SUCCESS;
		}

		changeCheckee();
		changeChecker();

		String monIdStr = getParameter("monId");
		if (isNumeric(monIdStr)) {
			assAdmin.setMon(parseInt(monIdStr));
		}

		if (hasParameter("scoreSubmit")) {
			mdfScore();
		} else if (hasParameter("valueSubmit")) {
			mdfValue();
		} else if (hasParameter("scoreAddSubmit")) {
			addScore();
		} else if (hasParameter("valueAddSubmit")) {
			addValue();
		} else if (hasParameter("delSubmit")) {
			deleteAssResult();
		}

		return SUCCESS;
	}

	private void addScore() {
		int monId = assAdmin.getMonId();
		String checkee = getParameter("sCheckee");
		String checker = getParameter("sChecker");
		String item = getParameter("sItem");
		String target = getParameter("sTarget");
		String weight = getParameter("sWeight");
		String formula = getParameter("sFormula");
		String data = getParameter("sData");
		String result = getParameter("sResult");

		if (monId > 0 && isNumeric(checkee) && isNumeric(checker)
				&& item != null && target != null && isNumeric(weight)
				&& formula != null && data != null && isNumeric(result)) {
			AssScore.addScore(monId, parseInt(checkee), parseInt(checker),
					item, target, parseFloat(weight), formula, data,
					parseFloat(result));
		}
	}

	private void addValue() {
		int monId = assAdmin.getMonId();
		String checkee = getParameter("vCheckee");
		String checker = getParameter("vChecker");
		String item = getParameter("vItem");
		String target = getParameter("vTarget");
		String unit = getParameter("vUnit");
		String other = getParameter("vOther");
		String formula = getParameter("vFormula");
		String data = getParameter("vData");
		String result = getParameter("vResult");

		if (monId > 0 && isNumeric(checkee) && isNumeric(checker)
				&& item != null && target != null && unit != null
				&& other != null && formula != null && data != null
				&& isNumeric(result)) {
			AssValue.addValue(monId, parseInt(checkee), parseInt(checker),
					item, target, unit, other, formula, data,
					parseFloat(result));
		}
	}

	private void deleteAssResult() {
		for (String key : getParameterNames()) {
			if (key.startsWith("chk_")) {
				if (key.charAt(4) == 's') {
					deleteScore(key.substring(6));
				} else if (key.charAt(4) == 'v') {
					deleteValue(key.substring(6));
				}
			}
		}
	}

	private void deleteScore(String idStr) {
		if (!isNumeric(idStr))
			return;

		try (HRMISConn cnn = new HRMISConn()) {
			cnn.execute("DELETE FROM ass_score WHERE id=" + idStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deleteValue(String idStr) {
		if (!isNumeric(idStr))
			return;

		try (HRMISConn cnn = new HRMISConn()) {
			cnn.execute("DELETE FROM ass_value WHERE id=" + idStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void mdfValue() {
		String id = getParameter("vId");
		String checkee = getParameter("vCheckee");
		String checker = getParameter("vChecker");
		String item = getParameter("vItem");
		String target = getParameter("vTarget");
		String unit = getParameter("vUnit");
		String other = getParameter("vOther");
		String formula = getParameter("vFormula");
		String data = getParameter("vData");
		String result = getParameter("vResult");

		if (isNumeric(id) && isNumeric(checkee) && isNumeric(checker)
				&& item != null && target != null && unit != null
				&& other != null && formula != null && data != null
				&& isNumeric(result)) {
			AssValue.updateAll(parseInt(id), parseInt(checkee),
					parseInt(checker), item, target, unit, other, formula,
					data, parseFloat(result));
		}
	}

	private void mdfScore() {
		String id = getParameter("sId");
		String checkee = getParameter("sCheckee");
		String checker = getParameter("sChecker");
		String item = getParameter("sItem");
		String target = getParameter("sTarget");
		String weight = getParameter("sWeight");
		String formula = getParameter("sFormula");
		String data = getParameter("sData");
		String result = getParameter("sResult");

		if (isNumeric(id) && isNumeric(checkee) && isNumeric(checker)
				&& item != null && target != null && isNumeric(weight)
				&& formula != null && data != null && isNumeric(result)) {
			AssScore.updateAll(parseInt(id), parseInt(checkee),
					parseInt(checker), item, target, parseFloat(weight),
					formula, data, parseFloat(result));
		}
	}
}
