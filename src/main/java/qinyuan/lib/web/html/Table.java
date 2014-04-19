package qinyuan.lib.web.html;

import java.util.ArrayList;
import java.util.List;

public class Table extends BodyElement {

	private List<TableRow> trs = new ArrayList<TableRow>();

	public Table add(TableRow tableRow) {
		trs.add(tableRow);
		return this;
	}

	public Table add(Table table) {
		trs.addAll(table.trs);
		return this;
	}

	@Override
	public Table setAttr(String name, String value) {
		return (Table) super.setAttr(name, value);
	}

	@Override
	public Table setClass(String style) {
		return (Table) super.setClass(style);
	}

	@Override
	public Table setId(String id) {
		return (Table) super.setId(id);
	}

	@Override
	public String getBody() {
		StringBuilder o = new StringBuilder();
		for (TableRow tableRow : trs) {
			o.append(tableRow);
		}
		return o.toString();
	}

	@Override
	public String getTagName() {
		return "table";
	}
	
	public static void main(String[] args) {
		System.out.println(new Table().setClass("style").setId("id"));
	}
}
