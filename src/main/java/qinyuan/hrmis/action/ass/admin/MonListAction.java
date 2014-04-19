package qinyuan.hrmis.action.ass.admin;

import java.util.HashSet;
import java.util.Set;
import qinyuan.hrmis.domain.ass.AssMon;
import qinyuan.lib.lang.MyMath;

public class MonListAction extends AssAdminAction {

	private static final long serialVersionUID = 1L;

	public String execute() {
		if (!checkSession()) {
			return SUCCESS;
		}

		if (hasParameter("mdfSubmit")) {
			mdfAssMon();
		} else if (hasParameter("addSubmit")) {
			addAssMon();
		} else if (hasParameter("delSubmit")) {
			deleteAssMon();
		} else if (hasParameter("unlockSubmit")) {
			unLockAssMon();
		} else if (hasParameter("lockSubmit")) {
			lockAssMon();
		}

		return SUCCESS;
	}

	private void unLockAssMon() {
		Set<Integer> ids = getCheckedAssMonIds();
		for (int id : ids) {
			AssMon.unlock(id);
		}
	}

	private void lockAssMon() {
		Set<Integer> ids = getCheckedAssMonIds();
		for (int id : ids) {
			AssMon.lock(id);
		}
	}

	private void deleteAssMon() {
		Set<Integer> ids = getCheckedAssMonIds();
		for (int id : ids) {
			AssMon.del(id);
		}
	}

	private void addAssMon() {
		String year = getParameter("year");
		String mon = getParameter("mon");
		if (MyMath.isNumeric(year) && MyMath.isNumeric(mon)) {
			AssMon.add(parseInt(year), parseInt(mon));
		}
	}

	private void mdfAssMon() {
		String id = getParameter("mdfId");
		String year = getParameter("mdfYear");
		String mon = getParameter("mdfMon");

		if (isNumeric(id) && isNumeric(year) && isNumeric(mon)) {
			AssMon.modify(parseInt(id), parseInt(year), parseInt(mon));
		}
	}

	private Set<Integer> getCheckedAssMonIds() {
		Set<Integer> set = new HashSet<Integer>();
		for (String key : getParameterNames()) {
			if (key.startsWith("chk")) {
				set.add(parseInt(key.substring(3)));
			}
		}
		return set;
	}
}