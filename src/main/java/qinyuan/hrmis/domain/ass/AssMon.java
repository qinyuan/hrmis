package qinyuan.hrmis.domain.ass;

import java.util.HashMap;
import java.util.Map;
import qinyuan.hrmis.lib.data.HRMISConn;

public class AssMon {

	private static Map<Integer, AssMon> map;
	private int id;
	private int year;
	private int mon;
	private boolean locked;

	public AssMon(int id, int year, int mon, boolean locked) {
		this.id = id;
		this.year = year;
		this.mon = mon;
		this.locked = locked;
	}

	public int getId() {
		return id;
	}

	public int getYear() {
		return year;
	}

	public int getMon() {
		return mon;
	}

	public boolean getLocked() {
		return locked;
	}

	public String getLockedStr() {
		return locked ? "锁定" : "激活";
	}

	public String toString() {
		return year + "-" + mon + " (" + getLockedStr() + ")";
	}

	public static boolean unlock(int id) {
		return lock(id, false);
	}

	public static boolean lock(int id) {
		return lock(id, true);
	}

	private static boolean lock(int id, boolean lock) {
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare("UPDATE ass_mon SET locked=? WHERE id=?")
					.setBoolean(1, lock).setInt(2, id).execute();
			initialize();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean add(int year, int mon) {
		if (!validateYearAndMon(year, mon)) {
			return false;
		}

		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare("INSERT INTO ass_mon(year,month) VALUES(?,?)")
					.setInt(1, year).setInt(2, mon).execute();
			cnn.prepare("SELECT id FROM ass_mon WHERE year=? AND month=?")
					.setInt(1, year).setInt(2, mon).execute();
			cnn.next();
			int monId = cnn.getInt(1);
			AssScore.addByMon(monId);
			AssValue.addByMon(monId);
			refresh();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean del(int id) {
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare("DELETE FROM ass_mon WHERE id=?").setInt(1, id)
					.execute();
			AssScore.deleteByMon(id);
			AssValue.deleteByMon(id);

			refresh();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static AssMon getLastInstance() {
		if (map == null)
			initialize();

		int maxId = -1;
		for (int key : map.keySet()) {
			if (key > maxId)
				maxId = key;
		}
		return getInstance(maxId);
	}

	public static AssMon getInstance(int id) {
		if (map == null)
			initialize();

		return map.get(id);
	}

	public static AssMon[] getInstances() {
		if (map == null)
			initialize();

		AssMon[] items = new AssMon[map.size()];
		int i = 0;
		for (AssMon item : map.values()) {
			items[i++] = item;
		}

		return items;
	}

	public static boolean modify(int id, int year, int mon) {
		if (!validateYearAndMon(year, mon)) {
			return false;
		}

		String query = "UPDATE ass_mon SET year=?,month=? WHERE id=?";
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare(query).setInt(1, year).setInt(2, mon).setInt(3, id)
					.execute();
			refresh();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static void initialize() {
		map = new HashMap<Integer, AssMon>();
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.execute("SELECT id,year,month,locked FROM ass_mon");
			int id, year, mon;
			boolean locked;
			while (cnn.next()) {
				id = cnn.getInt(1);
				year = cnn.getInt(2);
				mon = cnn.getInt(3);
				locked = cnn.getBoolean(4);
				map.put(id, new AssMon(id, year, mon, locked));
			}
		} catch (Exception e) {
			e.printStackTrace();
			map = null;
		}
	}

	private static void refresh() {
		map = null;
	}

	private static boolean validateYearAndMon(int year, int mon) {
		return year >= 1900 && year <= 2100 && mon >= 1 && mon <= 12;
	}

	public static void main(String[] args) {
		for (AssMon item : getInstances()) {
			System.out.println(item);
		}
	}
}
