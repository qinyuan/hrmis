package qinyuan.lib.web.html;

public class TableData extends BodyElement {

	private String text;
	private boolean isHead;

	public TableData() {
		this(false);
	}

	public TableData(boolean isHead) {
		this.isHead = isHead;
	}

	public TableData setText(String text) {
		this.text = text;
		return this;
	}

	@Override
	public String getBody() {
		return text;
	}

	@Override
	public String getTagName() {
		return isHead ? "th" : "td";
	}

	@Override
	public TableData setAttr(String name, String value) {
		return (TableData) super.setAttr(name, value);
	}

	@Override
	public TableData setClass(String style) {
		return (TableData) super.setClass(style);
	}

	@Override
	public TableData setId(String id) {
		return (TableData) super.setId(id);
	}

}
