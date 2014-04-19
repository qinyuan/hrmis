package qinyuan.hrmis.action.ass.admin;

import qinyuan.hrmis.domain.ass.ValueTpl;
import qinyuan.hrmis.lib.data.HRMISConn;

public class ValueTplListAction extends AssAdminAction {

	private static final long serialVersionUID = 1L;

	public String execute() {
		if (!checkSession()) {
			return SUCCESS;
		}

		changeCheckee();
		changeChecker();

		if (hasParameter("delSubmit")) {
			delete();
		} else if (hasParameter("addSubmit")) {
			add();
		} else if (hasParameter("mdfSubmit")) {
			modify();
		}

		return SUCCESS;
	}

	private void delete() {
		try (HRMISConn cnn = new HRMISConn()) {
			String query = "DELETE FROM ass_value_tpl WHERE id=?";
			cnn.prepare(query);
			for (String key : getParameterNames()) {
				if (key.startsWith("chk_v_")) {
					String id = key.substring(6);
					if (isNumeric(id)) {
						cnn.setInt(1, parseInt(id));
						cnn.execute();
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
		String unit = getParameter("iUnit");
		String formula = getParameter("iFormula");
		String other = getParameter("iOther");
		if (isNumeric(checkee) && isNumeric(checker) && item != null
				&& target != null && unit != null && formula != null
				&& other != null) {
			ValueTpl.add(parseInt(checkee), parseInt(checker), item, target,
					unit, formula, other);
		}
	}

	private void modify() {
		String id = getParameter("iId");
		String checkee = getParameter("iCheckee");
		String checker = getParameter("iChecker");
		String item = getParameter("iItem");
		String target = getParameter("iTarget");
		String unit = getParameter("iUnit");
		String formula = getParameter("iFormula");
		String other = getParameter("iOther");
		if (isNumeric(id) && isNumeric(checkee) && isNumeric(checker)
				&& item != null && target != null && unit != null
				&& formula != null && other != null) {
			ValueTpl.modify(parseInt(id), parseInt(checkee), parseInt(checker),
					item, target, unit, formula, other);
		}
	}
}
