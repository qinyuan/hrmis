package qinyuan.hrmis.domain.user;

import qinyuan.hrmis.domain.admin.Privilege;
import qinyuan.hrmis.domain.ass.AssFactory;
import qinyuan.hrmis.domain.ass.AssMon;
import qinyuan.lib.web.html.Select;

public class AssParticipant extends User {

	protected AssFactory af = new AssFactory();
	private AssMon mon = AssMon.getLastInstance();

	public AssParticipant(int userId, String username, String nickname,
			Privilege[] privileges) {
		super(userId, username, nickname, privileges);
	}

	public AssMon getMon() {
		return mon;
	}

	public int getMonCount() {
		return AssMon.getInstances().length;
	}

	public boolean getMonLocked() {
		return mon.getLocked();
	}

	public int getMonId() {
		return mon.getId();
	}

	public String getMonStr() {
		return mon.getYear() + "-" + mon.getMon();
	}

	public Select getMonSelect() {
		Select select = new Select().setId("monId").select(mon.getId());
		for (AssMon item : AssMon.getInstances()) {
			select.addOption(item.getId(), item);
		}
		return select;
	}

	public void setMon(int monId) {
		mon = AssMon.getInstance(monId);
		af.setMonId(monId);
	}

	
}
