package qinyuan.hrmis.domain.admin;

import java.util.HashSet;
import java.util.List;

public class PriUser {

	private SimpleUser su;
	private HashSet<Integer> priIds = new HashSet<Integer>();
	private Privilege[] pris;

	PriUser(SimpleUser su) {
		this.su = su;
		pris = Privilege.getPrisByUserId(su.getUserId());
		for (Privilege pri : pris) {
			priIds.add(pri.getId());
		}
	}

	public String getPriCheckBoxes() {
		Privilege[] allPris = Privilege.getAllPris();
		int priId;
		StringBuilder o = new StringBuilder();

		/*
		 * traverse all privilege in array totalPris, then judge if current user
		 * has the privilege, if current user has the privilege, set the
		 * checkbox as checked
		 */
		for (int i = 0; i < allPris.length; i++) {
			priId = allPris[i].getId();
			o.append("<span><input type='checkbox' name='user" + su.getUserId()
					+ "' value='" + priId);
			if (hasPri(priId)) {
				o.append("' checked='checked");
			}
			o.append("' />" + allPris[i].getDesc()
					+ "</span>&nbsp;&nbsp;&nbsp;");
		}

		return o.toString();
	}

	public String getUsername() {
		return su.getUsername();
	}

	public String getNickname() {
		return su.getNickname();
	}

	public int getUserId() {
		return su.getUserId();
	}

	public SimpleUser getSimpleUser() {
		return su;
	}

	public boolean hasPri(int priId) {
		return priIds.contains(priId);
	}

	public static PriUser[] getInstances() {
		List<SimpleUser> sus = SimpleUser.getInstances();
		PriUser[] pus = new PriUser[sus.size()];
		for (int i = 0; i < pus.length; i++) {
			pus[i] = new PriUser(sus.get(i));
		}

		return pus;
	}

}
