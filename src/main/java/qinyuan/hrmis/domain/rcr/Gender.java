package qinyuan.hrmis.domain.rcr;

import java.util.HashMap;
import java.util.Map;

import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.web.html.Select;

public class Gender {

	private int id;
	private String name;

	public Gender(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return getName();
	}

	private static Map<Integer, String> map;

	public static Gender getGender(int id) {
		if (map == null)
			initializeMap();

		if (map == null || map.size() == 0) {
			return null;
		}

		Gender gender = new Gender(id, map.get(id));
		return gender;
	}

	public static Gender[] getGenders() {
		if (map == null)
			initializeMap();

		if (map == null || map.size() == 0) {
			return new Gender[0];
		}

		Gender[] genders = new Gender[map.size()];
		int i = 0;
		for (int key : map.keySet()) {
			genders[i++] = getGender(key);
		}
		return genders;
	}

	public static Select getSelect(int defGenderId) {
		Gender[] genders = getGenders();
		Select select = new Select();
		select.setId("genderId");
		for (Gender gender : genders) {
			select.addOption(gender.getId(), gender.getName());
		}
		select.select(defGenderId);
		return select;
	}

	private static void initializeMap() {
		map = new HashMap<Integer, String>();
		String query = "SELECT gender_id,gender FROM rcr_gender";
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.execute(query);
			while (cnn.next()) {
				map.put(cnn.getInt(1), cnn.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
			map = null;
		}
	}

	public static void main(String[] args) {
		Gender[] gender = getGenders();
		System.out.println("gender size: " + gender.length);
		System.out.println("each gender: ");
		for (Gender g : gender) {
			System.out.println(g.getId() + " " + g.getName());
		}
	}
}
