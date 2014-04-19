package qinyuan.hrmis.domain.att.manager;

import java.util.ArrayList;
import java.util.List;
import qinyuan.lib.file.ExcelCreator;
import qinyuan.lib.file.TempExcelCreator;
import qinyuan.lib.web.html.Table;
import qinyuan.lib.web.html.TableRow;

public class NorLeaveUtil {

	private static LeaveType[] lts = LeaveType.getNorInstances();

	public static Table getHeadTable() {
		Table table = new Table();
		TableRow th = new TableRow(true).add("工号，姓名");
		for (LeaveType lt : lts) {
			th.add(lt.getDesc());
		}
		table.add(th);
		return table;
	}

	public static String[] getHeadStrArr() {
		String[] strs = new String[lts.length + 2];
		strs[0] = "工号";
		strs[1] = "姓名";
		int i = 2;
		for (LeaveType lt : lts) {
			strs[i++] = lt.getDesc();
		}
		return strs;
	}

	public static String createExcel(String folder, int deptId,
			String startDate, String endDate) {
		List<String[]> list = new ArrayList<String[]>();
		list.add(getHeadStrArr());
		List<NorLeave> nlList = NorLeaveFactory.getNorLeaves(deptId, startDate,
				endDate);
		double dayCount;
		for (NorLeave nl : nlList) {
			String[] strs = new String[lts.length + 2];
			strs[0] = nl.getBadgenumber();
			strs[1] = nl.getUsername();
			for (int i = 0; i < lts.length; i++) {
				dayCount = nl.getDayCount(lts[i].getId());
				strs[i + 2] = dayCount == 0 ? "" : String.valueOf(dayCount);
			}
			list.add(strs);
		}
		double[] sums = getSums(nlList);
		String[] strs = new String[lts.length + 2];
		strs[0] = "";
		strs[1] = "总计";
		for (int i = 0; i < sums.length; i++) {
			strs[i + 2] = sums[i] == 0 ? "" : String.valueOf(sums[i]);
		}
		list.add(strs);

		TempExcelCreator tec = new TempExcelCreator();
		tec.setAlign(ExcelCreator.CENTER);
		tec.setBorder(ExcelCreator.THIN);
		tec.setStrArrList(list);
		tec.create(folder);
		return tec.getFullFileName();
	}

	public static Table getBodyTable(int deptId, String startDate,
			String endDate) {
		Table table = new Table();
		TableRow tr;
		double dayCount;
		List<NorLeave> list = NorLeaveFactory.getNorLeaves(deptId, startDate,
				endDate);
		for (NorLeave nl : list) {
			tr = new TableRow().add(nl.getBadgenumber());
			tr.add("<a href='nor-leave-detail.jsp?userId=" + nl.getUserId()
					+ "' target='_blank'>" + nl.getUsername() + "</a>");
			for (LeaveType lt : lts) {
				dayCount = nl.getDayCount(lt.getId());
				if (dayCount == 0) {
					tr.add("");
				} else {
					tr.add(dayCount);
				}
			}
			table.add(tr);
		}
		double[] sums = getSums(list);
		tr = new TableRow().add("").add("总计");
		for (double d : sums) {
			if (d == 0) {
				tr.add("");
			} else {
				tr.add(d);
			}
		}
		table.add(tr);

		return table;
	}

	private static double[] getSums(List<NorLeave> list) {
		double[] sums = new double[lts.length];
		for (NorLeave nl : list) {
			for (int i = 0; i < lts.length; i++) {
				sums[i] += nl.getDayCount(lts[i].getId());
			}
		}
		return sums;
	}

	public static void main(String[] args) {
		String startDate = "2013-01-01";
		String endDate = "2013-07-01";
		System.out.println(getBodyTable(51, startDate, endDate));
		for (String str : getHeadStrArr()) {
			System.out.println(str);
		}
	}
}
