package qinyuan.hrmis.domain.admin;

import java.util.List;
import qinyuan.lib.db.HbnConn;

public class Privilege {

	private int id;
	private String desc;

	public Privilege(int priId) {
		id = priId;
		desc = PRI_DESCS[id - 1];
	}

	public int getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}

	public final static int ATT_PRI_ID = 3;
	public final static int ASS_PRI_ID = 5;
	public final static int RCR_PRI_ID = 7;
	private final static String[] PRI_DESCS = { "基础设置", "考勤设置", "考勤管理", "考核设置",
			"部门考核", "招聘设置", "招聘管理", "参考手册" };

	public static Privilege[] getAllPris() {
		int priCount = PRI_DESCS.length;
		Privilege[] pris = new Privilege[priCount];
		for (int i = 0; i < priCount; i++) {
			pris[i] = new Privilege(i + 1);
		}
		return pris;
	}

	public static Privilege[] getPrisByUserId(int userId) {
		HbnConn cnn = new HbnConn();
		List<?> list = cnn
				.setSQL("SELECT pri_id FROM user_pri WHERE userId=" + userId)
				.setInt("pri_id").list();
		Privilege[] pris = new Privilege[list.size()];
		for (int i = 0; i < list.size(); i++) {
			pris[i] = new Privilege((Integer) list.get(i));
		}

		try {
			cnn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pris;
	}

	public static void main(String[] args) throws Exception {
		Privilege[] pris = getPrisByUserId(2);
		for (Privilege pri : pris) {
			System.out.println(pri.getId() + " " + pri.getDesc());
		}
	}
}
