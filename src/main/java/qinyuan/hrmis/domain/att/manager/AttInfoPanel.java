package qinyuan.hrmis.domain.att.manager;

import qinyuan.lib.db.HbnConn;

public class AttInfoPanel {

	private static String latestAttTime;

	private AttInfoPanel() {
	}

	public static void refresh() {
		latestAttTime = null;
	}

	public static String getPanel() {
		if (latestAttTime == null)
			initialize();

		StringBuilder o = new StringBuilder();
		o.append("<fieldset>");
		o.append("<legend>温馨提示</legend>");
		o.append("刷卡记录更新至：<br /><b>" + latestAttTime + "</b>");
		o.append("</fieldset>");
		return o.toString();
	}

	private static void initialize() {
		try (HbnConn cnn = new HbnConn()) {
			latestAttTime = (String) (cnn
					.setSQL("SELECT MAX(CONCAT(checkdate,' ',checktime)) AS lat FROM checkinout")
					.setString("lat").list().get(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println(getPanel());
	}
}
