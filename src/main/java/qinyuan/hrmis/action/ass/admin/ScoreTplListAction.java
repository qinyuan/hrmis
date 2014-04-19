package qinyuan.hrmis.action.ass.admin;

import qinyuan.hrmis.domain.ass.ScoreTpl;
import qinyuan.hrmis.lib.data.HRMISConn;

public class ScoreTplListAction extends AssAdminAction {

	private static final long serialVersionUID = 1L;

	public String execute() {
		if (!checkSession()) {
			return SUCCESS;
		}

		changeCheckee();
		changeChecker();

		if (hasParameter("delSubmit")) {
			delete();
		} else if (hasParameter("mdfSubmit")) {
			modify();
		} else if (hasParameter("addSubmit")) {
			add();
		}

		return SUCCESS;
	}

	private void delete() {
		try (HRMISConn cnn = new HRMISConn()) {
			String query = "DELETE FROM ass_score_tpl WHERE id=?";
			cnn.prepare(query);
			for (String key : getParameterNames()) {
				if (key.startsWith("chk_s_")) {
					String id = key.substring(6);
					if (isNumeric(id)) {
						cnn.setInt(1, parseInt(id)).execute();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void add() {
		String checkee = getParameter("iCheckee");
		String checker = getParameter("iChecker");
		String item = getParameter("iItem");
		String target = getParameter("iTarget");
		String weight = getParameter("iWeight");
		String formula = getParameter("iFormula");
		if (isNumeric(checkee) && isNumeric(checker) && isNumeric(weight)
				&& item != null && target != null && formula != null) {
			ScoreTpl.add(parseInt(checkee), parseInt(checker), item, target,
					parseFloat(weight), formula);
		}
	}

	private void modify() {
		String id = getParameter("iId");
		String checkee = getParameter("iCheckee");
		String checker = getParameter("iChecker");
		String item = getParameter("iItem");
		String target = getParameter("iTarget");
		String weight = getParameter("iWeight");
		String formula = getParameter("iFormula");
		if (isNumeric(id) && isNumeric(checkee) && isNumeric(checker)
				&& isNumeric(weight)) {
			ScoreTpl.modify(parseInt(id), parseInt(checkee), parseInt(checker),
					item, target, parseFloat(weight), formula);
		}
	}
}
