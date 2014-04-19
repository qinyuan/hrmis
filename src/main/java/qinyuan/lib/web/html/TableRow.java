package qinyuan.lib.web.html;

import java.util.LinkedList;

public class TableRow extends BodyElement {

	private LinkedList<TableData> tdList = new LinkedList<TableData>();
	private boolean isHead = false;
	private String splitStr = ",|，";

	public TableRow() {
		this(false);
	}

	public TableRow(boolean isHead) {
		this.isHead = isHead;
	}

	public TableRow add(TableData td) {
		if (td == null)
			add("");

		tdList.add(td);
		return this;
	}

	public TableRow add(TableRow tableRow) {
		if (tableRow == null)
			add("");

		tdList.addAll(tableRow.tdList);
		return this;
	}

	public TableRow add(Object obj) {
		if (obj == null)
			return add("");

		String str = obj.toString();
		if (splitStr == null) {
			return add(new TableData(isHead).setText(str));
		} else {
			for (String text : str.split(splitStr)) {
				add(new TableData(isHead).setText(text));
			}
			return this;
		}
	}

	public TableRow append(Object obj) {
		if (obj == null)
			return add("");

		String str = obj.toString();
		if (splitStr == null) {
			return append(new TableData(isHead).setText(str));
		} else {
			String[] strs = str.split(splitStr);
			for (int i = strs.length - 1; i >= 0; i--) {
				append(new TableData(isHead).setText(strs[i]));
			}
			return this;
		}
	}

	public TableRow append(TableData td) {
		if (td == null)
			add("");

		tdList.addFirst(td);
		return this;
	}

	@Override
	public String getBody() {
		StringBuilder o = new StringBuilder();
		for (TableData tableData : tdList) {
			o.append(tableData);
		}
		return o.toString();
	}

	@Override
	public String getTagName() {
		return "tr";
	}

	public TableRow setSplitStr(String str) {
		splitStr = str;
		return this;
	}

	@Override
	public TableRow setAttr(String name, String value) {
		return (TableRow) super.setAttr(name, value);
	}

	@Override
	public TableRow setClass(String style) {
		return (TableRow) super.setClass(style);
	}

	@Override
	public TableRow setId(String id) {
		return (TableRow) super.setId(id);
	}
}
