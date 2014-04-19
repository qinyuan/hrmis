package qinyuan.hrmis.domain.att.manager;

import qinyuan.lib.db.HbnConn;
import qinyuan.lib.web.html.TableRow;

public class AccdItemUtil {
	
	public static TableRow getTableHead() {
		TableRow tr = new TableRow(true);
		tr.add("工号，姓名，日期，上/下班，原因，创建者，创建时间");
		return tr;
	}

	public static TableRow toTableRow(DetailAccdItem item) {
		TableRow tr = new TableRow();
		tr.add(item.getBadgenumber());
		tr.add(item.getUsername());
		tr.add(item.getAttDate());
		tr.add(item.getClassType());
		tr.add(item.getAccdType());
		tr.add(item.getOperator());
		tr.add(item.getOperateTime());
		return tr;
	}

	public static TableRow toTableRow(MergedAccdItem item) {
		TableRow tr = new TableRow();
		tr.add(item.getBadgenumber());
		tr.add(item.getUsername());
		tr.add(item.getShortTimeDesc());
		tr.add(item.getAccdType());
		tr.add("");
		return tr;
	}

	public static boolean addToDB(AccdItem[] items) {
		try (HbnConn cnn = new HbnConn()) {
			for (AccdItem item : items) {
				if (isDuplicated(item, cnn)) {
					return false;
				} else {
					cnn.save(item);
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static boolean isDuplicated(AccdItem item, HbnConn cnn) {
		final String query = "SELECT COUNT(*) FROM spec_accd WHERE userId="
				+ item.getEmpId() + " AND accddate='" + item.getAttDate()
				+ "' AND isreach=" + (item.isReach() ? "TRUE" : "FALSE");
		int count = (Integer) (cnn.setSQL(query).setInt("COUNT(*)").list()
				.get(0));
		return count > 0;
	}
}
