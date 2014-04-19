package qinyuan.lib.web.html;

import java.util.LinkedList;

public class Select extends BodyElement {

	private LinkedList<String[]> options = new LinkedList<String[]>();
	private String selectedValue;

	public Select appendOption(int value, Object text) {
		return appendOption(String.valueOf(value), text);
	}

	public Select appendOption(Object value, Object text) {
		options.addFirst(new String[] { value.toString(), text.toString() });
		return this;
	}

	public Select addOption(int value, Object text) {
		return addOption(String.valueOf(value), text);
	}

	public Select addOption(Object value, Object text) {
		options.add(new String[] { value.toString(), text.toString() });
		return this;
	}

	public Select select(int value) {
		return select(String.valueOf(value));
	}

	public Select select(String value) {
		selectedValue = value;
		return this;
	}

	@Override
	public Select setId(String id) {
		super.setId(id);
		return setAttr("name", id);
	}

	@Override
	public Select setAttr(String name, String value) {
		return (Select) super.setAttr(name, value);
	}

	@Override
	public Select setClass(String style) {
		return (Select) super.setClass(style);
	}

	@Override
	public String getBody() {
		StringBuilder o = new StringBuilder();
		for (String[] option : options) {
			o.append("<option value='" + option[0] + "'");
			if (option[0].equals(selectedValue)) {
				o.append(" selected='selected'");
			}
			o.append(">" + option[1] + "</option>");
		}
		return o.toString();
	}

	@Override
	public String getTagName() {
		return "select";
	}
}