package qinyuan.hrmis.domain.user;

import java.util.ArrayList;
import java.util.List;
import qinyuan.hrmis.domain.admin.Privilege;
import qinyuan.hrmis.domain.ass.AssDept;
import qinyuan.hrmis.domain.ass.AssResult;
import qinyuan.hrmis.domain.ass.AssResultUtil;
import qinyuan.hrmis.domain.ass.AssScore;
import qinyuan.hrmis.domain.ass.AssTpl;
import qinyuan.hrmis.domain.ass.AssValue;
import qinyuan.hrmis.lib.data.HRMISConn;
import qinyuan.lib.file.ExcelCreator;
import qinyuan.lib.file.TempExcelCreator;
import qinyuan.lib.web.html.Select;
import qinyuan.lib.web.html.Table;
import qinyuan.lib.web.html.TableRow;

public class AssDealer extends AssParticipant {

	private AssDept[] depts;
	private AssDept currentDept;

	protected AssDealer(int userId, String username, String nickname,
			Privilege[] privileges) {
		super(userId, username, nickname, privileges);

		depts = createAssDept(userId);
		if (depts.length > 0) {
			currentDept = depts[0];
		}

		super.setPriDesc("考核管理");
	}

	public String createExcelAsCheckee(String excelPath) {
		try {
			AssScore[] scores = af.getScores();
			AssValue[] values = af.getValues();
			return createExcel(excelPath, scores, values, false,
					getReportHead(false));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String createExcelAsChecker(String excelPath) {
		try {
			AssScore[] scores = af.getScores();
			AssValue[] values = af.getValues();
			return createExcel(excelPath, scores, values, true,
					getReportHead(true));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public AssDept[] getAssDept() {
		if (depts == null) {
			depts = createAssDept(getUserid());
		}
		return depts;
	}

	public AssDept getCurrentDept() {
		if (currentDept == null) {
			currentDept = depts[0];
		}
		return currentDept;
	}

	public int getCurrentDeptId() throws Exception {
		if (getCurrentDept() == null) {
			throw new Exception("no department set");
		} else {
			return getCurrentDept().getId();
		}
	}

	public String getCurrentDeptName() throws Exception {
		if (getCurrentDept() == null) {
			throw new Exception("no department set");
		} else {
			return getCurrentDept().getName();
		}
	}

	public int getDeptCount() {
		return depts.length;
	}

	public String getDeptCheckBoxes() {
		try (HRMISConn cnn = new HRMISConn()) {
			StringBuilder o = new StringBuilder("<fieldset>");
			o.append("<legend>" + getUsername() + "(" + getNickname()
					+ ")</legend>");

			final String query = "SELECT d.deptid,d.deptname,u.userid FROM ass_dept AS d "
					+ "LEFT JOIN (SELECT * FROM ass_dealer WHERE userid=?) AS u USING(deptid)";
			cnn.prepare(query).setInt(1, getUserid()).execute();
			int colCount = 0;
			o.append("<table>");
			while (cnn.next()) {
				if (colCount == 0) {
					o.append("<tr>");
				}
				o.append("<td><input type='checkbox' name='user" + getUserid()
						+ "' value='" + cnn.getInt(1) + "'");
				if (cnn.getString(3) != null) {
					o.append(" checked='checked'");
				}
				o.append(" />" + cnn.getString(2) + "</td>");

				colCount++;
				if (colCount == 5) {
					colCount = 0;
					o.append("</tr>");
				}
			}
			o.append("</table>");
			cnn.close();

			o.append("</fieldset>");
			return o.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public Select getDeptSelect() throws Exception {
		Select select = new Select().setId("deptId").select(getCurrentDeptId());
		for (AssDept dept : getAssDept()) {
			select.addOption(dept.getId(), dept.getName());
		}
		return select;
	}

	public String getReportHead(boolean isChecker) throws Exception {
		int year = getMon().getYear();
		int month = getMon().getMon();
		StringBuilder s = new StringBuilder(getCurrentDeptName());
		s.append(isChecker ? "对其他部门的考核" : "考核结果");
		s.append(" ---- " + year + "年" + month + "月（考核");
		s.append(month == 1 ? 12 : (month - 1));
		s.append("月）");
		return s.toString();
	}

	public String getScoresTableAsCheckee() throws Exception {
		AssScore[] scores = af.setCheckeeId(getCurrentDeptId())
				.setCheckerId(-1).getScores();
		return toTableAsCheckee(AssScore.getTableHeadAsCheckee(), scores,
				AssScore.getSumTableRow(scores));
	}

	public String getScoresTableAsChecker() throws Exception {
		AssScore[] scores = af.setCheckeeId(-1)
				.setCheckerId(getCurrentDeptId()).getScores();
		return toTableAsChecker(AssScore.getTableHeadAsChecker(), scores);
	}

	public String getValuesTableAsCheckee() throws Exception {
		AssValue[] values = af.setCheckeeId(getCurrentDeptId())
				.setCheckerId(-1).getValues();
		return toTableAsCheckee(AssValue.getTableHeadAsCheckee(), values,
				AssValue.getSumTableRow(values));
	}

	public String getValuesTableAsChecker() throws Exception {
		AssValue[] values = af.setCheckeeId(-1)
				.setCheckerId(getCurrentDeptId()).getValues();
		return toTableAsChecker(AssValue.getTableHeadAsChecker(), values);
	}

	public void setCurrentDept(int deptId) {
		for (AssDept dept : depts) {
			if (dept.getId() == deptId) {
				currentDept = dept;
				return;
			}
		}
	}

	public boolean updateAssScore(int id, String data, float result) {
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare("SELECT checker FROM ass_score WHERE id=?")
					.setInt(1, id).execute();
			cnn.next();
			if (cnn.getInt(1) == getCurrentDeptId()) {
				return AssScore.update(id, data, result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateAssValue(int id, String data, float result) {
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare("SELECT checker FROM ass_value WHERE id=?")
					.setInt(1, id).execute();
			cnn.next();
			if (cnn.getInt(1) == getCurrentDeptId()) {
				return AssValue.update(id, data, result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static AssDealer newAssDealer(User user) {
		return new AssDealer(user.getUserid(), user.getUsername(),
				user.getNickname(), user.getPrivilege());
	}

	public static AssDealer[] getAssDealers() {
		final String query = "SELECT userId FROM user_pri WHERE pri_id=?";
		try (HRMISConn cnn = new HRMISConn()) {
			cnn.prepare(query).setInt(1, Privilege.ASS_PRI_ID).execute();
			int count = cnn.getRowCount();
			if (count == 0) {
				return new AssDealer[0];
			}
			AssDealer[] assDealers = new AssDealer[count];
			User user;
			for (int i = 0; i < count; i++) {
				cnn.next();
				user = User.newInstance(cnn.getInt(1));
				assDealers[i] = AssDealer.newAssDealer(user);
			}
			return assDealers;
		} catch (Exception e) {
			e.printStackTrace();
			return new AssDealer[0];
		}
	}

	public static String[] getHeadStrArr(boolean isChecker, boolean isScore) {
		int len = isScore ? 7 : 8;
		String[] strArr = new String[len];
		strArr[0] = isChecker ? "被考核" : "考核部门";
		strArr[1] = "项目";
		strArr[2] = "指标";
		if (isScore) {
			strArr[3] = "分值";
		} else {
			strArr[3] = "单位";
			strArr[4] = "备注";
		}
		strArr[len - 3] = "评分标准";
		strArr[len - 2] = "数据";
		strArr[len - 1] = "结果";
		return strArr;
	}

	private static String toTableAsCheckee(TableRow head, AssTpl[] items,
			TableRow sumRow) {
		if (items == null || items.length == 0)
			return "";

		Table table = new Table().add(head);
		for (AssTpl item : items) {
			table.add(item.toTableRowAsCheckee());
		}
		table.add(sumRow);
		return table.toString().replaceAll("\r\n", "\r\n<br />");
	}

	private static String toTableAsChecker(TableRow head, AssTpl[] items) {
		if (items == null || items.length == 0)
			return "";

		Table table = new Table().add(head);
		for (AssTpl item : items) {
			table.add(item.toTableRowAsChecker());
		}
		return table.toString().replaceAll("\r\n", "\r\n<br />");
	}

	private static AssDept[] createAssDept(int userId) {
		try (HRMISConn cnn = new HRMISConn()) {
			final String query = "SELECT deptid FROM ass_dealer WHERE userid=?";
			cnn.prepare(query).setInt(1, userId).execute();
			int count = cnn.getRowCount();

			if (count == 0) {
				return new AssDept[0];
			}

			AssDept[] depts = new AssDept[count];
			for (int i = 0; i < count; i++) {
				cnn.next();
				depts[i] = AssDept.getInstance(cnn.getInt(1));
			}
			return depts;
		} catch (Exception e) {
			e.printStackTrace();
			return new AssDept[0];
		}
	}

	private static String createExcel(String excelPath, AssScore[] scores,
			AssValue[] values, boolean isChecker, String sheetHead) {
		List<String[]> list = new ArrayList<String[]>();
		String[] strArr;
		try {
			// convert scores data to String array
			if (scores.length > 0) {
				list.add(getHeadStrArr(isChecker, true));
				for (AssScore score : scores) {
					strArr = new String[7];
					strArr[0] = isChecker ? score.getCheckeeName() : score
							.getCheckerName();
					strArr[1] = score.getItem();
					strArr[2] = score.getTarget();
					strArr[3] = String.valueOf(score.getWeight());
					strArr[4] = score.getFormula();
					strArr[5] = score.getData();
					strArr[6] = String.valueOf(score.getResult());
					list.add(strArr);
				}
				if (!isChecker) {
					list.add(createSumArr(scores, 7));
				}
			}

			// convert values data to String array
			if (values.length > 0) {
				if (scores.length > 0) {
					list.add(new String[0]);
				}
				list.add(getHeadStrArr(isChecker, false));
				for (AssValue value : values) {
					strArr = new String[8];
					strArr[0] = isChecker ? value.getCheckeeName() : value
							.getCheckerName();
					strArr[1] = value.getItem();
					strArr[2] = value.getTarget();
					strArr[3] = value.getUnit();
					strArr[4] = value.getOther();
					strArr[5] = value.getFormula();
					strArr[6] = value.getData();
					strArr[7] = String.valueOf(value.getResult());
					list.add(strArr);
				}
				if (!isChecker) {
					list.add(createSumArr(values, 7));
				}
			}

			// create excel
			TempExcelCreator tec = new TempExcelCreator();
			tec.setVerticalAlign(ExcelCreator.TOP);
			tec.setAlign(ExcelCreator.LEFT);
			tec.setBorder(ExcelCreator.THIN);
			tec.setMaxColWidth(10000);
			tec.setSheetHead(sheetHead);
			tec.setStrArrList(list);
			if (tec.create(excelPath)) {
				return tec.getFullFileName();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String[] createSumArr(AssResult[] results, int colCount) {
		String[] strArr = new String[colCount];
		strArr[0] = "总计";
		for (int i = 1; i < colCount - 1; i++) {
			strArr[i] = "";
		}
		strArr[colCount - 1] = String.valueOf(AssResultUtil
				.getResultSum(results));
		return strArr;
	}
}
